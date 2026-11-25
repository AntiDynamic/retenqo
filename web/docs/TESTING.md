# Website testing guide

From the repository's `web/` directory:

```bash
pnpm verify
pnpm test:e2e
pnpm audit
pnpm test:coverage
```

Unit tests live in [tests/unit](../tests/unit/) and browser tests in [tests/e2e](../tests/e2e/). They cover release metadata and validation, APK identity, synchronization failure preservation, navigation, theme switching, clipboard, download links, layout and accessibility.

The 25 November 2026 recorded local run passed 25/25 unit tests across four files, with 94.59% line, 93.75% statement, 92.85% branch and 100% function coverage in the configured library scope. The production build generated 12 static pages; lint, typecheck and dependency audit passed.

Latest recorded browser execution: 98 passed, 68 skipped and 2 flaky accessibility cases passed on retry. Earlier baseline: 100 passed, 68 skipped. Full axe scans ran in Chromium desktop/mobile; Firefox and WebKit had smoke coverage. See the [complete verification record](../../docs/TESTING.md) for revision scope, formatting outcome and coverage denominators. The [engineering audit](ENGINEERING_AUDIT.md) retains the earlier baseline.

Record actual commands, revision, browser project and skips. Local preview checks do not establish deployed security headers; production verification is recorded separately.
