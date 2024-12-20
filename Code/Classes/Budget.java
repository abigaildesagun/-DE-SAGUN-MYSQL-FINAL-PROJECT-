package classes;

import java.util.HashMap;

public class Budget {
    protected HashMap<String, HashMap<String, Double>> expenseHM = new HashMap<>();
    private double bills, wants, savings;
    private double remainingBills, remainingWants, remainingSavings;
    private SQL sql;

    public Budget() {
        expenseHM.put("Bills", new HashMap<>());
        expenseHM.put("Wants", new HashMap<>());
        expenseHM.put("Savings", new HashMap<>());
        sql = new SQL();
        sql.connect();
    }

    public void setBudget(double initialBudget) {
        this.bills = initialBudget * 0.5;
        this.wants = initialBudget * 0.3;
        this.savings = initialBudget * 0.2;

        this.remainingBills = this.bills;
        this.remainingWants = this.wants;
        this.remainingSavings = this.savings;

        sql.populateBudgets(initialBudget, this.bills, this.wants, this.savings);
    }

    void setRemainingBills(double expense) {
        this.remainingBills -= expense;
    }

    void setRemainingWants(double expense) {
        this.remainingWants -= expense;
    }

    void setRemainingSavings(double expense) {
        this.remainingSavings -= expense;
    }

    public void budgetOverview() {
        Text text = new Text();
        text.lineBorder();
        System.out.printf("%-15s%-15s%-15s%n", "Category", "Budget", "Remaining");
        text.lineBorder();

        printRow(text, "Bills", bills, remainingBills);
        printRow(text, "Wants", wants, remainingWants);
        printRow(text, "Savings", savings, remainingSavings);
    }

    private void printRow(Text text, String category, double budget, double remaining) {
        String remainingText = String.format("%.2f", remaining);
        System.out.printf("%-15s%-15.2f%-15s%n", category, budget, remainingText);
    }

    protected void showExpenses(String category) {
        sql.fetchExpensesByCategory(getCategoryId(category));
    }

    private int getCategoryId(String category) {
        switch (category) {
            case "Bills":
                return 1;
            case "Wants":
                return 2;
            case "Savings":
                return 3;
            default:
                return -1;
        }
    }

    public void closeConnection() {
        sql.closeConnection();
    }
}
