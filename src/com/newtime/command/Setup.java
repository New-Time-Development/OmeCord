package com.newtime.command;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import com.newtime.database.LiteSQL;
import com.newtime.main.Main;
import com.newtime.util.Translations;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Setup extends ListenerAdapter {

	@SuppressWarnings("unused")
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

		if (event.getMessage().getContentRaw().startsWith(Main.prefix + "setup")) {
			// Angaben
			TextChannel ch = event.getChannel();
			Member m = event.getMember();
			Guild g = event.getGuild();
			Message mes = event.getMessage();
			List<Color> ColorList = Arrays.asList(Color.GREEN, Color.BLUE, Color.CYAN, Color.LIGHT_GRAY, Color.MAGENTA,
					Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW, Color.decode("#0b0064"));
			Color Color_RANDOM = ColorList.get(new Random().nextInt(ColorList.size()));
			String[] args = event.getMessage().getContentDisplay().split(" ");
			int length = args.length;

			// Hier gehts los
			if (m.hasPermission(Permission.MANAGE_CHANNEL)) {

			

				/*ResultSet lanRS = LiteSQL.onQuery("SELECT * FROM lang WHERE userid = " + m.getIdLong());
				
				try {
					if(lanRS.next()) {
						lan = lanRS.getString("botLan");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/

				ResultSet set = LiteSQL.onQuery("SELECT * FROM guilds WHERE guildid = " + event.getGuild().getIdLong());

				try {
				
					
					if (set.next()) {
						System.out.println("avc");

						long channelid = set.getLong("channelid");
						long catid = set.getLong("catid");

						// ch.sendMessage("Überszung").queue();
						Main.jda.getVoiceChannelById(channelid).delete().queue();
						Main.jda.getCategoryById(catid).delete().queue();
						LiteSQL.onUpdate("DELETE FROM guilds WHERE guildid = " + event.getGuild().getIdLong());
						ch.sendMessage("The OmeCord Bot functions has been disabled from this server. Hopefully not for long ;)").queue();
					} else {
						System.out.println("avcd");
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
							ch.sendMessage("Added Category. Join <#" + chan.getIdLong() + "> to connect to the network. **IMPORTANT:** You can rename the category or the channels at any time, but you must never move them, delete them or change the permissions!").queue();
						});

					}
				} catch (SQLException e) {
					e.printStackTrace();

				}

			} else
				Translations.Fehler("No Permission", ch, "en");
		}
	}

}
