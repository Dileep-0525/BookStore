
//import './App.css';
import './Components/Styles/Style1.css'
import Navbar from './Components/Home/Navbar'
import Home from './Components/Home/Home'
import Books from './Components/Books/books'
import PopUpModel from './Components/Books/popupModal';
import BookDetail from './Components/Books/bookDetail'
import Authors from './Components/Authors/Authors'
import AuthorDetail from './Components/Authors/AuthorDetail'
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";


function App() {
   return (
   <>
    <div className="App">
    <Navbar/>
    <Router>
    		<Routes>
    			  	<Route path='/home' element={<Home/>}/>
    				<Route path='/books' element={<Books />}/>
    				<Route path='/popUpModal' element={<PopUpModel />}/>
    				<Route path='/bookDetail' element={<BookDetail/>}/>
    				<Route path='/authors' element={<Authors/>}/>
    				<Route path='/authorDetail' element={<AuthorDetail/>}/>
   			 </Routes>
    	</Router>
    </div>
  </>
  );
}

export default App;
