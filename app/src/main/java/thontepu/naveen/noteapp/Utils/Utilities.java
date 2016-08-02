package thontepu.naveen.noteapp.Utils;

import android.util.Log;

/**
 * Created by mac on 8/2/16.
 */
public class Utilities {
    public void printLog(String msg) {
        String TAG = "thontepu.naveen.noteapp";
        printLog(TAG, msg);
    }

    public void printLog(String tag, String s) {
        // TODO: 26-05-2016 Production change to false
        if (true && s!=null) {
            Log.i(tag, s);
        }
    }
}
