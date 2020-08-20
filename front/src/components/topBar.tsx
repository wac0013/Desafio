import React, { Dispatch } from "react";
import { Button } from "primereact/button";
import { useDispatch } from "react-redux";
import { LogoutAction, UsuarioTypes } from "../store/usuario/types";
import logoReact from "../assets/img/react-redux.png";
import logoSpring from "../assets/img/spring.png";

export default function TopBar() {
	const dispatch = useDispatch<Dispatch<LogoutAction>>();

	function logout() {
		dispatch({ type: UsuarioTypes.LOGOUT });
	}

	return (
		<div className="layout-top-bar p-grid p-justify-between">
			<div className="p-col-1" style={{ textAlign: "center" }}>
				<img src={logoReact} style={{ height: "50px", position: "absolute", top: "30%" }} alt="logo-react-redux" />
				<img src={logoSpring} style={{ height: "50px", position: "absolute", top: "30%", left: "20%" }} alt="logo-spring" />
			</div>
			<div className="p-col-1" style={{ textAlign: "center" }}>
				<Button icon="pi pi-sign-out" onClick={logout} style={{ backgroundColor: "transparent", border: "0", top: "30%" }} />
			</div>
		</div>
	);
}
