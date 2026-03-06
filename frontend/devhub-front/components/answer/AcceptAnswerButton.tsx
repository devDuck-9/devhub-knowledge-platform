"use client";

import { useRouter } from "next/navigation";
import { useState } from "react";

export default function AcceptAnswerButton({
  answerId,
  accepted,
}: {
  answerId: number;
  accepted: boolean;
}) {
  const router = useRouter();
  const [isSubmitting, setIsSubmitting] = useState(false);

  async function handleAccept() {
    try {
      setIsSubmitting(true);

      const res = await fetch(`http://localhost:8080/answers/${answerId}/accept`, {
        method: "PATCH",
      });

      if (!res.ok) {
        throw new Error("권장 설정 실패");
      }

      router.refresh();
    } catch (error) {
      alert("권장 설정 중 오류가 발생했습니다.");
      console.error(error);
    } finally {
      setIsSubmitting(false);
    }
  }

  if (accepted) {
    return (
      <span className="rounded-full bg-green-100 px-2 py-1 text-xs font-medium text-green-700">
        ✅ 권장
      </span>
    );
  }

  return (
    <button
      type="button"
      onClick={handleAccept}
      disabled={isSubmitting}
      className="text-sm text-blue-600 hover:text-blue-800 disabled:opacity-50"
    >
      {isSubmitting ? "처리 중..." : "권장으로 선택"}
    </button>
  );
}