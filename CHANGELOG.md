# Changelog

## [v0.1.4](https://github.com/priyanshuchawda/retenqo/releases/tag/v0.1.4) — 2026-11-25

- Fixed SQLCipher passphrase lifetime for pooled Room/WAL connections without disabling database encryption or replacing user data.
- Verified the signed ARM64 APK against the pinned publisher certificate and upgraded the existing production installation on Android 16 while preserving app data.
- Passed the SQLCipher instrumentation suite (6/6) and launched successfully on the connected Android 16 device.

See [verification evidence](docs/TESTING.md).
