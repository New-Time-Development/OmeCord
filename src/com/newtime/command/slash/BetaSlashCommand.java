package com.newtime.command.slash;

import java.awt.Color;

import com.newtime.main.Main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class BetaSlashCommand extends ListenerAdapter{
	
	@SuppressWarnings("unused")
	public void onSlashCommand(SlashCommandEvent event) {
		
		if(event.getName().equalsIgnoreCase("beta")) {
			Guild g = event.getGuild();
			Member m = event.getMember();
			
			event.replyEmbeds(new EmbedBuilder()
					.setTitle("Beta")
					.setDescription("Comming soon...")
					.setFooter(Main.footer)
					.setColor(Color.WHITE)
					.build()).addActionRow(
							Button.secondary("beta-request", Emoji.fromMarkdown("<:bughunter:845332960017383424>"))
							).queue();
		}
		
	}

}
