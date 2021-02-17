import React from 'react';
import { Table } from 'react-bootstrap';

function StateNumbers({stateData}) {

  return (
    <Table responsive>
            <thead>
              <tr>
                <th>State</th>
                <th>No. of First Doses</th>
                <th>No. of Second Doses</th>
              </tr>
            </thead>
            <tbody>
              {stateData.map((stateObject) => {
                return (
                <tr>
                  <td>{stateObject.stateAbbr}</td>
                  <td>{stateObject.singleDoses}</td>
                  <td>{stateObject.doubleDoses}</td>
                </tr>
                )
              })
            }
            </tbody>
          </Table>
  );
}

export default StateNumbers;