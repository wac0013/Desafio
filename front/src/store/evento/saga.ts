import { call, put } from "redux-saga/effects";
import api, { BEARER } from "../constants";
import { SalvarEventoAction, ExcluirEventoAction, BuscarEventoAction } from "./types";
import {
	salvarEventoSucesso,
	salvarEventoFalha,
	excluirEventoSucesso,
	excluirEventoFalha,
	buscarEventosSucesso,
	buscarEventosFalha,
	addEventoSucesso,
	addEventoFalha,
} from "./actions";
import { Error } from "../types";

export function* salvarEventoSaga(action: SalvarEventoAction) {
	try {
		yield call(api.put, "evento/salvar", action.evento, { headers: { AUTHORIZATION: `${BEARER} ${action.token}` } });

		yield put(salvarEventoSucesso("Evento Salvo"));
		yield call(buscarEventoSaga, action.eventoBuscar);
	} catch (error) {
		const erro: Error = {
			message: error.response?.data?.message || error.response?.statusText,
			datail: error.response?.data?.mensagemDetalhada,
			error: error.message,
		};
		yield put(salvarEventoFalha(erro));
	}
}

export function* excluirEventoSaga(action: ExcluirEventoAction) {
	try {
		yield call(api.delete, "evento/excluir", { headers: { AUTHORIZATION: `${BEARER} ${action.token}` }, params: { id: action.evento.id } });

		yield put(excluirEventoSucesso("Evento excluído"));
		yield call(buscarEventoSaga, action.eventoBuscar);
	} catch (error) {
		const erro: Error = {
			message: error.response?.data?.message || error.response?.statusText,
			datail: error.response?.data?.mensagemDetalhada,
			error: error.message,
		};
		yield put(excluirEventoFalha(erro));
	}
}

export function* buscarEventoSaga(action: BuscarEventoAction) {
	try {
		const resposta = yield call(api.get, "evento/buscar-eventos", {
			headers: {
				AUTHORIZATION: `${BEARER} ${action.token}`,
				page: action.pageInfo.page,
				size: action.pageInfo.size,
				order: action.pageInfo.order,
				desc: action.pageInfo.desc,
			},
		});

		yield put(buscarEventosSucesso(resposta.data));
	} catch (error) {
		const erro: Error = {
			message: error.response?.data?.message || error.response?.statusText,
			datail: error.response?.data?.mensagemDetalhada,
			error: error.message,
		};
		yield put(buscarEventosFalha(erro));
	}
}

export function* addEventoSaga(action: SalvarEventoAction) {
	try {
		yield call(api.post, "evento/criar", action.evento, { headers: { AUTHORIZATION: `${BEARER} ${action.token}` } });

		yield put(addEventoSucesso("Evento criado"));
		yield call(buscarEventoSaga, action.eventoBuscar);
	} catch (error) {
		const erro: Error = {
			message: error.response?.data?.message || error.response?.statusText,
			datail: error.response?.data?.mensagemDetalhada,
			error: error.message,
		};
		yield put(addEventoFalha(erro));
	}
}
