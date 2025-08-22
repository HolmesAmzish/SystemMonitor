import { useEffect, useState } from "react";
import axios from "axios";

interface SystemStatus {
    hostname: string;
    cpuUsage: number;
    cpuTemperature: number;
    memoryUsed: number;
    memoryUsage: number;
    status: string;
    lastUpdated: string;
}

const formatRelativeTime = (dateString: string) => {
    const date = new Date(dateString);
    const now = new Date();
    const diffInSeconds = Math.floor((now.getTime() - date.getTime()) / 1000);

    if (diffInSeconds < 60) return `${diffInSeconds} seconds ago`;
    if (diffInSeconds < 3600) return `${Math.floor(diffInSeconds / 60)} minutes ago`;
    if (diffInSeconds < 86400) return `${Math.floor(diffInSeconds / 3600)} hours ago`;
    return `${Math.floor(diffInSeconds / 86400)} days ago`;
};

// Status badge component
const StatusBadge = ({ status }: { status: string }) => {
    const statusColors = {
        NORMAL: "bg-green-100 text-green-800",
        WARNING: "bg-yellow-100 text-yellow-800",
        CRITICAL: "bg-red-100 text-red-800",
        OFFLINE: "bg-gray-100 text-gray-800",
    };

    const colorClass = statusColors[status as keyof typeof statusColors] || statusColors.NORMAL;

    return (
        <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${colorClass}`}>
      {status}
    </span>
    );
};

// // Metric card component
// const MetricCard = ({ title, value, unit, icon }: { title: string; value: string | number; unit?: string; icon: React.ReactNode }) => (
//     <div className="bg-white overflow-hidden shadow rounded-lg">
//         <div className="p-5">
//             <div className="flex items-center">
//                 <div className="flex-shrink-0">
//                     {icon}
//                 </div>
//                 <div className="ml-5 w-0 flex-1">
//                     <dl>
//                         <dt className="text-sm font-medium text-gray-500 truncate">{title}</dt>
//                         <dd className="flex items-baseline">
//                             <div className="text-2xl font-semibold text-gray-900">
//                                 {value}{unit && <span className="text-sm font-medium text-gray-500 ml-1">{unit}</span>}
//                             </div>
//                         </dd>
//                     </dl>
//                 </div>
//             </div>
//         </div>
//     </div>
// );

function Dashboard() {
    const [systemStatus, setSystemStatus] = useState<SystemStatus[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchSystemStatus = async () => {
            try {
                const response = await axios.get<SystemStatus[]>("/api/status");
                setSystemStatus(response.data);
            } catch (err) {
                if (axios.isAxiosError(err)) {
                    setError(err.message);
                } else {
                    setError("An unexpected error occurred");
                }
            } finally {
                setLoading(false);
            }
        };

        fetchSystemStatus();
    }, []);

    if (loading) return (
        <div className="min-h-screen bg-gray-100 flex items-center justify-center">
            <div className="text-xl text-gray-600">Loading devices...</div>
        </div>
    );

    if (error) return (
        <div className="min-h-screen bg-gray-100 flex items-center justify-center">
            <div className="text-xl text-red-600">Error: {error}</div>
        </div>
    );

    return (
        <div className="">
            <div className="">
                <h1 className="font-bold text-3xl">Monitoring Dashboard</h1>

                <div className="mt-4 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    {systemStatus.map((device, index) => (
                        <div key={index} className="bg-white rounded-xl shadow-md overflow-hidden">
                            {/* Card Header */}
                            <div className="bg-gradient-to-r from-blue-500 to-indigo-600 p-6 text-white">
                                <div className="flex justify-between items-start">
                                    <div>
                                        <h2 className="text-2xl font-bold truncate">{device.hostname}</h2>
                                        <p className="text-blue-100 mt-1">Last updated: {formatRelativeTime(device.lastUpdated)}</p>
                                    </div>
                                    <StatusBadge status={device.status} />
                                </div>
                            </div>

                            {/* Card Body */}
                            <div className="p-6 grid grid-cols-2 gap-4">
                                {/* CPU Usage */}
                                <div className="bg-blue-50 rounded-lg p-4">
                                    <div className="flex items-center">
                                        <div className="bg-blue-100 p-2 rounded-full">
                                            <svg className="h-6 w-6 text-blue-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 3v2m6-2v2M9 19v2m6-2v2M5 9H3m2 6H3m18-6h-2m2 6h-2M7 19h10a2 2 0 002-2V7a2 2 0 00-2-2H7a2 2 0 00-2 2v10a2 2 0 002 2zM9 9h6v6H9V9z" />
                                            </svg>
                                        </div>
                                        <div className="ml-3">
                                            <p className="text-sm font-medium text-gray-600">CPU Usage</p>
                                            <p className="text-lg font-semibold">{(device.cpuUsage * 100).toFixed(1)}%</p>
                                        </div>
                                    </div>
                                </div>

                                {/* CPU Temperature */}
                                <div className="bg-red-50 rounded-lg p-4">
                                    <div className="flex items-center">
                                        <div className="bg-red-100 p-2 rounded-full">
                                            <svg className="h-6 w-6 text-red-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
                                            </svg>
                                        </div>
                                        <div className="ml-3">
                                            <p className="text-sm font-medium text-gray-600">CPU Temp</p>
                                            <p className="text-lg font-semibold">{device.cpuTemperature}°C</p>
                                        </div>
                                    </div>
                                </div>

                                {/* Memory Used */}
                                <div className="bg-purple-50 rounded-lg p-4">
                                    <div className="flex items-center">
                                        <div className="bg-purple-100 p-2 rounded-full">
                                            <svg className="h-6 w-6 text-purple-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 5a1 1 0 011-1h14a1 1 0 011 1v2a1 1 0 01-1 1H5a1 1 0 01-1-1V5zM4 13a1 1 0 011-1h6a1 1 0 011 1v6a1 1 0 01-1 1H5a1 1 0 01-1-1v-6zM16 13a1 1 0 011-1h2a1 1 0 011 1v6a1 1 0 01-1 1h-2a1 1 0 01-1-1v-6z" />
                                            </svg>
                                        </div>
                                        <div className="ml-3">
                                            <p className="text-sm font-medium text-gray-600">Memory Used</p>
                                            <p className="text-lg font-semibold">{device.memoryUsed} MB</p>
                                        </div>
                                    </div>
                                </div>

                                {/* Memory Usage */}
                                <div className="bg-green-50 rounded-lg p-4">
                                    <div className="flex items-center">
                                        <div className="bg-green-100 p-2 rounded-full">
                                            <svg className="h-6 w-6 text-green-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
                                            </svg>
                                        </div>
                                        <div className="ml-3">
                                            <p className="text-sm font-medium text-gray-600">Memory Usage</p>
                                            <p className="text-lg font-semibold">{(device.memoryUsage * 100).toFixed(1)}%</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}

export default Dashboard;