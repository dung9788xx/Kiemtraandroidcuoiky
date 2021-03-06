package com.ngaoda.php;

import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaskConnect extends AsyncTask<Void,Void,String> {
    private Map<String,String> map=new HashMap<>(  );
    AsyncResponse output;

    String url;
    public TaskConnect(AsyncResponse output, String url) {
        this.output=output;
        this.url=url;


    }

    @Override
    protected String doInBackground(Void... voids) {
        String error;
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody formBody = makeBuilderFromMap( map )
                .build();

        Request  request= new Request.Builder()
                    .url( url )
                    .post( formBody )
                    .build();



        try {
            Response response = client.newCall(request).execute();

            return response.body().string();


        }catch (Exception e){
            e.printStackTrace();
            error=e.toString();
            Log.d( "loi",error );
        }
        return error;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute( s );
        output.whenfinish( s );

    }
    public static FormBody.Builder makeBuilderFromMap( Map<String, String> map) {
        FormBody.Builder  formBody = new FormBody.Builder();
        if(!map.isEmpty())
        for (Map.Entry<String, String> entrySet : map.entrySet()) {
            formBody.add(entrySet.getKey(), entrySet.getValue());
        }
        return formBody;
    }
    public void setMap(Map<String,String> map){
        this.map=map;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
