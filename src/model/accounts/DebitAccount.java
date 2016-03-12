package model.accounts;

import java.math.BigDecimal;

public class DebitAccount extends Account {

    public DebitAccount(String accountName, BigDecimal amount) {
        super(accountName, amount);
    }

    @Override
    public void withdrawMoney(BigDecimal money) {
        super.amount.subtract(money);
    }

    @Override
    public void insertMoney(BigDecimal money) {
        super.amount.add(money);
    }

}
