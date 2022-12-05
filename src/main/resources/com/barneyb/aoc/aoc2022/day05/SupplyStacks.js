function setup(input) {
    const idx = input.indexOf("\n\n");
    const layout = input.substring(0, idx)
        .split("\n")
        .filter(l => l.trim());
    const last = layout[layout.length - 1];
    const stacks = [];
    for (let i = 0; i < last.length; i++) {
        if (last[i] === " ") continue
        const stack = [];
        stacks.push(stack);
        for (let r = layout.length - 2; r >= 0; r--) {
            const c = layout[r][i];
            if (!c || c === " ") break;
            stack.push(c);
        }
    }
    const instructions = input.substring(idx)
        .trim()
        .split("\n")
        .map(l => l.split(" "))
        .map(ws => [ parseInt(ws[1]), parseInt(ws[3]) - 1, parseInt(ws[5]) - 1 ]);
    const $container = d3.select("div.stacks")
    const $status = d3.select("div.status")
    const $stacks = $container.selectAll("section")
        .data(stacks)
        .enter()
        .append("section")
    const colors = new Map([ ..."ABCDEFGHIJKLMNOPQRSTUVWXYZ" ]
        .map((l, i, ls) => [ l, `hsl(${360 * i / ls.length} 70% 70%)` ]));
    let taken = 0;
    const total = instructions.reduce((t, i) => t + i[0], 0);
    const nFormat = new Intl.NumberFormat(undefined, {
        minimumIntegerDigits: 1,
        minimumFractionDigits: 1,
        maximumFractionDigits: 1,
        style: "percent",
    });
    const draw = () => {
        const $crates = $stacks.selectAll("article")
            .data(d => d)
        $crates
            .enter()
            .append("article")
            .text(d => d)
            .style("background-color", d =>
                colors.get(d))
        $crates
            .exit()
            .remove()
        $status.text(nFormat.format(taken / total))
    };
    draw();
    let ip = 0;
    let ip_n = 0;
    const interval = setInterval(() => {
        if (ip >= instructions.length) {
            clearInterval(interval);
            return; // DONE!
        }
        const ins = instructions[ip];
        if (ip_n >= ins[0]) {
            ip += 1;
            ip_n = 0;
            return; // NEXT!
        }
        ip_n += 1;
        taken += 1;
        stacks[ins[2]].push(stacks[ins[1]].pop());
        draw();
    }, 20)
}

fetch("SupplyStacks.txt")
    .then(r => r.text())
    .then(setup);

// setTimeout(() => setup(`
//     [D]
// [N] [C]
// [Z] [M] [P]
//  1   2   3
//
// move 1 from 2 to 1
// move 3 from 1 to 3
// move 2 from 2 to 1
// move 1 from 1 to 2`), 100)
