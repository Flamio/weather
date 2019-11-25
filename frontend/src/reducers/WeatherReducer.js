
const initState =
{
    date: new Date(),
    events: []
}

let postData = (url = '', data = {}) => {
    return fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data),
    });
}


const WeatherReducer = (state = initState, action) => {




    switch (action.type) {
        case "EVENT_COUNT_CHANGE":
            {
                console.log(action);
                let s =
                {
                    ...state,
                }

                if (action.data > s.events.length)
                    for (let i = 0; i <= action.data - s.events.length; i++)
                        s.events.push({ type: "precipitation" });
                else if (action.data < s.events.length)
                    for (let i = 0; i <= s.events.length - action.data; i++)
                        s.events.pop();

                return s;
            }

        case "EVENT_CHANGE_TYPE":
            {
                let s =
                {
                    ...state,

                }

                s.events[action.index] = {}
                s.events[action.index].type = action.data;
                return s;
            }

        case "CHANGE_DATE":
            {
                console.log(action);
                let s =
                {
                    ...state,
                    date: action.data
                }
                return s;
            }

        case "EVENT_CHANGE":
            {
                let s =
                {
                    ...state,
                }
                s.events[action.index][action.name] = action.data;
                s.events[action.index].type = action.dataType;
                console.log(s);
                return s;
            }

        case "REGISTER":
            {
                let s =
                {
                    ...state,
                }
                s.date = `${s.date.getDate()}.${s.date.getMonth() + 1}.${s.date.getFullYear()}`;
                postData("weather/api/register", s).then(
                    r => r.text()).then(d => {
                        if (d!= "")
                            alert("Ошибка! " + d);
                        else
                        alert("Успех!");
                    })

                s = {
                    date: new Date(),
                    events: []
                }

                return s;
            }

        default:
            return state;
    }
}

export default WeatherReducer