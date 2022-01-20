package controller.persistence.archive;

import controller.persistence.CurrencyLoader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import model.Currency;

public class ArchiveCurrencyLoader implements CurrencyLoader {
    private final String fileName;

    public ArchiveCurrencyLoader(String fileName) {
        this.fileName = fileName;
    }
    
    @Override
    public ArrayList<Currency> loadCurrencies() {
        ArrayList<Currency> currencies = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(", ");
                currencies.add(new Currency(split[0], split[1], split[2]));
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return currencies;
    }
    
}
