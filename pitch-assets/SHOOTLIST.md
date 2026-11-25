# Shoot list — remaining demo proofs (you on the phone)

> Historical development record. Current implementation and release evidence: [engineering status](../docs/IMPLEMENTATION_STATUS.md), [verification](../docs/TESTING.md), [research index](../docs/RESEARCH_INDEX.md).

These cannot be fully automated: MediaProjection consent, airplane toggle, and Gallery are human taps. Film on an authorized Android 13+ ARM64 device and record the exact device/API and app revision. Do not claim device results from an illustrative mockup.

## A. Ephemeral Pulse (sensitive dummy → empty gallery)

**Prep**
1. Open Notes (or any dummy screen) with fake sensitive text, e.g. `OTP 4821 — dummy, not real`.
2. Do **not** use a real OTP, bank screenshot, or someone else's chat.

**Capture**
3. Open Retenqo → tap the Pulse FAB (mint circle, bottom-right).
4. Android / MIUI: **Start now** / allow screen capture.
5. Switch to the dummy screen (or Pulse captures whatever is visible — keep dummy in front).
6. Wait until Retenqo shows a **text card** (title/body from OCR). Screenshot: `06-pulse-card.png`.
7. Open **Gallery / Photos** → Recents. Confirm the capture is **not** there. Screenshot: `07-gallery-empty.png`.
8. If a screenshot landed in Gallery, delete it and note that as a bug for 30h (ephemeral path must unlink the bitmap).

**Why judges care:** novelty 20% — pixels die, commitment stays.

## B. Airplane Ask (“what do I owe?”)

1. Settings → Network → **Airplane on**. Wait until Wi-Fi and mobile are off.
2. Open Retenqo → Ask → type `what do I owe?` → **Ask memory**.
3. Screenshot the answer + cited cards: `08-airplane-owe.png`.
4. Optional second Ask: `When is Priya?` (only if demo seed cards exist). Screenshot: `09-airplane-priya.png`.
5. Airplane **off** when done.

**Why:** on-device / privacy. If it spins forever, the extractive fallback should still list due titles — that still counts.

## C. Conflict beat (Calendar 4pm vs WA 5pm)

Only if you seed two notifications with the **same first name** (scripted **Priya**, not a real contact):

1. Trigger or paste two notifs: “Priya 4pm” (calendar-style) and “Priya 5pm” (WA-style).
2. Catch-up Brief should list both.
3. Ask `When is Priya?` → answer must show **two times / two sources**.
4. Stills: `10-conflict-brief.png`, `11-conflict-ask.png`.

If you cannot inject notifs today, skip for the video; keep it on the slide as “demo spine on the floor.”

## D. Photo → due card

1. Open a timetable / bill image (gallery or WA).
2. Pulse **or** Settings → Scan WhatsApp media.
3. Memory should show a **due** card. Still: `12-photo-due.png`.

## E. Focus (optional for Round 1 video)

- If showing model generation, identify the actual model and device. Do not imply NPU acceleration unless that path was verified on the recorded device.
- If the clip is sped up, label playback as edited and retain an unedited source recording.

## F. Video assembly (75–90s, captions on, no IDE)

| Time | Shot |
|------|------|
| 0–8s | Cold-open: airplane + Ask “what do I owe?” |
| 8–20s | Brief (3 due · new) |
| 20–35s | Conflict Ask *or* photo-due |
| 35–50s | Pulse dummy → Gallery empty |
| 50–75s | Focus 4× **or** mark one card Done |
| 75–90s | Title card: Retenqo · on-device memory · GitHub `priyanshuchawda/retenqo` |

Export 1080p, unlisted YouTube/Drive for the form.

## You do / I do

| Device recording | Evidence handling |
|------------------|-------------------|
| Capture consent, airplane-mode recall, gallery check, and optional model flow | Record exact device/API/app revision; use synthetic data and retain unedited clips |
| Perform TalkBack and keyboard checks where available | Do not invent screenshots, device performance, or evaluator results |
