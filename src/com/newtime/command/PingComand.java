package com.newtime.command;

import com.newtime.main.Main;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PingComand extends ListenerAdapter{
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		long time = System.currentTimeMillis();
		if(e.getMessage().getContentDisplay().equalsIgnoreCase(Main.prefix + "ping") || e.getMessage().getContentDisplay().equalsIgnoreCase(Main.prefix + "tps")) {
			e.getChannel().sendMessage("Pong").queue(response-> {
                response.editMessage("> Ping: `" + (System.currentTimeMillis() - time)  / 10 + " ms`").queue();
            });
		}
	}
}
