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


public class RegisterRequest {
    private ProgressDialog progress;
    private static final String link = "http://172.16.2.107/kudi/register.php";
    private RegistrationListener listener = null;
    private User user;

    public RegisterRequest(Context context){
        progress = new ProgressDialog(context);
        progress.setCancelable(false);
        progress.setTitle("Processing");
        progress.setMessage("Please, wait...");
    }

    public ProgressDialog getProgressDialog(){
        return progress;
    }
    public void performRegistration(RegistrationListener listener, User user) {
        this.listener = listener;
        this.user = user;
        progress.show();
        new StoreDataAsync().execute();
    }

    public class StoreDataAsync extends AsyncTask<Void, Void ,ResponseResult>{

        @Override
        protected void onPostExecute(ResponseResult responseResult) {
            getProgressDialog().dismiss();
            listener.registrationDone( responseResult);
            super.onPostExecute(responseResult);
        }

        protected  ResponseResult doInBackground(Void... params) {

            ResponseResult loginResult = null;
            User userDb = null;
            try {
                URL url = new URL(RegisterRequest.link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();


                conn.setRequestMethod("POST");
                conn.setRequestProperty("User-Agent", "FireBird");
                conn.setRequestProperty("Accept-Language", "UTF-8");

                StringBuilder tokenUri=new StringBuilder("username=");
                tokenUri.append(URLEncoder.encode(user.getPhone(),"UTF-8"));
                tokenUri.append("&password=");
                tokenUri.append(URLEncoder.encode(user.getPassword(),"UTF-8"));
                tokenUri.append("&email=");
                tokenUri.append(URLEncoder.encode(user.getEmail(),"UTF-8"));
                tokenUri.append("&name=");
                tokenUri.append(URLEncoder.encode(user.getName(),"UTF-8"));
                tokenUri.append("&address=");
                tokenUri.append(URLEncoder.encode(user.getAddress(), "UTF-8"));
                tokenUri.append("&id_type=");
                tokenUri.append(URLEncoder.encode(user.getIdCardType(),"UTF-8"));
                tokenUri.append("&id_card=");
                tokenUri.append(URLEncoder.encode(user.getIdCard(),"UTF-8"));
                tokenUri.append("&id_validity=");
                tokenUri.append(URLEncoder.encode(user.getIdCardValidity(),"UTF-8"));

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
                    boolean isInserted;
                    String errMessage;

                    if (jsonObject != null ){

                        status = jsonObject.getInt("status");
                        isInserted = jsonObject.getBoolean("inserted");
                        errMessage = jsonObject.getString("errmessage");

                        if (isInserted)
                            userDb  = new User(jsonObject.getJSONObject("user").getString("phone"), jsonObject.getJSONObject("user").getString("email"));
                        loginResult = new ResponseResult(status, isInserted, errMessage, userDb);

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