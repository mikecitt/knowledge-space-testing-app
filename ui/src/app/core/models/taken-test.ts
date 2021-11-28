export interface TakenTest {
    id: number,
    testName: string,
    testDuration: number,
    start: Date,
    end: Date | null,
    score: number
}
