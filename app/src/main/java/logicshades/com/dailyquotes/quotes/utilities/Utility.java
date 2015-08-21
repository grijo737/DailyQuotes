package logicshades.com.dailyquotes.quotes.utilities;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import logicshades.com.dailyquotes.quotes.activities.DailyQuoteActivity;

/**
 * Created by rijogeorge on 8/20/15.
 */
public class Utility {
    public static final String DAILYIMAGEFILEPATH="DailyQuote/DailyQuoteImage";
    public static final String DAILYIMAGEFILENAME="quoteTodayImage.png";
    public static final String DAILYQUOTEMESSAGE="quoteTodayImage.png";
    public static boolean isQuoteNeedUpdate(SharedPreferences sharedPref){
        long validUnixtime=sharedPref.getLong(DailyQuoteActivity.QUOTEIMAGEUPDATETIME,-1);
        if(validUnixtime==-1)
            return true;
        Date date = new Date(validUnixtime*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = sdf.format(date);
        Date validupto=null;
        try {
            validupto=sdf.parse(strDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        long currentTime = System.currentTimeMillis() / 1000L;
        date = new Date(currentTime*1000L);
        strDate = sdf.format(date);
        Date today=null;
        try {
            today=sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(today!=null && validupto!=null){
            if(today.after(validupto))
                return true;
            else
                return false;
        }

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
    public static Bitmap getStoredBitmap(String directory,String fileName) throws FileNotFoundException {
        File bitmapFile=new File(directory,fileName);
        Bitmap bitmapImage = BitmapFactory.decodeStream(new FileInputStream(bitmapFile));
        return bitmapImage;
    }
}
