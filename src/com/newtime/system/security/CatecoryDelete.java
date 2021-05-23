package com.newtime.system.security;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.newtime.database.LiteSQL;
import com.newtime.listener.OnOmeChannelJoin;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.channel.category.CategoryDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CatecoryDelete extends ListenerAdapter{
	
	public void onCategoryDelete(CategoryDeleteEvent e) {
		
		ResultSet setupChannel = LiteSQL.onQuery("SELECT * FROM guilds WHERE catid = " + e.getCategory().getIdLong());
		
		try {
			if(setupChannel.next()) {
				
				OnOmeChannelJoin.activeGuilds.remove(e.getGuild());
				for(VoiceChannel vc : e.getCategory().getVoiceChannels()) {
					for(TextChannel tc :e.getCategory().getTextChannels()) {
						try {
							OnOmeChannelJoin.omechannels.remove(vc.getIdLong(), tc);
						}catch(Exception e1) {
							
						}
					}
				}	
				
				System.out.println("e");
				LiteSQL.onUpdate("DELETE FROM guilds WHERE guildid = " + e.getGuild().getIdLong());
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
