'use client';
import { useState } from 'react';
import Job_Board from './components/JobBoard'

export default function Home() {
  const [jobTitle, setJobTitle] = useState('');
  const [companyName, setCompanyName] = useState('');
  const [isAdding, setIsAdding] = useState(false);
  const [addError, setAddError] = useState<string | null>(null);
  const [refreshTrigger, setRefreshTrigger] = useState(0);

  const handleAddApplication = async () => {
    // Validate inputs
    if (!jobTitle.trim() || !companyName.trim()) {
      setAddError('Please fill in both job title and company name');
      return;
    }

    try {
      setIsAdding(true);
      setAddError(null);

      const response = await fetch('http://localhost:8080/applications', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          title: jobTitle.trim(),
          company: companyName.trim(),
          status: 'APPLIED' // Default status for new applications
        }),
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      // Clear form inputs
      setJobTitle('');
      setCompanyName('');
      
      // Trigger refresh of JobBoard
      setRefreshTrigger(prev => prev + 1);

    } catch (err) {
      console.error('Error adding application:', err);
      setAddError(err instanceof Error ? err.message : 'Failed to add application');
    } finally {
      setIsAdding(false);
    }
  };

  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter') {
      handleAddApplication();
    }
  };

  return (    
    <main className="">
      <div className="flex items-center border-b border-gray-400 shadow-sm justify-between bg-gray-50 pb-5 pt-6 px-10">
        <h1 className="text-2xl md:text-3xl font-semibold">Your Job Applications</h1>
        <div className="flex items-center gap-2">
          <input 
            placeholder='Job title...'
            value={jobTitle}
            onChange={(e) => setJobTitle(e.target.value)}
            onKeyPress={handleKeyPress}
            className="px-3 py-2 border rounded-md bg-white w-64"
            disabled={isAdding}
          />
          <input
            placeholder="Company name..."
            value={companyName}
            onChange={(e) => setCompanyName(e.target.value)}
            onKeyPress={handleKeyPress}
            className="px-3 py-2 border rounded-md bg-white w-64"
            disabled={isAdding}
          />
          <button 
            onClick={handleAddApplication}
            disabled={isAdding || !jobTitle.trim() || !companyName.trim()}
            className="px-4 py-2 bg-teal-500 text-white hover:bg-white hover:text-teal-500 rounded-md disabled:opacity-50 disabled:cursor-not-allowed"
          >
            {isAdding ? 'Adding...' : 'Add Application'}
          </button>
        </div>
      </div>

      {addError && (
        <div className="px-10 pt-4">
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded">
            Error: {addError}
            <button 
              onClick={() => setAddError(null)}
              className="ml-4 text-red-500 hover:text-red-700"
            >
              ×
            </button>
          </div>
        </div>
      )}

      <div className='pt-5 px-10'>
        <Job_Board refreshTrigger={refreshTrigger} />
      </div>

    </main>
  );
}