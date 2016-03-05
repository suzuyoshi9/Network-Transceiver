package sss.com.network_transceiver;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Setting_class {
	//変数関係は基本はここで
	static double latitude=0;//緯度
	static double longitude=0;//経度
	
	static int gps_on_S=0;
	static int token_h=0;
	
	static String user_name = new String("test");
	static String token = new String("");
	static String wav = new String("");
	static final int sampling_rate = 16000;
	
    //MD5ハッシュにするもの
	public String encodePassdigiest(String password){
		byte[] enclyptedHash=null;
		// MD5で暗号化したByte型配列を取得する
				MessageDigest md5;
				try {
					md5 = MessageDigest.getInstance("MD5");
					md5.update(password.getBytes());
					enclyptedHash = md5.digest();

					// 暗号化されたByte型配列を、16進数表記文字列に変換する
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return bytesToHexString(enclyptedHash);
	}

	private String bytesToHexString(byte[] fromByte) {

		StringBuilder hexStrBuilder = new StringBuilder();
		for (int i = 0; i < fromByte.length; i++) {

			// 16進数表記で1桁数値だった場合、2桁目を0で埋める
			if ((fromByte[i] & 0xff) < 0x10) {
				hexStrBuilder.append("0");
			}
			hexStrBuilder.append(Integer.toHexString(0xff & fromByte[i]));
		}

		return hexStrBuilder.toString();
	}
	//MD5ハッシュにするもの
	
}
