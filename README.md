# Dino

<p align="center">
  <img src="arquivos/imagens/Dino.gif" alt="Dino game demo" width="900" />
</p>

<div align="center">
  <img alt="Java" src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" />
  <img alt="AI" src="https://img.shields.io/badge/AI%20%2F%20Neural%20Network-7C4DFF?style=for-the-badge" />
  <img alt="Game" src="https://img.shields.io/badge/Game-1ABC9C?style=for-the-badge" />
  <img alt="Java Pure" src="https://img.shields.io/badge/Java%20Pure-007396?style=for-the-badge" />
</div>

<p align="center">
  <strong>A Java game inspired by the Chrome dinosaur, where dinosaurs are controlled by a neural network that learns to survive.</strong>
</p>

---

## ✨ Overview

This project combines two engaging ideas:

- an obstacle-based game with simple but dynamic mechanics;
- a neural network built from scratch in Java to make decisions for the dinosaurs;
- a minimalist, fun, and easy-to-run environment.

The goal is to observe the dinosaurs trying to avoid obstacles and improve their performance over time, as if they were learning how to play.

---

## 🧠 How it works

The project is built around a straightforward flow:

1. The game generates random obstacles;
2. Each dinosaur receives information from its environment;
3. The neural network decides the best action;
4. The dinosaur jumps or continues depending on that decision;
5. The score increases as it survives longer.

This creates an interesting simulation of learning and adaptation, with a practical and visual approach to artificial intelligence.

---

## 🎮 Key highlights

- Pure Java game, without external AI libraries;
- Dinosaurs controlled by a neural network;
- Obstacle sequences with a gradual increase in difficulty;
- Score and survival tracking;
- Retro visual style with a distinctive minimalist look;
- Java classes organized to support learning and future improvements.

---

## 🚀 How to run

You only need the **JDK 8 or higher** installed on your system.

### 1. Clone the project

```bash
git clone https://github.com/fernando1000/Dino.git
cd Dino
```

Alternatively, download the repository as a ZIP file and extract it.

### 2. Start the game

#### Windows

```powershell
.\executar.bat
```

#### macOS and Linux

```bash
sh executar.sh
```

The script compiles the source code and launches the game. On the first run, compilation may take a few seconds.

> If you see the message `javac not found`, make sure the JDK is correctly installed and added to your `PATH`.

---

## 📁 Project structure

```text
Dino/
├── src/                 # Java source code
├── arquivos/            # Graphics and sound resources
├── build/               # Compiled files
├── executar.bat         # Windows launcher
├── executar.sh          # macOS/Linux launcher
├── README.md            # Project documentation
├── .gitignore
└── LICENSE              # If present in the repository
```

---

## 🧪 Technologies used

- Java
- Object-oriented programming
- Game logic
- Neural networks built from scratch
- Agent simulation and decision-making

---

## 💡 Why this project is interesting

Because it brings together two highly relevant areas of study:

- game development;
- artificial intelligence applied in a practical environment.

It is an excellent way to understand how an AI can make real-time decisions in a simple, visual, and interactive setting.

---

## 🚀 Conclusion

If you enjoy Java, games, AI, and hands-on experimentation, this project is a strong example of how these concepts can be combined in one practical solution.

Simply run it, watch the dinosaurs, and observe how their behavior evolves as the simulation progresses.
