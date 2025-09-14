import Link from "next/link";

export default function Header() {
    return (
    <nav className="w-full border-b border-gray-400 bg-white shadow-sm sticky top-0 z-50">
        <div className="container mx-auto px-10 py-3">
            <div id='nav' className="flex flex-wrap justify-between min-w-0 gap-4">
                <div className="md:flex items-center space-x-8">
                    <Link href='/' className="hover:text-teal-500 transition-colors duration-300">Dashboard</Link>
                    <Link href='/resumes/' className="hover:text-teal-500 transition-colors duration-300">Resume Tailoring</Link>
                    <Link href='/' className="hover:text-teal-500 transition-colors duration-300">Notifications</Link>
                </div>
                <Link href='/' className="text-3xl text-black font-medium hover:text-teal-500 transition-colors duration-300 ease-in-out">JobAnizer</Link>
            </div>
        </div>
    </nav>
    )
}