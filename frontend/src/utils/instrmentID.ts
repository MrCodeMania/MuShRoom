const insID = [
    ["피아노", 1],
    ["기타", 2],
    ["전자피아노", 3],
    ["우쿠렐레", 4],
    ["단소", 5],
    ["태평소", 6],
]
const grade = [
    ["초급", 1],
    ["중급", 2],
    ["고급", 3]
]
const IDmap = new Map<string, number>();
for (const item of Array.from(insID)) {
    IDmap.set(item[0].toString(), Number(item[1]));
}
const Grademap = new Map<string, number>();
for (const item of Array.from(grade)) {
    Grademap.set(item[0].toString(), Number(item[1]));
}
export { IDmap, Grademap }