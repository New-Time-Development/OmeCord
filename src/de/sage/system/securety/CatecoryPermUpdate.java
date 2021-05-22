package de.sage.system.securety;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.sage.database.LiteSQL;
import de.sage.listener.OnOmeChannelJoin;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.override.GenericPermissionOverrideEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CatecoryPermUpdate extends ListenerAdapter{
	
	public void onGenericPermissionOverride(GenericPermissionOverrideEvent event) {
		if(event.getCategory() != null){
			if(event.getMember() != null) {
				for(VoiceChannel vc : event.getCategory().getVoiceChannels()) {
					ResultSet setupChannel = LiteSQL.onQuery("SELECT * FROM guilds WHERE channelid = " + vc.getIdLong());
					try {
						if(setupChannel.next()) {
							LiteSQL.onUpdate("DELETE FROM guilds WHERE guildid = " + event.getGuild().getIdLong());
							OnOmeChannelJoin.activeGuilds.remove(event.getGuild());
							if(OnOmeChannelJoin.omechannels.get(vc.getIdLong()) != null) {
								OnOmeChannelJoin.activeGuilds.remove(event.getGuild());
								OnOmeChannelJoin.omechannels.remove(vc.getIdLong(), OnOmeChannelJoin.omechannels.get(vc.getIdLong()));
							}
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}
	}

}
