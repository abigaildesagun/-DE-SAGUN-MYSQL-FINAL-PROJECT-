package classes;

public class Text {
    public static final String RESET = "\u001b[0m";

   public Text() {
   }

   public String boldText(String var1) {
      return "\u001b[1m" + var1 + "\u001b[0m";
   }

   public String redText(String var1) {
      return "\u001b[31m" + var1 + "\u001b[0m";
   }

   public String greenText(String var1) {
      return "\u001b[32m" + var1 + "\u001b[0m";
   }

   public String blueText(String var1) {
      return "\u001b[34m" + var1 + "\u001b[0m";
   }

   public String yellowText (String str){
      return "\u001B[33m" + str + "\u001b[0m";
   }

   public void lineBorder() {
      System.out.println("════════════════════════════════════════");
   }
}
