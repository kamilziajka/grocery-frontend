package com.kazet.grocery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

class GroceryAdapter extends BaseAdapter {
    private Context context;
    private List<Item> items;

    private BackendClient client;

    private static LayoutInflater inflater = null;

    public GroceryAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;

        client = BackendClient.getInstance();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        if (vi == null) {
            vi = inflater.inflate(R.layout.row, null);
        }

        TextView textViewName = (TextView) vi.findViewById(R.id.textViewName);
        TextView textViewQuantity = (TextView) vi.findViewById(R.id.textViewQuantity);

        Button buttonDelete = (Button) vi.findViewById(R.id.buttonDelete);
        Button buttonIncrement = (Button) vi.findViewById(R.id.buttonIncrement);
        Button buttonDecrement = (Button) vi.findViewById(R.id.buttonDecrement);

        final EditText editText = (EditText) vi.findViewById(R.id.editText);

        buttonIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = items.get(position);
                Integer quantity = item.getQuantity() + Integer.parseInt(editText.getText().toString());
                quantity = quantity < 0 ? 0 : quantity;
                item.setQuantity(quantity);
                client.update(item);
                items = client.getGroceryList();
                notifyDataSetChanged();
            }
        });

        buttonDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = items.get(position);
                Integer quantity = item.getQuantity() - Integer.parseInt(editText.getText().toString());
                quantity = quantity < 0 ? 0 : quantity;
                item.setQuantity(quantity);
                client.update(item);
                items = client.getGroceryList();
                notifyDataSetChanged();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = items.get(position);
                client.delete(item);
                items = client.getGroceryList();
                notifyDataSetChanged();
            }
        });

        textViewName.setText(items.get(position).getName());
        textViewQuantity.setText(items.get(position).getQuantity().toString());

        return vi;
    }
}
