# Dino

Um jogo inspirado no Dino do Chrome, no qual dinossauros controlados por uma rede neural aprendem a desviar de obstáculos. O jogo e a rede neural foram implementados em Java puro, sem bibliotecas externas.

## Como executar

Você não precisa de uma IDE. Basta ter o **JDK 8 ou superior** instalado — o JDK inclui as ferramentas necessárias para compilar e executar Java.

### 1. Baixe o projeto

Com o Git instalado, abra o terminal e execute:

```bash
git clone https://github.com/fernando1000/Dino.git
cd Dino
```

Ou baixe o projeto como ZIP pelo GitHub e extraia os arquivos.

### 2. Inicie o jogo

Na pasta do projeto, use o comando correspondente ao seu sistema:

**Windows (PowerShell ou Prompt de Comando):**

```powershell
.\executar.bat
```

**macOS ou Linux:**

```bash
sh executar.sh
```

O script compila o código-fonte e abre o jogo. Na primeira execução, a compilação pode levar alguns segundos. Os arquivos compilados são salvos na pasta `build/`.

> Se aparecer uma mensagem dizendo que `javac` não foi encontrado, instale um JDK (não apenas o JRE), feche e reabra o terminal e tente novamente.

## Sobre o projeto

O projeto combina um jogo de obstáculos com uma rede neural de camada simples, criada do zero em Java. A rede recebe informações do jogo e decide como cada dinossauro deve agir para tentar sobreviver por mais tempo.

O código-fonte está em `src/`, e imagens e sons utilizados pelo jogo estão em `arquivos/`.
