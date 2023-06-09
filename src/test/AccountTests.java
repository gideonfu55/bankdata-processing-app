package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.account.Account;
import model.account.Chequing;
import model.account.Loan;
import model.account.Savings;
import model.account.implement.Taxable;

public class AccountTests {

  Account[] accounts;

  @Before
  public void setup() {
    accounts = new Account[] {
      new Chequing("f84c43f4-a634-4c57-a644-7602f8840870", "Michael Scott", 1524.51),
      new Savings("ce07d7b3-9038-43db-83ae-77fd9c0450c9", "Saul Goodman", 2241.60),
      new Loan("4991bf71-ae8f-4df9-81c1-9c79cff280a5", "Phoebe Buffay", 2537.31)
    };
  
  }
 
  // Test Case 1 - withdrawal for accounts:
  @Test
  public void withdrawal() {
    accounts[0].withdraw(1440);
    assertEquals(84.51, accounts[0].getBalance());
  }

  // Test Case 2 - Additional Overdraft Fee for Chequing account:
  @Test
  public void overdraft() {
    accounts[0].withdraw(1534.43);
    assertEquals(-15.42, accounts[0].getBalance());
  }

  // Test Case 3 - Overdraft Limit for Chequing account:
  @Test
  public void overdraftLimit() {
    accounts[0].withdraw(1726);
    assertEquals(1524.51, accounts[0].getBalance());
  }

  // Test Case 4 - Withdrawal Fee:
  @Test
  public void withdrawalFee() {
    accounts[1].withdraw(100);
    assertEquals(2136.60, accounts[1].getBalance());
  }

  // Test Case 5 - Withdrawal Interest:
  @Test
  public void withdrawalInterest() {
    accounts[2].withdraw(2434.31);
    assertEquals(5020.31, accounts[2].getBalance());
  }

  // Test Case 6 - Withdrawal Limit:
  @Test
  public void withdrawalLimit() {
    accounts[2].withdraw(7463.69);
    assertEquals(2537.31, accounts[2].getBalance());
  }

  // Test Case 7 - Deposit:
  @Test
  public void deposit() {
    accounts[0].deposit(5000);
    accounts[1].deposit(5000);
    assertEquals(6524.51, accounts[0].getBalance());
    assertEquals(7241.60, accounts[1].getBalance());
  }

  // Test Case 8 - Loan Deposit (credit repayment):
  @Test
  public void loanDeposit() {
    accounts[2].deposit(1000);
    assertEquals(1537.31, accounts[2].getBalance());
  }

  // Test Case 8 - Income Tax Calculation:
  @Test
  public void incomeTax() {
    double income = 4000;
    accounts[0].deposit(income);
    ((Taxable)accounts[0]).tax(income);
    assertEquals(5374.51, accounts[0].getBalance());
  }

  

}
