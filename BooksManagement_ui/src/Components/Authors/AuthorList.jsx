import Table from 'react-bootstrap/Table';
import { useEffect, useState } from 'react';
import { getAllAuthors } from '../../Services/authorService';
import Button from "react-bootstrap/Button";
import Pagination from "react-bootstrap/Pagination";
import { useNavigate } from "react-router-dom";
import { FaEye, FaEdit, FaTrash } from "react-icons/fa";
import '../Styles/AuthorList.css';
import CommonPagination from '../CommonPagination';

function AuthorList() {

    const navigate = useNavigate();

    const [authors, setAuthors] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [recordsPerPage, setRecordsPerPage] = useState(5);
    // const recordsPerPage = 5;

    useEffect(() => {
        loadAuthors();
    }, []);

    const loadAuthors = async () => {
        // API call
        const data = await getAllAuthors();
        setAuthors(data);
    };

    const getAuthorImage = (author) => {
        if (author?.photo && author?.fileType) {
            return `data:${author.fileType};base64,${author.photo}`;
        }

        return "/images/default-profile.png";
    };

    const lastIndex = currentPage * recordsPerPage;
    const firstIndex = lastIndex - recordsPerPage;

    const currentRecords = authors.slice(
        firstIndex,
        lastIndex
    );

    const totalPages = Math.ceil(
        authors.length / recordsPerPage
    );

    return (
        <div className="container mt-4">
            <div>
                <h2>Authors</h2>
            </div>
            <div className="table-top-bar">
                <div className="add-btn">
                    <Button
                        variant="success"
                        onClick={() =>
                            navigate("/authors/add")
                        }
                    >
                        + Add Author
                    </Button>
                </div>
            </div>

            <Table
                striped
                bordered
                hover
                responsive
                className="author-table"
            >
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Image</th>
                        <th>Name</th>
                        <th>Date Of Birth</th>
                        <th>Actions</th>
                    </tr>
                </thead>

                <tbody>
                    {currentRecords.map((author) => (
                        <tr key={author.id}>
                            <td>{author.id}</td>
                            <td>
                                <img
                                    src={getAuthorImage(author)}
                                    alt={author.name}
                                    className="author-image"
                                />
                            </td>
                            <td>{author.name}</td>
                            <td>{author.dateOfBirth}</td>


                            <td>
                                <FaEye
                                    className="action-icon view-icon"
                                    title="View"
                                    onClick={() => navigate(`/authors/view/${author.id}`)}
                                />

                                <FaEdit
                                    className="action-icon edit-icon"
                                    title="Edit"
                                    onClick={() => navigate(`/authors/edit/${author.id}`)}
                                />

                                <FaTrash
                                    className="action-icon delete-icon"
                                    title="Delete"
                                    onClick={() => handleDelete(author.id)}
                                />
                            </td>
                        </tr>
                    ))}
                </tbody>

            </Table>

            <div>
                <CommonPagination
                    currentPage={currentPage}
                    totalPages={totalPages}
                    setCurrentPage={setCurrentPage}
                    recordsPerPage={recordsPerPage}
                    setRecordsPerPage={setRecordsPerPage}
                />

                {/* <Pagination className="justify-content-center">

                <label className="justify-content-center">
                    Records:
                </label>

                <select className="records-dropdown"
                    value={recordsPerPage}
                    onChange={(e) => {
                        setRecordsPerPage(
                            Number(e.target.value)
                        );
                        setCurrentPage(1);
                    }}

                >
                    <option value={5}>5</option>
                    <option value={10}>10</option>
                    <option value={20}>20</option>
                    <option value={50}>50</option>
                </select>
                <Pagination.Prev
                    disabled={currentPage === 1}
                    onClick={() =>
                        setCurrentPage(
                            currentPage - 1
                        )
                    }
                />

               {[...Array(totalPages)].map((_, index) => (
                    <Pagination.Item
                        key={index + 1}
                        active={
                            currentPage === index + 1
                        }
                        onClick={() =>
                            setCurrentPage(index + 1)
                        }
                    >
                        {index + 1}
                    </Pagination.Item>
                ))}

                <Pagination.Next
                    disabled={
                        currentPage === totalPages
                    }
                    onClick={() =>
                        setCurrentPage(
                            currentPage + 1
                        )
                    }
                />

            </Pagination> */}
            </div>
        </div>
    );
}

export default AuthorList;