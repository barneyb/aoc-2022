# Advent of Code 2022

The yearly repo, seeded with a few solvers for random problems from prior years.
If you don't know what [Advent of Code](https://adventofcode.com) is, you should
go see! It's both lovely, and will help make sense of this repo. :)

2022 is Kotlin. Prior years have been Kotlin, Rust, Java, Haskell, Ruby, Go, and
Python. Of those, Java's the only one I can claim any professional experience.
Learning new languages/environments is both fun and educational, and AoC is a
great context to experiment with.

https://github.com/barneyb/aoc2017 has an index, if you want my details.

There are a few tests which use my real input; they can be excluded by adding
`CI=true` to your environment before running.

## Start Script

The `start.sh` script can be used to bootstrap a day's puzzle. Create
`.cookie.txt` w/ your cookie to auto-download input files (via cURL):

    Set-Cookie: session=123...def
