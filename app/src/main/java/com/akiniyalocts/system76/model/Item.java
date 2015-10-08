package com.akiniyalocts.system76.model;

import java.io.Serializable;

/**
 * Created by anthony on 10/6/15.
 */
public class Item implements Serializable{
    public final static String SER_TAG = Item.class.getSimpleName();

    private String title;

    private String imageLink;

    private String description;

    private String itemUrl;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageLink() {
        if(imageLink != null)
            return "https://system76.com" + imageLink;

        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }
}
