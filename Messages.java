/*

This class contains for printing out data to console, and for receiving user input from
the console. The user input is tested during parsing for synctatic validity - e.g. are numbers
numbers, and dates in a specific format? It does not check whether for example if a withdrawal
amount is less than or equal to the account balance.

Messages is more a collection of bundled methods since it has no real internal attributes

*/

package bankapp;
import bankapp.*;
import java.io.Console;
import java.lang.Math;
import java.util.HashMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.time.ZoneId;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.time.Period;

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
    System.out.println("Invalid amount entered, must be an amount greater than $0.");
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

  protected HashMap<String, Object> promptDate(String message){
    //returns a unix timestamp (s) for an entered date, to be used for submitting API requests
    DateTimeFormatter format = DateTimeFormatter.ofPattern( "uuuu-M-d" , Locale.US);
    LocalDate date = null;
    HashMap<String, Object> dates = new HashMap<String, Object>();
    while(date == null){
      try{
        date = LocalDate.parse(con.readLine("Enter the " + message + " date (YYYY-MM-DD): "), format);
        dates.put("DateObject", date);
        dates.put("Timestamp", date.atStartOfDay(ZoneId.of("America/Sao_Paulo")).toInstant().toEpochMilli() / 1000);

      } catch (DateTimeParseException e){
        System.out.println("Sorry, that was not a valid date. Please try again.");
      } catch (Exception e){
        System.out.println(e);
      }
    }
    return dates;
  }

  protected void printPrice(String descr, double amt, double price, double total){
    System.out.println("You " + descr + " " + amt + " bitcoin at a price of $" +
    price + "/BTC for a total of $" + total);
  }
  protected void printInvestmentPeriod(Period p){
    System.out.println("Investment period: " + p.getYears() + " years, " + p.getMonths() +
                   " months, and " + p.getDays() + " days.");
  }
}
