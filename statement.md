# statement.md

## Problem Statement

Individuals frequently struggle to keep track of daily expenses, which can lead to poor budgeting and difficulties managing personal finances. There is a need for a simple, accessible tool to record expenditures, categorize them, and summarize spending patterns over time.

## Scope of the Project

This project is a console-based Java application that allows users to record their expenses, view, edit, and delete entries, and generate basic spending summaries. It focuses on core expense management tasks and persists user data locally in a CSV file, making it suitable for personal daily use. The solution is intentionally designed to be modular and extendable, allowing future enhancements such as GUI support or extra analytics.

## Target Users

- College students who need to monitor day-to-day spending
- Individuals seeking a lightweight, offline tool for expense tracking
- Beginners looking to learn Java file handling, class design, and console interaction

## High-Level Features

- Add new expenses by date, category, description, and amount
- Edit and delete expense records using unique IDs
- List all expenses in a formatted table for easy viewing
- View total, per-category, and per-month spending summaries
- Persist all data locally via a CSV file (`expenses.csv`)
- Modular design enabling further upgrades (e.g., GUI, export/import, filters)
