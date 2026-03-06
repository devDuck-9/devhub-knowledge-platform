"use client";
import Link from "next/link";
import { useState } from "react";
import { useRouter } from "next/navigation";

export default function QuestionForm() {
  const router = useRouter();

  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [tags, setTags] = useState("");
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  async function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();

    if (!title.trim() || !content.trim()) {
      setError("제목과 내용을 입력해주세요.");
      return;
    }

    try {
      setIsSubmitting(true);
      setError("");

      const tagList = tags
        .split(",")
        .map((tag) => tag.trim())
        .filter(Boolean);

      const res = await fetch("http://localhost:8080/questions", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          userId: 1,
          title,
          content,
          visibility: "PUBLIC",
          tags: tagList,
        }),
      });

      if (!res.ok) {
        throw new Error("문제 등록 실패");
      }

      router.push("/questions");
      router.refresh();
    } catch (err) {
      setError("문제 등록 중 오류가 발생했습니다.");
      console.error(err);
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <main className="mx-auto max-w-4xl px-6 py-10">
      <Link
        href="/questions"
        className="mb-6 inline-block text-sm text-zinc-500 hover:text-zinc-800"
      >
        ← 저장소 목록
      </Link>

      <section className="rounded-xl border bg-white p-6 shadow-sm">
        <h1 className="mb-6 text-3xl font-bold">Create Question</h1>

        <form onSubmit={handleSubmit} className="space-y-5">
          <div>
            <label className="mb-2 block text-sm font-medium">Title</label>
            <input
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              placeholder="문제 상황을 간단히 정리하세요"
              className="w-full rounded-lg border p-3 outline-none focus:border-zinc-400"
            />
          </div>

          <div>
            <label className="mb-2 block text-sm font-medium">Content</label>
            <textarea
              value={content}
              onChange={(e) => setContent(e.target.value)}
              placeholder="문제 상황과 시도한 방법을 기록하세요"
              className="min-h-[200px] w-full rounded-lg border p-3 outline-none focus:border-zinc-400"
            />
          </div>

          <div>
            <label className="mb-2 block text-sm font-medium">Tags</label>
            <input
              value={tags}
              onChange={(e) => setTags(e.target.value)}
              placeholder="spring, security, jwt"
              className="w-full rounded-lg border p-3 outline-none focus:border-zinc-400"
            />
            <p className="mt-2 text-sm text-zinc-500">
              쉼표(,)로 구분해서 입력하세요.
            </p>
          </div>

          {error && <p className="text-sm text-red-500">{error}</p>}

          <button
            type="submit"
            disabled={isSubmitting}
            className="flex items-center gap-2 rounded-xl bg-zinc-900 px-5 py-2.5 shadow-sm transition hover:bg-zinc-700 text-sm font-semibold !text-white"
          >
            {isSubmitting ? "저장 중..." : "Save Question"}
          </button>
        </form>
      </section>
    </main>
  );
}