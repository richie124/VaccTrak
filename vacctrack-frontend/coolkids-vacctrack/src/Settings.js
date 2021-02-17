import { useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import { Multiselect } from 'multiselect-react-dropdown';

function Settings({vaccCenters, getToken, setToken, SERVICE_URL, setMarkers, handleClose}) {
  const [vaccCenterAccesses, setVaccCenterAccesses] = useState(vaccCenters.filter(center => getToken().vaccCenterAccesses.indexOf(center.id) >= 0));
  const [newAccesses, setNewAccesses] = useState([]);

  const handleSelect = (selectedList, selectedItem) => {
    setVaccCenterAccesses(selectedList);
    setNewAccesses([...newAccesses, selectedItem.id]);
  }

  const handleRemove = (selectedList, removedItem) => {
    setVaccCenterAccesses(selectedList);
    const index = newAccesses.indexOf(removedItem.id);
    var access = newAccesses;
    access.splice(index, 1);
    setNewAccesses(access);
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    var token = getToken();
    
    const accessObject = {
        "id": token.id,
        "vaccCenterAccesses": newAccesses
    };

    fetch(SERVICE_URL + '/AdminPortal/AddPermissions', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(accessObject),
    })
    .then(response => response.json())
    .then(data => {
      console.log('Update User - Success:', data);
      token.vaccCenterAccesses = data;
      setToken(token);
      setMarkers(vaccCenters.filter(marker => data.indexOf(marker.id) >= 0));
      handleClose();
    })
    .catch((error) => {
      console.log('Update User - Error:', error);
    });

  }

  return (
    <Form onSubmit={handleSubmit}>
      <Form.Group controlId="exampleForm.ControlSelect2">
        <Form.Label>VaccCenter Access</Form.Label>
        <Multiselect 
          options={vaccCenters}
          selectedValues={vaccCenterAccesses}
          onSelect={handleSelect}
          onRemove={handleRemove}
          displayValue="name"/>
        </Form.Group>
        <Button variant="primary" type="submit">
          Update
        </Button>
    </Form>
  );
}

export default Settings;