import { useEffect, useState } from "react";
import { Table, Button, Pagination } from "react-bootstrap";
import { FaEye, FaEdit, FaTrash } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import "../Styles/BookList.css";
import { getAllBooks } from "../../Services/booksService";
import CommonPagination from '../CommonPagination';


function BookList() {

    const navigate = useNavigate();

    const [books, setBooks] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [recordsPerPage, setRecordsPerPage] = useState(5);

    useEffect(() => {
        loadBooks();
    }, []);

    const loadBooks = async () => {

        // API Call
        const data = await getAllBooks();

        setBooks(data);
    };

const getBookThumbnail = (book) => {

    if (book.thumbnail) {
        return `data:${book.thumbnailType || "image/png"};base64,${book.thumbnail}`;
    }

    return book.thumbnail;
};

    const handleView = (id) => {
        navigate(`/books/view/${id}`);
    };

    // const handleEdit = (id) => {
    //     navigate(`/books/edit/${id}`);
    // };

    const handleDelete = (id) => {
        console.log("Delete", id);
    };

    const lastIndex = currentPage * recordsPerPage;
    const firstIndex = lastIndex - recordsPerPage;

    const currentRecords =
        books.slice(firstIndex, lastIndex);

    const totalPages = Math.ceil(
        books.length / recordsPerPage
    );

    return (

        <div className="container mt-4">
            <div>
                <h2>Books</h2>
            </div>
            <div className="table-top-bar">
                <div className="add-btn">
                    <Button
                        variant="success"
                        onClick={() =>
                            navigate("/books/add")
                        }
                    >

                        + Add Book
                    </Button>
                </div>
            </div>

            <Table
                striped
                bordered
                hover
                responsive
            >

                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Thumbnail</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Category</th>
                        <th>Published On</th>
                        <th>Actions</th>
                    </tr>
                </thead>

                <tbody>

                    {currentRecords.map((book) => (

                        <tr key={book.id}>
                            <td>{book.id}</td>
                            <td>

                                <img
                                    // src={book.thumbnail}
                                    src={getBookThumbnail(book)}
                                    alt={book.title}
                                    className="book-thumbnail"
                                />

                            </td>

                            <td>{book.name}</td>

                            <td>{book.author}</td>

                            <td>{book.categoryName}</td>

                            <td>
                                {book.publishedOn}
                            </td>

                            <td>

                                <FaEye
                                    className="view-icon action-icon"
                                    onClick={() =>
                                        handleView(book.id)
                                    }
                                />

                                <FaEdit
                                    className="action-icon edit-icon"
                                    onClick={() => navigate(`/books/edit/${book.id}`)}
                                />

                                <FaTrash
                                    className="delete-icon action-icon"
                                    onClick={() =>
                                        handleDelete(book.id)
                                    }
                                />

                            </td>

                        </tr>

                    ))}

                </tbody>

            </Table>

            <div className="table-footer">

                <span>
                    Showing {firstIndex + 1}
                    {" "}to{" "}
                    {Math.min(
                        lastIndex,
                        books.length
                    )}
                    {" "}of{" "}
                    {books.length}
                    {" "}records
                </span>

            </div>



            <div>
                <CommonPagination
                    currentPage={currentPage}
                    totalPages={totalPages}
                    setCurrentPage={setCurrentPage}
                    recordsPerPage={recordsPerPage}
                    setRecordsPerPage={setRecordsPerPage}
                />
            </div>

        </div>
    );
}

export default BookList;