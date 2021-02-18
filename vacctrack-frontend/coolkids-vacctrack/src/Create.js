import { Form, Button } from 'react-bootstrap';
import { useInput } from './hooks/input-hook';

function Create ({ getVaccCenterLatLong, setMarkers, markers, SERVICE_URL, handleClose, setVaccCenters, vaccCenters }) {
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
        "name": name,
        "address": address,
        "city": city,
        "state": state,
        "zipcode": zipCode,
        "latitude": "",
        "longitude": "",
        "phoneNumber": phone,
        "singleDoses": parseInt(firstVacc),
        "doubleDoses": parseInt(secondVacc)
    }

    const { lat, lng } = await getVaccCenterLatLong(vaccCenter);
    vaccCenter.latitude = lat;
    vaccCenter.longitude = lng;

    fetch(SERVICE_URL + '/createVaccCenter/', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(vaccCenter),
    })
    .then(response => response.json())
    .then(data => {
      console.log('Add Center - Success:', data);
      setMarkers([...markers, vaccCenter]);
      setVaccCenters([...vaccCenters, vaccCenter]);
      handleClose();
    })
    .catch((error) => {
      console.log('Add Center - Error:', error);
    });


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
                      <Form.Label>Vaccination Center Name:</Form.Label>
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
                    <Form.Label>No of Patients with First Dose:</Form.Label>
                    <Form.Control type="numFirstVaccine" placeholder="First Dose" {...bindFirstVacc} />
                  </Form.Group>
                  <Form.Group controlId="centerSecondVaccine">
                    <Form.Label>No of Patients with Second Dose:</Form.Label>
                    <Form.Control type="numSecondVaccine" placeholder="Second Dose" {...bindSecondVacc} />
                  </Form.Group>
                  <Button variant="primary" type="submit">
                      Submit
                  </Button>
      </Form>
    </div>
  );
}

export default Create;