import { combineReducers, Store, applyMiddleware, createStore } from "redux";
import { all, takeLatest } from "redux-saga/effects";
import createSagaMiddleware from "redux-saga";
import eventos from "./evento";
import usuario from "./usuario";
import { EventoState, EventoTypes } from "./evento/types";
import { UsuarioState, UsuarioTypes } from "./usuario/types";
import { loginSaga, getUsuarioSaga, cadastroSaga } from "./usuario/saga";
import { addEventoSaga, salvarEventoSaga, buscarEventoSaga, excluirEventoSaga } from "./evento/saga";

export interface AppState {
	eventos: EventoState;
	usuario: UsuarioState;
}

const rootReducers = combineReducers({ eventos, usuario });

export function* rootSaga() {
	return yield all([
		takeLatest(UsuarioTypes.LOGIN, loginSaga),
		takeLatest(UsuarioTypes.GET_USUARIO, getUsuarioSaga),
		takeLatest(UsuarioTypes.ADD_USUARIO, cadastroSaga),
		takeLatest(EventoTypes.ADD_EVENTO, addEventoSaga),
		takeLatest(EventoTypes.SALVAR_EVENTO, salvarEventoSaga),
		takeLatest(EventoTypes.BUSCAR_EVENTOS, buscarEventoSaga),
		takeLatest(EventoTypes.EXCLUIR_EVENTO, excluirEventoSaga),
	]);
}

const sagaMiddleware = createSagaMiddleware();

const store: Store<AppState> = createStore(rootReducers, applyMiddleware(sagaMiddleware));

sagaMiddleware.run(rootSaga);

export default store;
