package com.newtime.system.security;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.newtime.database.LiteSQL;

import net.dv8tion.jda.api.events.channel.voice.VoiceChannelDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ChannelDeleteDatabase extends ListenerAdapter{
	
	public void onVoiceChannelDelete(VoiceChannelDeleteEvent event) {
		ResultSet setUpChannel = LiteSQL.onQuery("SELECT * FROM guilds WHERE guildid = " + event.getGuild().getIdLong());
		
		try {
			if(setUpChannel.next()) {
				long channelId = setUpChannel.getLong("channelid");
				if(event.getChannel().getIdLong() == channelId) {
					LiteSQL.onUpdate("DELETE FROM guilds WHERE guildid = " + event.getGuild().getIdLong());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
