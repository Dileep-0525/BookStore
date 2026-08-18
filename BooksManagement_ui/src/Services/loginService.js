// src/Services/loginService.js

export const login = async (credentials) => {
    try {
        const response = await fetch(
            "http://localhost:8085/api/users/login",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(credentials)
            }
        );

        if (!response.ok) {
            throw new Error("Invalid username or password");
        }

        return await response.json();
    } catch (error) {
        console.error("Login Error:", error);
        throw error;
    }
};