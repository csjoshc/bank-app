/*
New account session spawned for each balance (no name associated)
Account session is only spawned after authenticator validates credentials
An account session continues until the user has finished all transactions, returning the
user to the login page.
*/

package bankapp;
import bankapp.*;
import java.lang.Math;
import java.util.Random;
import java.util.HashMap;
import java.time.Period;
import java.time.LocalDate;

public class Account{

  CryptoAPI crypto = new CryptoAPI();
  double account;
  Messages msg;
  Account (double account, Messages msg){
    this.account = account;
    this.msg = msg;
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
        Period pd;

        HashMap<String, Object> crypto;
        switch(choice) {
          case 1:
          this.account += Math.abs(msg.promptAmount("add money"));
          msg.printAccount(this.account);
          break;
          case 2:
          change = -1 * Math.abs(msg.promptAmount("take money"));
          if(checkSum(change, "withdraw")){
            this.account += change;
            msg.printAccount(this.account);
          }
          break;
          case 3:
          change = -1 * Math.abs(msg.promptAmount("invest in a startup"));
          enough = checkSum(change, "invest");
          if (enough) {
            earnings = investStartup(-1 * change);
            this.account += earnings;
            if (earnings > 0){
              msg.investSuccess(earnings);
            } else if (enough){
              msg.investFailure(earnings);
            }
          }
          msg.printAccount(this.account);
          break;

          case 4:
          change = -1 * Math.abs(msg.promptAmount("invest in crypto"));
          enough = checkSum(change, "invest");

          if (enough) {
            crypto = investCrypto(-1 * change);
            earnings =  Math.round((double)crypto.get("NetChange") * 100.0)/100.0;
            pd = (Period)crypto.get("Period");
            if (earnings > 0){
              msg.investSuccess(earnings);
              msg.printInvestmentPeriod(pd);
              if(pd.getYears() >= 1){
                double tax = Math.round(0.2 * earnings * 100.0)/100.0;
                System.out.println("Long term capital gains tax assessed at 20%: You pay $" + tax);
                earnings -= tax;
                this.account += earnings;
              } else {
                double tax = Math.round(0.4 * earnings * 100.0)/100.0;
                System.out.println("Short term capital gains tax assessed at 40%: You pay $" + tax);
                earnings -= tax;
                this.account += earnings;
              }
            } else {
              msg.investFailure(earnings);
              this.account += earnings;
            }
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
      msg.operationFailure(message + " $" + Math.abs(change));
      return false;
    } else if (change == 0){
      msg.defaultAmount();
      return false;
    } else {
      msg.operationSuccess(message + " $" + Math.abs(change));
      return true;
    }
  }

  private HashMap<String, Object> investCrypto(Double investAmount){

    HashMap<String, Object> data = new HashMap<String, Object>();

    HashMap<String, Object> buy = msg.promptDate("buy");
    HashMap<String, Object> sell  = msg.promptDate("sell");
    while((long)sell.get("Timestamp") < (long)buy.get("Timestamp")){
      System.out.println("Error: the selling date must be after the buying date.");
      sell  = msg.promptDate("sell");
    }

    double buy_price = crypto.submitRequest((long)buy.get("Timestamp"));
    double btc_bought =  Math.round(investAmount/buy_price * 10000.0)/10000.0;
    msg.printPrice("bought", btc_bought, buy_price, investAmount);

    double sell_price = crypto.submitRequest((long)sell.get("Timestamp"));
    double end_amount = Math.round(btc_bought * sell_price * 10000.0)/10000.0;
    msg.printPrice("sold", btc_bought, sell_price, end_amount);

    double netChange = end_amount - investAmount;
    data.put("NetChange", netChange);
    data.put("Period", Period.between((LocalDate)buy.get("DateObject"), (LocalDate)sell.get("DateObject")));
    return data;

  }

  private Double investStartup(Double change){
    Random r = new Random();
    double randomValue = change * (-0.47 + r.nextDouble());
    return Math.round(randomValue * 100.0)/100.0;
  }
}
