package controller.persistence;

import model.Currency;
import model.ExchangeRate;

public interface ExchangeRateLoader {
    
    public ExchangeRate loadExchangeRate(Currency from, Currency to);
    
}
