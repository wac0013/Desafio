import { call, put } from "redux-saga/effects";
import api, { BEARER } from "../constants";
import { loginSucesso, loginFalha, criarUsuarioSucesso, criarUsuarioFalha, getUsuario } from "./actions";
import { LoginAction, AddUsuarioAction, GetUsuarioAction } from "./types";
import { Error } from "../types";

export function* loginSaga(action: LoginAction) {
	try {
		const token = yield call(api.post, "usuario/login", action.usuario);

		localStorage.setItem("token", token.data);

		yield put(getUsuario(token.data));
	} catch (error) {
		const erro: Error = {
			message: error.response?.data?.message || error.response?.statusText,
			datail: error.response?.data?.mensagemDetalhada,
			error: error.message,
		};

		yield put(loginFalha(erro));
	}
}

export function* getUsuarioSaga(action: GetUsuarioAction) {
	try {
		const usuario = yield call(api.get, "usuario", { headers: { AUTHORIZATION: `${BEARER} ${action.token}` } });

		yield put(loginSucesso(usuario.data, action.token));
	} catch (error) {
		const erro: Error = {
			message: error.response?.data?.message || error.response?.statusText,
			datail: error.response?.data?.mensagemDetalhada,
			error: error.message,
		};

		yield put(loginFalha(erro));
	}
}

export function* cadastroSaga(action: AddUsuarioAction) {
	try {
		yield call(api.post, "usuario/novo", action.usuario);

		yield put(criarUsuarioSucesso("Usuario criado com sucesso"));
	} catch (error) {
		const erro: Error = {
			message: error.response?.data?.message || error.response?.statusText,
			datail: error.response?.data?.mensagemDetalhada,
			error: error.message,
		};
		yield put(criarUsuarioFalha(erro));
	}
}
