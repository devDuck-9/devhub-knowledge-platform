"use client";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";

export default function SnippetForm() {
  const router = useRouter();

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [language, setLanguage] = useState("JAVA");
  const [content, setContent] = useState("");
  const [tags, setTags] = useState("");
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  async function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();

    if (!title.trim() || !content.trim()) {
      setError("제목과 코드를 입력해주세요.");
      return;
    }

    try {
      setIsSubmitting(true);
      setError("");

      const tagList = tags
        .split(",")
        .map((tag) => tag.trim())
        .filter(Boolean);

      const res = await fetch("http://localhost:8080/snippets", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          userId: 1,
          title,
          description,
          language,
          content,
          visibility: "PUBLIC",
          tags: tagList,
        }),
      });

      if (!res.ok) {
        throw new Error("스니펫 등록 실패");
      }

      router.push("/snippets");
      router.refresh();
    } catch (err) {
      setError("스니펫 저장 중 오류가 발생했습니다.");
      console.error(err);
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <main className="mx-auto max-w-4xl px-6 py-10">
      <Link
        href="/snippets"
        className="mb-6 inline-block text-sm text-zinc-500 hover:text-zinc-800"
      >
        ← 스니펫 목록
      </Link>
      <section className="rounded-xl border bg-white p-6 shadow-sm">
        <h1 className="mb-6 text-3xl font-bold">Create Snippet</h1>
        <form onSubmit={handleSubmit} className="space-y-5">
          <div>
            <label className="mb-2 block text-sm font-medium">Title</label>
            <input
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              placeholder="스니펫 제목"
              className="w-full rounded-lg border p-3 outline-none focus:border-zinc-400"
            />
          </div>

          <div>
            <label className="mb-2 block text-sm font-medium">Description</label>
            <input
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              placeholder="짧은 설명"
              className="w-full rounded-lg border p-3 outline-none focus:border-zinc-400"
            />
          </div>

          <div>
            <label className="mb-2 block text-sm font-medium">Language</label>
            <select
              value={language}
              onChange={(e) => setLanguage(e.target.value)}
              className="w-full rounded-lg border p-3 outline-none focus:border-zinc-400"
            >
              <option value="JAVA">JAVA</option>
              <option value="JAVASCRIPT">JAVASCRIPT</option>
              <option value="TYPESCRIPT">TYPESCRIPT</option>
              <option value="SQL">SQL</option>
              <option value="HTML">HTML</option>
              <option value="CSS">CSS</option>
            </select>
          </div>

          <div>
            <label className="mb-2 block text-sm font-medium">Code</label>
            <textarea
              value={content}
              onChange={(e) => setContent(e.target.value)}
              placeholder="코드를 입력하세요"
              className="min-h-[240px] w-full rounded-lg border p-3 font-mono outline-none focus:border-zinc-400"
            />
          </div>

          <div>
            <label className="mb-2 block text-sm font-medium">Tags</label>
            <input
              value={tags}
              onChange={(e) => setTags(e.target.value)}
              placeholder="snippet, java, test"
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
            {isSubmitting ? "저장 중..." : "Save Snippet"}
          </button>
        </form>
      </section>
    </main>
  );
}