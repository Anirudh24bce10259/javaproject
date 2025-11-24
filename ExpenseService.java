package ExpenseTracker;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class ExpenseService {
    private List<Expense> expenses = new ArrayList<>();
    private int nextId = 1;
    private final String fileName;

    public ExpenseService(String fileName) {
        this.fileName = fileName;
        loadFromFile();
    }

    public Expense addExpense(LocalDate date, String category, String description, double amount) {
        Expense e = new Expense(nextId++, date, category, description, amount);
        expenses.add(e);
        saveToFile();
        return e;
    }

    public boolean deleteExpense(int id) {
        Iterator<Expense> it = expenses.iterator();
        while (it.hasNext()) {
            Expense e = it.next();
            if (e.getId() == id) {
                it.remove();
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public Expense findById(int id) {
        for (Expense e : expenses) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public List<Expense> getAll() {
        return new ArrayList<>(expenses);
    }

    public double getTotal() {
        double sum = 0;
        for (Expense e : expenses) {
            sum += e.getAmount();
        }
        return sum;
    }

    public Map<String, Double> getTotalByCategory() {
        Map<String, Double> map = new LinkedHashMap<>();
        for (Expense e : expenses) {
            String cat = e.getCategory();
            map.put(cat, map.getOrDefault(cat, 0.0) + e.getAmount());
        }
        return map;
    }

    public double getMonthlyTotal(int year, int month) {
        double sum = 0;
        for (Expense e : expenses) {
            if (e.getDate().getYear() == year && e.getDate().getMonthValue() == month) {
                sum += e.getAmount();
            }
        }
        return sum;
    }

    private void loadFromFile() {
        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int maxId = 0;
            while ((line = br.readLine()) != null) {
                Expense e = Expense.fromCsv(line);
                if (e != null) {
                    expenses.add(e);
                    if (e.getId() > maxId) {
                        maxId = e.getId();
                    }
                }
            }
            nextId = maxId + 1;
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (Expense e : expenses) {
                pw.println(e.toCsv());
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
}
