package com.ryan.powerschool;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import android.widget.TextView;
import android.os.AsyncTask;

public class LoginScreen extends Activity {

    private TextView theView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        theView = (TextView) findViewById(com.ryan.powerschool.R.id.textView);
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


                return page.asText();
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
