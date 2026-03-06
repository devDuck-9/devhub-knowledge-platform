import Link from "next/link";
import { getSnippetById } from "../../../lib/api/snippet";
import DeleteSnippetButton from "../../../components/snippet/DeleteSnippetButton";
import CodeBlock from "../../../components/snippet/CodeBlock";

export default async function SnippetDetailPage({
  params,
}: {
  params: Promise<{ id: string }>;
}) {
  const { id } = await params;
  const snippet = await getSnippetById(id);

  return (
    <main className="mx-auto max-w-4xl px-6 py-10">
      <Link
        href="/snippets"
        className="mb-6 inline-block text-sm text-zinc-500 hover:text-zinc-800"
      >
        ← 스니펫 목록
      </Link>

      <section className="rounded-xl border bg-white p-6 shadow-sm">
        <div className="flex items-start justify-between gap-4">
            <div>
                <h1 className="text-3xl font-bold">{snippet.title}</h1>
            </div>

            <div className="flex items-center gap-3">
                <span className="rounded-full bg-zinc-100 px-3 py-1 text-sm text-zinc-700">
                {snippet.language}
                </span>
                <DeleteSnippetButton snippetId={snippet.id} />
            </div>
        </div>

        {snippet.description && (
          <p className="mt-4 text-zinc-600">{snippet.description}</p>
        )}

        {snippet.tags && snippet.tags.length > 0 && (
          <div className="mt-4 flex flex-wrap gap-2">
              {snippet.tags.map((tag: string) => (
              <Link key={tag} href={`/snippets/search?tag=${encodeURIComponent(tag)}`}>
                  <span className="rounded-full bg-zinc-100 px-3 py-1 text-sm text-zinc-700 hover:bg-zinc-200">
                  #{tag}
                  </span>
              </Link>
              ))}
          </div>
        )}

        {/*
        <div className="mt-4 text-sm text-zinc-500">
          공개범위: {snippet.visibility}
        </div>
         */}

        <CodeBlock code={snippet.content} language={snippet.language} />
      </section>
    </main>
  );
}