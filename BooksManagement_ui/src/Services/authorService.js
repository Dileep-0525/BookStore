const token = sessionStorage.getItem("token");
export const getAllAuthors = async () => {
    try {
        // const token = sessionStorage.getItem("token");

        const response = await fetch(
            "http://localhost:8085/api/authors/all",
            {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`

                }
            }
        );

        if (!response.ok) {
            throw new Error("Failed to fetch authors");
        }

        return await response.json();
    } catch (error) {
        console.error("Error fetching authors:", error);
        throw error;
    }
};

export const getAuthorById = async (id) => {
    const response = await fetch(
        `http://localhost:8085/api/authors/getOne/${id}`,
        {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`

            }
        }


    );

    return await response.json();
};

export const updateAuthor = async (id, author, selectedFile) => {
    const formData = new FormData();

    formData.append(
        "author",
        JSON.stringify(author)
    );


    if (selectedFile) {
        formData.append("photo", selectedFile);
    }

    const response = await fetch(
        `http://localhost:8085/api/authors/update/${id}`,
        {
            method: "POST",
            headers: {
                // "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: formData
        });
    if (!response.ok) {
        throw new Error(
            "Failed to update author"
        );
    }


    return await response.json();
}