"use client";
import { useState } from "react";
import ResumeCard from "../components/ResumeCard";
import { useRouter } from "next/navigation"; // add at top

interface Resume {
  id: number;
  title: string;
  createdAt: string;
  lastEdited: string;
  isMaster: boolean;
  masterResumeId?: number;
  status?: string;
  fileName?: string;
  name?: string;
  email?: string;
  portfolioURL?: string;
  technicalSkills?: string;
  workExperience?: string;
  education?: string;
  projects?: string;
}

const masterResume: Resume = {
  id: 1,
  title: "Master Resume - Software Engineer",
  createdAt: "2024-01-15T10:30:00Z",
  lastEdited: "2024-01-20T14:45:00Z",
  isMaster: true,
};

export default function ResumesPage() {
  const router = useRouter(); // hook for navigation
  const [showModal, setShowModal] = useState(false);
  const [resumeData, setResumeData] = useState<Partial<Resume>>({
    title: "",
    status: "",
    fileName: "",
    name: "",
    email: "",
    portfolioURL: "",
    technicalSkills: "",
    workExperience: "",
    education: "",
    projects: "",
  });
  const [tailoredResumes, setTailoredResumes] = useState<Resume[]>([
    {
      id: 2,
      title: "Google Software Engineer Resume",
      createdAt: "2024-01-16T09:15:00Z",
      lastEdited: "2024-01-18T16:20:00Z",
      isMaster: false,
    },
    {
      id: 3,
      title: "Microsoft Full Stack Developer CV",
      createdAt: "2024-01-17T11:30:00Z",
      lastEdited: "2024-01-19T13:10:00Z",
      isMaster: false,
    },
  ]);

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setResumeData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!resumeData.title) {
      alert("Resume title is required.");
      return;
    }

    const payload = {
      title: resumeData.title,
      isMaster: false,
      master_resume_id: masterResume.id,
      status: resumeData.status || null,
      fileName: resumeData.fileName || null,
      name: resumeData.name || null,
      email: resumeData.email || null,
      portfolioURL: resumeData.portfolioURL || null,
      technicalSkills: resumeData.technicalSkills || null,
      workExperience: resumeData.workExperience || null,
      education: resumeData.education || null,
      projects: resumeData.projects || null,
    };

    const newResume: Resume = {
      id: tailoredResumes.length + 2, // naive unique id
      title: resumeData.title!,
      createdAt: new Date().toISOString(),
      lastEdited: new Date().toISOString(),
      isMaster: false,
      masterResumeId: masterResume.id,
      status: resumeData.status,
      fileName: resumeData.fileName,
      name: resumeData.name,
      email: resumeData.email,
      portfolioURL: resumeData.portfolioURL,
      technicalSkills: resumeData.technicalSkills,
      workExperience: resumeData.workExperience,
      education: resumeData.education,
      projects: resumeData.projects,
    };
    
    // Add the new resume to the list
    setTailoredResumes([...tailoredResumes, newResume]);
    
    // Close the modal
    setShowModal(false);
    
    // Navigate to the resumes page
    router.push('/resumes/');
  };

  return (
    <div className="min-h-screen p-8 bg-gray-50">
      <main className="max-w-6xl mx-auto">
        {/* Header */}
        <div className="flex justify-between items-center mb-8">
          <div>
            <h1 className="text-4xl font-bold text-gray-900 mb-2">My Resumes</h1>
            <p className="text-gray-600">
              Manage your master resume and tailored versions for different applications
            </p>
          </div>
          <button
            onClick={() => setShowModal(true)}
            className="px-6 py-3 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors font-medium"
          >
            + Create New Resume
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

      {/* Create Resume Modal */}
      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-2xl shadow-lg w-full max-w-md p-6 max-h-[80vh] overflow-y-auto">
            <h2 className="text-2xl font-bold text-gray-900 mb-4">
              Create New Resume
            </h2>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-gray-700 font-medium mb-1">
                  Resume Title
                </label>
                <input
                  type="text"
                  name="title"
                  value={resumeData.title}
                  onChange={handleInputChange}
                  required
                  className="w-full border rounded-lg p-2 focus:ring-2 focus:ring-blue-500"
                  placeholder="e.g., Tesla Backend Engineer Resume"
                />
              </div>
              <div>
                <label className="block text-gray-700 font-medium mb-1">
                  Status
                </label>
                <input
                  type="text"
                  name="status"
                  value={resumeData.status}
                  onChange={handleInputChange}
                  className="w-full border rounded-lg p-2 focus:ring-2 focus:ring-blue-500"
                  placeholder="e.g., Draft, Submitted"
                />
              </div>
              <div>
                <label className="block text-gray-700 font-medium mb-1">
                  File Name
                </label>
                <input
                  type="text"
                  name="fileName"
                  value={resumeData.fileName}
                  onChange={handleInputChange}
                  className="w-full border rounded-lg p-2 focus:ring-2 focus:ring-blue-500"
                  placeholder="e.g., resume_tesla.pdf"
                />
              </div>
              <div>
                <label className="block text-gray-700 font-medium mb-1">
                  Name
                </label>
                <input
                  type="text"
                  name="name"
                  value={resumeData.name}
                  onChange={handleInputChange}
                  className="w-full border rounded-lg p-2 focus:ring-2 focus:ring-blue-500"
                  placeholder="e.g., John Doe"
                />
              </div>
              <div>
                <label className="block text-gray-700 font-medium mb-1">
                  Email
                </label>
                <input
                  type="email"
                  name="email"
                  value={resumeData.email}
                  onChange={handleInputChange}
                  className="w-full border rounded-lg p-2 focus:ring-2 focus:ring-blue-500"
                  placeholder="e.g., john.doe@example.com"
                />
              </div>
              <div>
                <label className="block text-gray-700 font-medium mb-1">
                  Portfolio URL
                </label>
                <input
                  type="url"
                  name="portfolioURL"
                  value={resumeData.portfolioURL}
                  onChange={handleInputChange}
                  className="w-full border rounded-lg p-2 focus:ring-2 focus:ring-blue-500"
                  placeholder="e.g., https://portfolio.example.com"
                />
              </div>
              <div>
                <label className="block text-gray-700 font-medium mb-1">
                  Technical Skills
                </label>
                <textarea
                  name="technicalSkills"
                  value={resumeData.technicalSkills}
                  onChange={handleInputChange}
                  className="w-full border rounded-lg p-2 focus:ring-2 focus:ring-blue-500"
                  placeholder="e.g., Java, Python, React, SQL"
                  rows={4}
                />
              </div>
              <div>
                <label className="block text-gray-700 font-medium mb-1">
                  Work Experience
                </label>
                <textarea
                  name="workExperience"
                  value={resumeData.workExperience}
                  onChange={handleInputChange}
                  className="w-full border rounded-lg p-2 focus:ring-2 focus:ring-blue-500"
                  placeholder="e.g., Software Engineer at XYZ Corp, 2020-2023"
                  rows={4}
                />
              </div>
              <div>
                <label className="block text-gray-700 font-medium mb-1">
                  Education
                </label>
                <textarea
                  name="education"
                  value={resumeData.education}
                  onChange={handleInputChange}
                  className="w-full border rounded-lg p-2 focus:ring-2 focus:ring-blue-500"
                  placeholder="e.g., B.S. Computer Science, ABC University, 2016-2020"
                  rows={4}
                />
              </div>
              <div>
                <label className="block text-gray-700 font-medium mb-1">
                  Projects
                </label>
                <textarea
                  name="projects"
                  value={resumeData.projects}
                  onChange={handleInputChange}
                  className="w-full border rounded-lg p-2 focus:ring-2 focus:ring-blue-500"
                  placeholder="e.g., Built a web app using React and Node.js"
                  rows={4}
                />
              </div>
              <div className="flex justify-end gap-3">
                <button
                  type="button"
                  onClick={() => setShowModal(false)}
                  className="px-4 py-2 bg-gray-200 rounded-lg hover:bg-gray-300"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600"
                >
                  Create
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}