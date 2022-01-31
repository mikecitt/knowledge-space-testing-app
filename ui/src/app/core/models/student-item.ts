import { PossibleAnswer } from "./possible-answer";

export interface StudentItem {
    id: number,
    text: string,
    imgName: string,
    answers: PossibleAnswer[]
    sectionId: number
}
