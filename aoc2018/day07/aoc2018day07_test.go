package day07

import (
	"fmt"
	"testing"
)

const exampleOne = `
Step C must be finished before step A can begin.
Step C must be finished before step F can begin.
Step A must be finished before step B can begin.
Step A must be finished before step D can begin.
Step B must be finished before step E can begin.
Step D must be finished before step E can begin.
Step F must be finished before step E can begin.
`

func TestPartOne(t *testing.T) {
	tests := []struct {
		in   string
		n    int
		want string
	}{
		{exampleOne, 6, "CABDFE"},
		{input_txt, 26, "LAPFCRGHVZOTKWENBXIMSUDJQY"},
	}
	for _, c := range tests {
		t.Run(c.want, func(t *testing.T) {
			got := PartOne(c.in, c.n)
			if got != c.want {
				t.Errorf("gave %q, want %q", got, c.want)
			}
		})
	}
}

func TestPartTwo(t *testing.T) {
	tests := []struct {
		in         string
		n, w, want int
	}{
		{exampleOne, 6, 2, 15},
		{input_txt, 26, 5, 936},
	}
	for _, c := range tests {
		t.Run(fmt.Sprintf("%d,%d", c.n, c.w), func(t *testing.T) {
			got := PartTwo(c.in, c.n, c.w)
			if got != c.want {
				t.Errorf("gave %d, want %d", got, c.want)
			}
		})
	}
}
