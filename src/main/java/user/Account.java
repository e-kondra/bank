package user;

public class Account {
    private String accountNumber;
    private double balance;

    public Account() {
        this.accountNumber = this.generateAccountNumber();
        this.balance = 0d;
    }

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }


    public double getBalance() {
        return balance;
    }
    public String getBalanceStr() {
        return String.format("%.2f",balance);
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    private String generateAccountNumber(){
        String tail = String.format("0%d", (int) (Math.random() * 1000));
        return "LT12345678910111" + tail;
    }

    public void toCredit(double amount){
        this.setBalance(this.getBalance() + amount);
    }
    public boolean checkDebitAmount(double amount){
        return (amount <= this.getBalance());
    }
    public void toDebit(double amount) {
        this.setBalance(this.getBalance() - amount);
    }



}
