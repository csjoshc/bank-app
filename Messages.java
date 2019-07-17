package bankapp;
import bankapp.*;
import java.io.Console;
import java.lang.Math;
public class Messages{
  Console con = System.console();
  protected void greeting (){
    System.out.println("Welcome to bank app!");
  }

  protected void userExists(String name){
    System.out.println("Account belonging to " + name + " has been found.");
  }

  protected void userExistsError(String name){
    System.out.println("Sorry, an account belonging to " + name +
    " has been found. \nPlease login to the existing account or choose a different name.");
  }

  protected void userNoExist(String name){
    System.out.println("Sorry, account for " + name + " does not exist");
  }

  protected void loginSuccess(String name){
    System.out.println("Login successful for user " + name);
  }

  protected void loginFailure(int tries){
    System.out.println("Sorry, that is the wrong password. Tries left: " + tries);
  }

  protected void printAccount(double account){
    System.out.println("Current account balance is $" + Math.round(account* 100.0) / 100.0);
  }

  protected void choiceFailure() {
    System.out.println("Sorry, you did not choose a valid option.");
  }

  protected void investSuccess(Double amount) {
    System.out.println("Congratulations, you earned $" + Double.toString(amount));
  }

  protected void investFailure(Double amount) {
    System.out.println("Sorry, you lost $" + Double.toString(-1 * amount));
  }

  protected void operationFailure(String message){
    System.out.println("Sorry, you do not have enough to " + message);
  }

  protected void operationSuccess(String message){
    System.out.println("Congratulations, you have enough to " + message);
  }

  protected void defaultAmount(){
    System.out.println("Invalid amount entered, setting to default of $0.");
  }

  protected void readFailure(Integer num, String line){
    System.out.println("Continuing past incorrect data format at line " +
    Integer.toString(num) + ": " +  line);
  }

  protected String promptName(){
    String name = con.readLine("What is your name?\n");
    return name;
  }
  protected String promptPass(){
    char[] pass = con.readPassword("What is your password?\n");
    String password = new String(pass);
    return password;
  }

  protected String setPass(){
    char[] pass = con.readPassword("What do you want your password to be?\n");
    String password = new String(pass);
    return password;
  }

  protected Boolean promptContinue(String message){
    String cont = con.readLine("Do you want to continue" + message +
    "(y/n)?\n").toLowerCase();

    if (cont.equals("y")){
      System.out.println("Continuing!");
      return true;
    } else {
      System.out.println("Exiting, goodbye!");
      return false;
    }
  }

  protected Boolean promptLogin(){
    String login = con.readLine("Create new account (0) or login (1)? Default is login.\n");

    if (login.equals("0")){
      System.out.println("Creating new account!");
      return false;
    } else {
      System.out.println("Logging in!");
      return true;
    }
  }

  protected Integer promptChoice(){
    String choice  = con.readLine("What do you want to do? 1: Add money; 2: Withdraw money; \n"
    + " 3: Invest in Startup; 4: Invest in CryptoCurrency.\n");
    try {
      Integer choiceint = Integer.parseInt(choice);
      if (choiceint < 5 && choiceint > 0){
        return choiceint;
      } else {
        return 0;
      }
    }
    catch (NumberFormatException nfe) {
      return 0;
    }
  }

  protected Double promptAmount(String message){
    try{
      return Math.round(Double.parseDouble(con.readLine("You have chosen to " +
      message + ", what amount?\n")) * 100.0) / 100.0;
    }
    catch (NumberFormatException nfe) {
      defaultAmount();
      return 0.0;
    }
  }
}
