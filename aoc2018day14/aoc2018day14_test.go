package aoc2018day14

import (
	"fmt"
	"testing"
)

func TestPartOne(t *testing.T) {
	cases := []struct {
		n    int
		want string
	}{
		{9, "5158916779"},
		{5, "0124515891"},
		{18, "9251071085"},
		{2018, "5941429882"},
		{74501, "1464411010"},
	}
	for _, c := range cases {
		got := PartOne(c.n)
		if got != c.want {
			t.Errorf("PartOne(%d) == %q, want %q", c.n, got, c.want)
		}
	}
}

func TestPartOneLen(t *testing.T) {
	cases := []struct {
		n, l int
		want string
	}{
		{2, 1, "1"},
		{3, 1, "0"},
		{4, 3, "101"},
	}
	for _, c := range cases {
		t.Run(fmt.Sprintf("(%d,%d)", c.n, c.l), func(t *testing.T) {
			got := PartOneLen(c.n, c.l)
			if got != c.want {
				t.Errorf("PartOneLen(%d, %d) == %q, want %q", c.n, c.l, got, c.want)
			}
		})
	}
}
