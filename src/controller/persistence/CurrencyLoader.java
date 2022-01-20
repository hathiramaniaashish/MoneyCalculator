package controller.persistence;

import java.util.ArrayList;
import model.Currency;

public interface CurrencyLoader {

    public ArrayList<Currency> loadCurrencies();
    
}
