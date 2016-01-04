package com.kazet.grocery;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store {
    private static final String FILE_NAME = "storage";

    private BackendClient client;
    private Map<String, Item> items;
    private Context context;

    public Store(Context context) {
        this.context = context;
        items = retrieveItems();
        client = BackendClient.getInstance();
    }

    public void addDelta(String itemName, Delta delta) {
        Item item = items.get(itemName);

        if (item == null) {
            item = new Item(itemName);
            items.put(itemName, item);
        }

        item.addDelta(delta);

        storeItems(items);
    }

    public List<Item> getItemsList() {
        return new ArrayList<Item>(items.values());
    }

    public void sync() {
        client.populateDeltas(this);
        client.uploadDeltas(this);
    }

    private Map<String, Item> retrieveItems() {
        Map<String, Item> items = new HashMap<String, Item>();

        try {
            FileInputStream fileStream = context.openFileInput(FILE_NAME);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);

            items = (Map<String, Item>) objectStream.readObject();

            objectStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    private void storeItems(Map<String, Item> items) {
        try {
            FileOutputStream fileStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(items);
            objectStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
