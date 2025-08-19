import {BrowserRouter, Route, Routes} from "react-router-dom";
import Layout from "./Layout.tsx";
import Dashboard from "./pages/Dashboard.tsx";
import About from "./pages/About.tsx";
import Log from "./pages/Log.tsx";
import Setting from "./pages/Setting.tsx";

function App() {

    return (
        <>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<Layout/>}>
                        <Route index element={<Dashboard/>}/>
                        <Route path="log" element={<Log />}/>
                        <Route path="setting" element={<Setting />}/>
                        <Route path="about" element={<About/>}/>
                    </Route>
                </Routes>
            </BrowserRouter>
        </>
    )
}

export default App
