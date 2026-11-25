# Product requirements and acceptance criteria

## Self-defined Open Innovation problem

Everyday digital context is fragmented across notifications, messages, images and documents. People lose time reconstructing important information, miss commitments buried in conversations and struggle to follow through. Existing search and task tools often require manual organization, while cloud-based AI may expose sensitive personal context or require connectivity.

**Open Innovation challenge:** Build a privacy-first, mobile personal-intelligence system that turns user-authorized context into structured, retrievable memories and reviewable commitments; supports evidence-linked recall and timely follow-through; and uses resource-aware on-device processing while preserving user consent and decision authority.

## Intended outcomes

Help users recover important context, recognize commitments, review source evidence and manage follow-through with less manual organization. These are intended product outcomes, not measured user-impact statistics.

## Acceptance criteria

The following requirements translate the project's challenge into testable engineering behavior.

| ID | Product requirement | Acceptance criterion and evidence |
| --- | --- | --- |
| R1 | Permission-controlled context intake | Capture disabled sources must be filtered; selected media is explicitly authorized. [Capture architecture](ARCHITECTURE.md), [consent tests](../android/mneme/app/src/test/java/com/mneme/app/notif/NotificationConsentQueueTest.kt). |
| R2 | Structured personal memory | Extract actionable context and store source-linked records; deterministic extraction tests assert expected fields. [RulesExtractTest.kt](../android/mneme/app/src/test/java/com/mneme/app/ai/RulesExtractTest.kt). |
| R3 | Relevant recall | Rank matching memory candidates and validate answer references. [MemoryLexicalSearchTest.kt](../android/mneme/app/src/test/java/com/mneme/app/data/MemoryLexicalSearchTest.kt), [AskAnswerGuardTest.kt](../android/mneme/app/src/test/java/com/mneme/app/ai/AskAnswerGuardTest.kt). |
| R4 | Local intelligence | With a compatible installed model, answer a general question without network connectivity. [Recorded verification](TESTING.md). |
| R5 | Human-controlled follow-through | Support review, acceptance, reminders and completion evidence without automatically closing uncertain commitments. [Traceability](REQUIREMENTS_TRACEABILITY.md). |
| R6 | Protected persistence and recovery | Encrypt Room storage and new backup exports; reject wrong keys or tampered backup ciphertext. [Security review](SECURITY_REVIEW.md). |
| R7 | Resource-aware processing | Bound input, queue, candidate and generation work; measure representative latency and memory. [Performance](PERFORMANCE.md). |
| R8 | Accessible operation | Expose meaningful controls and verify keyboard, assistive technology and responsive operation in the relevant product surfaces. [Accessibility](ACCESSIBILITY.md). |
| R9 | Trustworthy distribution | Match the published APK identity, digest and publisher certificate; verify installation and upgrade on supported devices. [Testing](TESTING.md). |

The acceptance criteria define expected behavior, not a declaration that every end-to-end scenario has passed. The [evaluation guide](EVALUATION_GUIDE.md) maps the six evaluation criteria to implemented features and evidence.

## Open Innovation traceability IDs

These IDs describe Retenqo's self-defined problem; the selected Hack2Skill submission must be checked by the owner before claiming organizer alignment.

| ID | Requirement | Production path | Regression evidence |
| --- | --- | --- | --- |
| PS-01 | User chooses notification sources and can revoke them | [NotificationCaptureSettings.kt](../android/mneme/app/src/main/java/com/mneme/app/notif/NotificationCaptureSettings.kt), [MnemeNotificationListener.kt](../android/mneme/app/src/main/java/com/mneme/app/notif/MnemeNotificationListener.kt) | [NotificationCaptureSettingsTest.kt](../android/mneme/app/src/test/java/com/mneme/app/notif/NotificationCaptureSettingsTest.kt), [NotificationConsentQueueTest.kt](../android/mneme/app/src/test/java/com/mneme/app/notif/NotificationConsentQueueTest.kt) |
| PS-02 | Classify actionable commitments and dates | [PulsePipeline.kt](../android/mneme/app/src/main/java/com/mneme/app/pulse/PulsePipeline.kt), [DueDateGuess.kt](../android/mneme/app/src/main/java/com/mneme/app/ai/DueDateGuess.kt) | [RulesExtractTest.kt](../android/mneme/app/src/test/java/com/mneme/app/ai/RulesExtractTest.kt), [DueDateGuessTest.kt](../android/mneme/app/src/test/java/com/mneme/app/ai/DueDateGuessTest.kt) |
| PS-03 | Preserve source evidence with saved memories | [NoteRepository.kt](../android/mneme/app/src/main/java/com/mneme/app/data/NoteRepository.kt), [MnemeDatabase.kt](../android/mneme/app/src/main/java/com/mneme/app/data/MnemeDatabase.kt) | [IngestSourcesTest.kt](../android/mneme/app/src/test/java/com/mneme/app/data/IngestSourcesTest.kt), [encrypted migration tests](../android/mneme/app/src/androidTest/java/com/mneme/app/data/EncryptedDatabaseMigrationTest.kt) |
| PS-04 | Retrieve relevant old details and ground factual answers | [NoteRepository.kt](../android/mneme/app/src/main/java/com/mneme/app/data/NoteRepository.kt), [MemoryAsk.kt](../android/mneme/app/src/main/java/com/mneme/app/ai/MemoryAsk.kt), [AskAnswerGuard.kt](../android/mneme/app/src/main/java/com/mneme/app/ai/AskAnswerGuard.kt) | [MemoryLexicalSearchTest.kt](../android/mneme/app/src/test/java/com/mneme/app/data/MemoryLexicalSearchTest.kt), [AskAnswerGuardTest.kt](../android/mneme/app/src/test/java/com/mneme/app/ai/AskAnswerGuardTest.kt) |
| PS-05 | Schedule local reminders for confirmed commitments | [DueDigest.kt](../android/mneme/app/src/main/java/com/mneme/app/remind/DueDigest.kt), [DueReminderScheduler.kt](../android/mneme/app/src/main/java/com/mneme/app/remind/DueReminderScheduler.kt) | [DueDigestTest.kt](../android/mneme/app/src/test/java/com/mneme/app/remind/DueDigestTest.kt) |
| PS-06 | Suggest completion only with evidence and user approval | [FollowThroughMatcher.kt](../android/mneme/app/src/main/java/com/mneme/app/data/FollowThroughMatcher.kt), [FollowThroughDao.kt](../android/mneme/app/src/main/java/com/mneme/app/data/FollowThroughDao.kt) | [FollowThroughMatcherTest.kt](../android/mneme/app/src/test/java/com/mneme/app/data/FollowThroughMatcherTest.kt), [MnemeDatabaseDeviceTest.kt](../android/mneme/app/src/androidTest/java/com/mneme/app/data/MnemeDatabaseDeviceTest.kt) |
| PS-07 | Keep personal memory inference local and storage protected | [LlmBridge.kt](../android/mneme/app/src/main/java/com/mneme/app/ai/LlmBridge.kt), [MnemeDatabase.kt](../android/mneme/app/src/main/java/com/mneme/app/data/MnemeDatabase.kt), [BackupCrypto.kt](../android/mneme/app/src/main/java/com/mneme/app/data/BackupCrypto.kt) | [BackupCryptoTest.kt](../android/mneme/app/src/test/java/com/mneme/app/data/BackupCryptoTest.kt), [encrypted database tests](../android/mneme/app/src/androidTest/java/com/mneme/app/data/EncryptedDatabaseMigrationTest.kt) |
