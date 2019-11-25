import React from 'react';

class WeatherWidget extends React.Component {


    names = new Map();

    constructor() {
        super();
        this.names.set("wind", "Ветер");
        this.names.set("earthQuake", "Землятрясение");
        this.names.set("precipitation", "Осадки");
        this.names.set('moderate', "умеренный");
        this.names.set('calm', "штиль");
        this.names.set('storm', "шторм");
        this.names.set('rain', "дождь");
        this.names.set('snow', "снег");
    }

    getDescription(event) {
        switch (event.type) {
            case 'wind':
                return `${this.names.get(event.name)}, скорость ${event.speed} км/ч`;
            case 'preticipation':
                return `${this.names.get(event.name)}, температура ${event.temperature} градусов Цельсия, кол-во суточных норм ${event.numberOfDailyAllowances}, итенсивность ${event.itencity}`;
            case 'earthQuake':
                return `${event.magnitudeScaleValue} баллов`;
        }
    }

    getEvents = () => {
        var rows = [];
        for (var i = 0; i < this.props.data.events.length; i++) {
            rows.push(<tr>
                <td>{this.names.get(this.props.data.events[i].type)}</td>
                <td>{this.getDescription(this.props.data.events[i])}</td>
            </tr>);
        }
        return rows;
    }

    render() {
        return (
            <table className="table">
                {console.log(this.props.data)}
                <tbody>
                    {this.getEvents()}
                </tbody>
            </table>
        )
    }
}

export default WeatherWidget;