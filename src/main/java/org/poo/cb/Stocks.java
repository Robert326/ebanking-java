package org.poo.cb;

public class Stocks {
    private String StockName;
    private int amount;

    public Stocks(String StockType, int amount) {
        this.StockName = StockType;
        this.amount = amount;
    }

    public String getStockType() {
        return StockName;
    }

    public void setStockType(String stockType) {
        StockName = stockType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
