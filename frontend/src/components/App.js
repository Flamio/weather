import React from 'react';
import WeatherFinder from './WeatherFinder'
import WeatherRegister from './WeatherRegister'

class App extends React.Component {

    render() {
        return (
            <div>
                <div className="container">
                    <br></br>
                    <WeatherFinder />
                    <br />
                    <WeatherRegister />
                </div>
            </div>
        )
    }

}

export default App;