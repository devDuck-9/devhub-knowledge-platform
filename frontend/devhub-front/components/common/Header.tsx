import Link from "next/link";

export default function Header() {
  return (
    <header className="sticky top-0 z-50 border-b border-zinc-200/80 bg-white/80 backdrop-blur">
      <div className="mx-auto flex max-w-6xl items-center justify-between px-6 py-4">
        <Link href="/" className="text-xl font-bold tracking-tight">
          DevHub
        </Link>

        <nav className="flex items-center gap-6 text-sm text-zinc-600">
          <Link href="/questions" className="transition hover:text-zinc-900">
            Questions
          </Link>
          <Link href="/snippets" className="transition hover:text-zinc-900">
            Snippets
          </Link>
        </nav>
      </div>
    </header>
  );
}