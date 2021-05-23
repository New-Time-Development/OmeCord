package com.newtime.system.security;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.newtime.database.LiteSQL;
import com.newtime.listener.OnOmeChannelJoin;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.guild.override.GenericPermissionOverrideEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CatecoryPermUpdate extends ListenerAdapter{
	
	public void onGenericPermissionOverride(GenericPermissionOverrideEvent event) {
		if(event.getChannelType()== ChannelType.CATEGORY) {
			
				ResultSet setupChannel = LiteSQL.onQuery("SELECT * FROM guilds WHERE catid = " + event.getChannel().getIdLong());
				
				try {
					if(setupChannel.next()) {
						System.out.println("help");
						LiteSQL.onUpdate("DELETE FROM guilds WHERE guildid = " + event.getGuild().getIdLong());
						OnOmeChannelJoin.activeGuilds.remove(event.getGuild());

					}
				} catch (SQLException e) {
					e.printStackTrace();
				}			
			
		}
		
	}

}
