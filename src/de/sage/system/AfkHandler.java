package de.sage.system;

import java.util.HashMap;

import net.dv8tion.jda.api.audio.hooks.ConnectionListener;
import net.dv8tion.jda.api.audio.hooks.ConnectionStatus;
import net.dv8tion.jda.api.entities.User;

public class AfkHandler implements ConnectionListener{

	private HashMap<User, Long> userTimes = new HashMap<>();
	
	@Override
	public void onPing(long ping) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChange(ConnectionStatus status) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void onUserSpeaking(User user, boolean speaking) {
		// TODO Auto-generated method stub
		if(user.isBot() == false) {
			if(speaking) {
				userTimes.remove(user);
			}else {
				long unix = System.currentTimeMillis();
				int miuten = 180000;
				long afkKick = unix + miuten;
				userTimes.put(user, afkKick);
			}
		}
	}
	
	

}
