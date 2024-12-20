package classes;
import java.util.Scanner;

public class ModifyExpense extends ExpenseHandler{
    Text text = new Text();
    Scanner input = new Scanner(System.in);
    SQL sql = new SQL();

    private double newPrice;
    private String label;

    public ModifyExpense(Budget budget) {
        super(budget);
    }
    
    @Override
    public void execute() {
        text.lineBorder();
        System.out.println(text.boldText("SELECT THE CATEGORY OF THE PURCHASE TO BE MODIFIED"));
        System.out.println("1. Bills \n2. Wants \n3. Savings");
        System.out.print("Enter category # here: ");
        int category = input.nextInt();
        input.nextLine(); 
        if (category < 1 || category > 3) {
            text.redText("Invalid category number. Please select between 1 and 3.");
            return;
        }

        sql.connect();
        modifyFromCategory(category);
        sql.closeConnection();
    }


    private void modifyFromCategory(int categoryNum) {
        switch (categoryNum) {
            case 1:
                modifyExpense("Bills");
                break;
            case 2:
                modifyExpense("Wants");
                break;
            case 3:
                modifyExpense("Savings");
                break;
            default:
                text.redText("Invalid category.");
                break;
        }
    }

    private void getKeyLabel() {
        text.lineBorder();
        System.out.print("Please enter the label of the purchase: ");
        label = input.nextLine().toUpperCase();
    }

    private void getNewPurchaseDetails() {
        text.lineBorder();
        System.out.println("Label: " + label);
        System.out.print("Enter new price: ");
        newPrice = input.nextDouble();
        input.nextLine(); 
    }

    private void modifyExpense(String category) {
        text.lineBorder();
        System.out.println(text.boldText(category));

        if (!budget.expenseHM.containsKey(category) || budget.expenseHM.get(category).isEmpty()) {
            text.redText("No purchases found in the " + category + " category.");
            return;
        }

        budget.showExpenses(category);
        getKeyLabel();
        modifyHM(category);
    }
    
    private void modifyHM(String category) {
        if (budget.expenseHM.get(category).containsKey(label)) {
            double originalPrice = budget.expenseHM.get(category).get(label);
            getNewPurchaseDetails();
            budget.expenseHM.get(category).replace(label, newPrice);
    
            double priceDifference = newPrice - originalPrice;
            if (category.equals("Bills")) {
                budget.setRemainingBills(priceDifference);
                sql.updateExpensePrice(label, 1, priceDifference);
            } else if (category.equals("Wants")) {
                sql.updateExpensePrice(label, 2, priceDifference);
                budget.setRemainingWants(priceDifference);
            } else if (category.equals("Savings")) {
                budget.setRemainingSavings(priceDifference);
                sql.updateExpensePrice(label, 3, priceDifference);
            }
    
            text.greenText(label + " successfully updated to " + newPrice + " in " + category + ".");
        } else {
            text.redText("Sorry, no such purchase exists in the " + category + " category.");
        }
    }
    
}