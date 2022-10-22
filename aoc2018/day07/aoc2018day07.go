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
	for len(order) < len(visited) {
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

func PartOne(input string, n int) string {
	G := make(graph, n)
	for _, line := range strings.Split(strings.TrimSpace(input), "\n") {
		var as, bs string
		util.Must(fmt.Sscanf(line,
			"Step %1s must be finished before step %1s can begin.",
			&bs, &as))
		a, _ := utf8.DecodeRuneInString(as)
		b, _ := utf8.DecodeRuneInString(bs)
		G.addEdge(int(a-'A'), int(b-'A'))
	}
	return renderNodeList(G.orderedTopoSort())
}

func renderNodeList(ns []int) string {
	result := make([]byte, 0, len(ns))
	for _, n := range ns {
		result = utf8.AppendRune(result, rune(n+'A'))
	}
	return string(result)
}
