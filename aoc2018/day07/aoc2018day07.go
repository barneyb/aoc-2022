package day07

import (
	_ "embed"
	"fmt"
	"strings"
	"unicode/utf8"

	"github.com/barneyb/aoc-2022/util"
)

//go:embed input.txt
var input_txt string

// adjacency list
type graph [][]int

func (g graph) addEdge(a, b int) {
	g[a] = append(g[a], b)
}

func (g graph) topoSort() []int {
	visited := make([]bool, len(g))
	order := make([]int, 0, len(g))
	var walk func(int)
	walk = func(u int) {
		if visited[u] {
			return
		}
		for _, v := range g[u] {
			walk(v)
		}
		visited[u] = true
		order = append(order, u)
	}
	for i := range g {
		walk(i)
	}
	return order
}

func (g graph) orderedTopoSort() []int {
	visited := make([]bool, len(g))
	order := make([]int, 0, len(g))
	for range g {
	node:
		for u := range g {
			if visited[u] {
				continue
			}
			for _, v := range g[u] {
				if !visited[v] {
					continue node
				}
			}
			visited[u] = true
			order = append(order, u)
			break
		}
	}
	return order
}

func (g graph) V() int {
	return len(g)
}

func PartOne(input string, n int) string {
	G := parse(input, n)
	return renderNodeList(G.orderedTopoSort())
}

func parse(input string, n int) graph {
	G := make(graph, n)
	for _, line := range strings.Split(strings.TrimSpace(input), "\n") {
		var as, bs string
		util.Must(fmt.Sscanf(line,
			"Step %1s must be finished before step %1s can begin.",
			&bs, &as))
		a, _ := utf8.DecodeRuneInString(as)
		b, _ := utf8.DecodeRuneInString(bs)
		G.addEdge(idx(a), idx(b))
	}
	return G
}

func idx(r rune) int {
	return int(r - 'A')
}

func lbl(n int) rune {
	return rune(n + 'A')
}

func renderNodeList(ns []int) string {
	result := make([]byte, 0, len(ns))
	for _, n := range ns {
		result = utf8.AppendRune(result, lbl(n))
	}
	return string(result)
}

func PartTwo(input string, n int, w int) int {
	G := parse(input, n)
	return G.V()
}
