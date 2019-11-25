import React from 'react';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { connect } from 'react-redux'
import EventsCreator from './EventsCreator'


class WeatherRegister extends React.Component {

    constructor(props) {
        super(props);
    }

    dateChanged = (date) => {
        this.props.dispatch({
            type: "CHANGE_DATE",
            data: date
        })
    }

    changeEventCount = event => {
        this.props.dispatch({
            type: "EVENT_COUNT_CHANGE",
            data: event.currentTarget.value
        })
    }

    getEventCreators() {
        console.log("asdf");
        var rows = [];
        for (var i = 0; i < this.props.store.events.length; i++) {
            rows.push(<div><EventsCreator index={i} key={i} /> <br></br> </div>);
        }
        return rows;
    }

    register = () =>
    {
        this.props.dispatch({
            type: "REGISTER"
        })
    }

    render() {
        return (
            <div className="card">
                <div className="card-header">
                    Регистрация погодных явлений
                </div>
                <div className="card-body">
                    <div className="row">
                        <div className="form-group col-md-6">
                            <label>Дата:</label>
                            <br></br>
                            <DatePicker selected={this.props.store.date}
                                onChange={this.dateChanged}
                                dateFormat="dd.MM.yyyy"
                                className='form-control' />
                        </div>
                        <div className="form-group col-md-6">
                            <label>Количетво погодных явлений:</label>
                            <br></br>
                            <select onChange={this.changeEventCount} value={this.props.store.events.length} defaultValue="0" className="custom-select">
                                <option value='0'>0</option>
                                <option value='1'>1</option>
                                <option value='2'>2</option>
                                <option value='3'>3</option>
                                <option value='4'>4</option>
                            </select>
                        </div>
                    </div>
                    {this.getEventCreators()}
                    <br></br>
                    <div className="row text-center col-md-6">
                        <button onClick={this.register} className="btn btn-primary ">Зарегистрировать</button>
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

export default connect(mapStateToProps)(WeatherRegister);