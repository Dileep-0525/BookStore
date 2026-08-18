import Table from 'react-bootstrap/Table';
import React from 'react';
import "../Styles/Home.css";
import { HiPlusCircle } from "react-icons/hi2";
import { CiEdit } from "react-icons/ci";
import { useState, useEffect } from "react";
import { MdDeleteOutline } from "react-icons/md";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import Modal from 'react-bootstrap/Modal';
//import FileModal from './FileModal';
import Button from 'react-bootstrap/Button';
import { getAllBooks } from "../../Services/booksService";

function BookList() {
	
	const [loading, setLoading] = useState(true);
	
 
 useEffect(() => {
        loadBooks();
    }, []);

const loadBooks = async () => {
		try {
			const data = await getAllBooks();
			setAuthors(data);
		} catch (error) {
			console.error(error);
		}
	};
	
	if (loading) {
		return <div>Loading...</div>;
	}

	if (error) {
		return <div>Error: {error}</div>;
	}
	return (
		<>
			<div style={{ display: 'flex', justifyContent: 'space-between', marginLeft: 30, marginRight: 30, marginBottom: '10px' }}>

				<span style={{ marginleft: 30, fontSize: 32 }}>
					Books
				</span>
				<span style={{ marginright: 30, marginTop: 10 }}><HiPlusCircle style={{ fontSize: '38px', margintop: '35px' }} onClick={() => navigate('/bookDetail')} /> </span>

			</div>
			<div>
				<Table responsive striped="rows" bordered="7">
					<thead style={{ textAlign: 'center' }}>
						<tr>
							<th>S.No</th>
							<th>Book Name</th>
							<th>Written By</th>
							<th>Description</th>
							<th>Publiced On</th>
							<th>Downloaded By</th>
							<th>File</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody text>
						{
							books.length > 0 ?
								books.map((book, index) => (
									<tr key={book.id}>
										<td>{index + 1}</td>
										<td>{book.name}</td>
										<td>{book.author}</td>
										<td>{book.description}</td>
										<td>{book.publishedOn}</td>
										<td>{book.numberOfDownloads}</td>
										<td>
										<Link onClick={show}>{book.fileName}</Link>
										 <FileModal byteArray={book.file} filename={book.fileName} fileType={book.fileType} />
										
										</td>
										<td>
											<div>
												<CiEdit style={{ fontSize: '24px', width: '50%' }} onClick={() => getById(book.id)} />

												<MdDeleteOutline style={{ fontSize: '24px', width: '50%' }} />
											</div>
										</td>
									</tr>
								)
								)
								//style={{width:50}}
								//style={{width:50}}
								: <div style={{ alignContent: 'center' }}><h1 >Loading</h1></div>
						}

					</tbody>
				</Table>
			</div>
			<div>
				<Modal show={show} onHide={handleClose} animation={false} >
					<Modal.Header closeButton>
						<Modal.Title> </Modal.Title>
					</Modal.Header>
					<Modal.Body>

						 <img src={dataUrl} alt={filename} />

					</Modal.Body>

					<Modal.Footer>
						<Button variant="secondary" onClick={handleClose}>
							Close
						</Button>
						<Button variant="primary" onClick={handleClose}>
							Download
						</Button>
					</Modal.Footer>
				</Modal>
			</div>

		</>
	);
}

export default BookList;