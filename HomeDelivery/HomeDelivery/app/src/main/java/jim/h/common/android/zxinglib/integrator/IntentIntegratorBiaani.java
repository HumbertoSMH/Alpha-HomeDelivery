package jim.h.common.android.zxinglib.integrator;

import android.app.Activity;
import android.content.Intent;

import algroup.com.mx.homedelivery.utils.LogUtil;
import jim.h.common.android.zxinglib.CaptureActivity;

/**
 * Created by devmac03 on 27/04/15.
 */
//public class IntentIntegratorBiaani extends IntentIntegrator{
public class IntentIntegratorBiaani {
    private static final String  CLASSNAME = IntentIntegratorBiaani.class.getSimpleName();

    public static void initiateScan(Activity activity, int layoutResId, int viewFinderViewResId, int previewViewResId, boolean useFrontLight)
    {
        Intent intent = new Intent(activity,CaptureActivity.class);
        intent.putExtra("layoutResId", layoutResId);
        intent.putExtra("viewFinderViewResId", viewFinderViewResId);
        intent.putExtra("previewViewResId", previewViewResId);
        intent.putExtra("useFrontLight", useFrontLight);
        activity.startActivityForResult(intent, 0x0000c0de);
    }

    public static IntentResult parseActivityResult(int requestCode, int resultCode, Intent intent)
    {
        LogUtil.printLog(CLASSNAME, "parseActivityResult: requestCode:" + requestCode + ", resultCode:" + resultCode);
        if(requestCode == 0x0000c0de)
        {
            if(resultCode == -1)
            {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String formatName = intent.getStringExtra("SCAN_RESULT_FORMAT");
                return new IntentResult(contents, formatName);
            }
            else
            {
                return new IntentResult(null, null);
            }
        }
        else
        {
            return null;
        }
    }

    public static final int REQUEST_CODE = 0x0000c0de;
}
