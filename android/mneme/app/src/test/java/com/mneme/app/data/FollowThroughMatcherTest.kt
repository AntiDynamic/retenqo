package com.mneme.app.data

import org.junit.Assert.assertNull
import org.junit.Assert.assertNotNull
import org.junit.Test

class FollowThroughMatcherTest {
    private val slides = NoteEntity(
        id = 7,
        title = "Send presentation slides",
        body = "Send the presentation slides by Friday",
        source = "test",
        createdAt = 1L,
        dueAt = null,
        contentHash = "slides",
        embedding = null,
        cardType = CardType.PROMISE.storage,
    )

    @Test
    fun doesNotSuggestCompletionForNegatedEvidence() {
        assertNull(FollowThroughMatcher.find("I haven't sent the slides", listOf(slides)))
        assertNull(FollowThroughMatcher.find("I have not completed the slides", listOf(slides)))
    }

    @Test
    fun doesNotSuggestCompletionForFutureEvidence() {
        assertNull(FollowThroughMatcher.find("I'll send you the slides tomorrow", listOf(slides)))
    }

    @Test
    fun doesNotSuggestCompletionForQuestionsConditionsOrQuotedClaims() {
        assertNull(FollowThroughMatcher.find("Did you send the slides?", listOf(slides)))
        assertNull(FollowThroughMatcher.find("If I sent the slides, let me know", listOf(slides)))
        assertNull(FollowThroughMatcher.find("He wrote, ‘I sent the slides’", listOf(slides)))
    }

    @Test
    fun acceptsPositiveCompletionEvidenceForReview() {
        assertNotNull(FollowThroughMatcher.find("I sent the presentation slides", listOf(slides)))
    }
}
