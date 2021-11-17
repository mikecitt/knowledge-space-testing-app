export interface PagedEntity<T> {
    totalPages: number,
    totalElements: number,
    currentPage: number,
    pageSize: number,
    content: T[]
}