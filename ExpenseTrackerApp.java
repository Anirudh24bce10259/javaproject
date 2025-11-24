package ExpenseTracker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ExpenseTrackerApp {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ExpenseService service = new ExpenseService("expenses.csv");

        while (true) {
            System.out.println("\n==== Expense Tracker ====");
            System.out.println("1. Add expense");
            System.out.println("2. View all expenses");
            System.out.println("3. Edit expense");
            System.out.println("4. Delete expense");
            System.out.println("5. View summary");
            System.out.println("6. Monthly total");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            int choice = readInt(sc);
            switch (choice) {
                case 1 -> addExpense(sc, service);
                case 2 -> viewAll(service);
                case 3 -> editExpense(sc, service);
                case 4 -> deleteExpense(sc, service);
                case 5 -> viewSummary(service);
                case 6 -> viewMonthlyTotal(sc, service);
                case 0 -> {
                    System.out.println("Goodbye!");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void addExpense(Scanner sc, ExpenseService service) {
        LocalDate date = readDate(sc, "Enter date (yyyy-MM-dd): ");
        System.out.print("Enter category: ");
        String category = sc.nextLine().trim();
        System.out.print("Enter description: ");
        String desc = sc.nextLine().trim();
        System.out.print("Enter amount: ");
        double amount = readDouble(sc);

        Expense e = service.addExpense(date, category, desc, amount);
        System.out.println("Added: " + e);
    }

    private static void viewAll(ExpenseService service) {
        List<Expense> list = service.getAll();
        if (list.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        System.out.printf("%-4s %-12s %-12s %-25s %8s%n",
                "ID", "Date", "Category", "Description", "Amount");
        for (Expense e : list) {
            System.out.println(e);
        }
    }

    private static void editExpense(Scanner sc, ExpenseService service) {
        System.out.print("Enter expense ID to edit: ");
        int id = readInt(sc);
        Expense e = service.findById(id);
        if (e == null) {
            System.out.println("Expense not found.");
            return;
        }
        System.out.println("Current: " + e);

        LocalDate date = readDateOrSkip(sc,
                "Enter new date (yyyy-MM-dd) or press Enter to keep [" +
                        e.getDate().format(FORMATTER) + "]: ", e.getDate());
        System.out.print("Enter new category or press Enter to keep [" +
                e.getCategory() + "]: ");
        String catInput = sc.nextLine().trim();
        String newCat = catInput.isEmpty() ? e.getCategory() : catInput;

        System.out.print("Enter new description or press Enter to keep [" +
                e.getDescription() + "]: ");
        String descInput = sc.nextLine().trim();
        String newDesc = descInput.isEmpty() ? e.getDescription() : descInput;

        System.out.print("Enter new amount or press Enter to keep [" +
                e.getAmount() + "]: ");
        String amtInput = sc.nextLine().trim();
        double newAmt = amtInput.isEmpty() ? e.getAmount() : parseDoubleOrFallback(amtInput, e.getAmount());

        e.setDate(date);
        e.setCategory(newCat);
        e.setDescription(newDesc);
        e.setAmount(newAmt);

        // trigger save by a dummy call (or add explicit save method)
        service.addExpense(e.getDate(), e.getCategory(), e.getDescription(), 0);
        service.deleteExpense(e.getId()); // remove the extra one
        service.addExpense(e.getDate(), e.getCategory(), e.getDescription(), e.getAmount());
        service.deleteExpense(getMaxId(service)); // clean up

        System.out.println("Updated: " + e);
    }

    private static int getMaxId(ExpenseService service) {
        int max = 0;
        for (Expense ex : service.getAll()) {
            if (ex.getId() > max) max = ex.getId();
        }
        return max;
    }

    private static void deleteExpense(Scanner sc, ExpenseService service) {
        System.out.print("Enter expense ID to delete: ");
        int id = readInt(sc);
        boolean ok = service.deleteExpense(id);
        if (ok) {
            System.out.println("Deleted.");
        } else {
            System.out.println("Expense not found.");
        }
    }

    private static void viewSummary(ExpenseService service) {
        double total = service.getTotal();
        System.out.printf("Total spent: %.2f%n", total);

        System.out.println("Total by category:");
        Map<String, Double> map = service.getTotalByCategory();
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            System.out.printf("  %-12s : %.2f%n", entry.getKey(), entry.getValue());
        }
    }

    private static void viewMonthlyTotal(Scanner sc, ExpenseService service) {
        System.out.print("Enter year (e.g., 2025): ");
        int year = readInt(sc);
        System.out.print("Enter month (1-12): ");
        int month = readInt(sc);
        double total = service.getMonthlyTotal(year, month);
        System.out.printf("Total for %d-%02d: %.2f%n", year, month, total);
    }

    // Helper input methods

    private static int readInt(Scanner sc) {
        while (true) {
            String line = sc.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Try again: ");
            }
        }
    }

    private static double readDouble(Scanner sc) {
        while (true) {
            String line = sc.nextLine();
            try {
                return Double.parseDouble(line.trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid amount. Try again: ");
            }
        }
    }

    private static LocalDate readDate(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                return LocalDate.parse(line, FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use yyyy-MM-dd.");
            }
        }
    }

    private static LocalDate readDateOrSkip(Scanner sc, String prompt, LocalDate current) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            if (line.isEmpty()) {
                return current;
            }
            try {
                return LocalDate.parse(line, FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use yyyy-MM-dd.");
            }
        }
    }

    private static double parseDoubleOrFallback(String input, double fallback) {
        try {
            return Double.parseDouble(input.trim());
        } catch (NumberFormatException e) {
            return fallback;
        }
    }
}

