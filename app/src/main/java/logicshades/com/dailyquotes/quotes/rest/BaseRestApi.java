package logicshades.com.dailyquotes.quotes.rest;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by rijogeorge on 8/20/15.
 */
public class BaseRestApi {
    public static final String baseUrl="http://logicshades.com/dayilyQuote/rest/";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public void get(String url, RequestParams params,AsyncHttpResponseHandler asyncHttpResponseHandler){
        Log.d("urlString", url);
        client.get(url, params, asyncHttpResponseHandler);
    }
    public String getabsoluteUrl(String endUrl){
        return baseUrl+endUrl;
    }
}
