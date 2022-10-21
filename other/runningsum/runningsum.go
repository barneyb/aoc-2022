// Package runningsum provides a utility to compute a running sum over an []int
// such that each element of the returned slice will correspond to the sum of
// the element at the same index in the source array and all earlier elements.
package runningsum

// RunningSum accepts an []int and returns a new []int with the same length,
// where each element in the result is equal to the sum of all elements up to
// and including the corresponding index in the source slice.
//
// For example {1, 2, 3}'s running sum is {1, 3, 6}.
func RunningSum(s []int) []int {
	result := make([]int, len(s))
	sum := 0
	for i, n := range s {
		sum += n
		result[i] = sum
	}
	return result
}
