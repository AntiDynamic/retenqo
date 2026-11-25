# Architecture decision records

This retrospective decision record explains the design reflected in the current source; it does not claim that each alternative was separately benchmarked.

| ADR | Decision and rationale | Trade-off and evidence |
| --- | --- | --- |
| ADR-001 | Local-first inference rather than remote personal-context processing; reduces cloud data exposure and network dependence. | Local compute and model provisioning; [AI implementation](AI_IMPLEMENTATION.md), [offline models](OFFLINE_MODELS.md). |
| ADR-002 | Native Kotlin/Compose rather than a web wrapper for Android permission, notification, lifecycle and storage integration. | Platform-specific implementation; [architecture](ARCHITECTURE.md). |
| ADR-003 | Quantized GGUF CPU inference with pinned native runtime rather than a larger default model. | Device-dependent latency and accuracy; [model guide](OFFLINE_MODELS.md), [performance](PERFORMANCE.md). |
| ADR-004 | Deterministic extraction before optional generation; avoids invoking a model for simple structured cues. | Rule coverage requires regression fixtures; [RulesExtract.kt](../android/mneme/app/src/main/java/com/mneme/app/ai/RulesExtract.kt), [RulesExtractTest.kt](../android/mneme/app/src/test/java/com/mneme/app/ai/RulesExtractTest.kt). |
| ADR-005 | Bounded lexical candidate ranking rather than production learned embedding search. | Predictable work, with recent-candidate recall boundary; [NoteRepository.kt](../android/mneme/app/src/main/java/com/mneme/app/data/NoteRepository.kt), [MemoryLexicalSearchTest.kt](../android/mneme/app/src/test/java/com/mneme/app/data/MemoryLexicalSearchTest.kt). |
| ADR-006 | SQLCipher Room and Keystore-wrapped key rather than plaintext database persistence. | Key lifecycle and recovery require explicit handling; [security review](SECURITY_REVIEW.md). |
| ADR-007 | Reviewable commitment state and explicit approval rather than automatic task closure. | Additional confirmation interaction; [FollowThroughMatcher.kt](../android/mneme/app/src/main/java/com/mneme/app/data/FollowThroughMatcher.kt), [traceability](REQUIREMENTS_TRACEABILITY.md). |
| ADR-008 | WorkManager and bounded intake rather than unrestricted background processing. | Queue capacity and cancellation require testing; [NotifIngestWorker.kt](../android/mneme/app/src/main/java/com/mneme/app/notif/NotifIngestWorker.kt). |
| ADR-009 | Passphrase-derived authenticated backup encryption rather than raw export by default. | Password and restore handling; [BackupCrypto.kt](../android/mneme/app/src/main/java/com/mneme/app/data/BackupCrypto.kt), [BackupCryptoTest.kt](../android/mneme/app/src/test/java/com/mneme/app/data/BackupCryptoTest.kt). |
| ADR-010 | Static Astro site and verified APK distribution rather than a dynamic personal-data backend. | Distribution metadata must be synchronized with releases; [website architecture](../web/docs/ARCHITECTURE.md). |

Update affected decisions when architecture changes. Record alternatives, rationale, trade-offs and verification rather than attributing unmeasured benefits to a technology.
