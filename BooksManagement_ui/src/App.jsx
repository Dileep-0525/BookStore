
import './App.css';
import './Components/Styles/Style1.css'
import Navbar from './Components/Home/Navbar'
import Home from './Components/Home/Home'
import Books from './Components/Books/books'
import PopUpModel from './Components/Books/popupModal';
import BookDetail from './Components/Books/bookDetail';
import Authors from './Components/Authors/Authors';
import AuthorDetail from './Components/Authors/AuthorDetail';
import AdminNav from './Components/NavBars/AdminNav';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from './Components/Login/Login';
import Welcome from './Components/LandingPage/Welcome';
import { useState } from 'react';
import AuthorNavBar from './Components/NavBars/AuthorNav';
import UserNavBar from './Components/NavBars/UserNav';
import AuthorList from './Components/Authors/AuthorList';
import EditAuthor from './Components/Authors/EditAuthor';
import BookList from './Components/Books/BookList';
import EditBook from './Components/Books/EditBook';

function App() {

	let[roleId,setRoleId] = useState(0);

	// const [roleId, setRoleId] = useState(null);
	roleId = Number(sessionStorage.getItem("roleId"));

	console.log(roleId)

	console.log("App RoleId:", roleId);
	return (

		<div className="App">

			<Router>


				{
					roleId === 1 ? (
						<AdminNav key="admin" setRoleId={setRoleId}/>
					) : roleId === 2 ? (
						<AuthorNavBar key="author" />
					) : roleId === 3 ? (
						<UserNavBar key="user" />
					) : (
						<Navbar key="guest" />
					)
				}


				<Routes>

					<Route path='/' element={<Home />} />
					<Route path='/home' element={<Home />} />
					<Route path='/books' element={<Books />} />
					<Route path='/popUpModal' element={<PopUpModel />} />
					<Route path='/bookDetail' element={<BookDetail />} />
					<Route path='/authors' element={<Authors />} />
					<Route path='/authorDetail' element={<AuthorDetail />} />
					<Route path="/login" element={<Login setRoleId={setRoleId}/>} />
					<Route path="/dashboard" element={<Welcome />} />
					<Route path="/bookList" element={<BookList />} />
					<Route path="/authorList" element={<AuthorList />} />
					<Route path="/authors/view/:id" element={<AuthorDetail />} />
					<Route path="/authors/edit/:id" element={<EditAuthor />} />
					{/* <Route path="/books/view/:id" element={<BookDetail />} /> */}
					<Route path="/books/edit/:id" element={<EditBook/>} />
					{/* <Route path="/adminDashboard" element={<AdminNavBar />} /> */}
					{/* <Route path="/authorDashboard" element={<AuthorNav />} />
					<Route path="/userDashboard" element={<UserNav />} /> */}
				</Routes>
			</Router>
		</div>

	);
}

export default App;
