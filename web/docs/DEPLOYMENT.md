# Website deployment

Retenqo's website is a static Astro site in `web/`. It serves Android product information and the verified GitHub APK; it does not host the Android app, its local model, or a backend.

## Local verification

Use Node.js 20+ and the package manager pinned in `package.json` (`pnpm@12.21.0`). From `web/`:

```bash
pnpm install --frozen-lockfile --allow-build esbuild
pnpm lint
pnpm typecheck
pnpm test
pnpm build
pnpm test:e2e
```

The static output is `web/dist/` from repository root or `dist/` from this directory. The deployment must not contain the separately downloaded GGUF model or signing material.

## Cloudflare Pages (existing site)

Keep the existing Cloudflare project and `https://retenqo.pages.dev` intact. From the repository root after local verification:

```bash
wrangler pages deploy web/dist --project-name=retenqo
```

`web/public/_headers` remains the Cloudflare Pages header policy. Release metadata is generated and signer/digest validated with `pnpm release:sync` after a real APK release; never edit `src/data/release.generated.json` by hand.

## Vercel (static Astro, secondary host)

Vercel supports static Astro output without a server adapter. `web/vercel.json` mirrors every response header in `public/_headers`; the parity test is `tests/unit/security-headers.test.ts`. The Vercel deployment must retain the Cloudflare site and canonical URL until the owner changes that decision.

For a Vercel Git import, select the owner-controlled project and set **Root Directory** to `web`, framework preset **Astro**, install command `pnpm install --frozen-lockfile --allow-build esbuild`, build command `pnpm build`, and output directory `dist`. For CLI deployment, run the CLI from this directory and verify the linked project and effective root before uploading:

```bash
cd web
pnpm dlx vercel@latest --version
pnpm dlx vercel@latest whoami
pnpm dlx vercel@latest link
pnpm dlx vercel@latest deploy
```

After a preview passes the route, download, CSP, header, keyboard and mobile checks, production deployment is:

```bash
pnpm dlx vercel@latest deploy --prod
```

The production hostname is not known until the authorized account creates/links the project. Do not invent it or replace Cloudflare canonical metadata. Keep `.vercel/` local and untracked. The Vercel CLI/session must belong to the project owner; do not print or commit tokens.

## Post-deployment acceptance

- Check `/`, `/features`, `/how-it-works`, `/privacy`, `/security`, `/faq`, `/download`, `/installation`, `/demo`, `/robots.txt`, `/sitemap-index.xml`, and a missing path.
- Assert all headers, especially the restrictive CSP, against the live origin. `public/_headers` by itself does not configure Vercel.
- Verify the canonical and OpenGraph metadata still target the existing Cloudflare host, the published APK link and digest are current, and the model is not bundled.
- Verify keyboard flow, theme behavior, 320 px layout, and no console/page errors. The browser demo must remain labeled as synthetic rather than Android inference.

## Release metadata

After a correctly signed APK is published, use `pnpm release:sync` from `web/` with the release version code and pinned certificate metadata configured. The script downloads and checks the real GitHub release artifact. Do not silently replace the current signed APK or signing identity.
