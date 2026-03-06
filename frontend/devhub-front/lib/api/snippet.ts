import { apiFetch } from "./client";

export async function getSnippets() {
  return apiFetch("/snippets");
}

export async function getSnippetById(id: string) {
  return apiFetch(`/snippets/${id}`);
}

export async function searchSnippetsByTag(tag: string) {
  return apiFetch(`/snippets/search?tag=${encodeURIComponent(tag)}`);
}

export async function searchSnippetsByKeyword(keyword: string) {
  return apiFetch(`/snippets/search?keyword=${encodeURIComponent(keyword)}`);
}