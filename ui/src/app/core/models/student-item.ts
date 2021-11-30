import { PossibleAnswer } from "./possible-answer";

export interface StudentItem {
    id: number,
    text: string,
    picture: Uint8Array,
    answers: PossibleAnswer[]
    sectionId: number
}
