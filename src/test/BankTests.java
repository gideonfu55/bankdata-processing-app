package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.account.Chequing;
import model.mainframe.Bank;
import model.mainframe.Transaction;

public class BankTests {
  Bank bank;

  @Before
  public void setup() {
    bank = new Bank();

    bank.addAccount(new Chequing("f84c43f4-a634-4c57-a644-7602f8840870", "Michael Scott", 1524.51));
  }

  @Test
  public void successfulTransaction() {
    this.bank.executeTransaction(new Transaction(Transaction.Type.WITHDRAW, 1546905600, "f84c43f4-a634-4c57-a644-7602f8840870", 624.99));
    this.bank.executeTransaction(new Transaction(Transaction.Type.DEPOSIT, 1578700800, "f84c43f4-a634-4c57-a644-7602f8840870", 441.93));
    assertEquals(2, bank.getTransactions("f84c43f4-a634-4c57-a644-7602f8840870").length);
  }

  @Test
  public void failedTransaction() {
    this.bank.executeTransaction(new Transaction(Transaction.Type.WITHDRAW, 1546905600, "f84c43f4-a634-4c57-a644-7602f8840870", 10000000));
    assertEquals(0, bank.getTransactions("f84c43f4-a634-4c57-a644-7602f8840870").length);
  }

  @Test
  public void taxDeduction() {
    this.bank.executeTransaction(new Transaction(Transaction.Type.DEPOSIT, 1578700800, "f84c43f4-a634-4c57-a644-7602f8840870", 4000));
    this.bank.executeTransaction(new Transaction(Transaction.Type.WITHDRAW, 1578700800, "f84c43f4-a634-4c57-a644-7602f8840870", 500));
    // Tests deductTax() method - calculation should be: 1524.51 + 4000 - 500 - ((4000 - 500) - 3000) * 15% = 4949.51
    this.bank.deductTaxes();
    assertEquals(4949.51, bank.getAccount("f84c43f4-a634-4c57-a644-7602f8840870").getBalance());
  }

}
