export default function Home() {
  return (
    <div className="font-sans grid grid-rows-[20px_1fr_20px] items-center justify-items-center min-h-screen p-8 pb-20 gap-16">
      <main className="flex flex-col gap-[32px] row-start-2 items-center">
        <div id="kanban">
          <div id="kanban-header" className="flex">
            <div className="border-2 border-amber-300 p-2">Applied</div>
            <div className="border-2 border-amber-300 p-2">Interviewing</div>
            <div className="border-2 border-amber-300 p-2">Job Offer</div>
            <div className="border-2 border-amber-300 p-2">Rejected</div>
          </div>
          <div id="kanban-data">

          </div>
        </div>        
      </main>
    </div>
  );
}
