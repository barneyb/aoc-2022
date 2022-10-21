package day14

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

func TestPartTwo(t *testing.T) {
	cases := []struct {
		search string
		want   int
	}{
		{"51589", 9},
		{"01245", 5},
		{"92510", 18},
		{"59414", 2018},
		{"101", 2},
		{"371", 0},
		{"074501", 20288091},
	}
	for _, c := range cases {
		t.Run(fmt.Sprintf("(%q)", c.search), func(t *testing.T) {
			got := PartTwo(c.search)
			if got != c.want {
				t.Errorf("PartTwo(%q) == %d, want %d", c.search, got, c.want)
			}
		})
	}
}
