package controller;

import model.ExchangeRate;
import model.Money;

public class Controller {
    
    public double getMoneyConverted(Money money, ExchangeRate exchangeRate) {
        return money.getAmount() * exchangeRate.getRate();
    }

}
