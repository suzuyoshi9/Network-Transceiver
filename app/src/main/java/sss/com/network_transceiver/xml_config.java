package sss.com.network_transceiver;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class xml_config extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	//設定ファイル読み込み
    	addPreferencesFromResource(R.xml.login_pref);
    	
    }
	

}
