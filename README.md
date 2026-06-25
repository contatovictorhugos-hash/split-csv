# Split CSV

> Splits a large CSV file into multiple smaller files with a configurable number of data rows per output file.

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)

## Table of Contents
- [Overview](#overview)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Ecosystem](#ecosystem)
- [Contributing](#contributing)

## Overview

Split CSV is a command-line utility that reads a single CSV file and divides it into multiple output files, each containing a user-defined number of data rows. The tool preserves the original header row in every output file, supports configurable character encoding (ISO-8859-1 by default), and generates timestamped filenames to avoid collisions. It is designed for scenarios where downstream systems impose row-count limits on CSV imports.

## Architecture

The application is a single-class Java program with no external dependencies:

1. **Input** — Reads `input.csv` from the working directory.
2. **Configuration** — Prompts the user for the number of data rows per file (defaults to 10,000).
3. **Processing** — Streams through the input file, writing rows to sequentially numbered output files in the `output/` directory.
4. **Output** — Each file is named `<basename>-<part>-<timestamp>.csv` and includes the header row.

```
input.csv (150,000 rows)
    ↓ SplitCSV
output/
  ├── input-1-143025062026.csv   (10,000 rows + header)
  ├── input-2-143025062026.csv   (10,000 rows + header)
  ├── ...
  └── input-15-143025062026.csv  (10,000 rows + header)
```

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java (SE) |
| I/O | `java.nio.file` (BufferedReader / BufferedWriter) |
| Encoding | ISO-8859-1 (configurable in source) |
| Build | Direct `javac` compilation (no build tool) |

## Getting Started

### Prerequisites

- Java 21+ (JDK)

### Installation

```bash
# Clone the repository
git clone https://github.com/contatovictorhugos/split-csv.git
cd split-csv
```

### Running locally

```bash
# Compile
javac src/SplitCSV.java -d out

# Place your CSV in the working directory as input.csv
cp /path/to/your/file.csv input.csv

# Run
java -cp out SplitCSV
```

The tool will prompt for the number of rows per file (press Enter for the default of 10,000). Output files are written to the `output/` directory.

## Project Structure

```
split-csv/
├── src/
│   └── SplitCSV.java    # Main application class
└── README.md
```

## Ecosystem

This project is part of the **Projetcs** suite. The following projects work together:

| Project | Role | Depends On |
|---|---|---|
| **key-management-service** | REST API — cryptographic key lifecycle management | PostgreSQL |
| **mail-notifier-service** | REST API — transactional email delivery with encryption | key-management-service API, PostgreSQL, Brevo |
| **fipe-csv** | REST API — FIPE vehicle pricing table to CSV export | FIPE public API |
| **bko-project** | Server-rendered web app — internal backoffice administration | PostgreSQL |
| **split-csv** | CLI tool — splits large CSV files into smaller parts | — |
| **mergeCSV** | CLI tool — merges multiple CSV files into one | — |
| **prj_extensao** | Mobile app (React Native / Expo) — Methodist church community app | Firebase |

> **This project**: `split-csv` is the counterpart of `mergeCSV`. Use Split CSV to break a large file into importable chunks, and Merge CSV to reassemble them.

## Contributing

1. Fork the repository.
2. Create a feature branch: `git checkout -b feature/your-feature-name`
3. Commit your changes: `git commit -m 'feat: add your feature'`
4. Push to the branch: `git push origin feature/your-feature-name`
5. Open a Pull Request.

Please follow [Conventional Commits](https://www.conventionalcommits.org/) for commit messages.
