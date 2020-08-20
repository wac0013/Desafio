import React, { useRef, useEffect, Dispatch } from "react";
import { BrowserRouter, Route, Redirect, Switch } from "react-router-dom";
import "primereact/resources/themes/nova-dark/theme.css";
import "primereact/resources/primereact.min.css";
import "primeicons/primeicons.css";
import "primeflex/primeflex.css";
import "./assets/css/app.scss";

import Login from "./pages/login";
import Eventos from "./pages/eventos";
import NaoEncontrada from "./pages/naoEncontrada";
import { useSelector, useDispatch } from "react-redux";
import { AppState } from "./store";

import { Growl } from "primereact/growl";
import { GetUsuarioAction, UsuarioTypes } from "./store/usuario/types";

export default function App() {
	const usuarioState = useSelector((state: AppState) => state.usuario);
	const eventoState = useSelector((state: AppState) => state.eventos);
	const dispatch = useDispatch<Dispatch<GetUsuarioAction>>();
	let mensagge = useRef<Growl>(null);

	useEffect(() => {
		if (usuarioState.error) mensagge.current?.show({ severity: "error", summary: usuarioState.error.message, detail: usuarioState.error.datail });
	}, [usuarioState.error]);

	useEffect(() => {
		if (usuarioState.sucess) mensagge.current?.show({ severity: "success", summary: usuarioState.sucess });
	}, [usuarioState.sucess]);

	useEffect(() => {
		if (eventoState.error) mensagge.current?.show({ severity: "error", summary: eventoState.error.message, detail: eventoState.error.datail });
	}, [eventoState.error]);

	useEffect(() => {
		if (eventoState.sucess) mensagge.current?.show({ severity: "success", summary: eventoState.sucess });
	}, [eventoState.sucess]);

	useEffect(() => {
		if (usuarioState.token && !usuarioState.usuario) {
			dispatch({ type: UsuarioTypes.GET_USUARIO, token: usuarioState.token });
		}
	}, [usuarioState.token, usuarioState.usuario, dispatch]);

	useEffect(() => {
		document.title = "Desafio";
	}, []);

	return (
		<>
			<Growl ref={mensagge} />
			<BrowserRouter>
				<Switch>
					<Route
						path="/login"
						render={({ location }) => (usuarioState.usuario ? <Redirect to={{ pathname: "evento", state: { from: location } }} /> : <Login />)}
					/>
					<Route
						path="/evento"
						render={({ location }) => (usuarioState.usuario ? <Eventos /> : <Redirect to={{ pathname: "login", state: { from: location } }} />)}
					/>
					<Route path="/404" exact component={NaoEncontrada} />
					<Route path="/" render={() => <Redirect to="/evento" />} />
					<Route render={() => <Redirect to="/404" />} />
				</Switch>
			</BrowserRouter>
		</>
	);
}
