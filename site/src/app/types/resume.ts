export interface Resume {
  id: number;
  title: string;
  createdAt: string;
  lastEdited: string;
  isMaster: boolean;
  name?: string;
  email?: string;
  portfolioURL?: string;
  technicalSkills?: string;
  workExperience?: string;
  education?: string;
  projects?: string;
}