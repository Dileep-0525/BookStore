import Table from 'react-bootstrap/Table';
import React from 'react';
import "../Styles/HomeStyles.css";
import { HiPlusCircle } from "react-icons/hi2";
import { CiEdit } from "react-icons/ci";
import { useState,useEffect } from "react";
import { MdDeleteOutline } from "react-icons/md";
import { useNavigate } from "react-router-dom";
import { VscOpenPreview } from "react-icons/vsc";


function AuthorList() {
const [books, setBooks] = useState([]);
const [loading, setLoading] = useState(true);
const [error, setError] = useState(null);
const navigate = useNavigate();


const getById = (id) =>{
navigate('/authorDetail',{state:id});
}

useEffect(()=>{
		fetch('http://localhost:8080/authors/all',{
			method:'POST',
		})
		.then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        setBooks(data);
        setLoading(false);
      })
      .catch(err => {
        setError(err.message);
        setLoading(false);
      });
  },[]);
  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }
  return (
	  <>
	  <div style={{ display: 'flex', justifyContent: 'space-between',marginLeft:30,marginRight:30, marginBottom: '10px' }}>
	 
	 <span style={{marginleft:30,fontSize:32}}>
	 Authors
	 </span>
	 <span style={{marginright:30,marginTop:10}}><HiPlusCircle  style={{ fontSize: '38px' ,margintop:'35px'}} onClick={()=> navigate('/authorDetail')}/> </span>
	
	   </div>
	  <div>
    <Table responsive striped="rows" bordered="7">
      <thead style={{ textAlign: 'center' }}>
        <tr>
          <th>S.No</th>
       	  <th>Author Name</th>
       	  <th>About</th>
		  <th>Date Of Birth</th>
       	  <th>Photo</th>
       	  <th>Actions</th>
        </tr>
      </thead>
      <tbody text>
		{
			books.length>0?
			books.map((author, index)=>(
			<tr key={author.id}>
			<td>{index + 1}</td>
	  		<td>{author.name}</td>
	  		<td>{author.about}</td>
	  		<td>{author.dateOfBirth}</td>
	  		<td>{author.fileName}</td>
	  		<td>
	  		<div >
	  		
	  		<VscOpenPreview size={'25px'} style={{width:'33%',marginLeft:'-2px'}} onClick={()=> getById(author.id)}/>
	  		
	  		<CiEdit size={'25px'} style={{width:'33%',marginLeft:'-4px',marginRight:'-4px' }} onClick={()=> getById(author.id)}/>
	  	
	  		<MdDeleteOutline size={'25px'} style={{width:'33%',marginRight:'-2px'}} />
	  		</div>
	  		</td>
		  	</tr>
			)
		)
		//style={{width:50}}
		//style={{width:50}}
		:<div style={{alignContent:'center'}}><h1 >Loading</h1></div>
		}

      </tbody>
    </Table>
    </div>
    </>
  );
}

export default AuthorList;