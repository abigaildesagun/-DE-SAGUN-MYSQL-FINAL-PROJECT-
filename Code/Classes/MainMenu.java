package classes;
import java.util.Scanner;

public class MainMenu {
    Text text = new Text();
    private AddExpense addExpense;
    private ModifyExpense modifyExpense;
    private DeleteExpense deleteExpense;
    private ViewExpense viewExpense;

    public MainMenu(Budget budget) {
        this.addExpense = new AddExpense(budget);
        this.modifyExpense = new ModifyExpense(budget);
        this.deleteExpense = new DeleteExpense(budget);
        this.viewExpense = new ViewExpense(budget);
    }

    public void showMainMenu() {
        Scanner input = new Scanner(System.in);
        int command = 0;
        
        while (command != 5) {
            text.lineBorder();
            System.out.println(text.boldText("\t\tMAIN MENU"));
            System.out.println("1. Add Expense\n2. Modify Expense\n3. Delete Expense\n4. View Expenses\n5. Exit");
            System.out.print(text.boldText("Please enter a command: "));
            
            try {
                command = input.nextInt();
                chooseAction(command);  
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine(); 
            }
        }
        
        input.close();
    }
    
    public void chooseAction(int action) {
        switch (action) {
            case 1:
                addExpense.execute();
                addExpense.budget.budgetOverview(); 
                break;
            case 2:
                modifyExpense.execute();
                addExpense.budget.budgetOverview(); 
                break;
            case 3:
                deleteExpense.execute();
                addExpense.budget.budgetOverview(); 
                break;
            case 4:
                viewExpense.printExpense();
                addExpense.budget.budgetOverview(); 
                break;
            case 5:
                System.out.println("Exiting program...");
                break;
            default:
                System.out.println("Invalid command.");
        }
    }
}
