package sss.com.network_transceiver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

public class post_channel_add extends AsyncTask<String,Integer,Integer>{
	/*動くコードはいいコード*/
	HttpResponse response ;
	final String TAG="post_channel_add";
	final Setting_class st= new Setting_class();

	private Callback callback;
	private String requestCode;
	
	private static final String url="http://vps.suzuyoshi-net.com/tomcat/transceiver-server/add_channel.jsp";
	
	//HttpClient httpcliant = new DefaultHttpClient();
	//HttpPost httppost = new HttpPost(url);
	
	List<NameValuePair> nameValuePair;
	
	HttpClient http_Client = new DefaultHttpClient();
	ByteArrayOutputStream byteArrayOutputStream;
	String res =null;
	
	public post_channel_add (final Callback callback, final String requestCode){
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
		params.add(new BasicNameValuePair("groupname",contents[1]));
		
		UrlEncodedFormEntity form;
		try{
			form = new UrlEncodedFormEntity(params,"UTF-8");
			form.setContentEncoding(HTTP.UTF_8);
			post.setEntity(form);
			//post.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
			response=http_Client.execute(post);
			byteArrayOutputStream = new ByteArrayOutputStream();
			response.getEntity().writeTo(byteArrayOutputStream);
			
		}catch (IOException e){
			e.printStackTrace();
			return -1;
		}
		//return 0;
		//System.out.println("T"+Integer.valueOf(response.getStatusLine().getStatusCode()));
		return Integer.valueOf(response.getStatusLine().getStatusCode());
	}
	
	@Override
	protected void onPostExecute(Integer result){
		//System.out.println(response.getStatusLine().getStatusCode());
		if(result==-1 && byteArrayOutputStream==null){
			requestCode="server_problem";
			callback.callback(Callback.ERROR, requestCode, null);
			return;
		}
		if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
			String handan=byteArrayOutputStream.toString();
			handan=handan.replaceAll("\n", "");
			//Toast.makeText(act,"result:\n" + byteArrayOutputStream.toString(), Toast.LENGTH_LONG).show();
				System.out.println("result:\n" + byteArrayOutputStream.toString());
				if(handan.equals("ok")){
					requestCode="ok";
					callback.callback(Callback.SUCCESS, requestCode, null);
				}else{
					requestCode="ng";
					callback.callback(Callback.ERROR, requestCode, null);
				}
			}else{
				requestCode="ng";
				callback.callback(Callback.ERROR, requestCode, null);
				//Toast.makeText(act,"error:\n" + response.getStatusLine(), Toast.LENGTH_LONG).show();
			}
		}
	public void onCancel(DialogInterface dialog){
		this.cancel(true);
	}
}
	
	
	
	


