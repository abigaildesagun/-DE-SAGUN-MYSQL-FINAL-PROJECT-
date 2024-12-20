package classes;

public abstract class ExpenseHandler {
    protected Budget budget;
    protected Text text = new Text();

    public ExpenseHandler(Budget budget) {
        this.budget = budget;
    }

    public abstract void execute();

    protected void printCategories() {
        System.out.println(text.boldText("CATEGORIES"));
        System.out.println("1. Bills\n2. Wants\n3. Savings");
    }
}
