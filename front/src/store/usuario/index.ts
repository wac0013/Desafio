import { UsuarioState, UsuarioTypes, UsuarioAction } from "./types";
import { Reducer } from "redux";

const INITIAL_SATATE: UsuarioState = {
	usuario: undefined,
	token: localStorage.getItem("token") || "",
	sucess: "",
	error: undefined,
	loading: false,
};

const reducer: Reducer<UsuarioState, UsuarioAction> = (state = INITIAL_SATATE, action) => {
	switch (action.type) {
		case UsuarioTypes.ADD_USUARIO:
			return { ...state, loading: true };
		case UsuarioTypes.ADD_USUARIO_SUCESS:
			return { ...state, loading: false, sucess: action.sucesso };
		case UsuarioTypes.ADD_USUARIO_FAILURE:
			localStorage.setItem("token", "");
			return { ...state, error: action.erro, loading: false, sucess: "", token: "" };
		case UsuarioTypes.LOGIN:
			return { ...state, loading: true };
		case UsuarioTypes.GET_USUARIO:
			return { ...state };
		case UsuarioTypes.LOGIN_SUCESS:
			return { ...state, usuario: action.usuario, token: action.token, loading: false };
		case UsuarioTypes.LOGIN_FAILURE:
			return { ...state, error: action.erro, loading: false, sucess: "" };
		case UsuarioTypes.LOGOUT:
			localStorage.setItem("token", "");
			return { ...state, usuario: undefined, token: "" };
		default:
			return state;
	}
};

export default reducer;
