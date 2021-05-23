package com.newtime.system.security;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.newtime.database.LiteSQL;
import com.newtime.listener.OnOmeChannelJoin;

import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserJoin extends ListenerAdapter{
	
	public void onGuildVoiceJoin(GuildVoiceJoinEvent e) {
		
		ResultSet set = LiteSQL.onQuery("SELECT * FROM guilds WHERE guildid = " + e.getGuild().getIdLong());
		
		try {
			if(set.next()) {
				long setup = set.getLong("channelid");
				long voiceid = e.getChannelJoined().getIdLong();
				if(e.getChannelLeft() != null) {
					if(e.getChannelLeft().getIdLong() != setup) {
						
						
						if(OnOmeChannelJoin.omechannels.containsKey(voiceid)){
							e.getGuild().kickVoiceMember(e.getMember()).queue();
						}
					}
				}else {
					if(OnOmeChannelJoin.omechannels.containsKey(voiceid)){
						e.getGuild().kickVoiceMember(e.getMember()).queue();
					}
				}
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
	}

}
