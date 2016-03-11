package model.interfaces;

import model.user.User;

import java.math.BigDecimal;
import java.util.Currency;

public interface IAccount {

    void withdrawMoney(BigDecimal money);

    void insertMoney(BigDecimal money);

    double getAmount();

    String getAccountName();
}
