package org.me.gcu.mbaye_ibrahm_s1903674;
//  Ibrahim Mbaye S1903674

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private TextView rawDataDisplay;
    private Button startButton;
    private String result;
    private String url1="";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private List<EarthQuakeInfo> infoEarthquakes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up the raw links to the graphical components
        //rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);
        //startButton = findViewById(R.id.startButton);
        //startButton.setOnClickListener(this::onClick);
        fetchData();

    }

    //public void onClick(View aview) {}

    public void fetchData(){
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute();
    }

    //background worker used instead of this method
    public void startProgress()
    {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    } //

    //new implementation of Async task with onPreExecute,doInBackground and onPostExecute methods
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "Fetching Data",
                    "Waiting..Make sure internet is available");
        }
        @Override
        protected String doInBackground(String... params) {
            URL aurl;
            URLConnection yc;
            EarthQuakeInfo earthQuakeInfo = null;

            try {
                aurl = new URL(urlSource);
                yc = aurl.openConnection();
                earthQuakeInfo = parseXML(yc.getInputStream());
                infoEarthquakes.add(earthQuakeInfo);
            }  catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            //rawDataDisplay.setText(result);
            Intent intent = new Intent(getBaseContext(), EarthQuakeActivity.class);
            intent.putExtra("listEarthQuakeData", (Serializable) infoEarthquakes);
            startActivity(intent);
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(String... text) {
            //updating ProgessDialog
        }
    }



    public EarthQuakeInfo parseXML(InputStream is) {
        Log.e("MyTag","start parse");

        String text = "";
        EarthQuakeItem item = null;
        EarthQuakeInfo earthquakeInfo = null;
        List<EarthQuakeItem> items = new ArrayList<>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            String parsing = "earthquake";

            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("channel")) {
                            earthquakeInfo = new EarthQuakeInfo();
                        }
                        else if (tagname.equalsIgnoreCase("item")) {
                            item = new EarthQuakeItem();
                            parsing = "item";
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("item")) {
                            items.add(item);
                        }else if (tagname.equalsIgnoreCase("title")) {
                            if (parsing == "earthquake") {
                                earthquakeInfo.setTitle(text);
                            } else {
                                item.setTitle(text);
                            }
                        }  else if (tagname.equalsIgnoreCase("description")) {
                            if (parsing == "earthquake") {
                                earthquakeInfo.setDescription(text);
                            } else {
                                item.setDescription(text);

                            }
                        }
                        else if (tagname.equalsIgnoreCase("lat")) {
                                 item.setLatitude(text);
                                 //System.out.println("MainActing "+text);
                        }
                        else if (tagname.equalsIgnoreCase("long")) {
                                item.setLongitude(text);
                        }

                        else if (tagname.equalsIgnoreCase("pubDate")) {
                            item.setPublishDate(text);
                        }

                        else if (tagname.equalsIgnoreCase("channel")) {
                            earthquakeInfo.setItems(items);
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}

        Log.e("MyTag","end parse");

        return earthquakeInfo;
    }

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }

        @Override
        public void run()
        {

            URL aurl;
            URLConnection yc;
            EarthQuakeInfo earthQuakeInfo = null;


            Log.e("MyTag","in run");

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                earthQuakeInfo = parseXML(yc.getInputStream());
                infoEarthquakes.add(earthQuakeInfo);
                result = result + " -1- " + earthQuakeInfo.toString();

                Log.e("MyTag","after ready");


            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception");
            }

            // Now update the TextView to display raw XML data

            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    rawDataDisplay.setText(result);
                    Intent intent = new Intent(getBaseContext(), EarthQuakeActivity.class);
                    intent.putExtra("listEarthQuakeData", (Serializable) infoEarthquakes);
                    startActivity(intent);

                }
            });
        }

    }


}
