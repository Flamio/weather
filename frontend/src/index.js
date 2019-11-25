import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App';
import { createStore } from 'redux'
import {Provider} from 'react-redux'
import WeatherReducer from './reducers/WeatherReducer'
import 'bootstrap/dist/css/bootstrap.min.css'

const store = createStore(WeatherReducer);

ReactDOM.render(
  <Provider store={store}>
  <App />
  </Provider>,
  document.getElementById('root')
);
