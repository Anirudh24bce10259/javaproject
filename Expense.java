package ExpenseTracker;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Expense implements Serializable {
    private int id;
    private LocalDate date;
    private String category;
    private String description;
    private double amount;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Expense(int id, LocalDate date, String category, String description, double amount) {
        this.id = id;
        this.date = date;
        this.category = category;
        this.description = description;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String toCsv() {
        return id + "," + date.format(FORMATTER) + "," + category + "," +
                description.replace(",", " ") + "," + amount;
    }

    public static Expense fromCsv(String line) {
        try {
            String[] parts = line.split(",", 5);
            int id = Integer.parseInt(parts[0]);
            LocalDate date = LocalDate.parse(parts[1], FORMATTER);
            String category = parts[2];
            String description = parts[3];
            double amount = Double.parseDouble(parts[4]);
            return new Expense(id, date, category, description, amount);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("%-4d %-12s %-12s %-25s %8.2f",
                id,
                date.format(FORMATTER),
                category,
                description,
                amount
        );
    }
}

