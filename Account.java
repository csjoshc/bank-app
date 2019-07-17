// New account session spawned for each balance (no name associated)
// Account session is only spawned after authenticator validates credentials

package bankapp;
import bankapp.*;
import java.lang.Math;
import java.util.Random;
public class Account{
  public static void main (String[] args) {
    System.out.println("Hello");
  }
  double account;
  Messages msg;
  Account (double account){
    this.account = account;
    this.msg = new Messages();
  }

  protected Double runSession (){
    Boolean runsess = true;
    while(runsess){
      msg.printAccount(this.account);
      Integer choice = msg.promptChoice();

      if (choice == 0){
        msg.choiceFailure();
      } else {
        Double change;
        Double earnings;
        Boolean enough;
        switch(choice) {
          case 1:
          this.account += Math.abs(msg.promptAmount("add money"));
          msg.printAccount(this.account);
          break;
          case 2:
          change = -1 * Math.abs(msg.promptAmount("take money"));
          if(checkSum(change, "take that amount.")){
            this.account += change;
            msg.printAccount(this.account);
          }
          break;
          case 3:
          change = -1 * Math.abs(msg.promptAmount("invest in a startup"));
          enough = checkSum(change, "invest that amount.");
          if (enough) {
            earnings = investMoney(-1 * change);
          } else {
            msg.operationFailure("invest " + Double.toString(Math.abs(change)));
            msg.defaultAmount();
            change = 0.0;
            earnings = 0.0;
          }
          this.account += earnings;
          if (earnings > 0){
            msg.investSuccess(earnings);
          } else {
            msg.investFailure(earnings);
          }
          msg.printAccount(this.account);
          break;
          case 4:
          change = -1 * Math.abs(msg.promptAmount("invest in crypto"));
          enough = checkSum(change, "invest that amount.");

          if (enough) {
            earnings = investMoney(-1 * change);
          } else {
            msg.operationFailure("invest " + Double.toString(Math.abs(change)));
            msg.defaultAmount();
            change = 0.0;
            earnings = 0.0;
          }

          earnings = investMoney(-1 * change);
          this.account += earnings;
          if (earnings > 0){
            msg.investSuccess(earnings);
          } else {
            msg.investFailure(earnings);
          }
          msg.printAccount(this.account);
          break;
        }
      }
      runsess = msg.promptContinue(" with your account ");
    }
    return this.account;
  }
  private Boolean checkSum(Double change, String message){
    if (change + this.account < 0){
      msg.operationFailure(message);
      return false;
    } else {
      msg.operationSuccess(message);
      return true;
    }
  }
  private Double investMoney(Double change){
    Random r = new Random();
    double randomValue = change * (-0.47 + r.nextDouble());
    return Math.round(randomValue * 100.0)/100.0;
  }
}
