/*
New authenticator instance is created when attempting to use a name to either log in or
create a new account. Authenticator tracks this name, as well as the number of previous attempts
Authenticator contains code for reading and writing data to disk.

The logic for using Authenticator is in the Control, while the implementation is here. For example
the timing for when to read/write is controlled in Control, while the implementation for those
steps is here.

*/
package bankapp;
import bankapp.*;
import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.lang.String;
public class Authenticator{
  int tries = 0;
  Messages msg;
  String name;
  HashMap<String, String> passes = new HashMap<String, String>();
  HashMap<String, Double> accounts = new HashMap<String, Double>();

  Authenticator(String name, Messages msg)
  {
    this.name = name;
    this.msg = msg;
  }
  protected String getName (){
    return this.name;
  }

  protected Integer getTries(){
    return this.tries;
  }

  protected void readFile() throws FileNotFoundException {
    Scanner scanner = new Scanner(new File("accounts.csv"));

    Integer linenum = 0;
    while (scanner.hasNext())
    {
      linenum ++;
      String line = new String(scanner.nextLine());
      try{
        String[] parts = line.split(",");
        this.passes.put(parts[0], parts[1]);
        this.accounts.put(parts[0], Double.parseDouble(parts[2]));
      } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
        msg.readFailure(linenum, line);
        //System.out.println(ex);
        continue;
      }
    }
    scanner.close();
  }

  protected Boolean accountExists(){
    return this.passes.containsKey(this.name);
  }

  protected Boolean tryAuthenticate(String pass){
    if (pass.equals(this.passes.get(this.name))) {
      return true;
    } else {
      this.tries++;
      return false;
    }
  }

  protected Double getBalance(){
    return this.accounts.get(this.name);
  }

  protected void setBalance(Double amount){
    this.accounts.put(this.name, amount);
  }

  protected void setupAccount(String name, String pass, double account){
    this.passes.put(name, pass);
    this.accounts.put(name, account);
  }

  protected void writeData() throws FileNotFoundException{
    // writes contents of associated hashmaps back to csv
    PrintWriter writer = new PrintWriter("accounts.csv"); //overwrite
    StringBuilder sb = new StringBuilder();
    for (HashMap.Entry<String, String> entry1: this.passes.entrySet())
    {
      String key = entry1.getKey();
      String pass = this.passes.get(key);
      Double account = this.accounts.get(key);
      sb.append(key).append(',').append(pass).append(',').append(account).append('\n');
    }
    //System.out.println(sb);
    writer.write(sb.toString());
    writer.write("badonecolumnentry");
    writer.close();
  }
}
