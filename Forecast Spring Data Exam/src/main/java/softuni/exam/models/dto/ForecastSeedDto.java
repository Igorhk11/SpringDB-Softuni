package softuni.exam.models.dto;

import softuni.exam.models.entity.enums.DayOfWeek;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastSeedDto {

    @XmlElement(name = "day_of_week")
    @NotNull
    private DayOfWeek dayOfWeek;
    @XmlElement(name = "max_temperature")
    @NotNull
    @Min(-20)
    @Max(60)
    private Double maxTemperature;
    @XmlElement(name = "min_temperature")
    @NotNull
    @Min(-50)
    @Max(40)
    private Double minTemperature;
    @XmlElement(name = "sunrise")
    @NotNull
    private String sunrise;
    @XmlElement(name = "sunset")
    @NotNull
    private String sunset;
    @XmlElement(name = "city")
    @NotNull
    private Long city;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }
}
