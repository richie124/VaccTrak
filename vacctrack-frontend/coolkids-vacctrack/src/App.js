import './App.css';
import React, {useState, useRef, useCallback} from 'react';
import { GoogleMap, useLoadScript, Marker, InfoWindow } from '@react-google-maps/api';
import mapStyles from './mapStyles';
import Search from './Search';
import Locate from './Locate';
import {
  getGeocode,
  getLatLng
} from "use-places-autocomplete";

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

var markers2 = [
    {
        "id": 52,
        "name": "Six Flags America Theme Park",
        "address": "13710 Central Ave.",
        "city": "Upper Marlboro",
        "state": "ND",
        "zipcode": " 20721",
        "phoneNumber": "N/A",
        "singleDoses": 10887,
        "doubleDoses": 10478
    },
    {
        "id": 53,
        "name": "Holy Cross Hospital",
        "address": "1500 Forest Glen Road",
        "city": "Silver Spring",
        "state": "MD",
        "zipcode": " 20910",
        "phoneNumber": "N/A",
        "singleDoses": 3234,
        "doubleDoses": 3434
    },
    {
        "id": 54,
        "name": "Luminis Health Doctors Community Medical Center",
        "address": "8118 Good Luck Road",
        "city": " Lanham",
        "state": "MD",
        "zipcode": "20706",
        "phoneNumber": "N/A",
        "singleDoses": 8766,
        "doubleDoses": 7654
    }
]


function App() {
  const {isLoaded, loadError} = useLoadScript({
    googleMapsApiKey: "",
    libraries: libraries
  })
  // const [markers, setMarkers] = useState([]);
  const [selected, setSelected] = useState(null);
  const [markers, setMarkers] = useState([]);

  const mapRef = useRef();
  const onMapLoad = useCallback(
    (map) => {
      var temp;
      mapRef.current = map;
      // fetch(SERVICE_URL + "/getAllVaccCenters")
      // .then(data => data.json())
      // .then(data => data)
      // .then(getData(temp).then(data => setMarkers(data)));
      
      getData(markers2).then(data => console.log(data));

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

  if (loadError) return "Error loading maps";
  if (!isLoaded) return "Loading maps";

  return (<div>

    <img src="logo.png" alt="logo" id="logo"/>
    <Search panTo={panTo} getLatLngFromAddress={getLatLngFromAddress}/>
    <Locate panTo={panTo} />

    <GoogleMap 
    mapContainerStyle={mapContainerStyle}
    zoom={4.7}
    center={center}
    options={options}
    onLoad={onMapLoad}>
      {markers.map((marker, i) => 
        <Marker 
          key={i} 
          position={{lat: marker.lat, lng: marker.lng}}
          onClick={() => {
            setSelected(marker);
          }}
        />
      )}

      {selected ? (
        <InfoWindow 
        position={{lat: selected.lat, lng: selected.lng}} 
        onCloseClick={() => setSelected(null)}>
          <div>
            <h3>{selected.name}</h3>
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