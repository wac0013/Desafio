import React, { useState, Dispatch } from "react";
import { useDispatch, useSelector } from "react-redux";
import { UsuarioTypes, Auth, LoginAction } from "../store/usuario/types";
import { Button } from "primereact/button";
import { InputText } from "primereact/inputtext";
import { AppState } from "../store";

export default function LoginForm() {
	const usuarioState = useSelector((state: AppState) => state.usuario);
	const [email, setEmail] = useState("");
	const [senha, setSenha] = useState("");

	const dispatch = useDispatch<Dispatch<LoginAction>>();

	function onChangeEmail(event: any) {
		setEmail(event.target.value);
	}

	function onChangeSenha(event: any) {
		setSenha(event.target.value);
	}

	function onSubmit(event: any) {
		event.preventDefault();
		const usuario: Auth = { email, senhaHash: btoa(senha) };
		dispatch({ type: UsuarioTypes.LOGIN, usuario });
	}

	return (
		<form onSubmit={onSubmit}>
			<h1>Entrar</h1>
			<div className="social-container"></div>

			<InputText placeholder="Email" value={email} type="email" onChange={onChangeEmail} />
			<InputText placeholder="Senha" value={senha} type="password" onChange={onChangeSenha} />
			{/*<a href="#">Esqueceu sua senha?</a>*/}

			<Button
				className="button-login"
				style={{ backgroundColor: "#ff1d61", borderColor: "#ff1d61" }}
				label={usuarioState.loading ? "Entrando" : "Entrar"}
				disabled={usuarioState.loading}
				icon={usuarioState.loading ? "pi pi-spin pi-spinner" : ""}
				iconPos="right"
			/>
		</form>
	);
}
