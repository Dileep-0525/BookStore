import React, { useState, useEffect } from "react";
import Card from 'react-bootstrap/Card';
import { useLocation, useNavigate } from "react-router-dom";


function AuthorDetails(){
	
	const location = useLocation();
	const navigate = useNavigate();
	
	useEffect(()=>{
		if(location.state){
			const getById = (e)=>{
				const id = location.state;
				const fileName = location.state;
				fetch(`http://localhost:8080/books/getOne/${id}/${fileName}`,{
					method:'POST'
				}).then(response=>{
					if(!response.ok){
						throw new Error('Network response was not ok');
					}
					return response.json();
				}).then(
					data=>{
						setAuthor(data);
					}
				).catch(err => {
						console.log(err.message);
				});
			}
			getById(location.state);
		}
	},[location.state]);
	
	return (
		<>
		<div>
		
		
		
		</div>
		<div>
		<Card style={{ width: '18rem' }}>
      <Card.Img variant="top" src="holder.js/100px180" />
      <Card.Body>
        <Card.Title>Card Title</Card.Title>
        <Card.Text>
          Some quick example text to build on the card title and make up the
          bulk of the card's content.
        </Card.Text>
      </Card.Body>
    </Card>
		
		
		</div>
		
		
		
		</>
	)
	
}

export default AuthorDetails;