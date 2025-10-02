# VWTH-Code-Challgenge

### Author: Enrique Sanchez  

---

## Overview
This project implements a robot simulation inside a factory floor.
Robots move on a 2D grid, turn left/right, and execute sequences of commands.
The simulation ensures robots:
- stay within the defined grid boundaries,
- avoid collisions with each other,
- execute command sequences as defined in an input file.

---

## Project Structure

```
VWTH-Code-Challgenge/
 ├─ src/main/java/com/vwth/challenge/
 │   ├─ application/       # Application layer (use cases, orchestrator)
 │   │    └─ SimulationService.java
 │   ├─ domain/            # Core business logic (pure domain)
 │   │    ├─ Robot.java
 │   │    ├─ Direction.java
 │   │    ├─ Command.java
 │   │    └─ FactoryFloor.java
 │   ├─ infrastructure/    # Adapters (I/O, parsing)
 │   │    ├─ CommandParser.java
 │   │    └─ FileInputAdapter.java
 │   └─ MainApp.java       # CLI entry point
 └─ src/test/java/...      # Unit tests
```

---

## Domain Model

- **Robot**
  - State: `id`, `x`, `y`, `direction`
  - Behavior: `rotateLeft()`, `rotateRight()`, `moveForward()`, `executeCommand()`

- **Direction**
  - Enum: `NORTH, EAST, SOUTH, WEST`

- **Command**
  - Enum: `LEFT (L)`, `RIGHT (R)`, `MOVE (M)`

- **FactoryFloor**
  - Holds grid boundaries
  - Tracks robots’ positions
  - Referees moves (`tryMove`)

---

## Application Layer

- **SimulationService**
  - Orchestrates command execution for robots
  - Runs command sequences sequentially per robot
  - Supports multiple robots

---

## Infrastructure

- **CommandParser**: converts strings (`"LMLMRM"`) into `List<Command>`
- **FileInputAdapter**: reads a text file with grid size, initial robot positions, and command sequences

Example input file (`input.txt`):
```
5 5
1 2 N
LMLMLMLMM
3 3 E
MMRMMRMRRM
```

---

## Running the Program

Build and package the project:
```bash
mvn clean package
```

Run with an input file:
```bash
java -jar target/VWTH-Code-Challgenge-1.0-SNAPSHOT.jar input.txt
```

Expected output for the sample input:
```
1 3 N
5 1 E
```

---

## Testing

Run unit tests with:
```bash
mvn test
```

Tests cover:
- Robot behavior (turn, move)
- Factory floor boundaries and collisions
- Command parsing
- Simulation orchestration

---

## Design Notes

- **Hexagonal Architecture**:
  - **Domain** = business rules (independent, pure Java).
  - **Application** = orchestrates use cases.
  - **Infrastructure** = adapters for parsing and file I/O.

- **Separation of Concerns**:
  - Robots know *how* to move.
  - The floor validates *whether* they can move.
  - The simulation orchestrates execution order.
  - Input adapters transform raw data into domain-friendly objects.

