import { useState, useEffect } from "react";
import axios from "axios";

interface Device {
    hostname: string;
}

function Setting() {
    const [devices, setDevices] = useState<Device[]>([]);
    const [error, setError] = useState<string | null>(null);

    // Load device list when page loads
    useEffect(() => {
        fetchDevices();
    }, []);

    const fetchDevices = async () => {
        try {
            const response = await axios.get<Device[]>("/api/status"); // tell axios what type you expect
            setDevices(response.data);
        } catch (err: any) {
            setError(err.message);
        }
    };

    // Add new device
    const handleAddDevice = async () => {
        const hostname = prompt("Enter device hostname: ");
        if (hostname) {
            try {
                await axios.post(
                    "/api/status/addDevice",
                    new URLSearchParams({ hostname })
                );
                await fetchDevices(); // Refresh list after adding
            } catch (err: any) {
                setError(err.message);
            }
        }
    };

    return (
        <div>
            <div className="flex justify-between">
                <h1 className="font-bold text-3xl">Setting</h1>

                {error && <p className="text-red-500">{error}</p>}

                <button
                    onClick={handleAddDevice}
                    className="px-4 py-2 rounded bg-blue-500 text-white hover:bg-blue-600"
                >
                    Add Device
                </button>
            </div>


            <ul className="mt-4 bg-white p-2 rounded-lg shadow-md">
                {devices.map((device) => (
                    <li key={device.hostname} className="hover:bg-gray-50 px-1">
                        <strong>{device.hostname}</strong>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default Setting;
