import React from 'react';
import { connect } from 'react-redux'

class EventCreator extends React.Component {

    state =
        {
            type: "precipitation"
        }

    changeType = event => {
        this.setState({ type: event.currentTarget.value });
        this.props.dispatch(
            {
                type: "EVENT_CHANGE_TYPE",
                data: event.currentTarget.value,
                index: this.props.index
            }
        )
    }

    textChanged = (name, event) => {
        this.props.dispatch({
            type: "EVENT_CHANGE",
            index: this.props.index,
            data: event.currentTarget.value,
            dataType: this.state.type,
            name: name
        })
        this.setState({ eventsCount: Number(event.currentTarget.value) })
    }

    getFields = () => {
        switch (this.state.type) {
            case "wind":
                return (
                    <div className="row">
                        <div className="form-group col-md-6">
                            <label>Скорость:</label>
                            <br></br>
                            <input type="text" onChange={this.textChanged.bind(this, "speed")}></input>
                        </div>
                    </div>)

            case "earthQuake":
                return (
                    <div className="row">
                        <div className="form-group col-md-6">
                            <label>Баллы:</label>
                            <br></br>
                            <input type="text" onChange={this.textChanged.bind(this, "magnitudeScaleValue")}></input>
                        </div>
                    </div>)
            case "precipitation":
                return (
                    <div className="row">
                        <div className="form-group col-md-4">
                            <label>Количество суточных норм:</label>
                            <br></br>
                            <input type="text" onChange={this.textChanged.bind(this, "numberOfDailyAllowances")}></input>
                        </div>
                        <div className="form-group col-md-4">
                            <label>Температура:</label>
                            <br></br>
                            <input onChange={this.textChanged.bind(this, "temperature")} type="text"></input>
                        </div>
                        <div className="form-group col-md-4">
                            <label>Итенсивность:</label>
                            <br></br>
                            <input type="text" onChange={this.textChanged.bind(this, "intensity")}></input>
                        </div>
                    </div>)

        }
    }

    render() {
        return (
            <div className="card">
                <div className="card-header">
                    <select onChange={this.changeType} value={this.state.type} defaultValue="precipitation" className="custom-select">
                        <option value='precipitation'>Осадки</option>
                        <option value='wind'>Ветер</option>
                        <option value='earthQuake'>Землетрясение</option>
                    </select>
                </div>
                <div className="card-body">
                    {this.getFields()}
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

export default connect(mapStateToProps)(EventCreator);