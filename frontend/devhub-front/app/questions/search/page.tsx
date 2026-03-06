import Link from "next/link";
import {
  searchQuestionsByKeyword,
  searchQuestionsByTag,
} from "../../../lib/api/question";

export default async function SearchQuestionsPage({
  searchParams,
}: {
  searchParams: Promise<{ tag?: string; keyword?: string }>;
}) {
  const { tag, keyword } = await searchParams;

  let questions: any[] = [];

  if (tag) {
    const response = await searchQuestionsByTag(tag);
    questions = Array.isArray(response) ? response : response.content ?? [];
  } else if (keyword) {
    const response = await searchQuestionsByKeyword(keyword);
    questions = Array.isArray(response) ? response : response.content ?? [];
  }

  return (
    <main className="mx-auto max-w-5xl px-6 py-10">
      <div className="mb-6">
        <Link
          href="/questions"
          className="text-sm text-zinc-500 hover:text-zinc-800"
        >
          ← 저장소 목록
        </Link>
      </div>

      <h1 className="text-3xl font-bold">Search Results</h1>
      <p className="mb-8 mt-2 text-zinc-600">
        {tag && `#${tag} 태그가 붙은 질문`}
        {keyword && `"${keyword}" 검색 결과`}
      </p>

      {questions.length === 0 ? (
        <div className="rounded-2xl border border-zinc-200 bg-white p-6 text-zinc-500 shadow-sm">
          검색 결과가 없습니다.
        </div>
      ) : (
        <div className="space-y-5">
          {questions.map((q: any) => (
            <Link key={q.id} href={`/questions/${q.id}`}>
              <article className="rounded-2xl border border-zinc-200 bg-white p-6 shadow-sm transition hover:-translate-y-0.5 hover:shadow-md">
                <h2 className="text-2xl font-semibold tracking-tight">{q.title}</h2>
                <p className="mt-3 line-clamp-2 text-zinc-600">{q.content}</p>

                {q.tags && q.tags.length > 0 && (
                  <div className="mt-4 flex flex-wrap gap-2">
                    {q.tags.map((tag: string) => (
                      <span
                        key={tag}
                        className="rounded-full bg-zinc-100 px-3 py-1 text-xs font-medium text-zinc-700"
                      >
                        #{tag}
                      </span>
                    ))}
                  </div>
                )}

                <div className="mt-5 flex gap-4 text-sm text-zinc-500">
                  <span>Note Count: {q.answerCount ?? 0}</span>
                </div>
              </article>
            </Link>
          ))}
        </div>
      )}
    </main>
  );
}