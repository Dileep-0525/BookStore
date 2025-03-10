import React, { useState, useEffect } from "react";
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Button from 'react-bootstrap/Button';
import { useLocation, useNavigate } from "react-router-dom";


function AuthorDetails() {

	const location = useLocation();
	const navigate = useNavigate();
	const [photo, setPhoto] = useState(null);
	const [message, setMessage] = useState('');
	const [error, setError] = useState('');
	const [selectedDate, setSelectedDate] = useState(null);
	const [authorDetails, setAuthorDetails] = useState({
		name: '',
		about: '',
		dateOfBirth: ''
	})

	const handleFileChange = (event) => {
		const selectedFile = event.target.files[0];
		if (selectedFile) {
			const fileType = selectedFile.type;
			const validTypes = ['image/png', 'image/jpeg'];
			// Check if the file has a .pdf extension
			if (validTypes.includes(fileType)) {
				setPhoto(selectedFile);
			} else {
				alert('Please upload a valid image/png/jpeg images.');
				setPhoto(null);
			}
		}
		//    setFile(event.target.files[0]);
	};

	const handleCancel = () => {
		navigate('/authors') // Navigate back to the previous page
	};

	const handleDateChange = (event) => {
		const selectedDate = event.target.value;
		setSelectedDate(selectedDate);
		console.log(selectedDate)
		setAuthorDetails(prevState => ({
			...prevState,
			dateOfBirth: selectedDate,
		}));
	};

	useEffect(() => {
		if (location.state) {
			const getById = (e) => {
				const id = location.state;
				fetch(`http://localhost:8080/authors/getOne/${id}`, {
					method: 'POST'
				}).then(response => {
					if (!response.ok) {
						throw new Error('Network response was not ok');
					}
					return response.json();
				}).then(
					data => {
						setAuthorDetails(data);
					}
				).catch(err => {
					console.log(err.message);
				});
			}
			getById(location.state);
		}
	}, [location.state]);

	const handleSubmit = (e) => {
		e.preventDefault();
		const formData = new FormData();
		formData.append('photo', photo);
		formData.append('author', JSON.stringify(authorDetails));

		fetch('http://localhost:8080/authors/save', {
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
				setMessage('Author details saved successfully!');
				setError(''); // Clear error message
				// Clear form fields
				setAuthorDetails({ name: '', about: '', DateOfBirth: '' });
				if (data != null) {
					navigate('/authors')
				}
			})
			.catch(error => {
				console.error('Error:', error);
				setError('Failed to save author details.'); // Show error message
				setMessage(''); // Clear success message
			});
	};

	const handleChange = (e) => {
		const { name, value } = e.target;
		setAuthorDetails(prevState => ({
			...prevState,
			[name]: value,
		})
		)
	}

	return (
		<>
			<div style={{ alignContent: 'center', textAlign: 'center', marginTop: '2%' }} >
				<div >
					<h2>
						{location.state === null ? "Add Author" : "Update Author"}
					</h2>
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
										value={authorDetails.name}
										onChange={handleChange}
									/>
								</Col>
							</Row>
							<br />
							<Row style={{ border: '2' }}>
								<Form.Label column="lg" lg={2} bordercolor="#007BFF">
									About :
								</Form.Label>
								<Col>
									<Form.Control
										size="lg"
										type="text"
										name="description"
										value={authorDetails.about}
										onChange={handleChange}
									/>
								</Col>
							</Row>
							<br />
							<div style={{ alignItems: 'center' }}>
								<Row style={{ fontSize: '20px' }}>
									Date Of Birth :
									<Col style={{ marginLeft: '-35%' }}>
										<input type="date" style={{ borderRadius: '5px', borderColor: 'lightgrey' }} value={authorDetails.dateOfBirth} onChange={handleDateChange} />
									</Col>
								</Row>
							</div>
							<br />
							<div style={{ alignItems: 'center', marginLeft: '10%', textcolor: 'black' }}>
								<Row style={{ fontSize: '20px' }}>
									File :
									<Col style={{ marginLeft: '-25%' }}>
										<input type="file" onChange={handleFileChange} />
									</Col>
								</Row>
							</div>
							<br />
							<div >
								<span style={{ margin: '10px' }}>

									<Button variant="primary" size="lg" type="submit" style={{ color: 'black' }}>
										{(location.state === null) ? "Save" : "Upate"}


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
	)

}

export default AuthorDetails;