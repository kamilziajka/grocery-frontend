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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        client = BackendClient.getInstance();

        final EditText editText = (EditText) findViewById(R.id.editTextCreate);
        Button button = (Button) findViewById(R.id.buttonCreate);

        listView = (ListView) findViewById(R.id.listView);

        List<Item> items = client.getGroceryList();

        final GroceryAdapter adapter = new GroceryAdapter(this, items);
        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.create(editText.getText().toString());

                List<Item> items = client.getGroceryList();
                adapter.setList(items);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
