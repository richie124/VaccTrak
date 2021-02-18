import { Form, Button } from 'react-bootstrap';
import { useInput } from './hooks/input-hook';
import { useState} from 'react';
import { Multiselect } from 'multiselect-react-dropdown';

function AddUser ({  SERVICE_URL, setCreateUser, vaccCenters }) {
  const { value:username, bind:bindUsername, reset:resetUsername } = useInput('');
  const { value:password, bind:bindPassword, reset:resetPassword } = useInput('');
  const { value:password2, bind:bindPassword2, reset:resetPassword2 } = useInput('');
  const [vaccCenterAccesses, setVaccCenterAccesses] = useState([]);
  const [formErrors, setFormErrors] = useState({});

  const validateForm = (user) => {
    let errors = {isValid: true};

    if (!user.userName) {
      errors.userName = "Please enter a username";
      errors.isValid = false;
    }

    if(!user.password) {
      errors.password = "Please enter a password"
      errors.isValid = false;
    } else if (user.password !== user.password2){
      errors.password2 = "Passwords do not match."
      errors.isValid = false;
    }

    return errors;
  }
  
  const handleSubmit = async (e) => {
    e.preventDefault();
    setFormErrors({});
    const user = {
        "userName": username,
        "password": password,
        "vaccCenterAccesses": vaccCenterAccesses.map(center => center.id)
    }
    // console.log(user);

    let validationErrors = validateForm({...user, password2});
    if (!validationErrors.isValid) {
      setFormErrors(validationErrors);
      return;
    }


    fetch(SERVICE_URL + '/AdminPortal/CreateUser', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(user),
    })
    .then(response => response.json())
    .then(data => {
      console.log('Add User - Success:', data);
      resetUsername();
      resetPassword();
      resetPassword2();
      setCreateUser(false);
    })
    .catch((error) => {
      console.log('Add User - Error:', error);
      setFormErrors({username: "The username you entered has already been selected, please select a different username."});
    });
  }
  
  const handleSelect = (selectedList, selectedItem) => {
    setVaccCenterAccesses(selectedList);
  }


  return (
    <div>
      <Form onSubmit={handleSubmit}>
                  <Form.Group controlId="username">
                      <Form.Label>Username:</Form.Label>
                      <Form.Control type="text" placeholder="Username" {...bindUsername} isInvalid={!!formErrors.username}/>
                      <Form.Control.Feedback type="invalid">
                      {formErrors.username}
                      </Form.Control.Feedback>
                  </Form.Group>
                  <Form.Group controlId="password1">
                      <Form.Label>Password:</Form.Label>
                      <Form.Control type="password" placeholder="Password" {...bindPassword} isInvalid={!!formErrors.password}/>
                      <Form.Control.Feedback type="invalid">
                      {formErrors.password}
                      </Form.Control.Feedback>
                  </Form.Group>
                  <Form.Group controlId="password2">
                      <Form.Label>Re-enter Password:</Form.Label>
                      <Form.Control type="password" placeholder="Re-Enter Password" {...bindPassword2} isInvalid={!!formErrors.password2}/>
                      <Form.Control.Feedback type="invalid">
                      {formErrors.password2}
                      </Form.Control.Feedback>
                  </Form.Group>
                  <Form.Group controlId="exampleForm.ControlSelect2">
                    <Form.Label>VaccCenter Access</Form.Label>
                      <Multiselect 
                      options={vaccCenters}
                      selectedValues={vaccCenterAccesses}
                      onSelect={handleSelect}
                      onRemove={handleSelect}
                      displayValue="name"/>
                  </Form.Group>
                  <Button variant="primary" type="submit">
                      Submit
                  </Button>
                  <Button onClick={()=>setCreateUser(false)}> Return to Login</Button>
      </Form>
    </div>
  );
}

export default AddUser;