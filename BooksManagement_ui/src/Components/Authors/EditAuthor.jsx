import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import '../Styles/EditAuthor.css'
import { getAllAuthors, getAuthorById, updateAuthor } from "../../Services/authorService";

function EditAuthor() {

    const [selectedFile, setSelectedFile] = useState(null);
   const navigate = useNavigate();
    const handleFileChange = (event) => {

        const file = event.target.files[0];

        if (file) {

            setSelectedFile(file);

            setAuthor({
                ...author,
                fileName: file.name,
                fileType: file.type
            });
        }
    };

    const { id } = useParams();

    const [author, setAuthor] = useState({
        name: "",
        country: "",
        books: "",
        birthYear: ""
    });

    useEffect(() => {
        loadAuthor();
    }, []);



    const loadAuthor = async () => {

        // API Call
        const data = await getAuthorById(id);
        console.log(data);
        setAuthor(data);
    };

    const handleChange = (event) => {

        const { name, value } = event.target;

        setAuthor({
            ...author,
            [name]: value
        });
    };

    const getAuthorImage = () => {

    if (selectedFile) {
        return URL.createObjectURL(selectedFile);
    }

    if (author?.photo && author?.fileType) {
        return `data:${author.fileType};base64,${author.photo}`;
    }

    return "/images/default-profile.png";
};

    const handleSubmit = async (event) => {

        event.preventDefault();

        console.log(author);

        // await updateAuthor(id, author);

        const response = await updateAuthor(id,author,selectedFile);
        console.log(response.sucess,"status");
      if(response !=null){
        navigate("/authorList")
      }
       // alert("Author Updated Successfully");
    };

    return (
        <div className="edit-author-container">
            <div className="edit-author-card">

                <h2 className="edit-author-title">
                    Edit Author
                </h2>

                <Form onSubmit={handleSubmit}>
                    <div className="author-image-container">

                        <img
                            src={getAuthorImage()}
                            alt={author.name}
                            className="author-image"
                        />

                        <h5 className="author-name-preview">
                            {author.name}
                        </h5>

                    </div>
                    <Form.Group className="mb-3">

                        <Form.Label>
                            Change Photo
                        </Form.Label>

                        <Form.Control
                            type="file"
                            accept="image/*"
                            onChange={handleFileChange}
                        />

                    </Form.Group>


                    <Form.Group className="mb-3">
                        <Form.Label>
                            Name
                        </Form.Label>

                        <Form.Control
                            type="text"
                            name="name"
                            value={author.name}
                            onChange={handleChange}
                        />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>
                            Birth Year
                        </Form.Label>

                        <Form.Control
                            type="date"
                            name="birthYear"
                            value={author.dateOfBirth}
                            onChange={handleChange}
                        />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>
                            About
                        </Form.Label>

                        <Form.Control
                            type="about"
                            name="about"
                            value={author.about}
                            onChange={handleChange}
                        />
                    </Form.Group>

                    <Form.Group className="mb-2">
                        <Form.Label>
                            Books Published
                        </Form.Label>

                        <Form.Control
                            type="number"
                            name="books"
                            value={author.books}
                            onChange={handleChange}
                        />
                    </Form.Group>



                    <Button
                        type="submit"
                        className="update-btn"
                    >
                        Update Author
                    </Button>

                </Form>

            </div>

        </div>
    );
}

export default EditAuthor;