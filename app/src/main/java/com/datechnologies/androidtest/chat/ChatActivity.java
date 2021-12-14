package com.datechnologies.androidtest.chat;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;
import com.datechnologies.androidtest.api.ChatLogMessageModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Screen that displays a list of chats from a chat log.
 */
public class ChatActivity extends AppCompatActivity {
    String url ="http://dev.rapptrlabs.com/Tests/scripts/chat_log.php";
    String chatContent ="";

    List<ChatLogMessageModel> tempList = new ArrayList<>();

    //==============================================================================================
    // Class Properties
    //==============================================================================================

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, ChatActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        new jsonTask().execute();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        chatAdapter = new ChatAdapter();

        recyclerView.setAdapter(chatAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false));





        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.

        // TODO: Retrieve the chat data from http://dev.rapptrlabs.com/Tests/scripts/chat_log.php
        // TODO: Parse this chat data from JSON into ChatLogMessageModel and display it.
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    class jsonTask extends AsyncTask<Void,Void,String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL chatUrl = new URL(url);
                HttpURLConnection urlConnection =(HttpURLConnection)chatUrl.openConnection();
                InputStreamReader streamReader= new InputStreamReader(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder builder = new StringBuilder();
                String line;
                while((line = reader.readLine())!=null){
                    builder.append(line);
                }
                chatContent =builder.toString();
                //Log.e("Json",builder.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            return chatContent;
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            try {

                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                System.out.println("jsonObject #"+jsonArray.getJSONObject(1).toString());
                for(int i = 0; i<jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    System.out.println("jsonObject #"+i+" "+object.toString());
                    ChatLogMessageModel chatLogMessageModel = new ChatLogMessageModel();
                    chatLogMessageModel.userId = object.getInt("user_id");
                    chatLogMessageModel.avatarUrl = object.getString("avatar_url");
                    chatLogMessageModel.username = object.getString("name");
                    chatLogMessageModel.message = object.getString("message");


                    tempList.add(chatLogMessageModel);

                    chatAdapter.setChatLogMessageModelList(tempList);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
