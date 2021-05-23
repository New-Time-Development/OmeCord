package com.newtime.system.security;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.newtime.database.LiteSQL;
import com.newtime.listener.OnOmeChannelJoin;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildLeave extends ListenerAdapter{
	
	public void onGuildLeave(GuildLeaveEvent event) {
		ResultSet getGuildData = LiteSQL.onQuery("SELECT * FROM guilds WHERE guildid = " + event.getGuild().getIdLong());
		try {
			if(getGuildData.next()) {
				LiteSQL.onUpdate("DELETE FROM guilds WHERE guildid = " + event.getGuild().getIdLong());
				OnOmeChannelJoin.activeGuilds.remove(event.getGuild());
				
				for(VoiceChannel vc : event.getGuild().getVoiceChannels()) {
					for(TextChannel tc : event.getGuild().getTextChannels()) {
						try {
							OnOmeChannelJoin.omechannels.remove(vc.getIdLong(), tc);
						}catch(Exception e) {
							
						}
					
					}
					
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
