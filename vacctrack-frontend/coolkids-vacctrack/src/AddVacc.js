import { Form, Button } from 'react-bootstrap';
import { useInput } from './hooks/input-hook';

function AddVacc ({ SERVICE_URL, handleClose, vaccCenter, updateMarker }) {

  const { value:firstVacc, bind:bindFirstVacc, reset:resetFirstVacc } = useInput('');
  const { value:secondVacc, bind:bindSecondVacc, reset:resetSecondVacc } = useInput('');

  const handleSubmit = (e) => {
    e.preventDefault();
    const first = firstVacc !== "" ? parseInt(firstVacc) : 0;
    const second = secondVacc !== "" ? parseInt(secondVacc) : 0;
    const newVaccCenter = {
        "id": vaccCenter.id, 
        "singleDoses": first + vaccCenter.singleDoses - second,
        "doubleDoses": second + vaccCenter.doubleDoses
    }
    console.log(newVaccCenter);
    fetch(SERVICE_URL + '/UpdateDoses/', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(newVaccCenter),
    })
    .then(data => {
      console.log('Add Vaccinations - Success:', data);
      vaccCenter.singleDoses = newVaccCenter.singleDoses;
      vaccCenter.doubleDoses = newVaccCenter.doubleDoses;
      updateMarker(vaccCenter);
      handleClose();
    })
    .catch((error) => {
      console.log('Add Vaccinations - Error:', error);
    });

    resetFirstVacc();
    resetSecondVacc();
  }

  return (
    <div>
      <h4>{vaccCenter.name}</h4>
      <Form onSubmit={handleSubmit}>
                  <Form.Group controlId="centerFirstVaccine">
                    <Form.Label>New First Doses:</Form.Label>
                    <Form.Control type="numFirstVaccine" placeholder="First Dose" {...bindFirstVacc} />
                  </Form.Group>
                  <Form.Group controlId="centerSecondVaccine">
                    <Form.Label>New Second Doses:</Form.Label>
                    <Form.Control type="numSecondVaccine" placeholder="Second Dose" {...bindSecondVacc} />
                  </Form.Group>
                  <Button variant="primary" type="submit">
                      Submit
                  </Button>
      </Form>
    </div>
  );
}

export default AddVacc;