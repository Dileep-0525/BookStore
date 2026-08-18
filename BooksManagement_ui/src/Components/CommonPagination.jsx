import Pagination from "react-bootstrap/Pagination";
import './Styles/CommonPagination.css';

function CommonPagination({
    currentPage,
    totalPages,
    setCurrentPage,
    recordsPerPage,
    setRecordsPerPage

}) {

    return (
        <div className="pagination-container">

            <div className="records-section">

                <label>Records:</label>

                <select
                    className="records-dropdown"
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

            </div>

            <Pagination>

                <Pagination.Prev
                    disabled={currentPage === 1}
                    onClick={() =>
                        setCurrentPage(currentPage - 1)
                    }
                />

                {currentPage > 3 && (
                    <>
                        <Pagination.Item
                            onClick={() =>
                                setCurrentPage(1)
                            }
                        >
                            1
                        </Pagination.Item>

                        <Pagination.Ellipsis />
                    </>
                )}

                {Array.from(
                    { length: totalPages },
                    (_, i) => i + 1
                )
                    .filter(
                        page =>
                            page >= currentPage - 1 &&
                            page <= currentPage + 1
                    )
                    .map(page => (
                        <Pagination.Item
                            key={page}
                            active={
                                currentPage === page
                            }
                            onClick={() =>
                                setCurrentPage(page)
                            }
                        >
                            {page}
                        </Pagination.Item>
                    ))}

                {currentPage < totalPages - 2 && (
                    <>
                        <Pagination.Ellipsis />

                        <Pagination.Item
                            onClick={() =>
                                setCurrentPage(
                                    totalPages
                                )
                            }
                        >
                            {totalPages}
                        </Pagination.Item>
                    </>
                )}

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

            </Pagination>

        </div>
    );
}

export default CommonPagination;