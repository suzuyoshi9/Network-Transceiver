package sss.com.network_transceiver;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class user_add extends Activity implements Callback{
	final Setting_class st= new Setting_class();
	private ProgressDialog dialog;
	post_user_add task = null;
	ConnectivityManager cm;
	private static final String REQUEST_CODE = "ok";
	private static final String SERVER_ERROR = "server_problem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_add);
        cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		final EditText user_name = (EditText) findViewById(R.id.user_name);

		
		final EditText edit_nickname = (EditText) findViewById(R.id.nickname);
		final EditText password1 = (EditText) findViewById(R.id.password);
		final EditText password2 = (EditText) findViewById(R.id.password2);
		password1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		password2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		user_name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS );
		
        Button btn = (Button)findViewById(R.id.edit_button);
        btn.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		NetworkInfo nInfo = cm.getActiveNetworkInfo();
        		if(nInfo==null || !nInfo.isConnected()){
        			Toast.makeText(user_add.this,"ネットワーク接続がねーよ！", Toast.LENGTH_LONG).show();
        			return;
        		}
        		String user = user_name.getText().toString();
        		String nickname = edit_nickname.getText().toString();
        		String pass1 = password1.getText().toString();
        		String pass2 = password2.getText().toString();
        		//Toast.makeText(user_add.this,"PASS1="+pass1+"\nPASS2="+pass2,Toast.LENGTH_LONG).show();
        		if(user.isEmpty()){
        			Toast.makeText(user_add.this,"ユーザー名を入れてください",Toast.LENGTH_LONG).show();
        			return;
        		}
        		if(nickname.isEmpty()){
        			Toast.makeText(user_add.this,"表示名入れてください",Toast.LENGTH_LONG).show();
        			return;
        		}
        		if(pass1.isEmpty()){
        			Toast.makeText(user_add.this,"パスワード入れてください",Toast.LENGTH_LONG).show();
        			return;
        		}
        		if(pass2.isEmpty()){
        			Toast.makeText(user_add.this,"パスワード入れてください",Toast.LENGTH_LONG).show();
        			return;
        		}
        		
        		if(pass1.equals(pass2)){
            		dialog = new ProgressDialog(user_add.this);  
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);  
                    dialog.setMessage("登録中");  
                    dialog.show(); 
                    task = new post_user_add(user_add.this,REQUEST_CODE);
        			task.execute(user,nickname,pass1,pass2);
        	    }else{
        	    	Toast.makeText(user_add.this,"パスワードが不一致だよ",Toast.LENGTH_LONG).show();
        	    }
        	}
        });
    }

    
    public void callback(final int responseCode, String requestCode,final List<String> result){
    	if (REQUEST_CODE.equals(requestCode)){
    		dialog.dismiss();
    		Toast.makeText(this,"登録が完了しました", Toast.LENGTH_LONG).show();
    		finish();
    	}
    	else if(SERVER_ERROR.equals(requestCode)){
    		dialog.dismiss();
    		Toast.makeText(this, "サーバーかネットワークに問題が起きてるらしいよ！ドンマイ！！！！！", Toast.LENGTH_LONG).show();
    	}
    	else{
    		dialog.dismiss();
    		Toast.makeText(this,"なんかおかしいよ！\n今どんな気持ち！？！？！", Toast.LENGTH_LONG).show();
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
