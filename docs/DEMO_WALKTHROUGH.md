# Demo walkthrough

This scenario explains the native Android behavior with synthetic content. A browser walkthrough is available at `/demo`; it uses fixed sample responses and does not execute Android notification capture, modify app data, or run on-device inference.

## Synthetic scenario

Use only a test device/account and this sample notification:

> Can you send the presentation slides by Friday at 4 PM?

1. In Retenqo Settings, enable only the notification source used by the test. Grant Android Notification Access when prompted.
2. Deliver the sample message from a controlled test app/account. Do not treat test injection as a real WhatsApp delivery.
3. Open the captured suggestion. Check the source text and proposed deadline; leave it pending until the user approves it.
4. Approve the suggestion and confirm it appears in the task list with Friday, 4 PM. Correct or reject any incorrect extracted date before saving.
5. With network access disabled after model setup, ask `When are my slides due?`. Check the answer against its cited saved card. If the optional model is unavailable, record the actual fallback behavior rather than implying inference occurred.
6. Process `I haven't sent the slides` and verify no completion suggestion appears. Then process `I sent the presentation slides` and verify any suggestion points to the correct open task.
7. Confirm the suggestion explicitly. Verify the task changes to done, history records the user's decision, and its reminder is canceled only after the database update succeeds.
8. Restart the app and verify the task/history persist. Repeat with an unrelated task to check for a false match.

Automated unit tests inject synthetic evidence below the Android notification boundary. The source files and test names are linked in [requirements traceability](REQUIREMENTS_TRACEABILITY.md). The integration story above is a manual device scenario until an end-to-end Android test exercises all stages.

## Device recording checklist

Record the device model, Android version/API, app commit, installed APK version/signer, model availability and network state. Show source consent, review before save, an Ask answer with its source, a reminder, and the explicit completion confirmation. Do not record real messages, contacts, credentials, personal backups or model files. No new physical-device recording was produced for this branch.
