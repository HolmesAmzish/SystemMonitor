import { useEffect, useState } from "react";
import axios from "axios";

interface LogEntry {
    id: number;
    hostname: string;
    cpuUsage: number;
    cpuTemperature: number;
    memoryUsed: number;
    memoryUsage: number;
    status: string;
    timestamp: string;
}

function Log() {
    const [logs, setLogs] = useState<LogEntry[]>([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const pageSize = 15;

    const fetchLogs = async (pageNum: number) => {
        try {
            const response = await axios.get("/api/logs", {
                params: { page: pageNum, size: pageSize },
            });

            const data = response.data as {
                content: LogEntry[];
                totalPages: number;
                number: number;
            };

            setLogs(data.content);
            setTotalPages(data.totalPages);
            setPage(data.number);
        } catch (err) {
            console.error("Failed to fetch logs:", err);
        }
    };

    useEffect(() => {
        fetchLogs(0);
    }, []);

    return (
        <div>
            <div className="flex justify-between items-center mb-4">
                <h1 className="font-bold text-3xl">System Logs</h1>
                <button
                    onClick={() => fetchLogs(page)}
                    className="px-4 py-2 rounded-lg bg-blue-500 text-white hover:bg-blue-600"
                >
                    Refresh
                </button>
            </div>

            {/* Table */}
            <div className="overflow-x-auto shadow-lg bg-white rounded-lg">
                <table className="min-w-full text-sm text-left">
                    <thead>
                    <tr>
                        <th className="px-4 py-2 border-b border-gray-200">ID</th>
                        <th className="px-4 py-2 border-b border-gray-200">Host</th>
                        <th className="px-4 py-2 border-b border-gray-200">CPU Usage</th>
                        <th className="px-4 py-2 border-b border-gray-200">Temp (°C)</th>
                        <th className="px-4 py-2 border-b border-gray-200">Memory Used</th>
                        <th className="px-4 py-2 border-b border-gray-200">Usage %</th>
                        <th className="px-4 py-2 border-b border-gray-200">Status</th>
                        <th className="px-4 py-2 border-b border-gray-200">Timestamp</th>
                    </tr>
                    </thead>
                    <tbody>
                    {logs.map((log) => (
                        <tr key={log.id} className="hover:bg-gray-50">
                            <td className="px-4 py-2">{log.id}</td>
                            <td className="px-4 py-2">{log.hostname}</td>
                            <td className="px-4 py-2">{(log.cpuUsage * 100).toFixed(1)}%</td>
                            <td className="px-4 py-2">{log.cpuTemperature}</td>
                            <td className="px-4 py-2">{log.memoryUsed} MB</td>
                            <td className="px-4 py-2">{(log.memoryUsage * 100).toFixed(0)}%</td>
                            <td
                                className={`px-4 py-2 font-semibold ${
                                    log.status === "NORMAL"
                                        ? "text-green-600"
                                        : log.status === "WARNING"
                                            ? "text-yellow-600"
                                            : "text-red-600"
                                }`}
                            >
                                {log.status}
                            </td>
                            <td className="px-4 py-2">
                                {new Date(log.timestamp).toLocaleString()}
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>

            {/* Pagination Controls */}
            <div className="flex justify-between items-center mt-4">
                <button
                    onClick={() => fetchLogs(page - 1)}
                    disabled={page === 0}
                    className="px-4 py-2 rounded bg-gray-200 hover:bg-gray-300 disabled:opacity-50"
                >
                    Prev
                </button>
                <span className="text-sm">
          Page {page + 1} of {totalPages}
        </span>
                <button
                    onClick={() => fetchLogs(page + 1)}
                    disabled={page + 1 >= totalPages}
                    className="px-4 py-2 rounded bg-gray-200 hover:bg-gray-300 disabled:opacity-50"
                >
                    Next
                </button>
            </div>
        </div>
    );
}

export default Log;
