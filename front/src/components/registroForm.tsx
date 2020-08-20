import React, { useState, FormEvent, Dispatch } from "react";
import { NovoUsuario, UsuarioTypes, AddUsuarioAction } from "../store/usuario/types";
import { useDispatch, useSelector } from "react-redux";

import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";

import { AppState } from "../store";

export default function RegistroForm() {
	const usuarioState = useSelector((state: AppState) => state.usuario);

	const [nome, setNome] = useState("");
	const [nomeUsuario, setNomeUsuario] = useState("");
	const [email, setEmail] = useState("");
	const [senha, setSenha] = useState("");

	const dispatch = useDispatch<Dispatch<AddUsuarioAction>>();

	function submit(event: FormEvent) {
		event.preventDefault();
		const usuario: NovoUsuario = { nome, nomeUsuario, email, senhaHash: btoa(senha) };
		dispatch({ type: UsuarioTypes.ADD_USUARIO, usuario });
	}

	function onChangeNome(e: any) {
		setNome(e.target.value);
	}
	function onChangeNomeUsuario(e: any) {
		setNomeUsuario(e.target.value);
	}
	function onChangeEmail(e: any) {
		setEmail(e.target.value);
	}
	function onChangeSenha(e: any) {
		setSenha(e.target.value);
	}

	return (
		<form onSubmit={submit}>
			<h1>Cadastrar</h1>
			<div className="social-container"></div>
			<InputText placeholder="Nome" value={nome} onChange={onChangeNome} required />
			<InputText placeholder="Nome de Usuário" value={nomeUsuario} onChange={onChangeNomeUsuario} required />
			<InputText placeholder="Email" type="email" value={email} onChange={onChangeEmail} required />
			<InputText placeholder="Senha" type="password" value={senha} onChange={onChangeSenha} required />
			<Button
				className="button-login"
				label={usuarioState.loading ? "Registrando" : "Registrar"}
				disabled={usuarioState.loading}
				icon={usuarioState.loading ? "pi pi-spin pi-spinner" : ""}
				iconPos="right"
			/>
		</form>
	);
}
