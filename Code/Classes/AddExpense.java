package classes;
import java.util.Scanner;

public class AddExpense extends ExpenseHandler {

    Text text = new Text();
    SQL sql = new SQL();

    public AddExpense(Budget budget) {
        super(budget);
    }

    @Override
    public void execute() {
        Scanner input = new Scanner(System.in);

        try {
            text.lineBorder();

            System.out.print("Enter description/label: ");
            String prodLabel = input.nextLine().toUpperCase();

            System.out.print("Enter amount (php): ");
            double amount = input.nextDouble();

            System.out.println(text.boldText("CATEGORIES"));
            System.out.println("1. Bills");
            System.out.println("2. Wants");
            System.out.println("3. Savings");

            System.out.print("Enter the number of category: ");
            int categoryInt;

            try {
                categoryInt = input.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number for the category.");
                input.nextLine(); 
                return; 
            }

            String categoryStr = pickCategory(categoryInt);
            text.lineBorder();

            sql.connect();
            addToCategory(categoryStr, prodLabel, amount);
            printDetails(categoryStr, prodLabel, amount);
            sql.closeConnection();

        } catch (Exception e) {
            System.out.println("An error occurred please enter the right format.");
            input.nextLine(); 
        }

    }



    String pickCategory(int categoryInt) {
        switch (categoryInt) {
            case 1:
                return "Bills";
            case 2:
                return "Wants";
            case 3:
                return "Savings";
            default:
                return "Invalid";
        }
    }

    void printDetails(String categoryStr, String prodLabel, double amount) {
        System.out.println(text.boldText("EXPENSE DETAILS"));
        System.out.println("Expense: " + prodLabel);
        System.out.println("Amount: " + amount);
        System.out.println("Category: " + categoryStr);
    }

    public void addToCategory(String categoryStr, String prodLabel, double amount) {
        switch (categoryStr) {
            case "Bills":
                budget.expenseHM.get("Bills").put(prodLabel, amount);
                budget.setRemainingBills(amount);
                sql.insertExpense(1, prodLabel, amount);
                break;
            case "Wants":
                budget.expenseHM.get("Wants").put(prodLabel, amount);
                budget.setRemainingWants(amount);
                sql.insertExpense(2, prodLabel, amount);
                break;
            case "Savings":
                budget.expenseHM.get("Savings").put(prodLabel, amount);
                budget.setRemainingSavings(amount);
                sql.insertExpense(3, prodLabel, amount);
                break;
            default:
                System.out.println("Invalid category.");
        }
    }
}
