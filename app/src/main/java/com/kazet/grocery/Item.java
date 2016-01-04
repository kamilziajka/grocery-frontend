package com.kazet.grocery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {
    private String name;
    private List<Delta> deltas;

    public Item(String name) {
        this.name = name;
        this.deltas = new ArrayList<Delta>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addDelta(Delta delta) {
        if (!containsDelta(delta)) {
            deltas.add(delta);
        }
    }

    private Boolean containsDelta(Delta delta) {
        for (Delta item : deltas) {
            if (item.getGuid().equals(delta.getGuid())) {
                return true;
            }
        }

        return false;
    }

    public Integer getQuantity() {
        Integer quantity = 0;

        for (Delta delta : deltas) {
            quantity += delta.getQuantity();
        }

        return quantity;
    }

    @Override
    public String toString() {
        String result = name + " delta:\n";

        for (Delta delta : deltas) {
            result += delta.getGuid() + ", " + delta.getQuantity() + "\n";
        }

        return result;
    }

    public List<Delta> getDeltas() {
        return deltas;
    }
}
