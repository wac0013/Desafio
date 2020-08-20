import React, { useState, Dispatch } from "react";
import { InputText } from "primereact/inputtext";
import { Calendar } from "primereact/calendar";
import { useSelector, useDispatch } from "react-redux";
import { AppState } from "../store";
import { Button } from "primereact/button";
import { Evento, AddEventoAction, SalvarEventoAction, EventoTypes, BuscarEventoAction } from "../store/evento/types";

export interface EventFormProps {
	editando: boolean;
	evento?: Evento;
	onCancel: Function;
	onSave: Function;
}

export function EventoForm(props: EventFormProps) {
	const usuarioState = useSelector((state: AppState) => state.usuario);
	const eventoState = useSelector((state: AppState) => state.eventos);
	const [nome, setNome] = useState(props.evento?.nome || "");
	const [data, setData] = useState(props.evento?.data ? new Date(props.evento?.data) : undefined);
	const [usuario] = useState(props.evento?.usuario || usuarioState.usuario);
	const dispatchAdd = useDispatch<Dispatch<AddEventoAction>>();
	const dispatchSalvar = useDispatch<Dispatch<SalvarEventoAction>>();
	const dataMinima = new Date();

	const eventoBuscar: BuscarEventoAction = {
		type: EventoTypes.BUSCAR_EVENTOS,
		token: usuarioState.token,
		pageInfo: {
			page: eventoState.pageEvento?.pageable.pageNumber || 0,
			size: eventoState.pageEvento?.pageable.pageSize || 10,
		},
	};

	function onChangeNome(event: any) {
		setNome(event.target.value);
	}

	function onChangeData(event: any) {
		setData(event.target.value);
	}

	function salvarEvento(event: any) {
		event.preventDefault();
		let evento: Evento;

		if (props.evento) {
			evento = { data, nome, usuario: props.evento.usuario, id: props.evento.id };
			dispatchSalvar({
				type: EventoTypes.SALVAR_EVENTO,
				evento,
				token: usuarioState.token,
				eventoBuscar,
			});
		} else {
			evento = { data, nome, usuario };
			dispatchAdd({ type: EventoTypes.ADD_EVENTO, evento, token: usuarioState.token, eventoBuscar });
		}

		props.onSave();
	}

	function cancelar(event: any) {
		event.preventDefault();
		props.onCancel();
	}

	return (
		<form className="p-grid p-justify-start" onSubmit={salvarEvento}>
			<div className="p-field p-col-12">
				<label htmlFor="nome">Nome Evento</label>
				<InputText id="nome" placeholder="Evento" value={nome} onChange={props.editando ? onChangeNome : undefined} required={true} />
			</div>
			<div className="p-field p-col-12">
				<label htmlFor="data" style={{ width: "100%" }}>
					Data
				</label>
				<Calendar
					style={{ width: "100%" }}
					inputId="data"
					placeholder="  /  /    "
					value={data}
					onChange={props.editando ? onChangeData : undefined}
					showIcon={true}
					dateFormat="dd/mm/yy"
					minDate={dataMinima}
					required={true}
					appendTo={document.getElementById("dialogEvento")}
				/>
			</div>
			<div className="p-field p-col-12">
				<label htmlFor="nomeUsuario">Nome usuário</label>
				<InputText id="nomeUsuario" value={usuario?.nome} readOnly />
			</div>

			<div className="p-col-12 p-grid p-justify-between">
				{props.editando && (
					<div className="p-col-5">
						<Button label="Salvar" className="p-button-rounded" icon="pi pi-save" iconPos="right" style={{ height: "30px" }} />
					</div>
				)}
				<div className="p-col-5">
					<Button
						type="buttom"
						label="Cancelar"
						onClick={cancelar}
						className="p-button-danger p-button-rounded"
						icon="pi pi-times"
						iconPos="right"
						style={{ height: "30px" }}
					/>
				</div>
			</div>
		</form>
	);
}
