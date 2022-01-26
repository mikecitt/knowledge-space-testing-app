import { Domain, Edge } from ".";

export interface KnowledgeSpace {
    id: number,
    name: string,
    isReal: boolean,
    edges: Edge[],
    domain: Domain
}
