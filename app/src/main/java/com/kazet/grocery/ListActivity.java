package com.kazet.grocery;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends ActionBarActivity {
    private ListView listView;

    private BackendClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.listView);

        client = BackendClient.getInstance();

        List<Item> items = client.getGroceryList();

        listView.setAdapter(new GroceryAdapter(this, items));
    }
}
