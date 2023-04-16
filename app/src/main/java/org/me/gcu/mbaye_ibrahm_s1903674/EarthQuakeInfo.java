package org.me.gcu.mbaye_ibrahm_s1903674;
//  Ibrahim Mbaye S1903674

import java.io.Serializable;
import java.util.List;

public class EarthQuakeInfo implements Serializable {
    private String title;
    private String description;
    private List<EarthQuakeItem> items;

    EarthQuakeInfo() {}

    public List<EarthQuakeItem> getItems() {
        return items;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setItems(List<EarthQuakeItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "EarthQuakesInfo{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", items=" + items +
                '}';
    }
}



