package com.kazet.grocery;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends ActionBarActivity {
    private ListView listView;

    private BackendClient client;
    private Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        store = new Store(getApplicationContext());

        final EditText editTextName = (EditText) findViewById(R.id.editTextCreate);
        final EditText editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);
        final EditText editTextCategory = (EditText) findViewById(R.id.editTextCategory);
        final EditText editTextPriority = (EditText) findViewById(R.id.editTextPriority);
        Button button = (Button) findViewById(R.id.buttonCreate);
        Button buttonSync = (Button) findViewById(R.id.buttonSync);

        listView = (ListView) findViewById(R.id.listView);

        List<Item> items = store.getItemsList();

        final GroceryAdapter adapter = new GroceryAdapter(this, items, store);
        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                Integer quantity = Integer.valueOf(editTextQuantity.getText().toString());
                String category = editTextCategory.getText().toString();
                Integer priority = Integer.parseInt(editTextPriority.getText().toString());
                store.addDelta(name, new Delta(quantity, category, priority));

                adapter.setList(store.getItemsList());
                adapter.notifyDataSetChanged();
            }
        });

        buttonSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store.sync();
                adapter.setList(store.getItemsList());
                adapter.notifyDataSetChanged();
            }
        });
    }
}
