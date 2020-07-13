import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Company {
    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("companyFullName")
    private String companyFullName;

    @JsonProperty("registrationDate")
    private LocalDate registrationDate;

    @JsonProperty("legalAddress")
    private String legalAddress;

    @JsonProperty("actualAddress")
    private String actualAddress;

    @JsonProperty("mailingAddress")
    private String mailingAddress;

    @JsonProperty("centralPhone")
    private String centralPhone;

    @JsonProperty("officePhone")
    private String officePhone;

    @JsonProperty("fax")
    private String fax;

    @JsonProperty("INN")
    private long INN;

    @JsonProperty("OGRN")
    private long OGRN;

    @JsonProperty("stocks")
    private List<Stock> stocks;

    public final String pattern = "dd.MM.yyyy";

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyFullName() {
        return companyFullName;
    }

    public void setCompanyFullName(String companyFullName) {
        this.companyFullName = companyFullName;
    }

    public String getRegistrationDate() {
        return DateTimeFormatter.ofPattern(pattern).format(registrationDate);
    }

    public LocalDate getRegistrationDateObj() { return registrationDate; }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = LocalDate.from(DateTimeFormatter.ofPattern(pattern).parse(registrationDate));
    }

    public String getLegalAddress() {
        return legalAddress;
    }

    public void setLegalAddress(String legalAddress) {
        this.legalAddress = legalAddress;
    }

    public String getActualAddress() {
        return actualAddress;
    }

    public void setActualAddress(String actualAddress) {
        this.actualAddress = actualAddress;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public String getCentralPhone() {
        return centralPhone;
    }

    public void setCentralPhone(String centralPhone) {
        this.centralPhone = centralPhone;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public long getINN() {
        return INN;
    }

    public void setINN(int INN) {
        this.INN = INN;
    }

    public long getOGRN() {
        return OGRN;
    }

    public void setOGRN(int OGRN) {
        this.OGRN = OGRN;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        stocks.forEach(stock -> stock.setOwner(this));
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        return "Company Info:\n" +
                "companyName: " + companyName +
                ",\ncompanyFullName: " + companyFullName +
                ",\nregistrationDate: " + registrationDate +
                ",\nlegalAddress: " + legalAddress +
                ",\nactualAddress: " + actualAddress +
                ",\nmailingAddress: " + mailingAddress +
                ",\ncentralPhone " + centralPhone +
                ",\nofficePhone: " + officePhone +
                ",\nfax: " + fax +
                ",\nINN: " + INN +
                ",\nOGRN: " + OGRN +
                ",\nstocks: |      id       |  code  |  price  |  count  |  currency  |  validity  |  stockName  |    owner    |" +
                "\n" + stocks +
                "\n----------------------------";
    }
}