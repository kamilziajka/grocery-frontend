package com.kazet.grocery;

import java.io.Serializable;
import java.util.Random;

public class Delta implements Serializable {
    private static final Integer HASH_LENGTH = 32;
    private static final String HASH_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";

    private String guid;
    private Integer quantity;

    public Delta(Integer quantity) {
        this(quantity, randomHash());
    }

    public Delta(Integer quantity, String guid) {
        this.quantity = quantity;
        this.guid = guid;
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
}
