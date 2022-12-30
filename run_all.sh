#!/usr/bin/env bash
set -e

cd "$(dirname "$0")"
SCRIPT=$(basename "$0")
if [[ $SCRIPT != .* ]]; then
  cp "$SCRIPT" ".$SCRIPT"
  exec "./.$SCRIPT" "$@"
fi

time (
    find src/main/java -name '*.java' -print0 \
        | xargs -0 grep -l 'void main(' \
        | sed -e 's/\.java$//'
    find src/main/java -name '*.kt' -print0 \
        | xargs -0 grep -l 'fun main(' \
        | sed -e 's/\.kt$/Kt/'
) \
    | cut -c 15- \
    | tr / . \
    | grep '.aoc.' \
    | sort \
    | sed -e 's/\(.*\)/-Dexec.mainClass="\1"/' \
    | xargs -n 1 mvn -q -B -Dbenchmark=false exec:java

if [[ $SCRIPT = .* ]]; then
  rm "$SCRIPT"
fi
