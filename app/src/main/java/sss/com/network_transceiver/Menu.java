/*
 ┏━━┓　 ┏━━┓　 ┏┳┓┏━━━┓┏━┓┏┓
┗━┓┃　 ┗━┓┃　 ┃┃┃┗━━┓┃┗━┛┃┃
　 ┏┛┗┓　 ┏┛┗┓┗┻┛┏━━┛┃┏━┓┃┃
　 ┃┏┓┃　 ┃┏┓┃　 　 　 ┗━━┓┃┗━┛┃┃
┏┛┃┃┃┏┛┃┃┃　 　 　 ┏━━┛┃┏━━┛┃
┗━┛┗┛┗━┛┗┛　 　 　 ┗━━━┛┗━━━┛
 */

package sss.com.network_transceiver;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

//import com.google.android.gms.gcm.GoogleCloudMessaging;

@SuppressLint("ClickableViewAccessibility")
public class Menu extends Activity{
	LocationManager mLocationManager;
    //private GoogleCloudMessaging gcm;
    /** コンテキスト */
    private Context context;
    private Context context2;//とりあえず作ってみました　http://individualmemo.blog104.fc2.com/blog-entry-41.html
	final Setting_class st= new Setting_class();
	final token_user_login task1 = new token_user_login();
	private post_music task;

	 //音関係
	 static final wav w = new wav();
	 //final static int SAMPLING_RATE = 44100;
	 private int bufSize;
	 private AudioRecord AR;
	 //private Button btnStart;
	 //private Button btnStop;
	 private short[] shortData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_menu);

        context = getApplicationContext();

        //gcm = GoogleCloudMessaging.getInstance(this);

        registerInBackground();
        //observer.startWatching();

        //発信ボタン
        ImageButton send_btn = (ImageButton)findViewById(R.id.send_button);//発信のボタンは場所決めないとどこ押しても反応しちゃう
        //Button send_btn = (Button)findViewById(R.id.send_button);

       send_btn.setOnTouchListener(new View.OnTouchListener() {//OnTouchListener使うと、バックグラウンドが更新されない？
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){

					Toast.makeText(Menu.this, "押してる間は録音中、話したら録音停止", Toast.LENGTH_LONG).show();
			        initAudioRecord();
			        w.createFile("/sdcard/data.wav");

					AR.startRecording();
					AR.read(shortData, 0, bufSize / 2);

				}
				else if(event.getAction() == MotionEvent.ACTION_UP){
					AR.stop();

					Toast.makeText(Menu.this, "録音停止", Toast.LENGTH_LONG).show();
					task = new post_music(context2);//AsyncTaskは使い捨てだから一回一回インスタンスを生成しないと行けないんだぞこらぁ
					task.execute(Setting_class.user_name,"/sdcard/data.wav");//POSTする場所
				}

				return true;

			}
        });

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


/*
        //ナビゲーションドロワー関係
        Button home_btn = (Button)findViewById(R.id.home);
        home_btn.setOnClickListener(new View.OnClickListener() {
            // ボタンがクリックされた時のハンドラ
            @Override
            public void onClick(View v){
            		//発信ボタンの処理
            		//if(st.gps_on_S==1)gps_stop();//暫定的に
            		//Intent intent = new Intent(Menu.this, Menu.class);
                    //startActivity(intent);
            		//task.execute(st.user_name,"/sdcard/data.wav");//POSTする場所
            	}
            });

        Button login_btn = (Button)findViewById(R.id.login);
        login_btn.setOnClickListener(new View.OnClickListener() {
            // ボタンがクリックされた時のハンドラ
            @Override
            public void onClick(View v){
            		//発信ボタンの処理
            		//if(st.gps_on_S==1)gps_stop();//暫定的に
            		Intent intent = new Intent(Menu.this, user_login.class);
                    startActivity(intent);
            	}
            });

        Button user_add_btn = (Button)findViewById(R.id.user_add);
        user_add_btn.setOnClickListener(new View.OnClickListener() {
            // ボタンがクリックされた時のハンドラ
            @Override
            public void onClick(View v){
            		//発信ボタンの処理
            		//if(st.gps_on_S==1)gps_stop();//暫定的に
            		Intent intent = new Intent(Menu.this, user_add.class);
                    startActivity(intent);
            	}
            });

        Button channel_select_btn = (Button)findViewById(R.id.channel_select);
        channel_select_btn.setOnClickListener(new View.OnClickListener() {
            // ボタンがクリックされた時のハンドラ
            @Override
            public void onClick(View v){
            		//発信ボタンの処理
            		//if(st.gps_on_S==1)gps_stop();//暫定的に
            		Intent intent = new Intent(Menu.this, channel_select.class);
                    startActivity(intent);
            	}
            });

        Button channel_add_btn = (Button)findViewById(R.id.channel_add);
        channel_add_btn.setOnClickListener(new View.OnClickListener() {
            // ボタンがクリックされた時のハンドラ
            @Override
            public void onClick(View v){
            		//発信ボタンの処理
            		//if(st.gps_on_S==1)gps_stop();//暫定的に
            		Intent intent = new Intent(Menu.this, channel_add.class);
                    startActivity(intent);
            	}
            });

        Button channel_set_btn = (Button)findViewById(R.id.channel_set);
        channel_set_btn.setOnClickListener(new View.OnClickListener() {
            // ボタンがクリックされた時のハンドラ
            @Override
            public void onClick(View v){
            		//発信ボタンの処理
            		//if(st.gps_on_S==1)gps_stop();//暫定的に
            		Intent intent = new Intent(Menu.this, channel_set.class);
                    startActivity(intent);
            	}
            });

        final AlertDialog.Builder gps_dir = new AlertDialog.Builder(this);
        Button geo_btn = (Button)findViewById(R.id.geo);
        geo_btn.setOnClickListener(new View.OnClickListener() {
            // ボタンがクリックされた時のハンドラ
            @Override
            public void onClick(View v){
            gps_dir.setTitle("現在地取得");
    		gps_dir.setMessage("現在地を取得してグループを検索しますか？");
    		gps_dir.setPositiveButton("キャンセル", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	// 処理を中断
                }
            });
    		gps_dir.setNegativeButton("OK", new DialogInterface.OnClickListener() {
    			@Override
                public void onClick(DialogInterface dialog, int which) {
    				//st.gps_on_S=gps_up();
                    //GPS
    				//場所の確認処理
    				//そこからナンバーを取得する（グループ名も)
                }
            });
    		//gps_dir.show();//ダイアログを表示
        }
    });


      //ナビゲーションドロワー関係
       */
    }//final

    public int login(){
		Intent intent = new Intent(Menu.this, user_login.class);
        startActivity(intent);
    	return 0;

    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.option_menu, (android.view.Menu) menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	final AlertDialog.Builder gps_dir = new AlertDialog.Builder(this);
    	switch (item.getItemId()) {
//        case R.id.home: {
//    		Intent intent = new Intent(Menu.this, Menu.class);
//            startActivity(intent);
//    		return true;
//    	}
//        case R.id.login: {
//    		Intent intent = new Intent(Menu.this, user_login.class);
//            startActivity(intent);
//    		return true;
//    	}
//        case R.id.user_add: {
//    		Intent intent = new Intent(Menu.this, user_add.class);
//            startActivity(intent);
//    		return true;
//    	}
        case R.id.channel_select: {
    		Intent intent = new Intent(Menu.this, channel_select.class);
            startActivity(intent);
    		return true;
    	}
        case R.id.channel_add: {
    		Intent intent = new Intent(Menu.this, channel_add.class);
            startActivity(intent);
    		return true;
    	}
//        case R.id.channel_set: {
//    		Intent intent = new Intent(Menu.this, channel_set.class);
//            startActivity(intent);
//    		return true;
//    	}
//        case R.id.geo: {
//        	gps_dir.setTitle("現在地取得");
//    		gps_dir.setMessage("現在地を取得してグループを検索しますか？");
//    		gps_dir.setPositiveButton("キャンセル", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                	// 処理を中断
//                }
//            });
//    		gps_dir.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//    			@Override
//                public void onClick(DialogInterface dialog, int which) {
//    				//st.gps_on_S=gps_up();
//                    //GPS
//    				//場所の確認処理
//    				//そこからナンバーを取得する（グループ名も)
//                }
//            });
//    		return true;
//    	}
        case R.id.version: {
        		Toast.makeText(this, "Version:1.0.0", Toast.LENGTH_LONG).show();
        		return true;
        }
        default: {
        	return super.onOptionsItemSelected(item);
        }
        }

        /*if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
        */

    }

	//@Override
	public void onLocationChanged(Location location) {
    /*    // 緯度の表示
		TextView tv_lat = (TextView) findViewById(R.id.Latitude);
        tv_lat.setText("緯度:"+location.getLatitude());
        st.latitude=location.getLatitude();
        Toast.makeText(Menu.this,Double.toString(st.latitude),Toast.LENGTH_LONG).show();

        // 経度の表示
        TextView tv_lng = (TextView) findViewById(R.id.Longitude);
        tv_lng.setText("経度:"+location.getLongitude());
        st.longitude=location.getLongitude();
*/
    }
	//-------------------------------------------------------------------------なんだろうこれ
//	@Override
//	public void onProviderDisabled(String provider) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
//
//	@Override
//	public void onProviderEnabled(String provider) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
//
//	@Override
//	public void onStatusChanged(String provider, int status, Bundle extras) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
	//-------------------------------------------------------------------------なんだろうこれ
//	public int gps_up(){
//        //これでどうだ！
//		 //LocationManagerを取得
//		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//
//    	//Criteriaオブジェクトを生成
//    	Criteria criteria = new Criteria();
//    	//Accuracyを指定(wifi)
//        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
//        //criteria.setAccuracy(Criteria.ACCURACY_FINE);//GPS(試してないからわからない)
//        // PowerRequirementを指定(低消費電力)-
//        //criteria.setPowerRequirement(Criteria.POWER_LOW);
//        //criteria.setPowerRequirement(Criteria.POWER_HIGH);//強い（確信）
//    	//ロケーションプロバイダの取得
//    	String provider = mLocationManager.getBestProvider(criteria, true);
//
//        //取得したロケーションプロバイダを表示
//    	//TextView tv_provider = (TextView) findViewById(R.id.Provider);
//        //tv_provider.setText("場所を取得する方法: "+provider);
//        mLocationManager.requestLocationUpdates(provider, 0, 0, this);
//        st.gps_on_S=1;
//        return 1;
//	}
//
//	protected void onStop() {//アプリを閉じたらGPS停止（電池なくなっちゃうぞ）
//	    super.onStop();
//	    gps_stop();
//	}
//	public void gps_stop(){
//		if(st.gps_on_S==1) mLocationManager.removeUpdates(this);st.gps_on_S=0;
//	}


	private void initAudioRecord(){

	  // AudioRecordオブジェクトを作成
	  bufSize = AudioRecord.getMinBufferSize(Setting_class.sampling_rate,
	              AudioFormat.CHANNEL_IN_MONO,
	              AudioFormat.ENCODING_PCM_16BIT);
	  AR = new AudioRecord(MediaRecorder.AudioSource.MIC,
			  Setting_class.sampling_rate,
	    AudioFormat.CHANNEL_IN_MONO,
	    AudioFormat.ENCODING_PCM_16BIT,
	    bufSize);

	  shortData = new short[bufSize / 2];

	  // コールバックを指定
	  AR.setRecordPositionUpdateListener(new AudioRecord.OnRecordPositionUpdateListener() {

	   // フレームごとの処理
	   @Override
	   public void onPeriodicNotification(AudioRecord recorder) {
	    // TODO Auto-generated method stub
	    AR.read(shortData, 0, bufSize / 2); // 読み込む
	    w.addBigEndianData(shortData); // ファイルに書き出す
	   }

	   @Override
	   public void onMarkerReached(AudioRecord recorder) {}
	  });

	  // コールバックが呼ばれる間隔を指定
	  AR.setPositionNotificationPeriod(bufSize / 2);

	 }

	 public void onPause(){
		  super.onPause();
		  if(AR!=null) AR.stop();
		  if(w!=null) w.close();
	 }

	 public void onDestroy(){
		 super.onDestroy();
		 System.out.println("onDestroy");
		 //try {
			 //gcm.unregister();
		 //} catch (IOException e) {
			 // TODO 自動生成された catch ブロック
		//	 e.printStackTrace();
		 //}
		 //this.unregisterInBackground();
		 Intent i = new Intent(this, GcmIntentService.class);
		 stopService(i);
		 if(AR!=null) AR.release();
	 }

	 public void onStop(){
		 super.onStop();
		 System.out.println("onStop");
	 }

	 private void registerInBackground() {
	        new AsyncTask<Void, Void, String>() {
	            @Override
	            protected String doInBackground(Void... params) {
	                String msg = "";
	                //try {
	                    //if (gcm == null) {
	                    //    gcm = GoogleCloudMessaging.getInstance(context);
	                    //}
	                    //String regid = gcm.register("563643946655");
	                    //msg = "Device registered, registration ID=" + regid;
	                    //Setting_class.token=regid;

	                    //login();
	                //} catch (IOException ex) {
	                //    msg = "Error :" + ex.getMessage();
	                //}
	                //Log.d("registerInBackground","msg="+msg);
	                //st.token=msg;
	                return msg;
	            }

	            @Override
	            protected void onPostExecute(String msg) {
	            	task1.execute("register",Setting_class.token,Setting_class.user_name);
 	            }
	        }.execute(null, null, null);
	    }
}

