import Link from "next/link";
import { getQuestions } from "../../lib/api/question";

export default async function QuestionsPage() {
  const response = await getQuestions();
  const questions = Array.isArray(response) ? response : response.content ?? [];

  return (
    <main className="mx-auto max-w-5xl px-6 py-10">
      <div className="mb-8 flex items-end justify-between gap-4">
        <div>
          <h1 className="text-4xl font-bold tracking-tight">Knowledge Archive</h1>
          <p className="mt-2 text-zinc-600">
            개발 중 마주친 문제와 해결 과정을 정리한 개인 지식 저장소
          </p>
        </div>

        <Link href="/questions/new"
          className="flex items-center gap-2 rounded-xl bg-zinc-900 px-5 py-2.5 shadow-sm transition hover:bg-zinc-700"
        >
          <span className="text-sm font-semibold !text-white">+ New</span>
        </Link>
      </div>

      <form action="/questions/search" className="mb-8">
        <div className="flex gap-3">
          <input
            type="text"
            name="keyword"
            placeholder="어떤 키워드로 저장소 글을 검색해볼까요?"
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

      {questions.length === 0 ? (
        <div className="rounded-xl border bg-white p-6 text-zinc-500">
          아직 저장된 글이 없습니다.
        </div>
      ) : (
          <div className="space-y-5">
          {questions.map((q: any) => (
            <article
              key={q.id}
              className="rounded-2xl border border-zinc-200 bg-white p-6 shadow-sm transition hover:-translate-y-0.5 hover:shadow-md"
            >
              <Link href={`/questions/${q.id}`}>
                <h2 className="text-lg font-semibold hover:text-zinc-700">
                  {q.title}
                </h2>

                <p className="mt-2 text-sm text-zinc-600">{q.content}</p>
                
                <div className="mt-3 flex gap-4 text-sm text-zinc-500">
                  <span>Comments Note Count: {q.answerCount}</span>
                </div>
              </Link>

              {q.tags && q.tags.length > 0 && (
                <div className="mt-3 flex flex-wrap gap-2">
                  {q.tags.map((tag: string) => (
                    <Link
                      key={tag}
                      href={`/questions/search?tag=${encodeURIComponent(tag)}`}
                    >
                      <span className="rounded-full bg-zinc-100 px-2 py-1 text-sm text-zinc-500 hover:bg-zinc-200">
                        #{tag}
                      </span>
                    </Link>
                  ))}
                </div>
              )}
            </article>
          ))}
        </div>
      )}
    </main>
  );
}