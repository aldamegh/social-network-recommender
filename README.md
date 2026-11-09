# Social Network Friend Recommender

A Java graph coursework project with binary-search-tree maps/sets, a bounded priority queue, and friend recommendations by degree, common neighbors, and weighted common neighbors.

## Course and original date

- **Course / المادة:** CSC 212 - Data Structures / هياكل البيانات, King Saud University.
- **Original project period / تاريخ المشروع الأصلي:** May 2022.
- **Evidence:** The original assignment brief gives 17 May 2022 as the due date. The local submitted ZIP contains implementation files modified from 17 to 22 May 2022; the selected Eclipse `Project` variant was last modified on 21 May. Older interface/scaffolding dates are not treated as this project's creation date.

The original coursework dates above are distinct from the GitHub publication and maintenance date, **9 November 2026**. The original source files were preserved locally.

## Run

JDK 11 or newer with `javac` and `java` on PATH; Java 8-compatible output. No external Java dependencies.

```sh
mkdir build
javac --release 8 -encoding UTF-8 -d build src/*.java
java -cp build Main
```

## Verification

```sh
python check.py
```

5,404 graph and queue checks passed, including independent recommendation rankings on seeded random graphs, isolated vertices, stable ties, zero capacity, and self exclusion. Checks use newly authored synthetic inputs only. See [CHANGES.md](CHANGES.md) for the publication changes and validation scope.

## Scope and provenance

An educational in-memory implementation intended for small graphs. Stable queue ties follow insertion order; recommendation candidates are inserted in ascending ID order. Includes coursework interfaces/scaffolding and does not claim sole authorship of every file.

Published from the owner's local coursework. Course briefs, slides, exams, student identifiers, faculty names, real datasets, collected email, credentials, IDE history, and compiled files are excluded. Academic material may include supplied interfaces or collaborative code; no claim of sole authorship of all scaffolding and no blanket license is made.

Course titles are cross-checked against the [KSU computer science course catalog](https://ccis.ksu.edu.sa/en/cs/cs-prog-guides). Original local evidence is retained privately rather than publishing the source briefs/reports.
