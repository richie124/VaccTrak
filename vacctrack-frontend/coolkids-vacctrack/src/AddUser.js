import { Form, Button } from 'react-bootstrap';
import { useInput } from './hooks/input-hook';

function AddUser ({  SERVICE_URL, handleClose }) {
  const { value:username, bind:bindUsername, reset:resetUsername } = useInput('');
  const { value:password, bind:bindPassword, reset:resetPassword } = useInput('');
  
  const handleSubmit = async (e) => {
    e.preventDefault();
    const user = {
        "name": username,
        "password": password
    }
    console.log(user);


    fetch(SERVICE_URL + '/createVaccCenter/', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(user),
    })
    .then(response => response.json())
    .then(data => {
      console.log('Add User - Success:', data);
      //setMarkers([...markers, user]);
      handleClose();
    })
    .catch((error) => {
      console.log('Add Center - Error:', error);
    });


    resetUsername();
    resetPassword();

  }

  return (
    <div>
      <Form onSubmit={handleSubmit}>
                  <Form.Group controlId="userame">
                      <Form.Label>Username:</Form.Label>
                      <Form.Control type="text" placeholder="Username" {...bindUsername}/>
                  </Form.Group>
                  <Form.Group controlId="password">
                      <Form.Label>Password:</Form.Label>
                      <Form.Control type="password" placeholder="Password" {...bindPassword} />
                  </Form.Group>
                  <Button variant="primary" type="submit">
                      Submit
                  </Button>
      </Form>
    </div>
  );
}

export default AddUser;