package sss.com.network_transceiver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class PlayMusic {
	AudioTrack myAT;
	byte[] byteData;
	FileInputStream in = null;

	void initialize(){
		File file=new File("/sdcard/test.wav");
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		byteData = new byte[(int) file.length()];
		try {
			in.read(byteData);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		int bufSize = AudioTrack.getMinBufferSize(Setting_class.sampling_rate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
		myAT = new AudioTrack(AudioManager.STREAM_MUSIC,Setting_class.sampling_rate,AudioFormat.CHANNEL_OUT_MONO,AudioFormat.ENCODING_PCM_16BIT,bufSize,AudioTrack.MODE_STREAM);
	}

	public void run(){
		if(myAT!= null){
			myAT.play();
			myAT.write(byteData,0,byteData.length);
		}
	}
}

