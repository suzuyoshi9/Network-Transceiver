package sss.com.network_transceiver;

import java.util.List;

public interface Callback {
	/** 成功時のレスポンスコード */  
    public static final int SUCCESS = 0; 
    /** 失敗時のレスポンスコード */  
    public static final int ERROR = -1;  
  
    /** 
     * コールバックメソッド 
     */  
    public void callback(final int responseCode, final String requestCode, final List<String> result);  
}
