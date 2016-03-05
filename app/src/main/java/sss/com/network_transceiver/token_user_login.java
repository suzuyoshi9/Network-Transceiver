package sss.com.network_transceiver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

public class token_user_login extends AsyncTask<String,Integer,Integer>{
	HttpResponse response ;
	final String TAG="post_user_login";
	String handan;
	
	private static final String url="http://vps.suzuyoshi-net.com/tomcat/transceiver-server/gcmservlet";
	final Setting_class st= new Setting_class();
	
	//HttpClient httpcliant = new DefaultHttpClient();
	//HttpPost httppost = new HttpPost(url);
	
	List<NameValuePair> nameValuePair;
	
	HttpClient http_Client = new DefaultHttpClient();
	ByteArrayOutputStream byteArrayOutputStream;
	String res =null;

	
	protected void onPreExecute(){
		Log.d(TAG,"onPreExecute");
	}


	@Override
	protected Integer doInBackground(String... contents){
		//Log.d(TAG,"doInBackground-"+contents[0]);
		
		HttpPost post = new HttpPost(url);
		
		ArrayList <NameValuePair> params = new ArrayList <NameValuePair>();
		params.add(new BasicNameValuePair("action",contents[0]));
		params.add(new BasicNameValuePair("regId",contents[1]));
		params.add(new BasicNameValuePair("userId",contents[2]));
		//handan=contents[1];
		HttpResponse response=null;
		
		try{
			post.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
			response=http_Client.execute(post);
			byteArrayOutputStream = new ByteArrayOutputStream();
			response.getEntity().writeTo(byteArrayOutputStream);
			
			
		}catch (IOException e){
			e.printStackTrace();
		}
		//return 0;
		return Integer.valueOf(response.getStatusLine().getStatusCode());
	}
	
	@Override
	protected void onPostExecute(Integer result){
		//System.out.println(response.getStatusLine().getStatusCode());
		//if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
			//Toast.makeText(act,"result:\n" + byteArrayOutputStream.toString(), Toast.LENGTH_LONG).show();
			System.out.println("result:\n" + byteArrayOutputStream.toString());
			handan=byteArrayOutputStream.toString();
			if(handan.equals("register ok")){
				Log.d("token_user_login","登録OK");
			}else{
				//Toast.makeText(act,"ユーザ名がパスワードが間違ってますってサーバが言ってるよ！\n今どんな気持ち！？！？！", Toast.LENGTH_LONG).show();
			}
		}
	public void onCancel(DialogInterface dialog){
		this.cancel(true);
	}
}

