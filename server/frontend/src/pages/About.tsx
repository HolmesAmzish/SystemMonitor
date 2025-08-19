import { useEffect } from "react";
import axios from "axios";

function About() {
    useEffect(() => {
        axios.get("/api/hello")
            .then(response => {
                console.log("Backend api test received:", response.data);
            })
            .catch(error => {
                console.error("API call failed:", error);
            });
    }, []); // run once when component mounts

    return (
        <div>
            <h1 className="font-bold text-3xl">About Page</h1>
            <p className="py-2">
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Architecto quos rerum vero.
                Aspernatur consectetur culpa cupiditate esse nostrum ratione recusandae unde vitae, voluptatum?
                Consequatur dolore eum fuga ipsam placeat, quas!
            </p>
        </div>
    );
}

export default About;
