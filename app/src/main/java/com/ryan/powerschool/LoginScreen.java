package com.ryan.powerschool;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import android.widget.Button;
import android.widget.EditText;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import android.widget.TextView;
import android.os.AsyncTask;
import android.content.SharedPreferences;
import android.view.View;

public class LoginScreen extends Activity {

    private TextView theView;
    private Button submitButton;
    private SharedPreferences thePreferences;
    private SharedPreferences.Editor theEditor;
    private EditText usernameET, passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        thePreferences = getApplicationContext().getSharedPreferences("com.ryan.powerschool", 1);
        theEditor = thePreferences.edit();

        theView = (TextView) findViewById(com.ryan.powerschool.R.id.textView);
        submitButton = (Button) findViewById(com.ryan.powerschool.R.id.submitButton);
        usernameET = (EditText) findViewById(com.ryan.powerschool.R.id.userName);
        passwordET = (EditText) findViewById(com.ryan.powerschool.R.id.password);

        usernameET.setText(getField("username"));
        passwordET.setText(getField("password"));

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Login().execute();
            }
        });
    }

    private class Login extends AsyncTask<Void, Void, String> {
        @Override
        public String doInBackground(Void... params) {
            final WebClient client = new WebClient();
            try {
                final HtmlPage page = client.getPage("https://pschool.princetonk12.org/public/");
                final HtmlForm theForm = page.getFormByName("LoginForm");

                final HtmlTextInput username = theForm.getInputByName("fieldAccount");
                final HtmlTextInput password = theForm.getInputByName("pw");
                final HtmlSubmitInput submit = theForm.getInputByName("btn-enter");

                final String name = usernameET.getText().toString();
                final String pass = passwordET.getText().toString();

                setField("username", name);
                setField("password", pass);

                username.setValueAttribute(name);
                password.setValueAttribute(pass);

                final HtmlPage nextPage = submit.click();
                return nextPage.asText();
            }
            catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }
        }
        @Override
        public void onPostExecute(final String param) {
            theView.setText(param);
        }
    }

    private String getField(final String name) {
        return thePreferences.getString(name, "");
    }

    private void setField(final String name, final String value) {
        theEditor.putString(name, value);
        theEditor.apply();
        theEditor.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
