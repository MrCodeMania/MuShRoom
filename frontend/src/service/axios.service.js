import axios from 'axios'
export default axios.create({
  baseURL: 'https://musicshareroom.tk/api',
  headers: {
    "Content-type": "application/json"
  }
})