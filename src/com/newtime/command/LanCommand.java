package com.newtime.command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import com.newtime.database.LiteSQL;
import com.newtime.main.Main;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LanCommand extends ListenerAdapter {

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if (event.getMessage().getContentRaw().startsWith(Main.prefix + "language")) {
			String[] args = event.getMessage().getContentDisplay().split(" ");

			if (args.length == 1) {
				ResultSet languageSet = LiteSQL
						.onQuery("SELECT * FROM lang WHERE userid = " + event.getMember().getIdLong());
				try {
					String currentLanguage = languageSet.getString("botLan");
					if (currentLanguage.equals("en")) {
						event.getChannel().sendMessage(":flag_us: **|** Current language: **ENGLISH**").complete()
								.delete().queueAfter(10, TimeUnit.SECONDS);
					} else if (currentLanguage.equals("de")) {
						event.getChannel().sendMessage(":flag_de: **|** Aktuelle Sprache: **DEUTSCH**").complete()
								.delete().queueAfter(10, TimeUnit.SECONDS);
					} else {
						event.getChannel().sendMessage(":x: We couldn't find your language! Please contact the support")
								.queue();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (args.length == 2) {
				if (args[1].equalsIgnoreCase("list")) {
					ResultSet languageSet = LiteSQL
							.onQuery("SELECT * FROM lang WHERE userid = " + event.getMember().getIdLong());
					try {
						String currentLanguage = languageSet.getString("botLan");
						if (currentLanguage.equals("en")) {
							event.getChannel().sendMessage("Possible languages to select: **ENGLISH**, **DEUTSCH**")
									.complete().delete().queueAfter(10, TimeUnit.SECONDS);
						} else if (currentLanguage.equals("de")) {
							event.getChannel().sendMessage("Mögliche Sprachen zur Auswahl: **Englisch**, **Deutsch**")
									.complete().delete().queueAfter(10, TimeUnit.SECONDS);
						} else {
							event.getChannel()
									.sendMessage(":x: We couldn't find your language! Please contact the support")
									.queue();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else if (args[1].equalsIgnoreCase("English") || args[1].equalsIgnoreCase("Englisch")) {
					LiteSQL.onUpdate("UPDATE lang SET botLan = 'en' WHERE userid = " + event.getMember().getIdLong());
					event.getChannel()
							.sendMessage(
									":flag_us: **|** Your language has been updated. Your language now is: **ENGLISH**")
							.complete().delete().queueAfter(10, TimeUnit.SECONDS);

				} else if (args[1].equalsIgnoreCase("Deutsch") || args[1].equalsIgnoreCase("German")) {
					LiteSQL.onUpdate("UPDATE lang SET botLan = 'de' WHERE userid = " + event.getMember().getIdLong());
					event.getChannel()
							.sendMessage(
									":flag_de: **|** Deine Sprache wurde aktualisiert. Deine neue Sprache: **DEUTSCH**")
							.complete().delete().queueAfter(10, TimeUnit.SECONDS);
				}
			}
		}
	}
}
