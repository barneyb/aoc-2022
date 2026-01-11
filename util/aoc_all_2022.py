import os
import subprocess
import tempfile


def get_solvers():
    solvers_fn = os.path.join(os.path.dirname(__file__), "solvers.txt")
    with open(solvers_fn, "r", encoding="utf-8") as file:
        lines = [line.rstrip() for line in file if len(line.strip()) > 0 and line.lstrip()[0] != "#"]
    return [(int(y), int(d), y, d, fn) for y, d, fn in [l.split(" ") for l in lines]]


def java_dir():
    return os.path.join(os.path.dirname(__file__), "..")


def java_env():
    return {"JAVA_VERSION": "11"}


def support():
    proc = subprocess.run(
        ["./mvnw", "compile", "--quiet"],
        cwd=java_dir(),
        env=java_env(),
        text=True,
    )
    return [s[:2] for s in get_solvers()]


def solve(year, day, data):
    s = [s for s in get_solvers() if s[0] == year and s[1] == day]
    if len(s) == 0:
        raise TypeError(f"no solver for {year}/{day} is known.")
    if len(s) > 1:
        raise TypeError(f"Found {len(s)} solvers for {year}/{day}?!")
    ys, ds, fn = s[0][2:]
    clazz = f"com.barneyb.aoc.aoc{ys}.day{ds}."
    if fn[-3:] == ".kt":
        # FullOfHotAirKt
        clazz += fn[:-3] + "Kt"
    else:
        # NotQuiteLisp
        clazz += fn[:-5]
    with tempfile.NamedTemporaryFile(
            mode="w+t", prefix=f"input_{year}_{day}_", suffix=".txt"
    ) as fp:
        fp.write(data)
        fp.flush()
        proc = subprocess.run(
            [
                "./mvnw",
                "--quiet",
                "-Dbenchmark=false",
                "-Dtime_in_nanos=true",
                f"-Daoc_all_file={fp.name}",
                "exec:java",
                f"-Dexec.mainClass={clazz}"
            ],
            cwd=java_dir(),
            env=java_env(),
            capture_output=True,
            text=True,
        )
        part_a = None
        part_b = None
        nanos = None
        for line in proc.stdout.strip().splitlines():
            prefix = line[:13]
            if prefix == "Part One   : ":
                part_a = line[13:].split(" ")[0]
            elif prefix == "Part Two   : ":
                part_b = line[13:].split(" ")[0]
            elif prefix == "Total Time : ":
                nanos = int(line[13:].split(" ")[0])
        return part_a, part_b, nanos
