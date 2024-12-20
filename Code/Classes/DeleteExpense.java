package classes;
import java.util.Scanner;

public class DeleteExpense extends ExpenseHandler {
    Text text = new Text();
    Scanner input = new Scanner(System.in);
    SQL sql = new SQL();

    private String label;

    public DeleteExpense(Budget budget) {
        super(budget);
    }

    @Override
    public void execute() {
        text.lineBorder();
        System.out.println(text.boldText("SELECT THE CATEGORY OF THE PURCHASE TO BE DELETED"));
        System.out.println("1. Bills \n2. Wants \n3. Savings");
        System.out.print("Enter category # here: ");

        int category = input.nextInt(); 
        input.nextLine(); 
        
        sql.connect();
        deleteFromCategory(category);
        sql.closeConnection();
    }


    private void deleteFromCategory(int categoryNum) {
        switch (categoryNum) {
            case 1:
                deleteExpense("Bills");
                break;
            case 2:
                deleteExpense("Wants");
                break;
            case 3:
                deleteExpense("Savings");
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

    private void deleteExpense(String category) {
        text.lineBorder();
        System.out.println(text.boldText(category));

        if (!budget.expenseHM.containsKey(category) || budget.expenseHM.get(category).isEmpty()) {
            text.redText("No purchases found in the " + category + " category.");
            return;
        }
        int ctgryNum=0;
        if (category.equals("Bills")) {
            ctgryNum=1;
        }else if (category.equals("Wants")) {
            ctgryNum=2;
        }
        else{
            ctgryNum=3;
        }

        sql.fetchExpensesByCategory(ctgryNum);
        getKeyLabel();
        delLabel(category, label);
        checkIfDeleted(category, label);

    }

    private void checkIfDeleted (String category, String label){
        if (budget.expenseHM.get(category).containsKey(label)) {
            System.out.println(text.redText("Error in deleting."));
        }
        else{
            System.out.println(text.greenText("Deleted successfully."));
        }
    }

    private void delLabel(String category, String label) {
        if (budget.expenseHM.get(category).containsKey(label)) {

            double removedPrice = budget.expenseHM.get(category).get(label);
    
            budget.expenseHM.get(category).remove(label);
    
            if (category.equals("Bills")) {
                budget.setRemainingBills(-removedPrice); 
                sql.deleteExpense(label, 1);
            } else if (category.equals("Wants")) {
                budget.setRemainingWants(-removedPrice);
                sql.deleteExpense(label, 2);
            } else if (category.equals("Savings")) {
                budget.setRemainingSavings(-removedPrice);
                sql.deleteExpense(label, 3);
            }
    
            text.greenText(label + " successfully deleted from " + category + ".");
        } else {
            text.redText("Sorry, no such purchase exists in the " + category + " category.");
        }
    }
    
}
