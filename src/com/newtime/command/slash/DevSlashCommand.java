package com.newtime.command.slash;

import java.awt.Color;

import com.newtime.main.Main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class DevSlashCommand extends ListenerAdapter {
	
	private long response = -1l;
	private long gateway = -1l;
	
	public void onSlashCommand(SlashCommandEvent event) {
		if(event.getName().equalsIgnoreCase("dev")) {
			if(Main.OwnerIds.contains(event.getMember().getIdLong())) {
				
						event.getJDA().getRestPing().queue(time ->{
							response = time;
						}
						);
					gateway = event.getJDA().getGatewayPing();
					
					long time = System.currentTimeMillis();
						
				event.replyEmbeds(new EmbedBuilder()
						.setTitle("Dev Infos")
						.setColor(Color.YELLOW)
						.setFooter("Dev Stuff")
						.setDescription("Discord response time: " + response + " ms \n Gateway Ping: " + gateway + " ms \n Bot Ping: " + (System.currentTimeMillis() - time)  / 10 + " ms")
						.build()).queue();
			}
		}
		
	}

}
