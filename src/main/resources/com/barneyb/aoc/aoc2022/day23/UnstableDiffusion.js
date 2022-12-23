const NORTH = 0;
const EAST = 1;
const SOUTH = 2;
const WEST = 3;

const X = 0;
const Y = 0;

function init(data) {
    const $svg = d3.select("svg")
        .attr("viewBox", data.bounds.join(" "))
    const paintMove = $el => {
        $el.attr("points", d => {
            switch (d[2]) {
                case NORTH:
                    return `${d[0]},${d[1] + 0.5} ${d[0] + 0.5},${d[1] - 0.5} ${d[0] + 1},${d[1] + 0.5}`
                case SOUTH:
                    return `${d[0]},${d[1] + 0.5} ${d[0] + 0.5},${d[1] + 1.5} ${d[0] + 1},${d[1] + 0.5}`
                case EAST:
                    return `${d[0] + 0.5},${d[1]} ${d[0] + 1.5},${d[1] + 0.5} ${d[0] + 0.5},${d[1] + 1}`
                case WEST:
                    return `${d[0] + 0.5},${d[1]} ${d[0] - 0.5},${d[1] + 0.5} ${d[0] + 0.5},${d[1] + 1}`
            }
        });
        return $el
    }
    const paintStationary = $el => {
        $el.attr("cx", d => d[0] + 0.5)
            .attr("cy", d => d[1] + 0.5)
            .attr("r", 0.5);
        return $el
    }
    const paintConflict = $el => {
        $el.attr("x", d => d[0] - 0.25)
            .attr("y", d => d[1] - 0.25)
            .attr("width", 1.5)
            .attr("height", 1.5)
            .attr("opacity", 0.7);
        return $el
    }
    const render = i => {
        if (i >= data.rounds.length) return

        const $moving = paintStationary($svg
            .selectAll("circle.moving")
            .data(data.rounds[i].moves))
        paintStationary($moving.enter()
            .append("circle")
            .classed("moving", true))
        $moving.exit()
            .remove()

        const $moves = paintMove($svg
            .selectAll("polygon.moving")
            .data(data.rounds[i].moves))
        paintMove($moves.enter()
            .append("polygon")
            .classed("moving", true))
        $moves.exit()
            .remove()

        const $stationary = paintStationary($svg
            .selectAll("circle.stationary")
            .data(data.rounds[i].stationary))
        paintStationary($stationary.enter()
            .append("circle")
            .classed("stationary", true))
        $stationary.exit()
            .remove()

        const $conflicts = paintConflict($svg
            .selectAll("rect.conflict")
            .data(data.rounds[i].conflicts))
        paintConflict($conflicts.enter()
            .append("rect")
            .classed("conflict", true))
        $conflicts.exit()
            .remove()

        setTimeout(() => render(i + 1), 250)
    }
    render(0)
}

fetch("UnstableDiffusion.json")
    .then(r => r.json())
    .then(init);
