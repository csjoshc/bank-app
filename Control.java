// A control script that uses the authenticator, account and session management classes
// to compile and run:
// javac -d . Account.java Authenticator.java Control.java Messages.java
// java bankapp.Control

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
      Authenticator auth = new Authenticator(name);
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
            Account session = new Account(auth.getBalance());
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
          Account session = new Account(auth.getBalance());
          finalBalance = session.runSession();
          auth.setBalance(finalBalance);
        }
      }
      auth.writeData();
      cont = msg.promptContinue(" to login? ");
    }
  }
}
