import React from 'react';
import { connect } from 'react-redux'
import DatePicker from "react-datepicker";
import WeatherWidget from './WeatherWidget'
import "react-datepicker/dist/react-datepicker.css";

class WeatherFinder extends React.Component {

    dateChanged = d => {
        this.setState({ date: d });
    }

    getData = (url = '', data = {}) => {
        return fetch(url, {
            method: 'GET',
        }).then(response => response.json()
        );
    }

    findWeather = () =>
    {
        /*this.props.dispatch({
            type: "FIND_WEATHER",
            data: { date: this.state.date}
        })*/
        this.getData(`weather/api/find?date=${this.state.date.getDate()}.${this.state.date.getMonth() + 1}.${this.state.date.getFullYear()}`).then(r =>
            this.setState({data:r}))
    }

    state = {
        date: "",
        data:{
            events:[],
            date:{}
        }
    }

    render() {
        return (
            <div className="card">
                <div className="card-header">
                    Поиск погоды по дате
                </div>
                <div className="card-body">
                    <div className="row">
                        <div className="form-group col-md-3">
                            <label>Дата:</label>
                            <br></br>
                            <DatePicker selected={this.state.date}
                                onChange={this.dateChanged}
                                dateFormat="dd.MM.yyyy"
                                className='form-control' />

                            <button onClick={this.findWeather} className="btn btn-success">Поиск</button>
                        </div>
                        <div className="card col-md-9">
                            <div className="card-body">
                                <WeatherWidget data={this.state.data}/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

const mapStateToProps = state => {
    console.log(state);
    return {
        store: state
    };
};

export default connect(mapStateToProps)(WeatherFinder);