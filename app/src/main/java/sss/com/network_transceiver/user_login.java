package sss.com.network_transceiver;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sss.network_transceiver.R.id;

public class user_login extends Activity implements Callback{
	final Setting_class st= new Setting_class();
	post_user_login task = null;
	private ProgressDialog dialog;
	private static final String REQUEST_CODE = "ok";
	private static final String SERVER_ERROR = "server_problem";
	ConnectivityManager cm;
	//final token_user_login task2 = new token_user_login();
	//DefaultHttpClient client = new DefaultHttpClient();
	//HttpPost method = new HttpPost("vps.suzuyoshi-net.com:8080/pro-app/login.jsp");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        setContentView(R.layout.user_login);
        //xmlよみこみ〜
       // SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		final EditText user_name = (EditText) findViewById(id.user_name);
		final EditText password = (EditText) findViewById(id.password);
		//password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        Button btn = (Button)findViewById(R.id.edit_button);
        btn.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		NetworkInfo nInfo = cm.getActiveNetworkInfo();
        		if(nInfo==null || !nInfo.isConnected()){
        			Toast.makeText(user_login.this,"ネットワーク接続がねーよ！", Toast.LENGTH_LONG).show();
        			return;
        		}
        		String user = user_name.getText().toString();
        		Setting_class.user_name=user;
        		String pass = password.getText().toString();
        		String pass2;
        		if(user.isEmpty()){
        			Toast.makeText(user_login.this,"ユーザー名を入れてください",Toast.LENGTH_LONG).show();
        			return;
        		}
        		if(pass.isEmpty()){
        			Toast.makeText(user_login.this,"パスワード入れてください",Toast.LENGTH_LONG).show();
        			return;
        		}
        		
        		//MD5ハッシュ化
        		pass2 = st.encodePassdigiest(pass);
        		//MD5ハッシュ化
        		
        		dialog = new ProgressDialog(user_login.this);  
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);  
                dialog.setMessage("ログイン中");  
                dialog.show(); 
                task = new post_user_login(user_login.this,REQUEST_CODE);
                task.execute(user,pass2);
        		//Intent intent = new Intent(user_login.this, Menu.class);
                //startActivity(intent);
        	
        	}
        });
        
        Button btn2 = (Button)findViewById(R.id.user_make);
        btn2.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {

        		Intent intent = new Intent(user_login.this, user_add.class);
                startActivity(intent);
        	
        	}
        });
        
    }
    public void callback(final int responseCode, String requestCode,final List<String> result){
    	if (REQUEST_CODE.equals(requestCode)){
    		dialog.dismiss();
    		Toast.makeText(this,"ログインが完了しました", Toast.LENGTH_LONG).show();
    		Intent intent = new Intent(user_login.this, Menu.class);
            startActivity(intent);
    	}
    	else if(SERVER_ERROR.equals(requestCode)){
    		dialog.dismiss();
    		Toast.makeText(this, "サーバーかネットワークに問題が起きてるらしいよ！ドンマイ！！！！！", Toast.LENGTH_LONG).show();
    	}
    	else{
    		dialog.dismiss();
    		Toast.makeText(this,"ユーザー名かパスワードが間違ってますってサーバーが言ってるよ！\n今どんな気持ち！？！？！", Toast.LENGTH_LONG).show();
    	}
    }
    

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, (android.view.Menu) menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}