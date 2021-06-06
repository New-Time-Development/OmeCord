package com.newtime.listener.buttons;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class StopButton extends ListenerAdapter{
	
	public void onButtonClick(ButtonClickEvent event) throws IllegalStateException{
		if(event.getComponentId().equalsIgnoreCase("stop")) {
			event.getChannel().sendMessage("Comming soon").queue();
			System.out.println("abc");
			//event.deferEdit().queue();
		}
	}

}
