package controller.persistence.webservice;

import controller.persistence.ExchangeRateLoader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import model.Currency;
import model.ExchangeRate;

public class WebServiceExchangeRateLoader implements ExchangeRateLoader {

    @Override
    public ExchangeRate loadExchangeRate(Currency from, Currency to) {
        double rate = 0;
        try {
            URL url = new URL("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/"
                    + from.getCode().toLowerCase() + "/" + to.getCode().toLowerCase() + ".json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(to.getCode().toLowerCase())) {
                    String[] split = line.split(": ");
                    rate = Double.parseDouble(split[1]);
                }
            }
            reader.close();
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return new ExchangeRate(rate, from, to);
    }
    
}
