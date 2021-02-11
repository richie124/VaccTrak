import React, { useState, useCallback } from 'react';
import { Form, Button } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useInput } from './hooks/input-hook';

function Create ({ getVaccCenterLatLong }) {
  const [query, setQuery] = useState("");
  const { value:name, bind:bindName, reset:resetName } = useInput('');
  const { value:address, bind:bindAddress, reset:resetAddress } = useInput('');
  const { value:state, bind:bindState, reset:resetState } = useInput('');
  const { value:zipCode, bind:bindZipCode, reset:resetZipCode } = useInput('');
  const { value:city, bind:bindCity, reset:resetCity } = useInput('');
  const { value:phone, bind:bindPhone, reset:resetPhone } = useInput('');
  const { value:firstVacc, bind:bindFirstVacc, reset:resetFirstVacc } = useInput('');
  const { value:secondVacc, bind:bindSecondVacc, reset:resetSecondVacc } = useInput('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    const vaccCenter = {
        "address": address,
        "city": city,
        "state": state,
        "zipcode": zipCode
    }

    const { lat, lng } = await getVaccCenterLatLong(vaccCenter);
    setQuery(`"${name}", "${address}", "${city}", "${state}", "${zipCode}", "${phone}", ${firstVacc}, ${secondVacc}, "${lat}", "${lng}";`);
    resetName();
    resetAddress();
    resetState();
    resetZipCode();
    resetCity();
    resetPhone();
    resetFirstVacc();
    resetSecondVacc();
  }

  return (
    <div>
      <Form onSubmit={handleSubmit}>
                  <Form.Group controlId="centerName">
                      <Form.Label>VaccCenter:</Form.Label>
                      <Form.Control type="text" placeholder="Name" {...bindName}/>
                  </Form.Group>
                  <Form.Group controlId="centerAddress">
                      <Form.Label>Address:</Form.Label>
                      <Form.Control type="text" placeholder="Address" {...bindAddress} />
                  </Form.Group>
                  <Form.Group controlId="centerCity">
                      <Form.Label>City:</Form.Label>
                      <Form.Control type="text" placeholder="City" {...bindCity} />
                  </Form.Group>
                  <Form.Group controlId="centerStateAbbr">
                      <Form.Label>State:</Form.Label>
                      <Form.Control type="State" placeholder="State" {...bindState} />
                  </Form.Group>
                  <Form.Group controlId="centerZipCode">
                      <Form.Label>Zip Code:</Form.Label>
                      <Form.Control type="ZipCode" placeholder="Zip Code" {...bindZipCode} />
                  </Form.Group>
                  <Form.Group controlId="centerPhone">
                    <Form.Label>Phone Number:</Form.Label>
                    <Form.Control type="phone" placeholder="Phone Number" {...bindPhone} />
                  </Form.Group>
                  <Form.Group controlId="centerFirstVaccine">
                    <Form.Label>First Vacc:</Form.Label>
                    <Form.Control type="numFirstVaccine" placeholder="First Vacc" {...bindFirstVacc} />
                  </Form.Group>
                  <Form.Group controlId="centerSecondVaccine">
                    <Form.Label>Second Vacc:</Form.Label>
                    <Form.Control type="numSecondVaccine" placeholder="Second Vacc" {...bindSecondVacc} />
                  </Form.Group>
                  <Button variant="primary" type="submit">
                      Submit
                  </Button>
      </Form>
      <h5>INSERT INTO VaccineSites(VacCenter, Address, City, StateAbbreviation, ZipCode, PhoneNumber, NumFirstVaccine, NumSecondVaccine, Latitude, Longitude) </h5>
      <h5>{query}</h5>
    </div>
  );
}

export default Create;