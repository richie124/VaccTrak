import React, {useState, useRef, useCallback, useEffect} from 'react';
import { GoogleMap, useLoadScript, Marker, InfoWindow } from '@react-google-maps/api';
import mapStyles from './mapStyles';
import Search from './Search';
import Locate from './Locate';
import Create from './Create';
import AddVacc from './AddVacc';
import StateNumbers from './StateNumbers'
import Login from './Login'
import AddUser from './AddUser'
import Settings from './Settings'
import useToken from './hooks/useToken'
import {
  getGeocode,
  getLatLng
} from "use-places-autocomplete";
import { Modal, Button } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

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
const startZoom = 4.7;


function App() {
  const {isLoaded, loadError} = useLoadScript({
    googleMapsApiKey: "AIzaSyAp1BrMA7-YofbC3iJapScTWCpI8GmZm-w",
    libraries: libraries
  })

  const [selected, setSelected] = useState(null);
  const [markers, setMarkers] = useState([]);
  const [newCenterShow, setNewCenterShow] = useState(false);
  const [addVaccShow, setAddVaccShow] = useState(false);
  const [moreInfoShow, setMoreInfoShow] = useState(false);
  const [isZoomed, setIsZoomed] = useState(false);
  const [stateChartShow, setStateChartShow] = useState(false);
  const [loginShow, setLoginShow] = useState(false);
  const [stateData, setStateData] = useState([]);
  const {getToken, setToken, logOut} = useToken();
  const [createUser, setCreateUser] = useState(false);
  const [vaccCenters, setVaccCenters] = useState([]);
  const [settingsShow, setSettingsShow] = useState(false);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = useCallback(() => {
    fetch(SERVICE_URL + "/getAllVaccCenters")
      .then(data => data.json())
      .then(data => {
        !!getToken() ? setMarkers(data.filter(marker => getToken().vaccCenterAccesses.indexOf(marker.id) >= 0)):
        setMarkers(data);
        setVaccCenters(data);
      });

    fetch(SERVICE_URL + "/getVaccNumbersByState")
      .then(data => data.json())
      .then(data => {
        setStateData(data);
      });
  }, []);

  const mapRef = useRef();
  const onMapLoad = useCallback(
    (map) => {
      mapRef.current = map;
    },
    []
  )

  const returnZoom = useCallback(() => {
    panTo({lat:center.lat, lng: center.lng}, startZoom);
  }, [])

  const panTo = useCallback(
  ({lat, lng}, zoom) => {
    mapRef.current.panTo({lat, lng});
    mapRef.current.setZoom(zoom);
    zoom !== startZoom ? setIsZoomed(true) : setIsZoomed(false);
    console.log(isZoomed);
    console.log(zoom);
  })

  const updateMarker = useCallback((updateCenter) => {
    setSelected(updateCenter);
    loadData();
  }, []);

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

  const handleNewCenterClose = () => setNewCenterShow(false);
  const handleNewCenterShow = () => setNewCenterShow(true);
  const handleAddVaccClose = () => setAddVaccShow(false);
  const handleAddVaccShow = () => setAddVaccShow(true);
  const handleMoreInfoShow = () => setMoreInfoShow(true);
  const handleMoreInfoHide = () => setMoreInfoShow(false);
  const handleStateChartShow = () => setStateChartShow(true);
  const handleStateChartClose = () => setStateChartShow(false);
  const handleLoginShow = () => setLoginShow(true);
  const handleLoginClose = () => setLoginShow(false);
  const handleSettingsShow = () => setSettingsShow(true);
  const handleSettingsClose = () => setSettingsShow(false);
  const handleLogOut = () => {
    logOut();
    setMarkers(vaccCenters);
  }

  if (loadError) return "Error loading maps";
  if (!isLoaded) return "Loading maps";


  return (<div>
    <div className="navbar-custom">
      <img src="logo.png" alt="logo" id="logo"/>
      <Button onClick={handleStateChartShow} className="stateChartButton"> Numbers by State </Button>
      <Search panTo={panTo} getLatLngFromAddress={getLatLngFromAddress} handleShow={handleNewCenterShow} getToken={getToken}/>
      <Locate panTo={panTo} returnZoom={returnZoom} />
      
      {getToken() ? (<button className="settingsButton" onClick={handleSettingsShow}><img src="settings.png" alt="settings"/></button>) : null}
      {getToken() ? (<Button onClick={handleLogOut} className="loginButton"> LogOut </Button>) : (<Button onClick={handleLoginShow} className="loginButton"> Login </Button>)}
    </div>

    <Modal show={stateChartShow} onHide={handleStateChartClose}>
        <Modal.Header closeButton>
          <Modal.Title>Vaccination Numbers by State</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <StateNumbers stateData={stateData}/>
        </Modal.Body>
    </Modal>

    <Modal show={loginShow} onHide={handleLoginClose}>
        <Modal.Header closeButton>
          <Modal.Title>Login</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          { !createUser ? (
          <Login 
            setToken={setToken}
            markers={markers}
            setMarkers={setMarkers} 
            handleClose={handleLoginClose}
            setCreateUser={setCreateUser}
            SERVICE_URL={SERVICE_URL}/>) : 
           (<AddUser 
            SERVICE_URL={SERVICE_URL} 
            handleClose={handleLoginClose}
            setCreateUser={setCreateUser}
            vaccCenters={vaccCenters}/>)
          }
        </Modal.Body>
    </Modal>


    <Modal show={newCenterShow} onHide={handleNewCenterClose}>
        <Modal.Header closeButton>
          <Modal.Title>Add Vaccination Site</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Create getVaccCenterLatLong={getVaccCenterLatLong} setMarkers={setMarkers} markers={markers} SERVICE_URL={SERVICE_URL} handleClose={handleNewCenterClose}/>
        </Modal.Body>
    </Modal>

    <Modal show={addVaccShow} onHide={handleAddVaccClose}>
        <Modal.Header closeButton>
          <Modal.Title>Add Vaccinations</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <AddVacc handleClose={handleAddVaccClose} SERVICE_URL={SERVICE_URL} vaccCenter={selected} updateMarker={updateMarker}/>
        </Modal.Body>
    </Modal>

    <Modal show={settingsShow} onHide={handleSettingsClose}>
        <Modal.Header closeButton>
          <Modal.Title>Settings</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Settings 
          getToken={getToken}
          setToken={setToken}
          vaccCenters={vaccCenters}
          SERVICE_URL={SERVICE_URL}
          setMarkers={setMarkers}
          handleClose={handleSettingsClose}/>
        </Modal.Body>
    </Modal>

    <GoogleMap 
    mapContainerStyle={mapContainerStyle}
    zoom={startZoom}
    center={center}
    options={options}
    onLoad={onMapLoad}>
      {markers.map((marker, i) => 
        <Marker 
          key={i} 
          position={{lat: parseFloat(marker.latitude), lng: parseFloat(marker.longitude)}}
          onClick={() => {
            // panTo({lat: parseFloat(marker.latitude), lng: parseFloat(marker.longitude)}, 14);
            setSelected(marker);
          }}
        />
      )}

      {selected ? (
        <InfoWindow 
        position={{lat: parseFloat(selected.latitude), lng: parseFloat(selected.longitude)}} 
        onCloseClick={() => {
          setSelected(null);
          handleMoreInfoHide();
          }}>
          <div>
            <div>
              <h6>{selected.name}</h6>
              <div className="seeMore">
                { moreInfoShow ? 
                  <div id="info">
                  <div>
                    {selected.address}
                  </div>
                  <div>
                    {selected.city}, {selected.state} {selected.zipcode}
                  </div>
                  <div>
                    {selected.phoneNumber}
                  </div>
                  <button onClick={handleMoreInfoHide} className="moreHide">Hide...</button>
                </div>
                :  
                <button onClick={handleMoreInfoShow} className="moreHide">See more info...</button>
              }
              </div>
            </div>
            <hr />
            <div>
              <div>
                1st Dose: {selected.singleDoses}
              </div>
              <div>
                2nd Dose: {selected.doubleDoses}
              </div>
              
              Total Vaccinated: {selected.singleDoses + selected.doubleDoses}
            </div> 
            <hr />
            { getToken() ? 
            (<div className="infoEditBar">
              <Button onClick={handleAddVaccShow}>Add Vaccinations</Button>
            </div>) : null
            }
          </div>
        </InfoWindow>
      ) : null}
    </GoogleMap>
  </div>)
    
}

export default App;
