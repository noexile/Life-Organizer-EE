package model.interfaces;

import model.user.User;

import java.math.BigDecimal;
import java.util.Currency;

public interface IAccount {

    void withdrawMoney(double money);

    void insertMoney(double money);

    double getAmount();

    String getAccountName();
}
