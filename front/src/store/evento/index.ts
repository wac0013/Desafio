import { EventoState, EventoTypes, EventoAction } from "./types";
import { Reducer } from "redux";

const INITIAL_SATATE: EventoState = {
	pageEvento: undefined,
	sucess: "",
	error: undefined,
	loading: false,
};

const reducer: Reducer<EventoState, EventoAction> = (state = INITIAL_SATATE, action) => {
	switch (action.type) {
		case EventoTypes.BUSCAR_EVENTOS:
			return { ...state, loading: true };
		case EventoTypes.BUSCAR_EVENTOS_FAILURE:
			return { ...state, error: action.erro, loading: false };
		case EventoTypes.BUSCAR_EVENTOS_SUCESS:
			return { ...state, loading: false, pageEvento: action.page };
		case EventoTypes.EXCLUIR_EVENTO:
			return { ...state, loading: true };
		case EventoTypes.EXCLUIR_EVENTO_FAILURE:
			return { ...state, error: action.erro, loading: false };
		case EventoTypes.EXCLUIR_EVENTO_SUCESS:
			return { ...state, sucess: action.sucesso, loading: false };
		case EventoTypes.SALVAR_EVENTO:
			return { ...state, loading: true };
		case EventoTypes.SALVAR_EVENTO_FAILURE:
			return { ...state, error: action.erro, loading: false };
		case EventoTypes.SALVAR_EVENTO_SUCESS:
			return { ...state, sucess: action.sucesso, loading: false };
		case EventoTypes.ADD_EVENTO:
			return { ...state, loading: true };
		case EventoTypes.ADD_EVENTO_FAILURE:
			return { ...state, error: action.erro, loading: false };
		case EventoTypes.ADD_EVENTO_SUCESS:
			return { ...state, sucess: action.sucesso, loading: false };
		default:
			return state;
	}
};

export default reducer;
