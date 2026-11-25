# Security engineering review

**Release:** [v0.1.4](https://github.com/priyanshuchawda/retenqo/releases/tag/v0.1.4)  
**Evidence:** source inspection at submission baseline `834b04e62dedc2cf412c7f5fc2c967489ecd9411` and recorded release verification.

Retenqo protects personal context through explicit capture consent, local AI inference, protected persistence, authenticated recovery and verified software distribution. This review maps security properties to implementation and regression evidence; it is not an independent penetration test.

| Security property | Implementation | Verification evidence |
| --- | --- | --- |
| Capture starts paused and enabled sources are explicit | [NotificationCaptureSettings.kt](../android/mneme/app/src/main/java/com/mneme/app/notif/NotificationCaptureSettings.kt) | [NotificationCaptureSettingsTest.kt](../android/mneme/app/src/test/java/com/mneme/app/notif/NotificationCaptureSettingsTest.kt) |
| Queue filtering removes disabled/revoked and unknown sources | [NotificationCaptureSettings.kt](../android/mneme/app/src/main/java/com/mneme/app/notif/NotificationCaptureSettings.kt) | [NotificationConsentQueueTest.kt](../android/mneme/app/src/test/java/com/mneme/app/notif/NotificationConsentQueueTest.kt): stable IDs, legacy normalization, revoked-source filtering |
| Persisted database is encrypted; key survives store recreation | [DatabaseKeyStore.kt](../android/mneme/app/src/main/java/com/mneme/app/data/DatabaseKeyStore.kt), [MnemeDatabase.kt](../android/mneme/app/src/main/java/com/mneme/app/data/MnemeDatabase.kt) | [EncryptedDatabaseMigrationTest.kt](../android/mneme/app/src/androidTest/java/com/mneme/app/data/EncryptedDatabaseMigrationTest.kt) |
| Plaintext migration preserves records; wrong keys rejected | [PlaintextDatabaseMigrator.kt](../android/mneme/app/src/main/java/com/mneme/app/data/PlaintextDatabaseMigrator.kt) | Same migration suite; row preservation, wrong-key rejection and missing-key behavior |
| Secure delete and concurrent encrypted connections | [MnemeDatabase.kt](../android/mneme/app/src/main/java/com/mneme/app/data/MnemeDatabase.kt) | [MnemeDatabaseDeviceTest.kt](../android/mneme/app/src/androidTest/java/com/mneme/app/data/MnemeDatabaseDeviceTest.kt) |
| Backup ciphertext authenticated; invalid passwords and tampering rejected | [BackupCrypto.kt](../android/mneme/app/src/main/java/com/mneme/app/data/BackupCrypto.kt) | [BackupCryptoTest.kt](../android/mneme/app/src/test/java/com/mneme/app/data/BackupCryptoTest.kt), [BackupCryptoDeviceTest.kt](../android/mneme/app/src/androidTest/java/com/mneme/app/data/BackupCryptoDeviceTest.kt) |
| Model answer references validated against available evidence | [AskAnswerGuard.kt](../android/mneme/app/src/main/java/com/mneme/app/ai/AskAnswerGuard.kt) | [AskAnswerGuardTest.kt](../android/mneme/app/src/test/java/com/mneme/app/ai/AskAnswerGuardTest.kt) |
| APK identity, digest and pinned signer checked before distribution | [release-validation.ts](../web/scripts/release-validation.ts), [sync-release.ts](../web/scripts/sync-release.ts) | [release-sync.test.ts](../web/tests/unit/release-sync.test.ts), [apk-metadata.test.ts](../web/tests/unit/apk-metadata.test.ts) |
| Browser security policy | [HTTP headers](../web/public/_headers) | [Browser suite](../web/tests/e2e/site.spec.ts), [website threat model](../web/docs/THREAT_MODEL.md) |

## Resolved engineering finding: encrypted database connection lifecycle

SQLCipher retains the passphrase array for opening additional pooled WAL connections. Retenqo now retains the required array for the database lifetime and clears it on close, while clearing the transient decrypted key after initialization. The concurrent-connection regression, six instrumentation passes and successful signed production upgrade on API 36 are recorded in [Testing](TESTING.md). Database encryption was preserved.

## Scope

Application encryption applies to Room records and new encrypted backup exports; queue preferences are app-private but not application-encrypted. Queue filtering tests do not establish race-free revocation during inference. The [threat model](THREAT_MODEL.md) defines trust boundaries and the [roadmap](ROADMAP.md) tracks further hardening. Test references identify executable coverage; recorded run totals and device scope are maintained in [Testing](TESTING.md).
