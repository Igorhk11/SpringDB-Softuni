package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "astronomers")
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomersRootXMLDto {

    @XmlElement(name = "astronomer")
    List<AstronomersXMLDto> astronomer;

    public List<AstronomersXMLDto> getAstronomer() {
        return astronomer;
    }

    public void setAstronomer(List<AstronomersXMLDto> astronomer) {
        this.astronomer = astronomer;
    }
}
