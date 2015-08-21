package logicshades.com.dailyquotes.quotes.utilities;

import android.graphics.Bitmap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by rijogeorge on 8/20/15.
 */
public class Utility {
    public static final String DAILYIMAGEFILEPATH="DailyQuote/DailyQuoteImage";
    public static final String DAILYIMAGEFILENAME="quoteTodayImage.png";
    public static boolean isQuoteNeedUpdate(){
        return true;
    }
    public static void storeBitmap(Bitmap bitmap,String directory,String filename) throws IOException {
        File dir=new File(directory);
        if(!dir.exists())
            dir.mkdirs();
        File bitmapFile=new File(dir,filename);
        FileOutputStream fOut=new FileOutputStream(bitmapFile);
        bitmap.compress(Bitmap.CompressFormat.PNG,100, fOut);
        fOut.flush();
        fOut.close();
    }
}
