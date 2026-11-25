package com.mneme.app.ai

import com.mneme.app.data.NoteEntity

data class GuardedAsk(
    val answer: String,
    val citations: List<NoteEntity>,
)

/**
 * Post-process Ask answers so we refuse instead of hallucinating.
 */
object AskAnswerGuard {
    const val REFUSE = "I don't have that in your memory."

    fun sanitize(raw: String, citations: List<NoteEntity>): GuardedAsk {
        val cleaned = stripNoise(raw)
        if (citations.isEmpty()) {
            return GuardedAsk(REFUSE, emptyList())
        }
        if (looksLikeRefuse(cleaned)) {
            return GuardedAsk(REFUSE, emptyList())
        }
        val citedIds = citationIds(cleaned)
        val availableIds = citations.indices.map { it + 1 }.toSet()
        if (citedIds.isEmpty() || citedIds.any { it !in availableIds }) {
            // Model claimed an answer without grounding — refuse
            return GuardedAsk(REFUSE, emptyList())
        }
        val citedText = citedIds.joinToString(" ") { id -> "${citations[id - 1].title} ${citations[id - 1].body}" }
        if (unsupportedTimeOrAmount(cleaned, citedText)) {
            return GuardedAsk(REFUSE, emptyList())
        }
        // Drop obviously invented multi-paragraph essays
        val oneLine = cleaned.lineSequence().firstOrNull { it.isNotBlank() }?.trim() ?: REFUSE
        val referenced = citedIds.map { citations[it - 1] }.distinctBy { it.id }
        return GuardedAsk(oneLine.take(400), referenced)
    }

    fun looksLikeRefuse(text: String): Boolean {
        val t = text.lowercase()
        return t.contains("don't have that in your memory") ||
            t.contains("do not have that in your memory") ||
            t.contains("not in your memory") ||
            t.contains("i don't know") ||
            t.contains("no information") ||
            t.contains("cannot find") ||
            t.contains("can't find")
    }

    fun hasCitationMarker(text: String): Boolean =
        Regex("""\[\d+]""").containsMatchIn(text)

    /** Returns distinct positive citation numbers in appearance order. */
    fun citationIds(text: String): List<Int> =
        Regex("""\[(\d+)]""").findAll(text)
            .mapNotNull { it.groupValues[1].toIntOrNull()?.takeIf { id -> id > 0 } }
            .distinct()
            .toList()

    /** Conservative check for exact time and currency facts asserted by a generated answer. */
    private fun unsupportedTimeOrAmount(answer: String, evidence: String): Boolean =
        timeFacts(answer).any { it !in timeFacts(evidence) } ||
            amountFacts(answer).any { it !in amountFacts(evidence) }

    private fun timeFacts(text: String): Set<Int> =
        Regex("""(?i)\b(\d{1,2})(?::([0-5]\d))?\s*(a\.?m\.?|p\.?m\.?)\b""")
            .findAll(text)
            .mapNotNull { match ->
                val hour = match.groupValues[1].toIntOrNull() ?: return@mapNotNull null
                val minute = match.groupValues[2].toIntOrNull() ?: 0
                val meridiem = match.groupValues[3].lowercase().firstOrNull() ?: return@mapNotNull null
                if (hour !in 1..12) null else ((hour % 12) + if (meridiem == 'p') 12 else 0) * 60 + minute
            }.toSet()

    private fun amountFacts(text: String): Set<String> =
        Regex("""(?i)(?:₹|rs\.?\s*)\s*([0-9][0-9,]*(?:\.[0-9]{1,2})?)""")
            .findAll(text)
            .map { match -> match.groupValues[1].replace(",", "").toBigDecimalOrNull()?.stripTrailingZeros()?.toPlainString().orEmpty() }
            .toSet()

    /** Accept an uncited answer only when its meaningful words and exact facts appear on screen. */
    fun screenAnswerSupported(answer: String, screenText: String): Boolean {
        if (answer.isBlank() || looksLikeRefuse(answer)) return false
        val screenTimes = timeFacts(screenText)
        if (timeFacts(answer).any { it !in screenTimes }) return false
        val words = Regex("[\\p{L}\\p{N}]+")
        val ignored = setOf("a", "an", "and", "at", "by", "for", "from", "i", "in", "is", "it", "me", "my", "of", "on", "the", "to", "we", "you")
        val answerWords = words.findAll(answer.lowercase()).map { it.value }.filterNot { it in ignored || it.toIntOrNull() != null }.toSet()
        val visibleWords = words.findAll(screenText.lowercase()).map { it.value }.toSet()
        return answerWords.isNotEmpty() && answerWords.all { it in visibleWords }
    }

    private fun stripNoise(raw: String): String =
        raw
            .replace(Regex("(?is)<think>.*?</think>"), " ")
            .replace(Regex("(?i)/no_think"), " ")
            .replace(Regex("""\s+"""), " ")
            .trim()
}
