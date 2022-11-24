import axios from "axios";

export default function axiosConfig() {
    axios.defaults.baseURL = `${process.env.REACT_APP_API_HOST}`;
    if (`${process.env.NODE_ENV}` === 'development') {
        axios.defaults.withCredentials = true;
    }
    axios.defaults.timeout = 3000;
    axios.interceptors.response.use(
        (response) => response,
        (error) => Promise.reject(error)
    );
}
