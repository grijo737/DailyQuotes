package logicshades.com.dailyquotes.quotes.rest;

import org.apache.http.Header;

/**
 * Created by rijogeorge on 8/20/15.
 */
public abstract class RestResponseHandler<T>  {

    public abstract void onFailure(int statusCode, Header[] headers, String error, Throwable t);

    public abstract void onSuccess(int statusCode, Header[] headers, T responseObject);
}
