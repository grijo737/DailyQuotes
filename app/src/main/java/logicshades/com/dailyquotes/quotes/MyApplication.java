package logicshades.com.dailyquotes.quotes;

import android.app.Application;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import logicshades.com.dailyquotes.quotes.utilities.Configuration;

/**
 * Created by rijogeorge on 8/20/15.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Configuration.imageLoaderConf(getApplicationContext());
    }

    }
