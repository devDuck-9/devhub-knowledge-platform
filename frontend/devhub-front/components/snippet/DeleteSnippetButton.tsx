"use client";

import { useRouter } from "next/navigation";
import { useState } from "react";

export default function DeleteSnippetButton({
  snippetId,
}: {
  snippetId: number;
}) {
  const router = useRouter();
  const [isDeleting, setIsDeleting] = useState(false);

  async function handleDelete() {
    const ok = window.confirm("이 스니펫을 삭제할까요?");
    if (!ok) return;

    try {
      setIsDeleting(true);

      const res = await fetch(`http://localhost:8080/snippets/${snippetId}`, {
        method: "DELETE",
      });

      if (!res.ok) {
        throw new Error("스니펫 삭제 실패");
      }

      router.push("/snippets");
      router.refresh();
    } catch (error) {
      alert("스니펫 삭제 중 오류가 발생했습니다.");
      console.error(error);
    } finally {
      setIsDeleting(false);
    }
  }

  return (
    <button
      type="button"
      onClick={handleDelete}
      disabled={isDeleting}
      className="rounded-lg border border-red-200 px-3 py-2 text-sm text-red-600 hover:bg-red-50 disabled:opacity-50"
    >
      {isDeleting ? "삭제 중..." : "삭제"}
    </button>
  );
}