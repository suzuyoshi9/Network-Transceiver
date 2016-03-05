package sss.com.network_transceiver;

import java.io.File;
import java.io.IOException;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class GcmIntentService extends IntentService{
	private FileDownload fd;
	private Handler handler;
	private static final String TAG = "GcmIntentService";
	
	public GcmIntentService(){
		super("GcmIntentService");
		handler = new Handler();
	}
	
	@Override
	protected void onHandleIntent(Intent intent){
		//Bundle extras = intent.getExtras();
		//oogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		//String messageType = gcm.getMessageType(intent);
		String data=intent.getStringExtra("msg");
		Log.d(TAG,data);
//		if (!extras.isEmpty()) {
//			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
//				Log.d(TAG,"messageType: " + messageType + ",body:" + extras.toString());
//			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
//				Log.d(TAG,"messageType: " + messageType + ",body:" + extras.toString());
//			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
//				Log.d(TAG,"messageType: " + messageType + ",body:" + extras.toString());
//			}
//		}
		String[] str1Ary = data.split(",");
		String uri = "http://vps.suzuyoshi-net.com/tomcat/transceiver-server/files/" + str1Ary[1];
		fd = new FileDownload(uri,new File("/sdcard/test.wav"));
		fd.download();
		try {
			fd.close();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		PlayMusic pm = new PlayMusic();
		pm.initialize();
		this.toast(str1Ary[0]+" speaking");
		pm.run();
		File file = new File("/sdcard/test.wav");
		file.delete();
	}
	
	private void toast(final String msg) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(GcmIntentService.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }
	
}
