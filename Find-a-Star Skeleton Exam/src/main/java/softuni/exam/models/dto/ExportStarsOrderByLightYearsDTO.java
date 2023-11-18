package softuni.exam.models.dto;

public class ExportStarsOrderByLightYearsDTO {

    private String name;

    private Double lightYears;

    private String description;

    private String constellation;

    public ExportStarsOrderByLightYearsDTO(String name, Double lightYears, String description, String constellation) {
        this.name = name;
        this.lightYears = lightYears;
        this.description = description;
        this.constellation = constellation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLightYears() {
        return lightYears;
    }

    public void setLightYears(Double lightYears) {
        this.lightYears = lightYears;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }
}
