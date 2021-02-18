import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { Form, Button } from 'react-bootstrap';
import { useInput } from './hooks/input-hook';

var globalSetFormErrors = () => {};
var globalSERVICE_URL = '';

async function loginUser(credentials) {

  return fetch(globalSERVICE_URL + '/AdminPortal/Login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(credentials)
  })
    .then(data => data.json())
    .catch((error) => {
      console.log('Login - Error', error);
      globalSetFormErrors('Username or Password not recognized, please try again');
  });
 }

export default function Login({ setToken, setMarkers, markers, handleClose, setCreateUser, SERVICE_URL }) {
  const { value:userName, bind:bindUsername, reset:resetUsername } = useInput('');
  const { value:password, bind:bindPassword, reset:resetPassword } = useInput('');
  const [formErrors, setFormErrors] = useState(null);
  globalSERVICE_URL = SERVICE_URL;
  globalSetFormErrors = setFormErrors;
  

  const handleSubmit = async e => {
    e.preventDefault();
    const token = await loginUser({ //send in username and pass when calling loginUser and set it in 'token'
      userName,
      password,
    });
    console.log(token);
    return;
    if (null !== token.userName) {
      setToken(token);    // send succesfull token to setToken()

      setMarkers(markers.filter(marker => token.vaccCenterAccesses.indexOf(marker.id) >= 0));
      handleClose();
    }

  }
  return(
    <div className="login-wrapper">
    {/* <form onSubmit={handleSubmit}>
      <label>
        <p>Username</p>
        <input type="text" onChange={e => setUserName(e.target.value)} />
      </label>
      <label>
        <p>Password</p>
        <input type="password" onChange={e => setPassword(e.target.value)}/>
      </label>
      <div>
        <button type="submit">Submit</button>
      </div>
    </form> */}
    <Form onSubmit={handleSubmit}>
      <Form.Group controlId="username">
        <Form.Label>Username:</Form.Label>
        <Form.Control type="text" placeholder="Username" {...bindUsername} isInvalid={!!formErrors}/>
      </Form.Group>
      <Form.Group controlId="password1">
        <Form.Label>Password:</Form.Label>
        <Form.Control type="password" placeholder="Password" {...bindPassword} isInvalid={!!formErrors}/>
        <Form.Control.Feedback type="invalid">
          {formErrors}
        </Form.Control.Feedback>
      </Form.Group>
      
      <Button variant="primary" type="submit">
        Submit
      </Button>
    </Form>
      New to Vacctrack? <button id="clickHere" onClick={()=> setCreateUser(true)}>Click here!</button>
    </div>
  )
}
Login.propTypes = {
  setToken: PropTypes.func.isRequired
}