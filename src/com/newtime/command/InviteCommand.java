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
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;

public class InviteCommand extends ListenerAdapter {

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		final TextChannel textChannel = event.getChannel();
		if (event.getMessage().getContentRaw().startsWith(Main.prefix + "invite")) {
			String[] args = event.getMessage().getContentDisplay().split(" ");
			
			if(args.length == 1) {
				ResultSet languageSet = LiteSQL.onQuery("SELECT * FROM lang WHERE userid = " + event.getMember().getIdLong());
				
				try {
					String currentLanguage = languageSet.getString("botLan");
					if(currentLanguage.equals("de")) {
						EmbedBuilder embedBuilder = new EmbedBuilder();
						
						embedBuilder.setTitle("OmeCord || Einladung");
						embedBuilder.setDescription("Du möchtest mich auf deinen Server einladen? Klicke " + "[hier](https://discord.com/api/oauth2/authorize?client_id=838062574963523644&permissions=2268392792&scope=bot%20applications.commands), "
								+ "um mich auf deinen Server einzuladen!" );
						
						embedBuilder.setFooter(Main.footer);
						embedBuilder.setTimestamp(new Date().toInstant());

						embedBuilder.setColor(Color.decode("#e05e36"));
						textChannel.sendMessageEmbeds(embedBuilder.build()).setActionRow(Button.link("https://discord.com/api/oauth2/authorize?client_id=838062574963523644&permissions=2268392792&scope=bot%20applications.commands", "Invite")).complete().delete().queueAfter(1, TimeUnit.MINUTES);
						
					} else if(currentLanguage.equals("en")) {
						EmbedBuilder embedBuilder = new EmbedBuilder();
						
						embedBuilder.setTitle("OmeCord || Invite");
						embedBuilder.setDescription("You want to invite me to your server? Click [here](https://discord.com/api/oauth2/authorize?client_id=838062574963523644&permissions=2268392792&scope=bot%20applications.commands) "
								+ "to invite me to your server!");
						
						embedBuilder.setFooter(Main.footer);
						embedBuilder.setTimestamp(new Date().toInstant());

						embedBuilder.setColor(Color.decode("#e05e36"));
						textChannel.sendMessageEmbeds(embedBuilder.build()).setActionRow(Button.link("https://discord.com/api/oauth2/authorize?client_id=838062574963523644&permissions=2268392792&scope=bot%20applications.commands", "Invite")).complete().delete().queueAfter(1, TimeUnit.MINUTES);					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
 		}
	}
}
