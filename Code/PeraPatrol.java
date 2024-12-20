import classes.*;
import java.util.Scanner;

public class PeraPatrol {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Text text = new Text();

        System.out.println(text.boldText("\tWelcome to Pera Patrol"));
        System.out.print("Please enter your budget for the week: ");
        
        double weeklyBudget = input.nextDouble();

        Budget budget = new Budget();
        budget.setBudget(weeklyBudget);
        budget.budgetOverview();

        MainMenu mainMenu = new MainMenu(budget);  
        mainMenu.showMainMenu();
        
        text.lineBorder();

        input.close();
    }
}
