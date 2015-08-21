package logicshades.com.dailyquotes.quotes.activities;

import android.content.Context;
import android.content.SharedPreferences;
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

import java.io.FileNotFoundException;
import java.io.IOException;

import logicshades.com.dailyquotes.R;
import logicshades.com.dailyquotes.quotes.domain.DailyQuote;
import logicshades.com.dailyquotes.quotes.rest.GetDailyQuote;
import logicshades.com.dailyquotes.quotes.rest.RestResponseHandler;
import logicshades.com.dailyquotes.quotes.utilities.Utility;


public class DailyQuoteActivity extends AppCompatActivity {
    public static final String QUOTEIMAGEUPDATETIME="com.logicshades.quoteImageUpdateTime";
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
        SharedPreferences sharedPref = DailyQuoteActivity.this.getPreferences(Context.MODE_PRIVATE);
        boolean fetchQuote= Utility.isQuoteNeedUpdate(sharedPref);
        if(fetchQuote)
            getQuoteFromServer();
        else
            getQuoteFromStorage();
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
                try {
                    Utility.storeBitmap(loadedImage, getApplicationContext().getFilesDir() + Utility.DAILYIMAGEFILEPATH, Utility.DAILYIMAGEFILENAME);
                    storeQuoteImageUpdateTime();

                } catch (IOException e) {
                    Log.d(Utility.DAILYQUOTEMESSAGE, "error in storing bitmap to internal storage");
                }
            }
        });
    }

    public void getQuoteFromStorage(){
        try {
            Bitmap dailyQuoteImage=Utility.getStoredBitmap(getApplicationContext().getFilesDir()+Utility.DAILYIMAGEFILEPATH,Utility.DAILYIMAGEFILENAME);
            dailyImage.setImageBitmap(dailyQuoteImage);
        } catch (FileNotFoundException e) {
            Log.d(Utility.DAILYQUOTEMESSAGE, "error in loading bitmap from internal storage");
            getQuoteFromServer();
        }
    }
    private void storeQuoteImageUpdateTime(){
        long updateTime = System.currentTimeMillis() / 1000L;
        SharedPreferences sharedPref = DailyQuoteActivity.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(QUOTEIMAGEUPDATETIME,updateTime);
        editor.commit();
    }
}
