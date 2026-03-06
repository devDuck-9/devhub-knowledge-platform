"use client";

import { useRouter } from "next/navigation";
import { useState } from "react";

export default function DeleteQuestionButton({ questionId }: { questionId: number }) {
  const router = useRouter();
  const [isDeleting, setIsDeleting] = useState(false);

  async function handleDelete() {
    const ok = window.confirm("이 질문을 삭제할까요?");
    if (!ok) return;

    try {
      setIsDeleting(true);

      const res = await fetch(`http://localhost:8080/questions/${questionId}`, {
        method: "DELETE",
      });

      if (!res.ok) {
        throw new Error("삭제 실패");
      }

      router.push("/questions");
    } catch (err) {
      alert("삭제 중 오류가 발생했습니다.");
      console.error(err);
    } finally {
      setIsDeleting(false);
    }
  }

  return (
    <button
      onClick={handleDelete}
      disabled={isDeleting}
      className="text-sm text-red-500 hover:text-red-700"
    >
      {isDeleting ? "삭제 중..." : "삭제"}
    </button>
  );
}