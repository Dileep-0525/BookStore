import React from "react";
import { Navbar, Nav, Container, Form, Button, NavDropdown } from "react-bootstrap";
import { GiBullseye } from "react-icons/gi";
import "../Styles/Navbar.css";

const handleLogout = () => {
    localStorage.clear();
    window.location.href = "/login";
};

const UserNavBar = () => {
  const isLoggedIn =
    localStorage.getItem("isLoggedIn") === "true";
  console.log(isLoggedIn)
  return (
    <>
      <div>

      </div>
      <Navbar expand="lg" className="app-navbar" sticky="top">
        <Container>
          <div>
            <Navbar.Brand href="/dashboard" className="brand-logo">
              Stay Focus
              <GiBullseye className="logo-icon" />
            </Navbar.Brand>
          </div>
          <br />



          <Navbar.Toggle aria-controls="main-navbar" />

          <Navbar.Collapse id="main-navbar">
            <Nav className="mx-auto">
              <Nav.Link href="/dashboard">Dashboard</Nav.Link>
              <Nav.Link href="/authors">Authors</Nav.Link>
              <Nav.Link href="/books">Books</Nav.Link>
             
              {/* <NavDropdown title="Categories" id="category-dropdown">
              <NavDropdown.Item href="/books">
                Books
              </NavDropdown.Item>

              <NavDropdown.Item href="/authors">
                Authors
              </NavDropdown.Item>
            </NavDropdown> */}

            
              
             
                <Form className="search-form">
                  <Form.Control
                    type="search"
                    placeholder="Search books..."
                    className="me-2"
                  />
                  <Button variant="light">
                    Search
                  </Button>
                
                 <Button
                    variant="light"
                    onClick={handleLogout}
                  >
                    Logout
                  </Button>
                 
                </Form>
            
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
    </>
  );
};

export default UserNavBar;