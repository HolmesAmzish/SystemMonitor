import { Outlet, Link } from "react-router-dom";

export default function Layout() {
    return (
        <div className="flex flex-col min-h-screen bg-gray-100">
            <nav className="h-10 fixed top-0 left-0 w-full flex justify-between items-center px-32 py-2
            bg-white/80 backdrop-blur-md shadow-md">
                <h1 className="text-xl font-bold">System Monitor</h1>
                <div className="flex space-x-4">
                    <Link className="hover:text-blue-500" to="/">Dashboard</Link>
                    <Link className="hover:text-blue-500" to="/log">Log</Link>
                    <Link className="hover:text-blue-500" to="/setting">Setting</Link>
                    <Link className="hover:text-blue-500" to="/about">About</Link>
                </div>
            </nav>
            <main className="px-32 py-4 mt-10">
                <Outlet />
            </main>
        </div>
    );
}
