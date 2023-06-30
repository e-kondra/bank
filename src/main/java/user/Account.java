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

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    private String generateAccountNumber(){
        String tail = String.format("0%d", (int) (Math.random() * 1000));
        return "LT12345678910111" + tail;
    }

    public void makeDeposit(double deposit){
        try {
            this.setBalance(this.getBalance() + deposit);
            System.out.println("You have successfully funded your account");
            System.out.println("Your balance "+ this.getBalance());
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void makeTransfer(double summa){
        try {
            String result = (this.getBalance() - summa < 0)? "insufficient funds in the account": null;
            if (result == null) {
                this.setBalance(this.getBalance() - summa);
            }else {
                System.out.println(result);
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
