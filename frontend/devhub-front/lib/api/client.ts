const BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL;

export async function apiFetch(path: string) {
  const url = `${BASE_URL}${path}`;
  const res = await fetch(url);

  if (!res.ok) {
    throw new Error(`API 요청 실패: ${res.status} ${res.statusText} / ${url}`);
  }

  return res.json();
}