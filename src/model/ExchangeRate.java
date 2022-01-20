package model;

public class ExchangeRate {
    private double rate;
    private Currency from;
    private Currency to;

    public ExchangeRate(double rate, Currency from, Currency to) {
        this.rate = rate;
        this.from = from;
        this.to = to;
    }

    public double getRate() {
        return rate;
    }

    public Currency getFrom() {
        return from;
    }

    public Currency getTo() {
        return to;
    }
    
}
