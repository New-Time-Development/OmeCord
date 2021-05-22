package de.sage.util;

import java.awt.Color;

import de.sage.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class Fehler {

	public static void sendError(String Code, TextChannel channel, String lan) {
		channel.sendMessage(new EmbedBuilder().setDescription("An unexpected error occurred! \n Error code: **" + Code + "**. \n Please check our [website](https://discord.com/j8emH5ap3k) for details.").setColor(Color.RED).setFooter(Main.footer).setTitle(":x: Error :x:").build()).queue();

	}

}
	