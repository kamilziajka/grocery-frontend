package com.kazet.grocery;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class Delta implements Serializable {
    private static final Integer HASH_LENGTH = 32;
    private static final String HASH_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";

    private String guid;
    private Integer quantity;
    private String category;
    private Integer priority;
    private Date date;

    public Delta(Integer quantity, String category, Integer priority) {
        this(quantity, randomHash(), category, priority, new Date());
    }

    public Delta(Integer quantity, String guid, String category, Integer priority, Date date) {
        this.quantity = quantity;
        this.guid = guid;
        this.category = category;
        this.priority = priority;
        this.date = date;
    }

    public static String randomHash() {
        String result = "";
        Random random = new Random();

        while (result.length() < HASH_LENGTH) {
            result += HASH_CHARACTERS.charAt(random.nextInt(HASH_CHARACTERS.length()));
        }

        return result;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
