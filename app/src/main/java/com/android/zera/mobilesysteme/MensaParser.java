package com.android.zera.mobilesysteme;

import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

class MensaParser extends AsyncTask<String, Integer, String> {

    private Context context;
    private PowerManager.WakeLock mWakeLock;

    public MensaParser(Context context ) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... sUrl) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        /*
        try {
            URL url = new URL(sUrl[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            input = connection.getInputStream();

            byte[] data = new byte[65536];
            int count;
            String string = "";
            while ((count = input.read(data)) != -1) {
                if (isCancelled()) {
                    input.close();
                    return null;
                }

                String temp = new String(data);
                string += temp;
            }

            System.out.println(string);

        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        return null; */
        String urlString = "http://app2.hs-harz.de/app/filesV2/content/m/FoodContent.xml";
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection)url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            conn.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        conn.setDoInput(true);

        try {
            conn.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //read the result from the server
        BufferedReader rdr  = null;
        try {
            rdr = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sbr = new StringBuilder();
        ArrayList<String> responseList = new ArrayList<>();
        String line;
        int count = 0;
        try {
            while ((line = rdr.readLine()) != null) {
                responseList.add(line);
                System.out.println(count ++ + " :     "  +line);

            }
        }
        catch (Exception e){

        }

        for (int i = 0;i<responseList.size();i++){
            String rpLine = responseList.get(i);
            //System.out.println(responseList.get(i));
            sbr.append(rpLine + '\n');
        }
        Log.v(sbr.toString(), "Stream");
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
        mWakeLock.acquire();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
    }

    @Override
    protected void onPostExecute(String result) {
        mWakeLock.release();
        if (result != null)
            Toast.makeText(context, "Fehler" + result, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "Fertig", Toast.LENGTH_SHORT).show();
    }

}