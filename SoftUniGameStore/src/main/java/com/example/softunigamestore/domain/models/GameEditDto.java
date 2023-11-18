package com.example.softunigamestore.domain.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class GameEditDto {
    private Long id;
    private String title;
    private BigDecimal price;
    private double size;
    private String trailer;
    private String thumbnailURL;
    private String description;
    private LocalDate releaseDate;


    public String successfullyEditedGame() {
        return "Edited " + this.title;
    }

    public GameEditDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void updateFields(Map<String, String> updatedValues) {
        for (String key : updatedValues.keySet()) {
            if (Objects.equals(key, "title")) {
                setTitle(updatedValues.get(key));
            } else if (Objects.equals(key, "price")) {
                setPrice(new BigDecimal(updatedValues.get(key)));
            } else if (Objects.equals(key, "size")) {
                setSize(Float.parseFloat(updatedValues.get(key)));
            } else if (Objects.equals(key, "trailer")) {
                setTrailer(updatedValues.get(key));
            } else if (Objects.equals(key, "thumbnailURL")) {
                setThumbnailURL(updatedValues.get(key));
            } else if (Objects.equals(key, "description")) {
                setDescription(updatedValues.get(key));
            } else if (Objects.equals(key, "releaseDate")) {
                setReleaseDate(LocalDate.now());
            }
        }
    }
}
