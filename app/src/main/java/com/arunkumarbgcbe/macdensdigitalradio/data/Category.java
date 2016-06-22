package com.arunkumarbgcbe.macdensdigitalradio.data;

import java.io.Serializable;

/**
 * Created by ArunKumar on 10-06-2016.
 */
public class Category implements Serializable {

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String category;
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
