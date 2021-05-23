package com.newtime.listener;

import java.util.Timer;
import java.util.TimerTask;

import com.newtime.main.Main;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VoiceStatsUpdate extends ListenerAdapter{
	
	public void onReady(ReadyEvent e) {
		Timer startT = new Timer();
		startT.schedule(new TimerTask() {
			
			@Override
			public void run() {
				Main.jda.getPresence().setActivity(Activity.listening( Main.jda.getUsers().size() + " voices"));
				
			}
		}, 3000, 30*1000);
	}
	
	

}
