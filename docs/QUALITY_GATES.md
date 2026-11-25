# Engineering quality gates

Use these acceptance gates for changes and release verification. Targets are requirements, not measured results. The actual record is maintained in [Testing](TESTING.md).

| Criterion | Gate |
| --- | --- |
| Code Quality | Focused modules, typed contracts, reproducible dependencies, no unexplained suppression; applicable format, lint, type and build checks pass. |
| Security | Consent, key lifecycle, input bounds, backup authentication and release identity regression tests pass; review storage and data-flow changes against [security review](SECURITY_REVIEW.md). |
| Efficiency | Bound inputs and background work; record latency and memory for affected hot paths using synthetic workloads and named devices. |
| Testing | Test happy paths, malformed/boundary inputs, negative cases and regressions. Report exact scope, pass/fail/skip totals and revision. Use the recorded baseline in [Testing](TESTING.md) before setting coverage thresholds; do not exclude meaningful logic to inflate results. |
| Accessibility | Verify existing Android labels, roles, actions and touch targets; test web keyboard paths, responsive layout and axe checks. Record assistive-technology coverage separately. |
| Problem Statement Alignment | Link every material feature to a [product requirement](PRODUCT_REQUIREMENTS.md), implementation and acceptance evidence. |

## Reproducible verification

Use the commands in [Testing](TESTING.md) and [website testing](../web/docs/TESTING.md). Generate compact coverage summaries for Kotlin and TypeScript when configured; report native code, UI and device coverage separately. Confirm test artifacts correspond to the evaluated source revision. Before publication, apply the [release process](RELEASE_PROCESS.md) and confirm the artifact digest and signer against the published APK.

Do not declare a gate passed solely because a test file or implementation exists.
