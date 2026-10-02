#!/usr/bin/env sh
set -eu

cd "$(dirname "$0")"

if ! command -v javac >/dev/null 2>&1; then
    echo "Erro: JDK nao encontrado. Instale o JDK 8 ou superior e tente novamente." >&2
    exit 1
fi

if ! command -v java >/dev/null 2>&1; then
    echo "Erro: Java nao encontrado. Instale o JDK 8 ou superior e tente novamente." >&2
    exit 1
fi

mkdir -p build
javac -encoding UTF-8 -d build src/br/com/x10d/dino/*.java
exec java -cp "build:arquivos" br.com.x10d.dino.Main
