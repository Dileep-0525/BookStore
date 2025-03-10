import React, { useState, useEffect } from "react";
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Button from 'react-bootstrap/Button';
import { useLocation, useNavigate } from "react-router-dom";
import Select from "react-select";

function Savebook() {
	const location = useLocation();
	console.log(location.state);
	const navigate = useNavigate();
	const [allAuthors, setAllAuthors] = useState([]);
	const [authorList, setAuthorList] = useState([]);

	const [author, setAuthor] = useState(false);
	const [bookDetails, setBookDetails] = useState({
		name: '',
		newauthor:false,
		authorName: '',
		description: '',
		publishedOn: '',
		file: ''
	});
	const [message, setMessage] = useState('');
	const [error, setError] = useState('');
	const [file, setFile] = useState(null);
	const [selectedDate, setSelectedDate] = useState(null);

	useEffect(() => {
		if (location.state) {
			const getById = (e) => {
				const id = location.state;
				fetch(`http://localhost:8080/books/getOne/${id}`, {
					method: 'POST',
				}).then(response => {
					if (!response.ok) {
						throw new Error('Network response was not ok');
					}
					return response.json();
				})
					.then(data => {
						setBookDetails(data);
					})
					.catch(err => {
						console.log(err.message);
					});
			}
			getById(location.state);
		}
	}
		, [location.state]);

	const handleCheckboxChange = (value, e) => {
		console.log(value, "value");
				setAuthor(value);	
		
		setBookDetails(prevState => ({
			...prevState,
			newauthor:value
		})
		)
	};

	useEffect(() => {
		getAllAuthors();
	}, [])


	//	const id = location.state; 	}

	const handleChange = (e) => {
		const { name, value } = e.target;
		setBookDetails(prevState => ({
			...prevState,
			[name]: value
		}));
	};


	const handleCancel = () => {
		navigate('/books') // Navigate back to the previous page
	};

	const handleDateChange = (event) => {
		const selectedDate = event.target.value;
		setSelectedDate(selectedDate);
		console.log(selectedDate)
		setBookDetails(prevState => ({
			...prevState,
			publishedOn: selectedDate,
		}));
	};
	const [authorSelect, setAuthorSelect] = useState()
	const handleAuthorSelection = (selection) => {
		const authorName = selection.label;
		
		setAuthorSelect(selection);
		console.log()
			setBookDetails(prevState => ({
			...prevState,
			authorName:authorName,
		}));
	};
		
	/*const handleAuthorSelection = (selection) => {
		setAuthorSelect(selection)
	}*/

	const handleFileChange = (event) => {
		const selectedFile = event.target.files[0];
		if (selectedFile) {
			// Check if the file has a .pdf extension
			if (selectedFile.type === 'application/pdf') {
				setFile(selectedFile);
			} else {
				alert('Please upload a valid PDF file.');
				setFile(null);
			}
		}
		//    setFile(event.target.files[0]);
	};

	const getAllAuthors = () => {
		fetch(`http://localhost:8080/authors/all`, {
			method: 'POST',
		}).then(response => {
			if (!response.ok) {
				throw new Error('Network response was not ok');
			}
			return response.json();
		})
			.then(data => {
				setAuthorList(data);
				setAllAuthors(data.map(e => e.id));
				console.log(allAuthors, "AllAuthors");
				console.log(authorList, "authorList");
			})
			.catch(err => {
				console.log(err.message);
			});
	}



	const options = authorList && authorList.map((option) => ({
		value: option.id,
		label: option.name,
	}));

	const handleSubmit = (e) => {
		e.preventDefault();
		const formData = new FormData();
		formData.append('file', file);
		formData.append('book', JSON.stringify(bookDetails));

		fetch('http://localhost:8080/books/save', {
			method: 'POST',
			body: formData,
		}).then(response => {
			if (!response.ok) {
				throw new Error('Network response was not ok');
			}
			return response.json();
		})
			.then(data => {
				console.log('Success:', data);
				console.log('Success:', data);
				setMessage('Book details saved successfully!');
				setError(''); // Clear error message
				// Clear form fields
				setBookDetails({ name: '', authorName: '', description: '', publishedOn: '' });
				if (data != null) {
					navigate('/books')
				}
			})
			.catch(error => {
				console.error('Error:', error);
				setError('Failed to save book details.'); // Show error message
				setMessage(''); // Clear success message
			});
	};
	return (
		<>
			<div style={{ alignContent: 'center', textAlign: 'center', marginTop: '2%' }} >
				<div >
					<h2> Add Book Details</h2>
				</div>
				<div style={{ borderbox: '3px' }}>
					<div style={{ width: '60%', alignItems: 'center', marginLeft: '20%', marginRight: '10%', marginTop: '2%', fontSize: '20px' }}>

						<Form onSubmit={handleSubmit} style={{ border: '2' }}>
							<Row >
								<Form.Label column="lg" lg={2} >
									Name :
								</Form.Label>
								<Col>
									<Form.Control
										size="lg"
										type="text"
										name="name"
										value={bookDetails.name}
										onChange={handleChange}
									/>
								</Col>
							</Row>
							<br />
							<div>
								<Row >
									New Author :
									<span style={{ marginLeft: '-40%', marginRight: '-40%', marginBottom: '2%' }}>
										<input
											size="lg"
											type="checkbox"
											//name="author"
											//checked={author}
											onChange={(e) => handleCheckboxChange(e.target.checked)}
										/>
									</span>
									<Col >
									
										{author === false && (

											<Select
												options={options}
												/*
												value={options && options.filter((elem) => {
													return authorSelect.some((ele) => {
														return ele == elem.value
													})
												})}
												*/
												
												value={authorSelect}
												onChange={handleAuthorSelection}
												isClearable
											/>
										)
										}
										{author === true && (

											<Form.Control
												size="lg" lg={2}
												type="text"
												name="authorName"
												value={ (location.state===null)? null:bookDetails.author}
												onChange={handleChange}
											/>
										)
										}
									</Col>
								</Row>
							</div>

							<br />
							<Row style={{ border: '2' }}>
								<Form.Label column="lg" lg={2} bordercolor="#007BFF">
									Description :
								</Form.Label>
								<Col>
									<Form.Control
										size="lg"
										type="text"
										name="description"
										value={bookDetails.description}
										onChange={handleChange}
									/>
								</Col>
							</Row>
							<br />
							<div style={{ alignItems: 'center' }}>
								<Row style={{ fontSize: '20px' }}>
									Publiced On :
									<Col style={{ marginLeft: '-300px' }}>
										<input type="date" style={{ borderRadius: '5px', borderColor: 'lightgrey' }} value={bookDetails.publishedOn} onChange={handleDateChange} />
									</Col>
								</Row>
							</div>
							<br />
							<div style={{ alignItems: 'center', marginLeft: '10%', textcolor: 'black' }}>
								<Row style={{ fontSize: '20px' }}>
									File :
									<Col style={{ marginLeft: '-20%' }}>
										<input type="file" onChange={handleFileChange} />
									</Col>
								</Row>
							</div>
							<br />
							<div >
								<span style={{ margin: '10px' }}>
								
									<Button variant="primary" size="lg" type="submit" style={{ color: 'black' }}>
										{ (location.state===null)? "Save":"Upate"}
									
										
									</Button>
								</span>
								<span>
									<Button variant="primary" size="lg" type="button" onClick={handleCancel} style={{ background: 'darkgrey', color: 'black' }}>
										Cancle
									</Button>
								</span>
							</div>
						</Form>
					</div>
				</div>
			</div>
		</>
	);
}
export default Savebook;
