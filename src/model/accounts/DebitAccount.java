package model.accounts;

public class DebitAccount extends Account {

    public DebitAccount(String accountName, double amount, int dbUid) {
        super(accountName, amount, dbUid);
    }

    @Override
    public void withdrawMoney(double money) {
        super.amount -= money;
    }

    @Override
    public void insertMoney(double money) {
        super.amount += money;
    }

}
