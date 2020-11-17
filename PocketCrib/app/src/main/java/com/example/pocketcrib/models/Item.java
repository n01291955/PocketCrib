package com.example.pocketcrib.models;

public class Item {
    private String itemName;
    private String itemType;
    private String itemQty;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Item(String itemName, String itemQty , String itemType) {
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.itemType = itemType;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Item() {
    }

    public Item(String itemName, String itemQty) {
        this.itemName = itemName;
        this.itemQty = itemQty;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }
}

