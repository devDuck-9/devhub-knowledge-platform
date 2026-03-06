import { apiFetch } from "./client";

//글목록
export async function getQuestions() {
  return apiFetch("/questions");
}

//글상세
export async function getQuestionById(id: string) {
  return apiFetch(`/questions/${id}`);
}

//글작성함수
export async function createQuestion(payload: {
  title: string;
  content: string;
}) {
  const BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL;

  const res = await fetch(`${BASE_URL}/questions`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(payload),
  });

  if (!res.ok) {
    throw new Error("질문 등록 실패");
  }

  return res.json();
}

//태그조회
export async function searchQuestionsByTag(tag: string) {
  return apiFetch(`/questions/search?tag=${encodeURIComponent(tag)}`);
}

//키워드검색
export async function searchQuestionsByKeyword(keyword: string) {
  return apiFetch(`/questions/search?keyword=${encodeURIComponent(keyword)}`);
}