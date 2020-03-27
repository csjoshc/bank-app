/*
A control/driver script that uses the Authenticator, Account and Messages classes
All logic for instantiating those classes and handling user interaction outside of
interacting with an account balance is found here. Once a user has logged in to a valid account,
the user transactions with that account is handled by running a session with Account.runSession()

All jar files are included as dependencies when compiling and running using these commands:
javac -d . -cp ".;lib/*" *.java
java -cp ".;lib/*" bankapp.Control

accounts.csv contains mock bank data, some of which is purposefully "bad" to test
error handling by the app - such as bad formatting or nonnumerical account balances
*/

package bankapp;
import bankapp.*;
import java.io.FileNotFoundException;
public class Control{
  public static void main (String[] args) throws FileNotFoundException {

    Boolean cont = true;
    while (cont){
      Double finalBalance;
      Messages msg = new Messages();
      msg.greeting();
      String name = msg.promptName();
      Authenticator auth = new Authenticator(name, msg);
      auth.readFile();
      Boolean login = msg.promptLogin();

      if (login){
        Boolean exists = auth.accountExists();
        if (exists){
          Boolean authed = false;
          msg.userExists(name);
          while(auth.getTries() < 3 && !authed){
            if(auth.tryAuthenticate(msg.promptPass())) {
              msg.loginSuccess(name);
              authed = true;
            } else {
              msg.loginFailure(3 - auth.getTries());
            }
          }
          if (authed){
            Account session = new Account(auth.getBalance(), msg);
            finalBalance = session.runSession();
            auth.setBalance(finalBalance);
          }
        } else {
          msg.userNoExist(name);
        }
      } else {
        Boolean exists = auth.accountExists();
        if (exists) {
          msg.userExistsError(name);
        } else {
          String pass = msg.setPass();
          auth.setupAccount(name, pass, 0);
          Account session = new Account(auth.getBalance(), msg);
          finalBalance = session.runSession();
          auth.setBalance(finalBalance);
        }
      }
      auth.writeData();
      cont = msg.promptContinue(" to login? ");
    }
  }
}
