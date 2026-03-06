import Link from "next/link";
import {
  searchSnippetsByKeyword,
  searchSnippetsByTag,
} from "../../../lib/api/snippet";

export default async function SearchSnippetsPage({
  searchParams,
}: {
  searchParams: Promise<{ tag?: string; keyword?: string }>;
}) {
  const { tag, keyword } = await searchParams;

  let snippets: any[] = [];

  if (tag) {
    const response = await searchSnippetsByTag(tag);
    snippets = Array.isArray(response) ? response : response.content ?? [];
  } else if (keyword) {
    const response = await searchSnippetsByKeyword(keyword);
    snippets = Array.isArray(response) ? response : response.content ?? [];
  }

  return (
    <main className="mx-auto max-w-5xl px-6 py-10">
      <div className="mb-6">
        <Link
          href="/snippets"
          className="text-sm text-zinc-500 hover:text-zinc-800"
        >
          ← 스니펫 목록
        </Link>
      </div>

      <h1 className="text-3xl font-bold">Snippet Search Results</h1>
      <p className="mb-8 mt-2 text-zinc-600">
        {tag && `#${tag} 태그가 붙은 스니펫`}
        {keyword && `"${keyword}" 검색 결과`}
      </p>

      {snippets.length === 0 ? (
        <div className="rounded-2xl border border-zinc-200 bg-white p-6 text-zinc-500 shadow-sm">
          검색 결과가 없습니다.
        </div>
      ) : (
        <div className="space-y-5">
          {snippets.map((snippet: any) => (
            <Link key={snippet.id} href={`/snippets/${snippet.id}`}>
              <article className="rounded-2xl border border-zinc-200 bg-white p-6 shadow-sm transition hover:-translate-y-0.5 hover:shadow-md">
                <div className="flex items-center justify-between gap-4">
                  <h2 className="text-2xl font-semibold tracking-tight">
                    {snippet.title}
                  </h2>
                  <span className="rounded-full bg-zinc-100 px-3 py-1 text-xs font-medium text-zinc-700">
                    {snippet.language}
                  </span>
                </div>

                {snippet.description && (
                  <p className="mt-3 line-clamp-2 text-zinc-600">
                    {snippet.description}
                  </p>
                )}

                {snippet.tags && snippet.tags.length > 0 && (
                  <div className="mt-4 flex flex-wrap gap-2">
                    {snippet.tags.map((tag: string) => (
                      <span
                        key={tag}
                        className="rounded-full bg-zinc-100 px-3 py-1 text-xs font-medium text-zinc-700"
                      >
                        #{tag}
                      </span>
                    ))}
                  </div>
                )}
              </article>
            </Link>
          ))}
        </div>
      )}
    </main>
  );
}