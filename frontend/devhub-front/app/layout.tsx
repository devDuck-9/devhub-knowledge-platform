import "./globals.css";
import Header from "@/components/common/Header";

export const metadata = {
  title: "DevHub",
  description: "Spring Boot 기반 개발자 Q&A + Snippet 공유 플랫폼",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="ko">
      <body>
        <Header />
        {children}
      </body>
    </html>
  );
}