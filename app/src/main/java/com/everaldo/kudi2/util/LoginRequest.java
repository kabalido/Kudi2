package com.everaldo.kudi2.util;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class LoginRequest {
    private ProgressDialog progress;
    //private static final String link = "https://jsonplaceholder.typicode.com/photos";
    private static final String link = "http://172.16.2.107/kudi/login.php";

    //https://picsum.photos/v2/list?limit=200
    private LoginListener listener = null;

    private String username;
    private String password;

    public LoginRequest(Context context){
        progress = new ProgressDialog(context);
        progress.setCancelable(false);
        progress.setTitle("Processing");
        progress.setMessage("Please, wait...");
    }

    public ProgressDialog getProgressDialog(){
        return progress;
    }
    public void performLogin(LoginListener listener, String username, String password) {
        this.listener = listener;
        this.username = username;
        this.password = password;
        progress.show();
        new StoreDataAsync().execute();
    }

    public class StoreDataAsync extends AsyncTask<Void, Void ,ResponseResult>{

        @Override
        protected void onPostExecute(ResponseResult loginResult) {
            getProgressDialog().dismiss();
            listener.loginFinish( loginResult);
            super.onPostExecute(loginResult);
        }

        protected  ResponseResult doInBackground(Void... params) {

            ResponseResult loginResult = null;
            User user = null;
            try {
                URL url = new URL(LoginRequest.link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();


                conn.setRequestMethod("POST");
                conn.setRequestProperty("User-Agent", "FireBird");
                conn.setRequestProperty("Accept-Language", "UTF-8");

                StringBuilder tokenUri=new StringBuilder("username=");
                tokenUri.append(URLEncoder.encode(username,"UTF-8"));
                tokenUri.append("&password=");
                tokenUri.append(URLEncoder.encode(password,"UTF-8"));

                conn.setDoOutput(true);

                conn.connect();

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());
                outputStreamWriter.write(tokenUri.toString());
                outputStreamWriter.flush();
                outputStreamWriter.close();


                String line;
                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
                    BufferedReader buff = new BufferedReader(in);
                    StringBuilder builder = new StringBuilder();
                    do {
                        line = buff.readLine();
                        builder.append(line);
                    } while (line != null);
                    buff.close();
                    JSONObject jsonObject = new JSONObject(builder.toString());
                    int status;
                    boolean isLogged;
                    String errMessage;

                    if (jsonObject != null ){

                        status = jsonObject.getInt("status");
                        isLogged = jsonObject.getBoolean("logged");
                        errMessage = jsonObject.getString("errmessage");

                        if (isLogged)
                            user  = new User(jsonObject.getJSONObject("user").getString("phone"), jsonObject.getJSONObject("user").getString("email"));
                        loginResult = new ResponseResult(status, isLogged, errMessage, user);

                    }
                }
                conn.disconnect();
            } catch (IOException e) {
                Log.e("EVERALDO", e.getMessage());
            }
            catch (JSONException e) {
                Log.e("EVERALDO", e.getMessage());
            }

            return loginResult;
        }
    }
}