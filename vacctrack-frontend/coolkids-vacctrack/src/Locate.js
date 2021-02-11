import React from 'react';

function Locate({panTo}) {

  return (
  <button className="locate" onClick={() => {
    navigator.geolocation.getCurrentPosition((position) => {
      panTo({lat: position.coords.latitude, lng: position.coords.longitude})
    }, () => null);
  }}>
    <img src="compass.png" alt="compass - locate me"/>
  </button>);
}

export default Locate;