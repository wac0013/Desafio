export interface Sort {
	empty: boolean;
	sorted: boolean;
	unsorted: boolean;
}

export interface Pageable {
	offset: number;
	pageNumber: number;
	pageSize: number;
	paged: boolean;
	sort: Sort;
	unpaged: boolean;
}

export interface Page<T> {
	content: T[];
	empty: boolean;
	first: boolean;
	last: boolean;
	number: number;
	numberOfElements: number;
	pageable: Pageable;
	size: number;
	sort: Sort;
	totalElements: number;
	totalPages: number;
}

export interface Error {
	timestamp?: number;
	status?: number;
	error: string;
	message: string;
	datail?: string;
	path?: string;
}

export interface PaginaHeader {
	page: number;
	size: number;
	order?: string;
	desc?: boolean;
}
