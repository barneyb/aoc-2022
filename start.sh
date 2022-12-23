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
THIS_DAY=$(date +%-d)
if [ "$(date +%H)" -ge 21 ]; then
    # Past 9pm (EST midnight), assume "tomorrow"
    THIS_DAY=$((THIS_DAY+1))
fi

read -r -p "Year (${THIS_YEAR}): " YEAR
read -r -p "Day (${THIS_DAY}): " BARE_DAY
read -r -p "Title: " -a WORDS

if [ -z "${YEAR}" ]; then
    YEAR=${THIS_YEAR}
fi

if [ -z "${BARE_DAY}" ]; then
    BARE_DAY=${THIS_DAY}
fi
DAY=$BARE_DAY
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
mkdir -p "$(dirname "$SRC_FILE")"
cat > "$SRC_FILE" << EOF
package $PACKAGE

import ${BASE_PACKAGE}.util.Solver
import ${BASE_PACKAGE}.util.toSlice

fun main() {
    Solver.execute(
        ::parse,
    )
}

internal fun parse(input: String) =
    input.toSlice().trim()
EOF

TEST_FILE=$TEST_ROOT/$DIR/${CAMEL}KtTest.kt
mkdir -p "$(dirname "$TEST_FILE")"
cat > "$TEST_FILE" << EOF
package $PACKAGE

import com.barneyb.aoc.util.Slice
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = """
"""

class ${CAMEL}KtTest {

    @Test
    fun parsing() {
        assertEquals(Slice(""), parse(EXAMPLE_ONE))
    }

}
EOF

INPUT_FILE=$RESOURCE_ROOT/$DIR/${CAMEL}.txt
mkdir -p "$(dirname "$INPUT_FILE")"
cat > "$INPUT_FILE" << EOF
                                Dec $DAY, $YEAR

You look fabulous, by the way. Very healthy.

  -- barney

EOF

COOKIE_FILE=.cookie.txt
if [ -f "$COOKIE_FILE" ]; then
    curl --request GET -sL \
         --url "https://adventofcode.com/${YEAR}/day/${BARE_DAY}/input" \
         --cookie "$COOKIE_FILE" \
         --output "$INPUT_FILE"
fi

git add .
idea --line 12 "$SRC_FILE" "$INPUT_FILE" --line 14 "$TEST_FILE"

if [[ $SCRIPT = .* ]]; then
  rm "$SCRIPT"
fi
