import Link from 'next/link';
import {Resume} from '../../types/resume';

interface Resume {
  resume: Resume
}

interface ResumeCardProps {
  resume: Resume;
}

function formatDate(dateString: string) {
  const date = new Date(dateString);
  return date.toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  });
}

export default function ResumeCard({ resume }: ResumeCardProps) {
  return (
    <Link 
      href={`/resumes/${resume.id}`}
      className="block border border-gray-200 rounded-lg p-4 hover:shadow-lg hover:border-blue-300 transition-all duration-200 bg-white"
    >
      <div className="flex items-start justify-between mb-2">
        <h3 className="font-semibold text-gray-800 text-lg">{resume.title}</h3>
        {resume.isMaster && (
          <span className="bg-yellow-100 text-yellow-800 text-xs font-medium px-2 py-1 rounded-full">
            Master
          </span>
        )}
      </div>
      
      <div className="space-y-1 text-sm text-gray-600">
        <div className="flex items-center gap-2">
          <span className="text-gray-400">📅</span>
          <span>Created: {formatDate(resume.createdAt)}</span>
        </div>
        <div className="flex items-center gap-2">
          <span className="text-gray-400">✏️</span>
          <span>Last edited: {formatDate(resume.lastEdited)}</span>
        </div>
      </div>
      
      <div className="mt-3 pt-3 border-t border-gray-100">
        <span className="text-blue-600 text-sm font-medium hover:text-blue-800">
          View Resume →
        </span>
      </div>
    </Link>
  );
}
