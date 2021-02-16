import React, { useState } from 'react';
import PropTypes from 'prop-types';
const SERVICE_URL = "http://localhost:8080/VaccTrak";

async function loginUser(credentials) {
  return fetch(SERVICE_URL + 'AdminPortal/Login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(credentials)
  })
    .then(data => data.json())
    .catch((error) => {
      alert("FAILURE!");
  });
 }

export default function Login({ setToken }) {
  const [username, setUserName] = useState();
  const [password, setPassword] = useState();
  const handleSubmit = async e => {
    e.preventDefault();
    const token = await loginUser({ //send in username and pass when calling loginUser and set it in 'token'
      username,
      password
    });
    setToken(token);    // send succesfull token to setToken()
  }
  return(
    <div className="login-wrapper">
    <form onSubmit={handleSubmit}>
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
    </form>
    </div>
  )
}
Login.propTypes = {
  setToken: PropTypes.func.isRequired
}