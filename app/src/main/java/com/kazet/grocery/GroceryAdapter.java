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
    private Store store;

    //private BackendClient client;

    private static LayoutInflater inflater = null;

    public GroceryAdapter(Context context, List<Item> items, Store store) {
        this.context = context;
        this.items = items;
        this.store = store;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setList(List<Item> items) {
        this.items = items;
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
        final EditText editTextCategory = (EditText) vi.findViewById(R.id.editTextCategory);
        final EditText editTextPriority = (EditText) vi.findViewById(R.id.editTextPriority);

        Button buttonDelete = (Button) vi.findViewById(R.id.buttonDelete);
        Button buttonIncrement = (Button) vi.findViewById(R.id.buttonIncrement);
        Button buttonDecrement = (Button) vi.findViewById(R.id.buttonDecrement);
        Button buttonSave = (Button) vi.findViewById(R.id.buttonSave);

        final EditText editText = (EditText) vi.findViewById(R.id.editText);

        buttonIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = items.get(position);
                Integer quantity = Integer.parseInt(editText.getText().toString());
                quantity = quantity < 0 ? 0 : quantity;
                store.addDelta(item.getName(), new Delta(
                    quantity,
                    item.getCategory(),
                    item.getPriority()
                ));
                notifyDataSetChanged();
            }
        });

        buttonDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = items.get(position);
                Integer quantity = Integer.parseInt(editText.getText().toString());
                quantity = quantity < 0 ? 0 : quantity;
                store.addDelta(item.getName(), new Delta(
                    -quantity,
                    item.getCategory(),
                    item.getPriority()
                ));
                notifyDataSetChanged();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = items.get(position);
                store.addDelta(item.getName(), new Delta(
                    -item.getQuantity(),
                    item.getCategory(),
                    item.getPriority()
                ));
                notifyDataSetChanged();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = items.get(position);
                store.addDelta(item.getName(), new Delta(
                    0,
                    editTextCategory.getText().toString(),
                    Integer.parseInt(editTextPriority.getText().toString())
                ));
                notifyDataSetChanged();
            }
        });

        textViewName.setText(items.get(position).getName());
        textViewQuantity.setText(items.get(position).getQuantity().toString());
        editTextCategory.setText(items.get(position).getCategory());
        editTextPriority.setText(items.get(position).getPriority().toString());

        return vi;
    }
}
