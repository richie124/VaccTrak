import './App.css';
import React, {useState, useRef, useCallback, useEffect} from 'react';
import { GoogleMap, useLoadScript, Marker, InfoWindow } from '@react-google-maps/api';
import mapStyles from './mapStyles';
import Search from './Search';
import Locate from './Locate';
import Create from './Create';
import {
  getGeocode,
  getLatLng
} from "use-places-autocomplete";
import { Modal, Button } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const SERVICE_URL = "http://localhost:8080/VaccTrak";

const libraries = ["places"];
const mapContainerStyle = {
  width: '100vw',
  height: '100vh'
};
const center = {
  lat: 37.090240,
  lng: -95.712891
}
const options = {
  styles: mapStyles,
  disableDefaultUI: true,
  zoomControl: true
}


function App() {
  const {isLoaded, loadError} = useLoadScript({
    googleMapsApiKey: "",
    libraries: libraries
  })

  const [selected, setSelected] = useState(null);
  const [markers, setMarkers] = useState([]);
  const [show, setShow] = useState(true);

  useEffect(() => {
    fetch(SERVICE_URL + "/getAllVaccCenters")
      .then(data => data.json())
      .then(data => setMarkers(data));
      //getData(markers2).then(data => setMarkers(data));
  }, []);

  const mapRef = useRef();
  const onMapLoad = useCallback(
    (map) => {
      mapRef.current = map;
    },
    []
  )

  const getData = async (vaccList) => {
    return Promise.all(vaccList.map((vaccCenter) => 
        getVaccCenterLatLong(vaccCenter)));
  }

  const panTo = useCallback(
  ({lat, lng}) => {
    mapRef.current.panTo({lat, lng});
    mapRef.current.setZoom(14);
  },
  [],
  )

  const getVaccCenterLatLong = useCallback(async (vaccCenter) => {
    const address = vaccCenter.address + " " + vaccCenter.city + ", " + vaccCenter.state + " " + vaccCenter.zipcode;
    try {
    const {lat, lng} = await getLatLngFromAddress(address);
    return {...vaccCenter, lat, lng};
    } catch(error) {
      console.log("error getting latlong for vaccenter " + vaccCenter.id);
    }
    
  }, [])

  const getLatLngFromAddress = useCallback(async (address) => {
        try {
          const results = await getGeocode({address});
          const { lat, lng } = await getLatLng(results[0]);
          return ({lat, lng})
        } catch(error) {
          console.log("error1");
        }
  }, []);

  const handleShow = () => setShow(true);
  const handleClose = () => setShow(false);

  if (loadError) return "Error loading maps";
  if (!isLoaded) return "Loading maps";


  return (<div>

    <img src="logo.png" alt="logo" id="logo"/>
    <Search panTo={panTo} getLatLngFromAddress={getLatLngFromAddress} handleShow={handleShow}/>
    <Locate panTo={panTo} />

    <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Add Vaccination Site</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Create getVaccCenterLatLong={getVaccCenterLatLong} setMarkers={setMarkers} markers={markers} SERVICE_URL={SERVICE_URL} handleClose={handleClose}/>
        </Modal.Body>
    </Modal>

    <GoogleMap 
    mapContainerStyle={mapContainerStyle}
    zoom={4.7}
    center={center}
    options={options}
    onLoad={onMapLoad}>
      {markers.map((marker, i) => 
        <Marker 
          key={i} 
          position={{lat: parseFloat(marker.latitude), lng: parseFloat(marker.longitude)}}
          onClick={() => {
            setSelected(marker);
          }}
        />
      )}

      {selected ? (
        <InfoWindow 
        position={{lat: parseFloat(selected.latitude), lng: parseFloat(selected.longitude)}} 
        onCloseClick={() => setSelected(null)}>
          <div>
            <h6>{selected.name}</h6>
            <div>
              <div>
                1st Dose: {selected.singleDoses}
              </div>
              <div>
                2nd Dose: {selected.doubleDoses}
              </div>
              <hr />
              Total Vaccinated: {selected.singleDoses + selected.doubleDoses}
            </div>
          </div>
        </InfoWindow>
      ) : null}
    </GoogleMap>
  </div>)
    
}

export default App;
