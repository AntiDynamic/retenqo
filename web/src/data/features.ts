export type FeatureIconName =
  | 'inbox'
  | 'image'
  | 'mic'
  | 'sparkles'
  | 'quote'
  | 'calendar'
  | 'lock'
  | 'shield'
  | 'offline';

export interface Feature {
  readonly icon: FeatureIconName;
  readonly title: string;
  readonly description: string;
}

export interface FeatureStory {
  readonly eyebrow: string;
  readonly title: string;
  readonly body: string;
  readonly icon: FeatureIconName;
  readonly sample: string;
}

export const FEATURES: readonly Feature[] = [
  {
    icon: 'inbox',
    title: 'Smart Capture & Commitment Detection',
    description:
      'Choose supported notification sources, review detected commitment suggestions, and keep their original source. Capture starts paused and can be revoked in Android Settings.',
  },
  {
    icon: 'sparkles',
    title: 'Offline AI Memory & Recall',
    description:
      'Ask about saved context and review the returned memory cards. Personal memory questions use local retrieval and on-device inference when the model is installed.',
  },
  {
    icon: 'calendar',
    title: 'Smart Reminders & Completion Tracking',
    description:
      'Review and edit commitments, schedule local reminders, and decide whether evidence should mark a task complete.',
  },
  {
    icon: 'quote',
    title: 'Memory source cards',
    description:
      'Memory-backed answers can point to the retrieved cards; general answers may not cite saved memories.',
  },
  {
    icon: 'image',
    title: 'Screenshot and file OCR',
    description:
      'Select images or PDFs and extract text on this device for review and memory capture.',
  },
  {
    icon: 'mic',
    title: 'Voice notes',
    description:
      'Record a note when you choose and transcribe it locally with the installed speech model.',
  },
  {
    icon: 'lock',
    title: 'Encrypted database and backups',
    description:
      'Room data is encrypted with SQLCipher. New backup exports use a password-derived encryption key.',
  },
  {
    icon: 'shield',
    title: 'Consent and deletion controls',
    description:
      'Select notification sources, revoke Android permissions, delete individual cards, or clear saved personal data.',
  },
  {
    icon: 'offline',
    title: 'Offline after model setup',
    description:
      'Local search, storage, OCR paths, and inference work without a cloud AI service once required models are available.',
  },
];

export const FEATURE_STORIES: readonly FeatureStory[] = [
  {
    eyebrow: '01 · Smart Capture & Commitment Detection',
    title: 'Capture selected context and review suggested commitments.',
    body: 'Enable only supported notification groups, select files explicitly, or record a voice note when you need it.',
    icon: 'inbox',
    sample:
      'A selected message or image enters Retenqo only through a permission or action you control.',
  },
  {
    eyebrow: '02 · Offline AI Memory & Recall',
    title: 'Ask about saved details with local retrieval.',
    body: 'Review captured cards and retrieve them with local search. The Room database is encrypted on the device.',
    icon: 'lock',
    sample: 'Example: “The apartment inspection is on Friday at 2.”',
  },
  {
    eyebrow: '03 · Smart Reminders & Completion Tracking',
    title: 'Turn evidence into a task you approve.',
    body: 'Review proposed commitments, correct the details, and choose whether to create a local reminder.',
    icon: 'calendar',
    sample: 'Example: a reminder suggestion stays pending until you accept it.',
  },
];
