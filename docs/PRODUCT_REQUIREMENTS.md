# Product requirements and acceptance criteria

Retenqo addresses fragmented digital context and forgotten commitments through a local-first Android memory assistant. The following requirements are derived from the current product design; they are not a verbatim competition challenge statement.

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
