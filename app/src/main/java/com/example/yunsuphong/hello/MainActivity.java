package com.example.yunsuphong.hello;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Enumeration;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button myButton = (Button)findViewById(R.id.myButton);
        MyOnClickListener myOnClickListener = new MyOnClickListener();
        myButton.setOnClickListener(myOnClickListener);

        //Get our hands on the TextView in the activity_main.xml file.
        TextView textView = (TextView)findViewById(R.id.textView);

        //Erase the existing text in the TextView, if any.
        textView.setText("");

        //Dimensions of the screen
        WindowManager windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        Point point = new Point();	//import android.graphics.Point;
        defaultDisplay.getSize(point);

        //Number of pixels per inch
        DisplayMetrics metrics = new DisplayMetrics();
        defaultDisplay.getMetrics(metrics);

        //Orientation
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        int orientation = configuration.orientation;
        String[] ori = {"", "ORIENTATION_PORTRAIT", "ORIENTATION_LANDSCAPE"};

        //App Name
        Resources nameResources = getResources();
        String appName = nameResources.getString(R.string.app_name);

        //Process ID and User ID
        int pid = android.os.Process.myPid();
        int uid = android.os.Process.myUid();

        //OpenGL
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        int v = configurationInfo.reqGlEsVersion;

        //Date and time
        Date date = new Date();	//current date and time
        DateFormat dateFormat = DateFormat.getDateTimeInstance();

        //Internet connectivity
        ConnectivityManager connectivityManager =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //Print
        String s = "SDK version: " + Build.VERSION.SDK_INT + "\n"
                + "Android release: " + Build.VERSION.RELEASE + "\n" +
                "Width: " + point.x + "\n"
                + "Height: " + point.y + " pixels\n" +
                "Density: "
                + metrics.density * DisplayMetrics.DENSITY_DEFAULT + " dpi" + "\n" +
                "Orientation: " + ori[orientation] + "\n" +
                "App name: " + appName + "\n" +
                "App Process ID: " + pid + "\n" +
                "App User ID: " + uid + "\n" +
                "Thread: " + Thread.currentThread().getName() + "\n" +
                "major version: " + (v & 0xFF00) + "\n" +
                "minor version: " + (v & 0x00FF) + "\n" +
                dateFormat.format(date) + "\n" +
                networkInfo.toString();

        textView.append(s);

        //IP Address
        try {
            for (Enumeration<NetworkInterface> ei = NetworkInterface.getNetworkInterfaces();
                 ei.hasMoreElements();) {
                NetworkInterface networkInterface = ei.nextElement();

                for (Enumeration<InetAddress> ea = networkInterface.getInetAddresses();
                     ea.hasMoreElements();) {
                    InetAddress inetAddress = ea.nextElement();

                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = inetAddress.getHostAddress();
                        textView.append("IP address: " + ip + "\n");
                    }
                }
            }
        } catch (SocketException socketException) {
            textView.append(socketException.getMessage() + "\n");
        }

        Toast.makeText(this, "MainActivity onCreate", Toast.LENGTH_LONG).show();

        //textView.setText("Debuging output in a TextView.");

        /*
        for (int i = 1; i <= 10; i = i + 1) {
            textView.append(String.valueOf(i) + "\n");
        }
        */

        int k = 10;
        System.out.println("standard output: k = " + k);
        System.err.println("standard error output: k = " + k);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Menu menu is an empty object created by the OS. The inflate fills that menu with info
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

        if (id == R.id.action_settings2) {
            Toast toastMessage = Toast.makeText(this, "Option 2 Selected", Toast.LENGTH_LONG);
            toastMessage.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Generally should be a separate Java file
    class MyOnClickListener implements View.OnClickListener {

        @Override
        //Receives the object that was clicked, View v
        public void onClick(View v) {
            //Convert into a button object. Assuming view being received is a button. Need if statement to ensure
            Button button = (Button) v;
            button.setText("Thanks for pressing this button.");
        }
    }
}
