
const token = sessionStorage.getItem("token");
export const getAllBooks= async() =>{
     

	 try {
        const response = await fetch(
            "http://localhost:8085/api/books/all",
            {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`

                }
            }
        );
console.log(response);
        if (!response.ok) {
            throw new Error("Failed to fetch books");
        }

        return await response.json();
    } catch (error) {
        console.error("Error fetching authors:", error);
        throw error;
    }
};
export const getBookById = async(id) =>{
    try{
        const response = await fetch(
           `http://localhost:8085/api/books/one/${id}`,
            {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`

                }
                // Authorization: `Bearer ${token}`
            }
        );
        console.log(response);
        if (!response.ok) {
            throw new Error("Failed to fetch books");
        }

        return await response.json();
    } catch (error) {
        console.error("Error fetching authors:", error);
        throw error;
    }
};

export const updateBook = async(id, book, selectedFile) => {
       const token = sessionStorage.getItem("token");
    const formData = new FormData();

    formData.append(
        "book",
        JSON.stringify(book)
    );


    if (selectedFile) {
        formData.append("file", selectedFile);
    }
    const response = await fetch(
        `http://localhost:8085/api/books/update/${id}`,
        {
            method: "POST",
            headers: {
                // "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: formData
        });
        console.log(response)
    if (!response.ok) {
        throw new Error(
            "Failed to update book"
        );
    }


    return await response.json();
}