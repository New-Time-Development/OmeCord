package com.newtime.command;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.newtime.database.LiteSQL;
import com.newtime.main.Main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelpCommand extends ListenerAdapter{
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		final TextChannel textChannel = event.getChannel();
		
		final User user = event.getAuthor();
		if(event.getMessage().getContentRaw().startsWith(Main.prefix + "help")) {
			String[] args = event.getMessage().getContentDisplay().split(" ");
			
			if (args.length == 1) {
				ResultSet languageSet = LiteSQL.onQuery("SELECT * FROM lang WHERE userid = " + event.getMember().getIdLong());
				
				try {
					String currentLanguage = languageSet.getString("botLan");
					if(currentLanguage.equals("en")) {
						EmbedBuilder embedBuilder = new EmbedBuilder();
						
						embedBuilder.setTitle("OmeCord || Helpmenu");
						embedBuilder.addField(":postal_horn: Important Commands:", "`!help` � Show you this help\r\n"
								+ "`!about` � Information about the bot & contact\r\n"
								+ "`!invite` � Sends you the invitation link.\r\n"
								+ "`!setup` � Sets up the bot automatically.\r\n"
								+ "`!ome <language> <gender> <yes, no>` � Creates a user for you *(Yes for automatic translation)*.\r\n"
								+ "`!premium` � Shows you all premium features for the bot.\r\n"
								+ "`!join <message (@user + @server)` � Sets your custom join message *only Premium*"
								, false);
						
						embedBuilder.addField(":flag_white: Language Commands:", "`!language` � Shows you your current language\r\n"
								+ "`!language list` � Shows you all available languages\r\n"
								+ "that are available for selection\r\n"
								+ "`!language <language>` � Change your language", false);
						
						embedBuilder.addField(":loud_sound: General Commands: ", "`!start` � Adds you to the queue\r\n"
								+ "`!next` � Connects you to a new user *(Adapted to your \r\n"
								+ "(Adjusted to your settings.)*\r\n"
								+ "`!report <message...>` � Reports a user\r\n", false);

						embedBuilder.setFooter(Main.footer);
						embedBuilder.setTimestamp(new Date().toInstant());
						
						embedBuilder.setThumbnail(Main.jda.getSelfUser().getAvatarUrl());

						embedBuilder.setColor(Color.decode("#e05e36"));
						
						textChannel.sendMessage(":white_check_mark: **|** You have received a direct message!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
						
						user.openPrivateChannel().queue((privatechannel) -> {
							privatechannel.sendMessageEmbeds(embedBuilder.build()).queue(null, failed -> {
								textChannel.sendMessageEmbeds(embedBuilder.build()).queue();
							});
						});
						
					} else if(currentLanguage.equals("de")) {
						EmbedBuilder embedBuilder = new EmbedBuilder();
						
						embedBuilder.setTitle("OmeCord || Hilfestellung");
						embedBuilder.addField(":postal_horn: Wichtige Kommandos:", "`!help` � Zeigt dir diese Hilfestellung\r\n"
								+ "`!about` � Informationen zu dem Bot & Kontakt\r\n"
								+ "`!invite` � Sendet dir den Einladungslink.\r\n"
								+ "`!setup` � Richtet den Bot automatisch ein.\r\n"
								+ "`!ome <Sprache> <Geschlecht> <Yes, No>` � Erstellt dir einen Nutzer *(Ja f�r automatische �bersetzung)*\r\n"
								+ "`!premium` � Zeigt dir alle Premiumfunktionen f�r den Bot \r\n"
								+ "`!join <Nachricht (@user + @server)` � Setzt deine Custom Join Nachricht *only Premium*"
								, false);
						
						embedBuilder.addField(":flag_white: Sprachen Kommandos:", "`!language` � Zeigt dir deine aktuelle Sprache\r\n"
								+ "`!language list` � Zeigt dir alle verf�gbaren Sprachen\r\n"
								+ "die zur Auswahl stehen\r\n"
								+ "`!language <Sprache>` � �ndert deine Sprache", false);
						
						embedBuilder.addField(":loud_sound: Allgemeine Kommandos: ", "`!start` � F�gt dich zu der Warteschlange hinzu\r\n"
								+ "`!next` � Verbindet dich mit einem neuem Benutzer *(An deinen \r\n"
								+ "Einstellungen angepasst.)*\r\n"
								+ "`!report <Nachricht...>` � Meldet einen Nutzer\r\n", false);
						
						embedBuilder.setFooter(Main.footer);
						embedBuilder.setTimestamp(new Date().toInstant());
						
						embedBuilder.setThumbnail(Main.jda.getSelfUser().getAvatarUrl());

						embedBuilder.setColor(Color.decode("#e05e36"));
						
						textChannel.sendMessage(":white_check_mark: **|** Du hast eine Direktnachricht erhalten!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
						
						user.openPrivateChannel().queue((privatechannel) -> {
							privatechannel.sendMessageEmbeds(embedBuilder.build()).queue(null, failed -> {
								textChannel.sendMessageEmbeds(embedBuilder.build()).queue();
							});
						});
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
