package de.sage.util;

import java.awt.Color;

import de.sage.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class Fehler {
	
	// List<String> NORMAL_BAD_WORDS = Arrays.asList("fick", "fuck", "LGBTQ", "LGBTQ+", "nudes", "nude", "hure", "nutte", "huren", "whore", "bastart", "ficke", "ficken", "porno", "porn", "hurensohn", "schlampe", "fotze", "ficken", "Depp", "idiot", "vollidiot", "arschkriecher", "wichser", "wixxer", "mee6", "wixer", "arsch", "arschloch", "drecksau", "trottel", "hackfresse", "dummkopf", "Dreckschwein", "sau", "bitch", "schwachkopf", "pussy", "pu$$y", "ass", "boobs", "titten", "brüste", "Dickhead", "scheide", "vagina");

	public static void sendError(String Code, TextChannel channel, String lan) {
		channel.sendMessage(new EmbedBuilder().setDescription("An unexpected error occurred! \n Error code: **" + Code + "**. \n Please check our [website](https://discord.com/j8emH5ap3k) for details.").setColor(Color.RED).setFooter(Main.footer).setTitle(":x: Error :x:").build()).queue();

	}

}
	