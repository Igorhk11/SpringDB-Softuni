package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class CreditCard extends BillingDetails{

    @Column(name = "card_type", nullable = false, length = 30)
    private String cardType;

    @Column(name = "exp_month", nullable = false)
    private Short expirationMonth;

    @Column(name = "exp_year", nullable = false)
    private Short expirationYear;

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Short getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(Short expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public Short getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(Short expirationYear) {
        this.expirationYear = expirationYear;
    }
}
