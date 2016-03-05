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



public class post_user_login extends AsyncTask<String,Integer,Integer>{

	HttpResponse response ;
	final String TAG="post_user_login";
	String handan;

	//private Activity act;
	private Callback callback;
	private String requestCode;
	
	private static final String url="http://vps.suzuyoshi-net.com/tomcat/transceiver-server/login.jsp";
	final Setting_class st= new Setting_class();
	
	//HttpClient httpcliant = new DefaultHttpClient();
	//HttpPost httppost = new HttpPost(url);
	
	List<NameValuePair> nameValuePair;
	
	HttpClient http_Client = new DefaultHttpClient();
	ByteArrayOutputStream byteArrayOutputStream;
	String res =null;
	
	public post_user_login(final Callback callback, final String requestCode){
		//this.act = callback;
		this.callback = callback;
		this.requestCode = requestCode;
	}

	
	protected void onPreExecute(){
		Log.d(TAG,"onPreExecute");
	}
	
	
	@Override
	protected Integer doInBackground(String... contents){
		//Log.d(TAG,"doInBackground-"+contents[0]);
		
		HttpPost post = new HttpPost(url);
		
		ArrayList <NameValuePair> params = new ArrayList <NameValuePair>();
		params.add(new BasicNameValuePair("user",contents[0]));
		params.add(new BasicNameValuePair("pass",contents[1]));
		handan=contents[1];
		HttpResponse response=null;
		
		try{
			post.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
			response=http_Client.execute(post);
			byteArrayOutputStream = new ByteArrayOutputStream();
			response.getEntity().writeTo(byteArrayOutputStream);
		}catch (IOException e){
			e.printStackTrace();
			return -1;
		}
		//return 0;
		return Integer.valueOf(response.getStatusLine().getStatusCode());
	}
	
	@Override
	protected void onPostExecute(Integer result){
		//System.out.println(response.getStatusLine().getStatusCode());
		//if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
			if(result==-1 && byteArrayOutputStream==null){
				requestCode="server_problem";
				callback.callback(Callback.ERROR, requestCode, null);
				return;
			}
			System.out.print("result:" + byteArrayOutputStream.toString());
			handan=byteArrayOutputStream.toString();
			handan=handan.replaceAll("\n", "");
			if(handan.equals("1") && !handan.equals(null)){
				Setting_class.token=handan;
				Setting_class.token_h=1;
				requestCode="ok";
				callback.callback(Callback.SUCCESS, requestCode, null); 
				//
			}else{
				requestCode="ng";
				callback.callback(Callback.ERROR, requestCode, null);
			}
		//}else{
			//Toast.makeText(act,"error:\n" + response.getStatusLine(), Toast.LENGTH_LONG).show();
			
		//}
		}
	public void onCancel(DialogInterface dialog){
		this.cancel(true);
	}
}