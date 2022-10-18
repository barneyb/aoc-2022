package runningsum

import (
	"github.com/barneyb/aoc-2022/util"
	"testing"
)

func Test(t *testing.T) {
	cases := []struct {
		in, want []int
	}{
		{[]int{}, []int{}},
		{[]int{1}, []int{1}},
		{[]int{1, 2}, []int{1, 3}},
		{[]int{1, 2, 3, 4, 5, 6}, []int{1, 3, 6, 10, 15, 21}},
		{[]int{1, 2, -4, 1}, []int{1, 3, -1, 0}},
	}
	for _, c := range cases {
		got := RunningSum(c.in)
		if !util.Equal(got, c.want) {
			t.Errorf("RunningSum(%d) == %d, want %d", c.in, got, c.want)
		}
	}
}
