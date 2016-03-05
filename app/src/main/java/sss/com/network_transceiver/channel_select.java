package sss.com.network_transceiver;

import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class channel_select extends ListActivity implements Callback,OnItemClickListener{
	get_channel task1 = null;
	String TAG="channel_select";
	private String[] items = {};
	private ProgressDialog dialog;
	private static final String REQUEST_CODE = "get_ok";
	private static final String POST_CODE = "set_ok";
	private static final String SERVER_ERROR = "server_problem";
	post_channel_change task2 = null;
	ArrayAdapter<String> adapter;
	ListView listview;
	String groupname;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channel_select);
		listview=getListView();
		dialog = new ProgressDialog(channel_select.this);  
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);  
        dialog.setMessage("取得中");  
        dialog.show(); 
        task1 = new get_channel(channel_select.this,REQUEST_CODE);
		task1.execute(Setting_class.user_name);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id){
		groupname=parent.getAdapter().getItem(position).toString();
		dialog = new ProgressDialog(channel_select.this);  
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);  
        dialog.setMessage("設定中");  
        dialog.show();
        task2 = new post_channel_change(channel_select.this,REQUEST_CODE);
        task2.execute(parent.getAdapter().getItem(position).toString());
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

	@Override
	public void callback(int responseCode, String requestCode, List<String> result) {
		int now=-1;
    	if (REQUEST_CODE.equals(requestCode)){
    		dialog.dismiss();
    		//Toast.makeText(this,"ログインが完了しました", Toast.LENGTH_LONG).show();
    		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice);
    		for(String item:result){
    			//item=item.replaceAll("\n", "");
    			if(item.startsWith("now:")){
    				String[] temp = item.split(":");
    				now=result.indexOf(temp[1]);
    				continue;
    			}
    			adapter.add(item);
    		}
    		setListAdapter(adapter);
    		listview.setItemChecked(now, true);
    		//listview.setSelection(now);
    		listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    		listview.setOnItemClickListener(this);
    		//Intent intent = new Intent(user_login.this, Menu.class);
            //startActivity(intent);
    	}
    	else if(POST_CODE.equals(requestCode)){
    		dialog.dismiss();
    		Toast.makeText(this,groupname+"に切り替えました", Toast.LENGTH_LONG).show();
    		finish();
    	}
    	else if(SERVER_ERROR.equals(requestCode)){
    		dialog.dismiss();
    		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice, items);
    		setListAdapter(adapter);
    		Toast.makeText(this, "サーバーかネットワークに問題が起きてるらしいよ！ドンマイ！！！！！", Toast.LENGTH_LONG).show();
    	}
    	else{
    		dialog.dismiss();
    		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice, items);
    		setListAdapter(adapter);
    		Toast.makeText(this,"ユーザー名かパスワードが間違ってますってサーバーが言ってるよ！\n今どんな気持ち！？！？！", Toast.LENGTH_LONG).show();
    	}
	}

}
