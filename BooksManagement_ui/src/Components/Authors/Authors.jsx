import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import { useEffect,useState } from 'react';
import {getAllAuthors} from '../../Services/authorService'
import "../Styles/Author.css";
function Authors() {
   const [authors, setAuthors] = useState([]);

    useEffect(() => {
        loadAuthors();
    }, []);

    const loadAuthors = async () => {
        try {
            const data = await getAllAuthors();
            setAuthors(data);
        } catch (error) {
            console.error(error);
        }
    };

  return (
	<div className="container mt-5">
    <Row xs={2} md={3} className="g-3">
      {authors.map((author) => (
        <Col key={author.id}>
          <Card className="author-card">
            {/* <Card.Img variant="top" src="holder.js/100px160" /> */}
			 <Card.Img variant="top" src="#"  className="author-image" />
            <Card.Body className="author-body">
              <Card.Title className="author-title">{author.name}</Card.Title>
              <Card.Text>{author.about}
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
      ))}
    </Row>
	</div>
  );
}

export default Authors;