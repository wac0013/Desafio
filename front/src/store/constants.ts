import axios from "axios";

const API = axios.create({
	baseURL: "http://localhost:3000/api/",
});

export const AUTHORIZATION = "Authorization";
export const BEARER = "Bearer";

export default API;
