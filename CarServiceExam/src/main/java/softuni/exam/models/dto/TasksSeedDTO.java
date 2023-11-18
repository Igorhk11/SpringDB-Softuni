package softuni.exam.models.dto;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class TasksSeedDTO {
    @XmlElement
    private String date;
    @XmlElement
    @Positive
    private BigDecimal price;
    @XmlElement
    private CarIdXMLDto car;
    @XmlElement
    private MechanicFirstNameXMLDto mechanic;
    @XmlElement
    private PartIdXMLDto part;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CarIdXMLDto getCar() {
        return car;
    }

    public void setCar(CarIdXMLDto car) {
        this.car = car;
    }

    public MechanicFirstNameXMLDto getMechanic() {
        return mechanic;
    }

    public void setMechanic(MechanicFirstNameXMLDto mechanic) {
        this.mechanic = mechanic;
    }

    public PartIdXMLDto getPart() {
        return part;
    }

    public void setPart(PartIdXMLDto part) {
        this.part = part;
    }
}
