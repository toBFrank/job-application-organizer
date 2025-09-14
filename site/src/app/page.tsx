import Job_Board from './components/JobBoard'

export default function Home() {
  return (    
    <main className="">
      <div className="flex items-center border-b border-gray-400 shadow-sm justify-between bg-gray-50 pb-5 pt-6 px-10">
        <h1 className="text-2xl md:text-3xl font-semibold">Your Job Applications</h1>
        <div className="flex items-center gap-2">
          <input 
            placeholder='Job title...'
            className="px-3 py-2 border rounded-md bg-white w-64"
          />
          <input
            placeholder="Company name..."
            className="px-3 py-2 border rounded-md bg-white w-64"
          />
          <button className="px-4 py-2 bg-teal-500 text-white hover:bg-white hover:text-teal-500 rounded-md">
            Add Applications
          </button>
        </div>
      </div>

      <div className='pt-5 px-10'>
        <Job_Board />
      </div>

    </main>
  );
}