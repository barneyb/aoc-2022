package util

func Must[T any](v T, e error) T {
	if e == nil {
		return v
	}
	panic(e)
}
