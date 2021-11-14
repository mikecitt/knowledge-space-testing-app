import { Answer } from "./answer";

export interface Item {
    id: number,
    text: string,
    picture: Uint8Array,
    answers: Answer[]
    sectionId: number
}
