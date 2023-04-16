package org.me.gcu.mbaye_ibrahm_s1903674;
//  Ibrahim Mbaye S1903674

import java.io.Serializable;

public class EarthQuakeItem implements Serializable {
    private String title;
    private String description;
    private String publishDate;
    private String latitude;
    private String longitude;


    EarthQuakeItem(){ }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
    public String getPublishDate() {
        return publishDate;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLatitude() {
        return latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getLongitude() {
        return longitude;
    }




    @Override
    public String toString() {
        return "EarthQuakeItem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

