"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";

export default function AnswerForm({ questionId }: { questionId: number }) {
  const router = useRouter();
  const [content, setContent] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState("");

  async function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();

    if (!content.trim()) {
      setError("노트 내용을 입력해주세요.");
      return;
    }

    try {
      setIsSubmitting(true);
      setError("");

      const res = await fetch("http://localhost:8080/answers", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          questionId,
          userId: 1,
          content,
        }),
      });

      if (!res.ok) {
        throw new Error("노트 등록 실패");
      }

      setContent("");
      router.refresh();
    } catch (err) {
      setError("노트 등록 중 오류가 발생했습니다.");
      console.error(err);
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <section className="mt-8 rounded-xl border bg-white p-6 shadow-sm">
      <h2 className="mb-4 text-2xl font-bold">Add Note</h2>

      <form onSubmit={handleSubmit} className="space-y-4">
        <textarea
          value={content}
          onChange={(e) => setContent(e.target.value)}
          placeholder="해결 방법이나 메모를 기록하세요."
          className="min-h-[160px] w-full rounded-lg border p-4 outline-none focus:border-zinc-400"
        />

        {error && <p className="text-sm text-red-500">{error}</p>}

        <button
          type="submit"
          disabled={isSubmitting}
          className="flex items-center gap-2 rounded-xl bg-zinc-900 px-5 py-2.5 shadow-sm transition hover:bg-zinc-700 text-sm font-semibold !text-white"
        >
          {isSubmitting ? "저장 중..." : "노트 저장"}
        </button>
      </form>
    </section>
  );
}