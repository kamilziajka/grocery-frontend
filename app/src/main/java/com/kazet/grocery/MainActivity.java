package com.kazet.grocery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
    private Button buttonLogin;
    private EditText editTextName;
    private EditText editTextPassword;
    private LinearLayout layoutLogin;
    private TextView textViewError;

    private BackendClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = BackendClient.getInstance();

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        layoutLogin = (LinearLayout) findViewById(R.id.layoutLogin);
        textViewError = (TextView) findViewById(R.id.textViewError);

        final Activity activity = this;

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String password = editTextPassword.getText().toString();

                Boolean result = client.login(name, password);

                if (result) {
                    Intent intent = new Intent(activity, ListActivity.class);
                    startActivity(intent);
                } else {
                    textViewError.setText("Nieprawidłowy login lub hasło");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
