import { Domain } from ".";

export interface ITest {
    id: number,
    name: string,
    timer: number,
    validFrom: Date,
    validUntil: Date,
    createdById: number,
    domain: Domain
}
