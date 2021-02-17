import React from 'react';

function Locate({panTo, isZoomed, returnZoom}) {

  return (
    <div>
      { isZoomed ?
    <button className="return" onClick={() => returnZoom}>Return</button> : null
      }
    <button className="locate" onClick={() => {
      navigator.geolocation.getCurrentPosition((position) => {
        panTo({lat: position.coords.latitude, lng: position.coords.longitude}, 14)
      }, () => null);
    }}>
      <img src="compass.png" alt="compass - locate me"/>
    </button>
  </div>
  );
}

export default Locate;