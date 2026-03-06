import { apiFetch } from "./client";

export async function getAnswersByQuestionId(id: string) {
  return apiFetch(`/answers/question/${id}`);
}

export async function createAnswer(payload: {
  questionId: number;
  content: string;
}) {
  const BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL;

  const res = await fetch(`${BASE_URL}/answers`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(payload),
  });

  if (!res.ok) {
    throw new Error("답변 등록 실패");
  }

  return res.json();
}