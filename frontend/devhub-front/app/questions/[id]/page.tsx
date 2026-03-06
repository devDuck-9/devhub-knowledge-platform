import Link from "next/link";
import { getQuestionById } from "../../../lib/api/question";
import { getAnswersByQuestionId } from "../../../lib/api/answer";
import AnswerForm from "../../../components/answer/AnswerForm";
import DeleteAnswerButton from "../../../components/answer/DeleteAnswerButton";
import AcceptAnswerButton from "../../../components/answer/AcceptAnswerButton";
import DeleteQuestionButton from "../../../components/question/DeleteQuestionButton";

export default async function QuestionDetailPage({
  params,
}: {
  params: Promise<{ id: string }>;
}) {
  const { id } = await params;

  const question = await getQuestionById(id);
  const answers = await getAnswersByQuestionId(id);

  return (
    <main className="mx-auto max-w-4xl px-6 py-10">
      <Link
        href="/questions"
        className="mb-6 inline-block text-sm text-zinc-500 hover:text-zinc-800"
      >
        ← 저장소 목록
      </Link>

      <section className="rounded-xl border bg-white p-6 shadow-sm">
        <div className="flex items-center justify-between">
          <h1 className="text-3xl font-bold">{question.title}</h1>
          <DeleteQuestionButton questionId={question.id} />
        </div>

        <div className="mt-4 flex flex-wrap gap-4 text-sm text-zinc-500">
          <span>Comments Note Count: {question.answerCount ?? 0}</span>
        </div>

        {question.tags && question.tags.length > 0 && (
          <div className="mt-4 flex flex-wrap gap-2">
            {question.tags.map((tag: string) => (
              <Link key={tag} href={`/questions/search?tag=${encodeURIComponent(tag)}`}>
                <span className="rounded-full bg-zinc-100 px-3 py-1 text-sm text-zinc-700 hover:bg-zinc-200">
                  #{tag}
                </span>
              </Link>
            ))}
          </div>
        )}

        <div className="mt-6 whitespace-pre-line text-base leading-7 text-zinc-800">
          {question.content}
        </div>
      </section>

      <section className="mt-8">
        <h2 className="mb-4 text-2xl font-bold">Notes</h2>

        {answers.length === 0 ? (
          <div className="rounded-xl border bg-white p-6 text-sm text-zinc-500">
            아직 등록된 기록이 없습니다.
          </div>
        ) : (
          <div className="space-y-4">
            {answers.map((answer: any) => (
              <div key={answer.id} className="rounded-xl border bg-white p-5 shadow-sm">
                <p className="whitespace-pre-line text-zinc-800">{answer.content}</p>

                <div className="mt-4 flex items-center justify-between text-sm text-zinc-500">
                  <AcceptAnswerButton
                    answerId={answer.id}
                    accepted={answer.accepted}
                  />

                  <DeleteAnswerButton answerId={answer.id} />
                </div>
              </div>
            ))}
          </div>
        )}
      </section>
      
      <br/>
      <AnswerForm questionId={Number(id)} />
    </main>
  );
}