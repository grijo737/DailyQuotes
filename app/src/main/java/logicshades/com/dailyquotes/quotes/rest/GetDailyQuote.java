package logicshades.com.dailyquotes.quotes.rest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import logicshades.com.dailyquotes.quotes.domain.DailyQuote;

/**
 * Created by rijogeorge on 8/20/15.
 */
public class GetDailyQuote extends BaseRestApi{
    private RestResponseHandler asyncHttpResponseHandler;
    String endUrl;
    public GetDailyQuote(String endUrl,RestResponseHandler asyncHttpResponseHandler){
        this.endUrl=endUrl;
        this.asyncHttpResponseHandler=asyncHttpResponseHandler;
    }
    public void execute(){
        get(getabsoluteUrl(endUrl),null,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json=new String(responseBody);
                Log.d("json", json);
                DailyQuote dq=fromJson(json);
                asyncHttpResponseHandler.onSuccess(0,null,dq);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public DailyQuote fromJson(String json){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson=gsonBuilder.create();
        DailyQuote model=gson.fromJson(json,new TypeToken<DailyQuote>(){

        }.getType());
        return model;
    }
}
