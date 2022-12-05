#!/usr/bin/env bash
set -e

cd "$(dirname "$0")"
SCRIPT=$(basename "$0")
if [[ $SCRIPT != .* ]]; then
  cp "$SCRIPT" ".$SCRIPT"
  exec "./.$SCRIPT" "$@"
fi

BASE_PACKAGE="com.barneyb.aoc"
SRC_ROOT="src/main/java"
TEST_ROOT="src/test/java"
RESOURCE_ROOT="src/main/resources"

if [ "$(git status --porcelain src | wc -l)" != "0" ]; then
    (
        echo "= Working copy is dirty; clean it up. ================================"
        echo
        git status
        echo "======================================================================"
    ) >&2
    exit 1
fi

THIS_YEAR=$(date +%Y)
THIS_DAY=$(date +%d)
if [ "$(date +%H)" -ge 21 ]; then
    # Past 9pm (EST midnight), assume "tomorrow"
    THIS_DAY=$((THIS_DAY+1))
fi

read -r -p "Year (${THIS_YEAR}): " YEAR
read -r -p "Day (${THIS_DAY}): " DAY
read -r -p "Title: " -a WORDS

if [ -z "${YEAR}" ]; then
    YEAR=${THIS_YEAR}
fi

if [ -z "${DAY}" ]; then
    DAY=${THIS_DAY}
fi
if [ "${#DAY}" = "1" ]; then
    DAY="0${DAY}"
fi

# "${foo[@]^}" capitalizes each element in array 'foo'
CAMEL=$(printf "%s" "${WORDS[@]^}" | tr -c -d '[:alpha:]')
if [ -z "${CAMEL}" ]; then
    echo "You must enter a valid title" >&2
    exit 2
fi

#
# WE ARE GO.
#

PACKAGE="${BASE_PACKAGE}.aoc${YEAR}.day${DAY}"
DIR=$(echo "$PACKAGE" | tr . /)

SRC_FILE=$SRC_ROOT/$DIR/${CAMEL}.kt
mkdir -p "$SRC_ROOT/$DIR"
cat > "$SRC_FILE" << EOF
package $PACKAGE

import ${BASE_PACKAGE}.util.Solver

fun main() {
    Solver.execute(
        ::parse,
    )
}

internal fun parse(input: String) =
    input.trim()
EOF

mkdir -p "$TEST_ROOT/$DIR"
cat > "$TEST_ROOT/$DIR/${CAMEL}KtTest.kt" << EOF
package $PACKAGE

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = "\n"

class ${CAMEL}KtTest {

    @Test
    fun parse() {
        assertEquals("", parse(EXAMPLE_ONE))
    }

}
EOF

mkdir -p "$RESOURCE_ROOT/$DIR"
cat > "$RESOURCE_ROOT/$DIR/${CAMEL}.txt" << EOF
                                Dec $DAY, $YEAR

You look fabulous, by the way. Very healthy.

  -- barney

EOF

git add .
idea --line 12 "$SRC_FILE"

if [[ $SCRIPT = .* ]]; then
  rm "$SCRIPT"
fi
