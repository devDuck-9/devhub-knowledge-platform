import Link from "next/link";
import { getSnippets } from "../../lib/api/snippet";

export default async function SnippetsPage() {
 const response = await getSnippets();
 const snippets = Array.isArray(response) ? response : response.content ?? [];

  return (
    <main className="mx-auto max-w-5xl px-6 py-10">
      <div className="mb-6 flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold">Code Snippets</h1>
          <p className="mt-2 text-zinc-600">
            자주 참고하는 코드 조각을 정리한 개인 스니펫 저장소
          </p>
        </div>

        <Link
          href="/snippets/new"
          className="flex items-center gap-2 rounded-xl bg-zinc-900 px-5 py-2.5 shadow-sm transition hover:bg-zinc-700"
        >
          <span className="text-sm font-semibold !text-white">+ New</span>
        </Link>
      </div>

      <form action="/snippets/search" className="mb-8">
        <div className="flex gap-3">
          <input
            type="text"
            name="keyword"
            placeholder="스니펫 제목 혹은 설명 키워드로 검색이 가능합니다."
            className="w-full rounded-xl border border-zinc-200 bg-white px-4 py-3 outline-none placeholder:text-zinc-400 focus:border-zinc-400"
          />
          <button
            type="submit"
            className="rounded-xl bg-zinc-900 px-5 py-3 text-sm font-semibold text-white transition hover:bg-zinc-700"
          >
            검색
          </button>
        </div>
      </form>

      {snippets.length === 0 ? (
        <div className="rounded-xl border bg-white p-6 text-zinc-500">
          아직 저장된 스니펫이 없습니다.
        </div>
      ) : (
        <div className="space-y-5">
          {snippets.map((snippet: any) => (
            <article
              key={snippet.id}
              className="rounded-2xl border border-zinc-200 bg-white p-6 shadow-sm transition hover:-translate-y-0.5 hover:shadow-md"
            >
              <Link href={`/snippets/${snippet.id}`}>
                <div className="flex items-center justify-between gap-4">
                    <h2 className="text-xl font-semibold hover:text-zinc-700">
                      {snippet.title}
                    </h2>

                  <span className="rounded-full bg-zinc-100 px-3 py-1 text-xs text-zinc-700">
                    {snippet.language}
                  </span>
                </div>

                {snippet.description && (
                  <p className="mt-2 text-zinc-600">{snippet.description}</p>
                )}
              </Link>

              {snippet.tags && snippet.tags.length > 0 && (
                <div className="mt-3 flex flex-wrap gap-2">
                  {snippet.tags.map((tag: string) => (
                    <Link
                      key={tag}
                      href={`/snippets/search?tag=${encodeURIComponent(tag)}`}
                    >
                      <span className="rounded-full bg-zinc-100 px-2 py-1 text-xs text-zinc-700 hover:bg-zinc-200">
                        #{tag}
                      </span>
                    </Link>
                  ))}
                </div>
              )}

              {/*
              <div className="mt-4 text-sm text-zinc-500">
                <span>공개범위: {snippet.visibility}</span>
              </div>
              */}
            </article>
          ))}
        </div>
      )}
    </main>
  );
}