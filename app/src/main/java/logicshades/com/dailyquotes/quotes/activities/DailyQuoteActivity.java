package logicshades.com.dailyquotes.quotes.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.apache.http.Header;

import logicshades.com.dailyquotes.R;
import logicshades.com.dailyquotes.quotes.domain.DailyQuote;
import logicshades.com.dailyquotes.quotes.rest.GetDailyQuote;
import logicshades.com.dailyquotes.quotes.rest.RestResponseHandler;
import logicshades.com.dailyquotes.quotes.utilities.Utility;


public class DailyQuoteActivity extends AppCompatActivity {
    private DisplayImageOptions mProfileImageOptions = new DisplayImageOptions.Builder()
            .cacheOnDisk(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .resetViewBeforeLoading(true)
            .build();
    ImageView dailyImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_quote);
        dailyImage=(ImageView)findViewById(R.id.dailyImage);
        boolean fetchQuote= Utility.isQuoteNeedUpdate();
        if(fetchQuote)
            getQuoteFromServer();
    }

    private void getQuoteFromServer(){
        new GetDailyQuote("quoteToday", new RestResponseHandler<DailyQuote>() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String error, Throwable t) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, DailyQuote responseObject) {
                Log.d("imageurl", responseObject.getImageUrl());
                GetDailyImage(responseObject.getImageUrl());
            }
        }).execute();
    }

    private void GetDailyImage(String imageUrl) {
        ImageLoader.getInstance().loadImage(imageUrl, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
               dailyImage.setImageBitmap(loadedImage);
            }
        });
    }
}
