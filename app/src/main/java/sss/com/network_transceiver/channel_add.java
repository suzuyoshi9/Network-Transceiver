package sss.com.network_transceiver;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class channel_add extends Activity implements Callback{
	post_channel_add task = null;
	private ProgressDialog dialog;
	private static final String REQUEST_CODE = "ok";
	private static final String SERVER_ERROR = "server_problem";
	final Setting_class st= new Setting_class();
	String group_name=new String("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_add);
        //Toast.makeText(channel_add.this,"latiude="+st.latitude,Toast.LENGTH_LONG).show();
        Button btn = (Button)findViewById(R.id.edit_button);
        final EditText et = (EditText) findViewById(R.id.group_name);
        btn.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		//POST=ユーザ名、グループ名、緯度、経度、adminフラグ=1をサーバに送る
        		group_name=et.getText().toString();
        		if(group_name.startsWith("now")){
        			Toast.makeText(channel_add.this,"nowで始まるグループ名はダメ!",Toast.LENGTH_LONG).show();
        			return;
        		}
        		dialog = new ProgressDialog(channel_add.this);  
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);  
                dialog.setMessage("追加中");  
                dialog.show(); 
                task = new post_channel_add(channel_add.this,REQUEST_CODE);
                task.execute(Setting_class.user_name,group_name);
        	}
        });
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
    
    public void callback(final int responseCode, String requestCode,final List<String> result){
    	if (REQUEST_CODE.equals(requestCode)){
    		dialog.dismiss();
    		Toast.makeText(this,"追加が完了しました", Toast.LENGTH_LONG).show();
    		finish();
    		//Intent intent = new Intent(channel_add.this, Menu.class);
            //startActivity(intent);
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
}


