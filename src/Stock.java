import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

public class Stock {
    private String stockName;
    private String id;
    private String code;
    private int count;
    private int price;
    private Currency currency;
    private LocalDate validity;
    private Company owner;
    public final String pattern = "dd.MM.yyyy";

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = Currency.getInstance(currency);
    }

    public String getValidity() { return DateTimeFormatter.ofPattern(pattern).format(validity); }

    public LocalDate getValidityDateObj() { return validity; }

    public void setValidity(String validity) {
        this.validity = LocalDate.from(DateTimeFormatter.ofPattern(pattern).parse(validity));
    }

    public Company getOwner() { return owner; }

    public void setOwner(Company owner) { this.owner = owner; }

    @Override
    public String toString() {
        return
                //"           " + stockName +
                "         " + id +
                "     " + code +
                "       " + count +
                "       " + price +
                "       " + currency +
                "       " + validity +
                "     " + stockName +
                "     " + owner.getCompanyName() +
                "       \n";
    }
}
