package hit.com.cordova.bitmap.converter;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import android.util.Log;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class BitmapConverter extends CordovaPlugin {

    private static final String LOG_TAG = "BitmapConverter";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("imageBase64")) {
            String base64 = args.getString(0);
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    convertImageBase64(callbackContext, base64);
                }
            });
            return true;
        } else if (action.equals("imagePath")) {
            String path = args.getString(0);
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    convertImage(callbackContext, path);
                }
            });
            return true;
        }
        return false;
    }

    private void convertImage(CallbackContext callbackContext, String path) {
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            callbackContext.success(toBase64(Utils.decodeBitmap(bitmap)));
            bitmap.recycle();
        } catch (Exception e) {
            e.printStackTrace();
            callbackContext.error(e.getMessage());
        }
    }

    private void convertImageBase64(CallbackContext callbackContext, String base64) {
        try {
            final String pureBase64Encoded = base64.substring(base64.indexOf(",") + 1);
            final byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            callbackContext.success(toBase64(Utils.decodeBitmap(bitmap)));
            bitmap.recycle();
        } catch (Exception e) {
            e.printStackTrace();
            callbackContext.error(e.getMessage());
        }
    }

    private String toBase64(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.DEFAULT).replaceAll("\n", "");
    }

}
