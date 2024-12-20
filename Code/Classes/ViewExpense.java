package classes;
public class ViewExpense  {
    Text text = new Text();
    Budget budget;
    SQL sql = new SQL();

    public ViewExpense (Budget budget){
        this.budget=budget;
    }

    public void printExpense() {
        text.lineBorder();
        getExpense("Bills");
        getExpense("Wants");
        getExpense("Savings");
    }

    
    protected void getExpense(String category) {
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
    }
}


