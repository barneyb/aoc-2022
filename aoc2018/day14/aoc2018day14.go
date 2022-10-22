package day14

import (
	_ "embed"
	"strconv"
	"strings"
	"unicode/utf8"

	"github.com/barneyb/aoc-2022/util"
)

//go:embed input.txt
var input_txt string

const empty = -1

type board struct {
	a, b    int
	arr     []int
	pending int
}

func newBoard() board {
	return board{
		b:       1,
		arr:     []int{3, 7},
		pending: empty,
	}
}

func (b *board) append(n int) {
	b.arr = append(b.arr, n)
}

func (b *board) appendAndAdvance(n int) {
	b.append(n)
	b.a = b.nextPos(b.a)
	b.b = b.nextPos(b.b)
}

func (b *board) nextPos(curr int) int {
	return (curr + 1 + b.scoreAt(curr)) % b.len()
}

func (b *board) tick() {
	if b.pending >= 0 {
		b.appendAndAdvance(b.pending)
		b.pending = empty
		return
	}
	sum := b.arr[b.a] + b.arr[b.b]
	if sum < 10 {
		b.appendAndAdvance(sum)
	} else {
		b.append(1)
		b.pending = sum % 10
	}
}

func (b *board) len() int {
	return len(b.arr)
}

func (b *board) scoreAt(i int) int {
	return b.arr[i]
}

func (b *board) endsWith(needle []int) bool {
	i := b.len()
	j := len(needle)
	if i < j {
		return false
	}
	for d := 0; d < j; d++ {
		if b.scoreAt(i-d-1) != needle[j-d-1] {
			return false
		}
	}
	return true
}

func PartOne(input string) string {
	return PartOneLen(input, 10)
}

func PartOneLen(input string, l int) string {
	n := util.Must(strconv.Atoi(strings.TrimSpace(input)))
	b := newBoard()
	for b.len() < n+l {
		b.tick()
	}
	result := ""
	for i := 0; i < l; i++ {
		result += strconv.Itoa(b.scoreAt(n + i))
	}
	return result
}

func PartTwo(input string) int {
	input = strings.TrimSpace(input)
	nums := make([]int, utf8.RuneCountInString(input))
	for i, r := range input {
		nums[i] = int(r - '0')
	}
	for b := newBoard(); true; b.tick() {
		if b.endsWith(nums) {
			return b.len() - len(nums)
		}
	}
	panic("infinite loop terminated?")
}
