import React from 'react';
import { connect } from 'react-redux'
import DatePicker from "react-datepicker";
import WeatherWidget from './WeatherWidget'
import "react-datepicker/dist/react-datepicker.css";

class WeatherFinder extends React.Component {

    dateChanged = d => {
        this.setState({ date: d });
    }

    getData = (url = '') => {
        return fetch(url, {
            method: 'GET',
        }).then(response => {
            if (response.status == 404)
                alert("Не найдено!");

            return response.json()
        }
        );
    }

    deleteData = (url = '') => {
        return fetch(url, {
            method: 'DELETE',
        });
    }

    findWeather = () => {
        this.getData(`weather/api/find?date=${this.state.date.getDate()}.${this.state.date.getMonth() + 1}.${this.state.date.getFullYear()}`).then(r => {
            if (r.events.length == 0)
                alert("Погодные явления отсутствуют на данную дату");
            this.setState({ data: r })
        })
    }

    deleteWeather = () => {

        this.deleteData(`weather/api/delete/${this.state.data.id}`).then(r => {
            if (r.status == 404)
                alert("Не найдено!")
            else if (r.status == 200) {
                alert("Успех!");
                this.setState({
                    data: {
                        events: [],
                        date: {}
                    }
                })
            }
        }
        )
    }

    state = {
        date: "",
        data: {
            events: [],
            date: {}
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
                            <button onClick={this.findWeather} className="btn btn-primary">Поиск</button>
                            <button onClick={this.deleteWeather} className="btn btn-success">Удалить</button>
                        </div>
                        <div className="card col-md-9">
                            <div className="card-body">
                                <WeatherWidget data={this.state.data} />
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