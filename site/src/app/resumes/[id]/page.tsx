"use client";

import { useState, useEffect } from "react";
import { Resume } from "../../../types"; // your TS type

interface ResumePageProps {
  params: { id: string };
}

export default function ResumePage({ params }: ResumePageProps) {
  const { id } = params;
  const [resume, setResume] = useState<Resume | null>(null);
  const [editing, setEditing] = useState(false);
  const [formData, setFormData] = useState<Partial<Resume>>({});

  // Fetch resume data on mount
  useEffect(() => {
    async function fetchResume() {
      try {
        const res = await fetch(`http://localhost:8080/resumes/${id}`);
        if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
        const data: Resume = await res.json();
        setResume(data);
        setFormData(data);
      } catch (err) {
        console.error("Failed to fetch resume:", err);
      }
    }
    fetchResume();
  }, [id]);

  if (!resume) return <p>Loading...</p>;

  const handleChange = (field: keyof Resume, value: string) => {
    setFormData((prev) => ({ ...prev, [field]: value }));
  };

  const handleSave = async () => {
    try {
      const res = await fetch(`http://localhost:8080/resumes/${id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });
      if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
      setResume(formData as Resume);
      setEditing(false);
    } catch (err) {
      console.error("Failed to save resume:", err);
    }
  };

  const handleExportPDF = () => {
    // Placeholder: integrate jsPDF or react-to-print later
    console.log("Export PDF clicked for resume", resume.id);
  };

  return (
    <div className="min-h-screen p-8 bg-gray-50">
      <main className="max-w-3xl mx-auto">
        <h1 className="text-3xl font-bold mb-4">{resume.title}</h1>

        <div className="flex gap-2 mb-6">
          <button
            onClick={() => setEditing(!editing)}
            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
          >
            {editing ? "Cancel" : "Edit"}
          </button>
          <button
            onClick={handleExportPDF}
            className="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600"
          >
            Export PDF
          </button>
        </div>

        {editing ? (
          <form className="space-y-4">
            <div>
              <label className="block text-gray-700 mb-1">Title</label>
              <input
                type="text"
                value={formData.title || ""}
                onChange={(e) => handleChange("title", e.target.value)}
                className="w-full border rounded px-3 py-2"
              />
            </div>
            <div>
              <label className="block text-gray-700 mb-1">Email</label>
              <input
                type="email"
                value={formData.email || ""}
                onChange={(e) => handleChange("email", e.target.value)}
                className="w-full border rounded px-3 py-2"
              />
            </div>
            {/* Add other fields here */}
            <button
              type="button"
              onClick={handleSave}
              className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
            >
              Save
            </button>
          </form>
        ) : (
          <div className="space-y-4">
            <p><strong>Email:</strong> {resume.email}</p>
            <p><strong>Portfolio:</strong> {resume.portfolioURL}</p>
            <p><strong>Technical Skills:</strong> {resume.technicalSkills}</p>
            <p><strong>Work Experience:</strong> {resume.workExperience}</p>
            <p><strong>Education:</strong> {resume.education}</p>
            <p><strong>Projects:</strong> {resume.projects}</p>
          </div>
        )}
      </main>
    </div>
  );
}
