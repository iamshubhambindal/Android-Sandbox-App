/*
 * Created by eknee on 3/8/2018.
 */
package apps.optimizer.androidsandboxapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.util.HashMap;
import com.adobe.mobile.*;
import com.google.firebase.iid.FirebaseInstanceId;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // Allow the SDK access to the application context
        Config.setContext(this.getApplicationContext());

        //Turns on debug logging
        Config.setDebugLogging(true);

        String token = FirebaseInstanceId.getInstance().getToken();
        Config.setPushIdentifier(token);
        Log.d("Registration token: ", token);

        /*
        Intent i = new Intent(this.getApplicationContext(), MyFirebaseInstanceIDService.class);
        startService(i);
        */

        TextView pushText = (TextView) findViewById(R.id.pushText);
        String pushLine = "Push ID: " + token;
        pushText.setText(pushLine);

        TextView mcidText = (TextView) findViewById(R.id.mcidText);
        String MCID = Visitor.getMarketingCloudId();
        String mcidLine = "MCID: " + MCID;
        mcidText.setText(mcidLine);
    }

    @Override
    public void onResume() {
        super.onResume();
        Config.collectLifecycleData(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Config.pauseCollectingLifecycleData();
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

    //Calls collectPII from Adobe Mobile SDK
    public void collectPII(View view) {

        EditText email = (EditText) findViewById(R.id.email);
        EditText firstName = (EditText) findViewById(R.id.firstName);
        EditText lastName = (EditText) findViewById(R.id.lastName);

        String userKey = "";
        String strEmail = email.getText().toString();
        String strFirstName = firstName.getText().toString();
        String strLastName = lastName.getText().toString();
        String marketingCloudId = Visitor.getMarketingCloudId();

        HashMap<String, Object> data = new HashMap<>();
        data.put("triggerKey", "collectPII");
        data.put("marketingCloudId", marketingCloudId);
        data.put("userKey", userKey);
        data.put("cusEmail", strEmail);
        data.put("cusFirstName", strFirstName);
        data.put("cusLastName", strLastName);
        Config.collectPII(data);

        Log.d("MCID ", marketingCloudId);
        Log.d("userKey ", userKey);
        Log.d("Email ", strEmail);
        Log.d("First Name ", strFirstName);
        Log.d("Last Name ", strLastName);

        //Snackbar sb = Snackbar.make(view, R.string.confirmCollectPII,Snackbar.LENGTH_LONG);
        //sb.show();
    }
}
