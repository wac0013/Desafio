import { Usuario } from "../usuario/types";
import { Page, Error, PaginaHeader } from "../types";
import { Action } from "redux";

export enum EventoTypes {
	BUSCAR_EVENTOS = "@evento/BUSCAR",
	BUSCAR_EVENTOS_FAILURE = "@evento/BUSCAR_FAILURE",
	BUSCAR_EVENTOS_SUCESS = "@evento/BUSCAR_SUCESS",
	EXCLUIR_EVENTO = "@evento/EXCLUIR",
	EXCLUIR_EVENTO_FAILURE = "@evento/EXCLUIR_FAILURE",
	EXCLUIR_EVENTO_SUCESS = "@evento/EXCLUIR_SUCESS",
	SALVAR_EVENTO = "@evento/SALVAR",
	SALVAR_EVENTO_FAILURE = "@evento/SALVAR_FAILURE",
	SALVAR_EVENTO_SUCESS = "@evento/SALVAR_SUCESS",
	ADD_EVENTO = "@evento/ADD",
	ADD_EVENTO_FAILURE = "@evento/ADD_FAILURE",
	ADD_EVENTO_SUCESS = "@evento/ADD_SUCESS",
}

export interface Evento {
	id?: string;
	nome?: string;
	data?: Date;
	usuario?: Usuario;
}

export interface EventoFiltro {
	nome?: string;
	dataInicio?: Date;
	dataFim?: Date;
	usuario?: string;
}

export interface EventoState {
	readonly pageEvento?: Page<Evento>;
	readonly sucess?: string;
	readonly loading?: boolean;
	readonly error?: Error;
}

export interface BuscarEventoAction extends Action {
	type: EventoTypes.BUSCAR_EVENTOS;
	filtro?: EventoFiltro;
	pageInfo: PaginaHeader;
	token: string;
}

export interface BuscarEventoFailureAction extends Action {
	type: EventoTypes.BUSCAR_EVENTOS_FAILURE;
	erro: Error;
}

export interface BuscarEventoSucessAction extends Action {
	type: EventoTypes.BUSCAR_EVENTOS_SUCESS;
	page: Page<Evento>;
}

export interface ExcluirEventoAction extends Action {
	type: EventoTypes.EXCLUIR_EVENTO;
	evento: Evento;
	token: string;
	eventoBuscar: BuscarEventoAction;
}

export interface ExcluirEventoFailureAction extends Action {
	type: EventoTypes.EXCLUIR_EVENTO_FAILURE;
	erro: Error;
}

export interface ExcluirEventoSucessAction extends Action {
	type: EventoTypes.EXCLUIR_EVENTO_SUCESS;
	sucesso: string;
}

export interface SalvarEventoAction extends Action {
	type: EventoTypes.SALVAR_EVENTO;
	evento: Evento;
	token: string;
	eventoBuscar: BuscarEventoAction;
}

export interface SalvarEventoFailureAction extends Action {
	type: EventoTypes.SALVAR_EVENTO_FAILURE;
	erro: Error;
}

export interface SalvarEventoSucessAction extends Action {
	type: EventoTypes.SALVAR_EVENTO_SUCESS;
	sucesso: string;
}

export interface AddEventoAction extends Action {
	type: EventoTypes.ADD_EVENTO;
	evento: Evento;
	token: string;
	eventoBuscar: BuscarEventoAction;
}

export interface AddEventoFailureAction extends Action {
	type: EventoTypes.ADD_EVENTO_FAILURE;
	erro: Error;
}

export interface AddEventoSucessAction extends Action {
	type: EventoTypes.ADD_EVENTO_SUCESS;
	sucesso: string;
}

export type EventoAction =
	| BuscarEventoAction
	| BuscarEventoFailureAction
	| BuscarEventoSucessAction
	| ExcluirEventoAction
	| ExcluirEventoFailureAction
	| ExcluirEventoSucessAction
	| SalvarEventoAction
	| SalvarEventoFailureAction
	| SalvarEventoSucessAction
	| AddEventoAction
	| AddEventoFailureAction
	| AddEventoSucessAction;
