import {
	UsuarioTypes,
	NovoUsuario,
	Auth,
	AddUsuarioAction,
	AddUsuarioFailureAction,
	AddUsuarioSucessAction,
	LoginAction,
	LoginFailureAction,
	LoginSucessAction,
	LogoutAction,
	Usuario,
	GetUsuarioAction,
} from "./types";
import { Error } from "../types";

export const criarUsuario = (usuario: NovoUsuario): AddUsuarioAction => ({ type: UsuarioTypes.ADD_USUARIO, usuario });

export const criarUsuarioFalha = (erro: Error): AddUsuarioFailureAction => ({ type: UsuarioTypes.ADD_USUARIO_FAILURE, erro });

export const criarUsuarioSucesso = (sucesso: string): AddUsuarioSucessAction => ({ type: UsuarioTypes.ADD_USUARIO_SUCESS, sucesso });

export const login = (usuario: Auth): LoginAction => ({ type: UsuarioTypes.LOGIN, usuario });

export const getUsuario = (token: string): GetUsuarioAction => ({ type: UsuarioTypes.GET_USUARIO, token });

export const loginFalha = (erro: Error): LoginFailureAction => ({ type: UsuarioTypes.LOGIN_FAILURE, erro });

export const loginSucesso = (usuario: Usuario, token: string): LoginSucessAction => ({ type: UsuarioTypes.LOGIN_SUCESS, usuario, token });

export const logout = (): LogoutAction => ({ type: UsuarioTypes.LOGOUT });
