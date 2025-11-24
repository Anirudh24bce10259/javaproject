
# Java Console Expense Tracker
A simple Java console application to record daily expenses, view them in a table, and see basic spending summaries (total, per category, and per month).

## Features

- Add expenses with date, category, description, and amount.
- View all expenses in a formatted table.
- Edit and delete existing expenses by ID.
- View overall total spending.
- View total spending per category.
- View total spending for a specific month and year.
- Persist data in a CSV file (`expenses.csv`) so records are not lost between runs.

## Technologies Used

- Java (core, console-based)
- File I/O (CSV for persistence)
- Collections (`List`, `Map`)
- Basic date handling (`LocalDate`, `DateTimeFormatter`)

## Project Structure
ExpenseTracker/
├─ Expense.java
├─ ExpenseService.java
├─ ExpenseTrackerApp.java
└─ expenses.csv (created automatically after first run)

- `Expense.java` – Model class representing a single expense.
- `ExpenseService.java` – Business logic and file storage (add, list, delete, summaries).
- `ExpenseTrackerApp.java` – Main class with console menu and user interaction.

## Getting Started
### Prerequisites

- JDK 17 or later installed
- VS Code (or any Java-compatible IDE)
- Java extensions installed in VS Code if you want to run from the editor

Verify Java is installed:
java -version
javac -version
### Running from VS Code
1. Open the project folder in VS Code.
2. Ensure the three `.java` files are in the same folder.
3. Open `ExpenseTrackerApp.java`.
4. Click on **Run** (or the `▶ Run` link above `public static void main`).
5. Use the menu in the terminal to interact with the app.
### Running from the Terminal
From inside the project folder:
javac *.java
java ExpenseTrackerApp

A file named `expenses.csv` will be created/updated in the same folder to store your data.

## Usage

When you run the app, you will see a menu like:
==== Expense Tracker ====
Add expense
View all expenses
Edit expense
Delete expense
View summary
Monthly total
Exit
- Choose an option by typing its number and pressing Enter.
- Dates must be entered in the format `yyyy-MM-dd` (for example, `2025-11-24`).
- Amounts are entered as decimal numbers (for example, `199.99`).

## Possible Enhancements

- Add predefined categories with validation.
- Filter expenses by date range or category when listing.
- Export reports to a separate CSV or text file.
- Add a graphical user interface (Swing/JavaFX) on top of the same service layer.
- Add unit tests for `ExpenseService`.

## License

This project is for academic/learning purposes. You can modify and reuse it as needed for your own learning, while respecting your institution’s academic integrity guidelines.
