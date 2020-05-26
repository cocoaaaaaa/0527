package com.example.cafemoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CafelistActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "203.237.179.120:7003";
    private static String TAG = "phpcafelist";

    private EditText mEditTextName;
    private EditText mEditTextAddress;
    private EditText mEditTextBusinesshour;
    private EditText mEditTextEmptySeats;
    private EditText mEditTextInstargram;
    private EditText mEditTextPhone;
    private EditText mEditTextInform;
    private TextView mTextViewResult;
    private ArrayList<ListData> mArrayList;
    private ListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText mEditTextSearchKeyword;
    private String mJsonString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafelist);

        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView)findViewById(R.id.listView_main_list);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), 1));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());



        mArrayList = new ArrayList<>();

        mAdapter = new ListAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);




        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/getjson_info.php", "");
    }


    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(CafelistActivity.this,
                    "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){

                mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){

        String TAG_JSON="user";
        String TAG_NAME = "name";
        String TAG_address ="address";
  /*     String TAG_businessHour ="businessHour";
        String TAG_emptySeats ="emptySeats";
        String TAG_instargram ="instargram";
        String TAG_phone ="phone";
        String TAG_inform ="inform";*/



        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);


                String name = item.getString(TAG_NAME);
                String address = item.getString(TAG_address);
            /*    String businessHour = item.getString(TAG_businessHour);
                String emptySeats = item.getString(TAG_emptySeats);
                String instargram = item.getString(TAG_instargram);
                String phone = item.getString(TAG_phone);
                String inform = item.getString(TAG_inform);
*/




                ListData ListData = new ListData();

                ListData.setCafe_name(name);
               ListData.setCafe_address(address);
        /*        InformData.setCafe_businessHour(businessHour);
                InformData.setCafe_emptySeats(emptySeats);
                InformData.setCafe_instargram(instargram);
                InformData.setCafe_phone(phone);
                InformData.setCafe_inform(inform);
*/



                mArrayList.add(ListData);
                mAdapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }



}

