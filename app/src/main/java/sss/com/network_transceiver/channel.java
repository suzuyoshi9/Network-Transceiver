package sss.com.network_transceiver;

import java.util.List;

public class channel {
	public String channel_name;
	public String admin_name;
	public List<String> participants;
	
	public int get_attendnum(){
		return participants.size();
	}
	
}
