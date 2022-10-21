package aoc2018day14

import "strconv"

func PartOne(n int) string {
	return PartOneLen(n, 10)
}

func PartOneLen(n, l int) string {
	a, b := 0, 1
	arr := []int{3, 7}
	for len(arr) < n+l {
		sum := arr[a] + arr[b]
		if sum >= 10 {
			arr = append(arr, 1)
		}
		arr = append(arr, sum%10)
		a = (a + 1 + arr[a]) % len(arr)
		b = (b + 1 + arr[b]) % len(arr)
	}
	result := ""
	for i := 0; i < l; i++ {
		result += strconv.Itoa(arr[n+i])
	}
	return result
}
