package sss.com.network_transceiver;
 
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;
 
public class FileDownload{
  private final String TAG = "AsyncFileDownload";
  private final int TIMEOUT_READ = 5000; 
  private final int TIMEOUT_CONNECT = 30000; 
   
  private final int BUFFER_SIZE = 1024;
   
  private String urlString;
  private File outputFile;
  private FileOutputStream fileOutputStream;
  private InputStream inputStream;
  private BufferedInputStream bufferedInputStream;
 
  private byte[] buffer = new byte[BUFFER_SIZE];
 
  private URL url;
  private URLConnection urlConnection;
   
  public FileDownload(String url, File oFile) {
    urlString = url; 
    outputFile = oFile; 
 }

  protected Boolean download(){
    try{
      connect();
    }catch(IOException e){
      Log.d(TAG, "ConnectError:" + e.toString());
    }
     
    if (bufferedInputStream !=  null){
      try{
        int len;
        while((len = bufferedInputStream.read(buffer)) != -1){
            fileOutputStream.write(buffer, 0, len);
            //publishProgress();
        }
      }catch(IOException e){
        Log.d(TAG, e.toString());
        return false;
      }
    }else{
      Log.d(TAG, "bufferedInputStream == null");
    }
     
    try{
      close();
    }catch(IOException e){
      Log.d(TAG, "CloseError:" + e.toString());
    }
    System.out.println("download ok");
    return true; 
  }
  
  private void connect() throws IOException 
  { 
    url = new URL(urlString);
    urlConnection = url.openConnection();
    urlConnection.setReadTimeout(TIMEOUT_READ);
    urlConnection.setConnectTimeout(TIMEOUT_CONNECT);
    inputStream = urlConnection.getInputStream();
    bufferedInputStream = new BufferedInputStream(inputStream, BUFFER_SIZE);
    fileOutputStream = new FileOutputStream(outputFile);
  }
   
  public void close() throws IOException
  {
    fileOutputStream.flush();
    fileOutputStream.close();
    bufferedInputStream.close();
  }
 
}