package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class BankAccount extends BillingDetails {

    @Column(name = "bank_name", length = 50)
    private String bankName;

    @Column(name = "swift_code", length = 50)
    private String swiftCode;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public BankAccount() {
    }
}
