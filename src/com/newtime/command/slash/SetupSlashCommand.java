package com.newtime.command.slash;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;

import com.newtime.database.LiteSQL;
import com.newtime.main.Main;
import com.newtime.util.Translations;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class SetupSlashCommand extends ListenerAdapter{
	
	public void onSlashCommand(SlashCommandEvent event) {
		
		if(event.getName().equalsIgnoreCase("setup")) {
			Guild g = event.getGuild();
			Member m = event.getMember();
			if (m.hasPermission(Permission.MANAGE_CHANNEL)) {

				ResultSet set = LiteSQL.onQuery("SELECT * FROM guilds WHERE guildid = " + event.getGuild().getIdLong());

				try {
					if (set.next()) {

						long channelid = set.getLong("channelid");
						long catid = set.getLong("catid");

						// ch.sendMessage("�berszung").queue();
						Main.jda.getVoiceChannelById(channelid).delete().queue();
						Main.jda.getCategoryById(catid).delete().queue();
						LiteSQL.onUpdate("DELETE FROM guilds WHERE guildid = " + event.getGuild().getIdLong());
						event.reply("The OmeCord Bot functions has been disabled from this server. Hopefully not for long ;)").queue();
					} else {
						Category cat = g.createCategory("OmeCord").complete();

						cat.getManager()
								.putPermissionOverride(g.getPublicRole(), null,
										EnumSet.of(Permission.VIEW_CHANNEL, Permission.VOICE_STREAM,
												Permission.VOICE_MOVE_OTHERS, Permission.PRIORITY_SPEAKER,
												Permission.VOICE_SPEAK, Permission.VOICE_CONNECT,
												Permission.MESSAGE_WRITE, Permission.MESSAGE_HISTORY))
								.queue();

						cat.createVoiceChannel("OmeCord - Create").queue(chan -> {
							long idd = chan.getIdLong();
							chan.getManager().putPermissionOverride(g.getPublicRole(), null, null).queue();
							LiteSQL.onUpdate("INSERT INTO guilds(guildid, channelid, catid, mute) VALUES("
									+ g.getIdLong() + ", " + idd + ", " + cat.getIdLong() + ", 0)");
							event.reply("Added Category. Join <#" + chan.getIdLong() + "> to connect to the network. **IMPORTANT:** You can rename the category or the channels at any time, but you must never move them, delete them or change the permissions!").queue();
						});

					}
				} catch (SQLException e) {
					e.printStackTrace();

				}

			} else
				Translations.Fehler("No Permission", event.getTextChannel(), "en");
		}
		
	}

}
