import { Error } from "../types";
import { Action } from "redux";

export enum UsuarioTypes {
	ADD_USUARIO = "@usuario/ADD",
	ADD_USUARIO_FAILURE = "@usuario/ADD_FAILURE",
	ADD_USUARIO_SUCESS = "@usuario/ADD_SUCESS",
	LOGIN = "@usuario/LOGIN",
	GET_USUARIO = "@usuario/GET",
	LOGIN_FAILURE = "@usuario/LOGIN_FAILURE",
	LOGIN_SUCESS = "@usuario/LOGIN_SUCESS",
	LOGOUT = "@usuario/LOGOUT",
}

export interface NovoUsuario {
	nome: string;
	nomeUsuario: string;
	email: string;
	senhaHash: string;
}

export interface Usuario {
	nome: string;
	nomeUsuario: string;
	email: string;
	id?: string;
}

export interface Auth {
	email: string;
	senhaHash: string;
}

export interface UsuarioState {
	readonly usuario?: Usuario;
	readonly token: string;
	readonly sucess: string;
	readonly loading: boolean;
	readonly error?: Error;
}

export interface AddUsuarioAction extends Action {
	type: UsuarioTypes.ADD_USUARIO;
	usuario: NovoUsuario;
}

export interface AddUsuarioSucessAction extends Action {
	type: UsuarioTypes.ADD_USUARIO_SUCESS;
	sucesso: string;
}

export interface AddUsuarioFailureAction extends Action {
	type: UsuarioTypes.ADD_USUARIO_FAILURE;
	erro: Error;
}

export interface LoginAction extends Action {
	type: UsuarioTypes.LOGIN;
	usuario: Auth;
}

export interface GetUsuarioAction extends Action {
	type: UsuarioTypes.GET_USUARIO;
	token: string;
}

export interface LoginSucessAction extends Action {
	type: UsuarioTypes.LOGIN_SUCESS;
	usuario: Usuario;
	token: string;
}

export interface LoginFailureAction extends Action {
	type: UsuarioTypes.LOGIN_FAILURE;
	erro: Error;
}

export interface LogoutAction extends Action {
	type: UsuarioTypes.LOGOUT;
}

export type UsuarioAction =
	| AddUsuarioAction
	| AddUsuarioSucessAction
	| AddUsuarioFailureAction
	| LoginAction
	| GetUsuarioAction
	| LoginSucessAction
	| LoginFailureAction
	| LogoutAction;
