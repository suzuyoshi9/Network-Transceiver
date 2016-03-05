package sss.com.network_transceiver;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;




public class post_music extends AsyncTask<String,Integer,Integer>{
	Context context;
	ByteArrayOutputStream byteArrayOutputStream;
	String res =null;
	public post_music(Context context){
	    this.context = context;
	}
	
	@Override
	protected Integer doInBackground(String... params){
        if (isCancelled()){
            return -1;
        }
		String username = params[0];
		String filename = params[1];
		String registid = Setting_class.token;
		HttpResponse response=null;
		Charset charset = Charset.forName(HTTP.UTF_8);

		try {
			HttpClient httpClient = new DefaultHttpClient();
			// ポスト先のファイルを指定
			HttpPost httpPost = new HttpPost("http://vps.suzuyoshi-net.com/tomcat/transceiver-server/upload_voice");
			//ResponseHandler<String> responseHandler = new BasicResponseHandler();
			MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
		  	
			// ファイル名&パスを指定
			File file = new File(filename);
			FileBody fileBody = new FileBody(file);
			// KEYとファイルを指定
			multipartEntity.addPart("file", fileBody);
			multipartEntity.addPart("userId",new StringBody(username,charset));
			multipartEntity.addPart("regId", new StringBody(registid,charset));
		    
			httpPost.setEntity(multipartEntity);
			response=httpClient.execute(httpPost);
			byteArrayOutputStream = new ByteArrayOutputStream();
			response.getEntity().writeTo(byteArrayOutputStream);

		
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Integer.valueOf(response.getStatusLine().getStatusCode());
	}
	
	@Override
	protected void onPostExecute(Integer result){
		
			System.out.println("result:\n" + byteArrayOutputStream.toString());
			File file = new File("/sdcard/data.wav");
			file.delete();
	}
	  @Override
	  protected void onCancelled() {
	    Log.d("voice_post", "onCancelled");

	  }

	  protected void onPostExecute(Long result) {
	    Log.d("voice_post", "onPostExecute - " + result);
	  }

	  public void onCancel(DialogInterface dialog) {
	   // Log.d(TAG, "Dialog onCancell... calling cancel(true)");
	    this.cancel(true);
	  }

}
	
	
	
	
	


