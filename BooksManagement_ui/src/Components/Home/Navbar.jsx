import React from "react";
import { Navbar, Nav, Container, Form, Button, NavDropdown } from "react-bootstrap";
import { GiBullseye } from "react-icons/gi";
import "../Styles/Navbar.css";



const AppNavbar = () => {

  return (
    <>
      <div>

      </div>
      <Navbar expand="lg" className="app-navbar" sticky="top">
        <Container>
          <div>
            <Navbar.Brand href="/" className="brand-logo">
              Stay Focus
              <GiBullseye className="logo-icon" />
            </Navbar.Brand>
          </div>
          <br />



          <Navbar.Toggle aria-controls="main-navbar" />

          <Navbar.Collapse id="main-navbar">
            <Nav className="mx-auto">
              <Nav.Link href="/home">Home</Nav.Link>
              <Nav.Link href="/books">Books</Nav.Link>
              <Nav.Link href="/authors">Authors</Nav.Link>
              {/* <NavDropdown title="Categories" id="category-dropdown">
              <NavDropdown.Item href="/books">
                Books
              </NavDropdown.Item>

              <NavDropdown.Item href="/authors">
                Authors
              </NavDropdown.Item>
            </NavDropdown> */}

              <Nav.Link href="/about">About</Nav.Link>
              
             
                <Form className="search-form">
                  <Form.Control
                    type="search"
                    placeholder="Search books..."
                    className="me-2"
                  />
                  <Button variant="light">
                    Search
                  </Button>
                  
                  
                  <Button href="/login" variant="light">
                    Login
                  </Button>
                  {/* <Button
                    variant="danger"
                    onClick={handleLogout}
                  >
                    Logout
                  </Button> */}
                </Form>
            
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
    </>
  );
};

export default AppNavbar;