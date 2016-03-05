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



public class get_channel extends AsyncTask<String,Integer,Integer>{

	HttpResponse response ;
	final String TAG="get_channel";
	String handan;

	//private Activity act;
	private Callback callback;
	private String requestCode;
	
	private static final String url="http://vps.suzuyoshi-net.com/tomcat/transceiver-server/getGroup.jsp";
	
	//HttpClient httpcliant = new DefaultHttpClient();
	//HttpPost httppost = new HttpPost(url);
	
	//List<NameValuePair> nameValuePair;
	
	
	HttpClient http_Client = new DefaultHttpClient();
	ByteArrayOutputStream byteArrayOutputStream;
	String res =null;
	
	public get_channel(final Callback callback, final String requestCode){
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
		params.add(new BasicNameValuePair("regId",Setting_class.token));
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
		Log.d(TAG,"result:");
		Log.d(TAG, byteArrayOutputStream.toString());
			handan=byteArrayOutputStream.toString();
			if(!handan.isEmpty()){
				String str[] = handan.split("\n");
				List<String> list = new ArrayList<String>();
				for(int i=0;i<str.length;i++){
					str[i]=str[i].replaceAll("\n", "");
					//if(str.equals("")) continue;
					list.add(str[i]);
				}
				requestCode="get_ok";
				Log.d(TAG,"success");
				callback.callback(Callback.SUCCESS, requestCode, list); 
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