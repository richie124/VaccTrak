import React from "react";
import usePlacesAutocomplete from "use-places-autocomplete";
import {
  Combobox,
  ComboboxInput,
  ComboboxPopover,
  ComboboxOption,
  ComboboxList
} from '@reach/combobox';
import '@reach/combobox/styles.css';

function Search({getLatLngFromAddress, panTo, handleShow, token}) {
  const {
    ready, 
    value, 
    suggestions: {status, data}, 
    setValue, 
    clearSuggestions
  } = usePlacesAutocomplete({
    requestOptions: {
      location: {
        lat: () => 41.878113,
        lng: () => -87.629799,
      },
      radius: 200 * 1000
    }
  });
  return (
    <div className="search">
      <div className="center addVaccSite">
      { token ?
      (
        <span>Don't see your vaccination site listed? <button id="clickHere" onClick={handleShow}>Click here!</button></span>
      ) : '-'
      }
      </div>
    <Combobox
      onSelect={async (address) => {
      setValue(address, false);
      clearSuggestions();

      try {
        const { lat, lng } = await getLatLngFromAddress(address);
        panTo({lat, lng}, 14);
      } catch (error) {
        console.log("error2");
      }
      }}>
      <ComboboxInput 
      value={value} 
      onChange={(e) => setValue(e.target.value)}
      disabled={!ready}
      placeholder="Enter an address"
      />
      <ComboboxPopover>
        <ComboboxList>
        {status === "OK" && data.map(({id, description})=> (<ComboboxOption key={id} value={description}/>))}
        </ComboboxList>
      </ComboboxPopover>
    </Combobox>
  </div>
  );
}

export default Search