import React, { useState, Dispatch, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";
import { Button } from "primereact/button";
import { Dialog } from "primereact/dialog";

import TopBar from "../components/topBar";
import { EventoForm } from "../components/eventoForm";
import Loader from "../components/loader";
import { AppState } from "../store";
import { Evento, BuscarEventoAction, EventoTypes, ExcluirEventoAction } from "../store/evento/types";

function Eventos() {
	const eventosState = useSelector((state: AppState) => state.eventos);
	const usuarioState = useSelector((state: AppState) => state.usuario);
	const eventos: Evento[] = eventosState.pageEvento ? eventosState.pageEvento.content : [];
	const [editando, setEditando] = useState(true);
	const [openDialog, setOpenDialog] = useState(false);
	const [confirmDialog, setConfigDialog] = useState(false);
	const [eventoSelecionado, setEventoSelecionado] = useState<Evento>();
	const [totalLinhas, setTotalLinhas] = useState(10);
	const [indexPagina, setIndexPagina] = useState(0);
	const [totalEventos, setTotalEventos] = useState(0);
	const dispatchLoad = useDispatch<Dispatch<BuscarEventoAction>>();
	const dispatchDelete = useDispatch<Dispatch<ExcluirEventoAction>>();
	const [eventoBuscar, setEventoBuscar] = useState<BuscarEventoAction>({
		type: EventoTypes.BUSCAR_EVENTOS,
		token: usuarioState.token,
		pageInfo: {
			page: eventosState.pageEvento?.pageable.pageNumber || 0,
			size: eventosState.pageEvento?.pageable.pageSize || 10,
		},
	});

	function onHideDialog() {
		setEditando(true);
		setEventoSelecionado(undefined);
		setOpenDialog(false);
	}

	function abrirDialog() {
		setOpenDialog(true);
	}

	function onSaveEvento() {
		setOpenDialog(false);
		setEditando(true);
		setEventoSelecionado(undefined);
		loadEventos();
	}

	function loadEventos() {
		eventoBuscar.pageInfo = { size: totalLinhas, page: indexPagina };
		setEventoBuscar(eventoBuscar);
		dispatchLoad(eventoBuscar);
	}

	useEffect(() => {
		loadEventos();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		setTotalLinhas(eventosState.pageEvento?.pageable.pageSize || 0);
		setIndexPagina(eventosState.pageEvento?.pageable.pageNumber || 0);
		setTotalEventos(eventosState.pageEvento?.totalElements || 0);
	}, [eventosState.pageEvento]);

	const botaoEsquerdo = <Button icon="pi pi-refresh" onClick={loadEventos} />;
	const acoesTemplate = (valor: any, coluna: any) => {
		return (
			<div style={{ textAlign: "center" }}>
				<Button type="button" icon="pi pi-pencil" className="p-button-success" style={{ marginRight: ".5em" }} onClick={() => editarEvento(valor)}></Button>
				<Button
					type="button"
					icon="pi pi-times"
					className="p-button-danger"
					onClick={() => {
						setEventoSelecionado(valor);
						setConfigDialog(true);
					}}></Button>
			</div>
		);
	};

	const dataTemplate = (rowData: any, column: any) => {
		function dataFormatada(data: Date) {
			const dia = data.getDate().toString();
			const diaF = dia.length === 1 ? "0" + dia : dia;
			const mes = (data.getMonth() + 1).toString();
			const mesF = mes.length === 1 ? "0" + mes : mes;
			const anoF = data.getFullYear();
			return diaF + "/" + mesF + "/" + anoF;
		}

		return <>{dataFormatada(new Date(rowData.data))}</>;
	};

	function excluirEvento(evento?: Evento) {
		setConfigDialog(false);
		if (!evento) return;

		dispatchDelete({ type: EventoTypes.EXCLUIR_EVENTO, evento, token: usuarioState.token, eventoBuscar });
	}

	function editarEvento(evento: Evento) {
		setEventoSelecionado(evento);
		abrirDialog();
	}

	function onPageChange(event: any) {
		setIndexPagina(event.first);
		setTotalLinhas(event.rows);
	}

	return (
		<div>
			<TopBar />
			<div className="layout-content p-grid p-justify-center">
				<div className="p-sm-12 p-md-8 p-lg-6">
					<DataTable
						value={eventos}
						paginator={true}
						paginatorLeft={botaoEsquerdo}
						paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
						currentPageReportTemplate="Mostrando {first} a {last} de {totalRecords} eventos"
						rows={totalLinhas}
						rowsPerPageOptions={[5, 10, 20]}
						first={indexPagina}
						dataKey="id"
						totalRecords={totalEventos}
						onPage={onPageChange}
						emptyMessage="Nenhum evento encontrado">
						<Column field="nome" header="Nome" />
						<Column field="data" header="Data" bodyStyle={{ textAlign: "center" }} body={dataTemplate} />
						<Column field="usuario.nome" header="Usuario" />
						<Column header="Ações" body={acoesTemplate} />
					</DataTable>
				</div>
			</div>
			<Button icon="pi pi-plus" className="fab-button" onClick={abrirDialog} />
			<Dialog id="dialogEvento" modal={true} onHide={onHideDialog} visible={openDialog} header="Evento" closable={false}>
				<EventoForm editando={editando} evento={eventoSelecionado} onSave={onSaveEvento} onCancel={onHideDialog} />
			</Dialog>
			<Dialog
				id="confirmDialog"
				modal={true}
				onHide={() => {
					setEventoSelecionado(undefined);
					setConfigDialog(false);
				}}
				visible={confirmDialog}
				header="Excluir"
				closable={true}>
				<div className="p-grid p-justify-start">
					<div className="p-col-12">Realmente deseja excluir esse evento?</div>
					<div className="p-col-12 p-grid p-p-justify-end">
						<div className="p-col-3">
							<Button label="Sim" onClick={() => excluirEvento(eventoSelecionado)} />
						</div>
						<div className="p-col-3">
							<Button label="Não" onClick={() => setConfigDialog(false)} />
						</div>
					</div>
				</div>
			</Dialog>
			<Loader display={eventosState.loading || false} />
		</div>
	);
}

export default Eventos;
