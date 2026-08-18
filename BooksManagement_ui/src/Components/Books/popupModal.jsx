import { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

function PopupModal(){
	const [show,setShow] = useState(false);
	const handleShow = () =>setShow(true);
	const handleClose = () => setShow(false);
	
	return (
		<>
		<div>
		<Modal show={show} onHide={handleClose} animation={false} >
			<Modal.Header closeButton>
				<Modal.Title> </Modal.Title>
			</Modal.Header>
			        <Modal.Body>
			        
			       {()=>handleShow}
			        
			        </Modal.Body>

		   <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
          <Button variant="primary" onClick={handleClose}>
            Download
          </Button>
        </Modal.Footer>
		
		
		</Modal>
		
		
		</div>
		</>
	)
	
}
export default PopupModal;