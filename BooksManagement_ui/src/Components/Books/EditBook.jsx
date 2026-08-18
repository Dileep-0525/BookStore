import { useEffect, useState } from "react";
import { Form, Button } from "react-bootstrap";
import { useParams } from "react-router-dom";
// import { getBookById } from "../../Services/bookService";
import { getBookById ,updateBook} from "../../Services/booksService";
import { getAllAuthors } from "../../Services/authorService";
import "../Styles/EditBook.css";

function EditBook() {

    const { id } = useParams();
    const [selectedFile, setSelectedFile] = useState(null);
    const [book, setBook] = useState({
        name: "",
        description: "",
        price: "",
        authorId: ""
    });

    const [authors, setAuthors] = useState([]);

    useEffect(() => {
        loadBook();
        loadAuthors();
    }, []);

    const loadBook = async () => {

        const data = await getBookById(id);

        setBook({
            ...data,
            authorId: data.authorId || ""
        });
    };

    const loadAuthors = async () => {
        const data = await getAllAuthors();
        setAuthors(data);
    };

    const handleChange = (event) => {
        const { name, value } = event.target;
        setBook({
            ...book,
            [name]: value
        });
    };


     const handleFileChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            console.log(file.name);
            setSelectedFile(file);
            setBook({
                ...book,
                fileName: file.name,
                fileType: file.type
            });
        }
    };



    const getBookImage = () => {
    if (selectedFile) {
        return URL.createObjectURL(selectedFile);
    }

    if (book?.photo && book?.fileType) {
        return `data:${book.fileType};base64,${book.thumbnail}`;
    }

    return "/images/default-profile.png";
};
const getBookThumbnail = (book) => {

    if (book.thumbnail) {
        return `data:${book.thumbnailType || "image/png"};base64,${book.thumbnail}`;
    }

    return book.thumbnail;
};

    const handleSubmit = async (event) => {

        event.preventDefault();

        try {

           const response = await updateBook(id,book,selectedFile);
            console.log(response);
            alert("Book Updated Successfully");

        } catch (error) {

            console.error(error);

            alert("Failed To Update Book");
        }
    };

    return (
        <div className="edit-book-container">

            <div className="edit-book-card">

                <h2 className="edit-book-title">
                    Edit Book
                </h2>

                <Form onSubmit={handleSubmit}>

                <div className="book-image-container">

                        <img
                             src={getBookThumbnail(book)}
                            alt={book.name}
                            className="book-thumbnial"
                        />

                        <h5 className="book-name-preview">
                            {book.name}
                        </h5>

                    </div>
                     <Form.Group className="mb-3">

                        <Form.Label>
                            Change Photo
                        </Form.Label>

                        <Form.Control
                            type="file"
                            accept=".pdf,.txt,.doc,.docx"
                            onChange={handleFileChange}
                        />

                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>
                            Title
                        </Form.Label>

                        <Form.Control
                            type="text"
                            name="title"
                            value={book.name}
                            onChange={handleChange}
                        />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>
                            Author
                        </Form.Label>

                        <Form.Select
                            name="authorId" 
                            value={book.authorId || ""}
                            onChange={handleChange}
                        >
                            <option value="">
                                Select Author
                            </option>

                            {authors.map(author => (

                                <option
                                    key={author.id}
                                    value={author.id}
                                >
                                    {author.name}
                                </option>

                            ))}

                        </Form.Select>

                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>
                            Description
                        </Form.Label>

                        <Form.Control
                            as="textarea"
                            rows={4}
                            name="description"
                            value={book.description}
                            onChange={handleChange}
                        />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>
                            Price
                        </Form.Label>

                        <Form.Control
                            type="number"
                            name="price"
                            value={book.price}
                            onChange={handleChange}
                        />
                    </Form.Group>

                    <Button
                        type="submit"
                        className="update-btn"
                    >
                        Update Book
                    </Button>

                </Form>

            </div>

        </div>
    );
}

export default EditBook;