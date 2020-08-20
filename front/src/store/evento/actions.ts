import {
	EventoTypes,
	Evento,
	BuscarEventoAction,
	ExcluirEventoAction,
	SalvarEventoAction,
	ExcluirEventoSucessAction,
	ExcluirEventoFailureAction,
	SalvarEventoSucessAction,
	SalvarEventoFailureAction,
	EventoFiltro,
	BuscarEventoSucessAction,
	BuscarEventoFailureAction,
	AddEventoAction,
	AddEventoSucessAction,
	AddEventoFailureAction,
} from "./types";
import { Error, Page, PaginaHeader } from "../types";

export const buscarEventos = (filtro: EventoFiltro, pageInfo: PaginaHeader, token: string): BuscarEventoAction => ({
	type: EventoTypes.BUSCAR_EVENTOS,
	filtro,
	pageInfo,
	token,
});
export const buscarEventosSucesso = (page: Page<Evento>): BuscarEventoSucessAction => ({ type: EventoTypes.BUSCAR_EVENTOS_SUCESS, page });
export const buscarEventosFalha = (erro: Error): BuscarEventoFailureAction => ({ type: EventoTypes.BUSCAR_EVENTOS_FAILURE, erro });
export const excluirEvento = (evento: Evento, token: string, eventoBuscar: BuscarEventoAction): ExcluirEventoAction => ({
	type: EventoTypes.EXCLUIR_EVENTO,
	evento,
	token,
	eventoBuscar,
});
export const excluirEventoSucesso = (sucesso: string): ExcluirEventoSucessAction => ({ type: EventoTypes.EXCLUIR_EVENTO_SUCESS, sucesso });
export const excluirEventoFalha = (erro: Error): ExcluirEventoFailureAction => ({ type: EventoTypes.EXCLUIR_EVENTO_FAILURE, erro });
export const salvarEvento = (evento: Evento, token: string, eventoBuscar: BuscarEventoAction): SalvarEventoAction => ({
	type: EventoTypes.SALVAR_EVENTO,
	evento,
	token,
	eventoBuscar,
});
export const salvarEventoSucesso = (sucesso: string): SalvarEventoSucessAction => ({ type: EventoTypes.SALVAR_EVENTO_SUCESS, sucesso });
export const salvarEventoFalha = (erro: Error): SalvarEventoFailureAction => ({ type: EventoTypes.SALVAR_EVENTO_FAILURE, erro });
export const addEvento = (evento: Evento, token: string, eventoBuscar: BuscarEventoAction): AddEventoAction => ({
	type: EventoTypes.ADD_EVENTO,
	evento,
	token,
	eventoBuscar,
});
export const addEventoSucesso = (sucesso: string): AddEventoSucessAction => ({ type: EventoTypes.ADD_EVENTO_SUCESS, sucesso });
export const addEventoFalha = (erro: Error): AddEventoFailureAction => ({ type: EventoTypes.ADD_EVENTO_FAILURE, erro });
