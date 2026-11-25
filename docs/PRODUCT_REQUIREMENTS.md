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
