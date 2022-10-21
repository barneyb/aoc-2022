package aoc2018day14

import "strconv"

type board struct {
	a, b int
	arr  []int
}

func newBoard() board {
	return board{
		a:   0,
		b:   1,
		arr: []int{3, 7},
	}
}

func (b *board) append(n int) {
	b.arr = append(b.arr, n)
}

func (b *board) nextPos(elf int) int {
	return (elf + 1 + b.arr[elf]) % len(b.arr)
}

func (b *board) nextScore() int {
	return b.arr[b.a] + b.arr[b.b]
}

func (b *board) tick() {
	sum := b.nextScore()
	if sum >= 10 {
		b.append(1)
	}
	b.append(sum % 10)
	b.a = b.nextPos(b.a)
	b.b = b.nextPos(b.b)
}

func (b *board) len() int {
	return len(b.arr)
}

func (b *board) scoreAt(i int) int {
	return b.arr[i]
}

func PartOne(n int) string {
	return PartOneLen(n, 10)
}

func PartOneLen(n, l int) string {
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
