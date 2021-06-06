package com.newtime.command.devs;

import java.util.concurrent.TimeUnit;

import com.newtime.main.Main;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class OnlineCommand extends ListenerAdapter{
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if(event.getMessage().getContentRaw().startsWith(Main.prefix + "online")) {
			if(Main.OwnerIds.contains(event.getMember().getIdLong())) {
				Main.jda.getPresence().setStatus(OnlineStatus.ONLINE);
				event.getChannel().sendMessage(":white_check_mark: v1.0 PRE releasd").complete().delete().queueAfter(5, TimeUnit.SECONDS);
			}
		}
	}
}
