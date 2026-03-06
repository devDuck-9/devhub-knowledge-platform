"use client";

import { useRouter } from "next/navigation";
import { useState } from "react";

export default function DeleteAnswerButton({ answerId }: { answerId: number }) {
  const router = useRouter();
  const [isDeleting, setIsDeleting] = useState(false);

  async function handleDelete() {
    const ok = window.confirm("이 노트를 삭제할까요?");
    if (!ok) return;

    try {
      setIsDeleting(true);

      const res = await fetch(`http://localhost:8080/answers/${answerId}`, {
        method: "DELETE",
      });

      if (!res.ok) {
        throw new Error("노트 삭제 실패");
      }

      router.refresh();
    } catch (error) {
      alert("노트 삭제 중 오류가 발생했습니다.");
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
      className="text-sm text-red-500 hover:text-red-700 disabled:opacity-50"
    >
      {isDeleting ? "삭제 중..." : "삭제"}
    </button>
  );
}