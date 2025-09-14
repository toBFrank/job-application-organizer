import ResumeCard from '../components/ResumeCard';

// Fake data for resumes
const masterResume = {
  id: 1,
  title: "Master Resume - Software Engineer",
  createdAt: "2024-01-15T10:30:00Z",
  lastEdited: "2024-01-20T14:45:00Z",
  isMaster: true
};

const tailoredResumes = [
  {
    id: 2,
    title: "Google Software Engineer Resume",
    createdAt: "2024-01-16T09:15:00Z",
    lastEdited: "2024-01-18T16:20:00Z",
    isMaster: false
  },
  {
    id: 3,
    title: "Microsoft Full Stack Developer CV",
    createdAt: "2024-01-17T11:30:00Z",
    lastEdited: "2024-01-19T13:10:00Z",
    isMaster: false
  },
  {
    id: 4,
    title: "Amazon Backend Engineer Resume",
    createdAt: "2024-01-18T14:00:00Z",
    lastEdited: "2024-01-21T10:30:00Z",
    isMaster: false
  },
  {
    id: 5,
    title: "Meta Frontend Developer CV",
    createdAt: "2024-01-19T08:45:00Z",
    lastEdited: "2024-01-22T15:25:00Z",
    isMaster: false
  }
];

export default function ResumesPage() {
  return (
    <div className="min-h-screen p-8 bg-gray-50">
      <main className="max-w-6xl mx-auto">
        {/* Header */}
        <div className="flex justify-between items-center mb-8">
          <div>
            <h1 className="text-4xl font-bold text-gray-900 mb-2">
              My Resumes
            </h1>
            <p className="text-gray-600">
              Manage your master resume and tailored versions for different applications
            </p>
          </div>
          <button className="px-6 py-3 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors font-medium">
            + Upload New Resume
          </button>
        </div>

        {/* Master Resume Section */}
        <div className="mb-8">
          <h2 className="text-2xl font-semibold text-gray-800 mb-4 flex items-center gap-2">
            <span className="text-yellow-500">📄</span>
            Master Resume
          </h2>
          <p className="text-gray-600 mb-4">
            Your main resume template that serves as the foundation for all tailored versions
          </p>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            <ResumeCard resume={masterResume} />
          </div>
        </div>

        {/* Tailored Resumes Section */}
        <div>
          <h2 className="text-2xl font-semibold text-gray-800 mb-4 flex items-center gap-2">
            <span className="text-green-500">🎯</span>
            Tailored Resumes
          </h2>
          <p className="text-gray-600 mb-4">
            Customized resumes tailored for specific job applications
          </p>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            {tailoredResumes.map((resume) => (
              <ResumeCard key={resume.id} resume={resume} />
            ))}
          </div>
        </div>
      </main>
    </div>
  );
}
