"use client";

import { useState } from "react";
import { Prism as SyntaxHighlighter } from "react-syntax-highlighter";
import { oneDark } from "react-syntax-highlighter/dist/esm/styles/prism";

function normalizeLanguage(language?: string) {
  if (!language) return "text";

  const value = language.toLowerCase();

  if (value === "java") return "java";
  if (value === "javascript") return "javascript";
  if (value === "typescript") return "typescript";
  if (value === "sql") return "sql";
  if (value === "html") return "markup";
  if (value === "css") return "css";

  return "text";
}

export default function CodeBlock({
  code,
  language,
}: {
  code: string;
  language?: string;
}) {
  const [copied, setCopied] = useState(false);

  async function handleCopy() {
    try {
      await navigator.clipboard.writeText(code);
      setCopied(true);

      setTimeout(() => {
        setCopied(false);
      }, 2000);
    } catch (err) {
      console.error("복사 실패", err);
    }
  }

  return (
    <div className="mt-6 overflow-hidden rounded-xl border">

      {/* 상단 바 */}
      <div className="flex items-center justify-between bg-zinc-800 px-4 py-2 text-xs text-zinc-300">
        <span>{language ?? "TEXT"}</span>

        <button
          onClick={handleCopy}
          className="rounded bg-zinc-700 px-2 py-1 text-xs hover:bg-zinc-600"
        >
          {copied ? "Copied!" : "Copy"}
        </button>
      </div>

      <SyntaxHighlighter
        language={normalizeLanguage(language)}
        style={oneDark}
        customStyle={{
          margin: 0,
          borderRadius: 0,
          fontSize: "14px",
          lineHeight: "1.6",
          padding: "16px",
        }}
        wrapLongLines
      >
        {code}
      </SyntaxHighlighter>
    </div>
  );
}