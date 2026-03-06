export default function HomePage() {
  return (
    <main className="mx-auto max-w-5xl px-6 py-10">
      <h1 className="mb-4 text-4xl font-bold">DevHub</h1>
      <p className="mb-8 text-zinc-600">
        지식 저장소 + Snippet 공유 플랫폼
      </p>

      <div className="flex gap-4">
        <a href="/questions" className="flex items-center gap-2 rounded-xl bg-zinc-900 px-5 py-2.5 shadow-sm transition hover:bg-zinc-700 text-sm font-semibold !text-white">
          저장소 보기
        </a>
        <a href="/snippets" className="flex items-center gap-2 rounded-xl bg-yellow-400 px-5 py-2.5 shadow-sm transition hover:bg-yellow-500 text-sm font-semibold !text-black">
          코드 스니펫
        </a>
      </div>
    </main>
  );
}