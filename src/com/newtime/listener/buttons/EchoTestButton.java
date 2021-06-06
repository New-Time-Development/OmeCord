package com.newtime.listener.buttons;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class EchoTestButton extends ListenerAdapter{
	
	public void onButtonClick(ButtonClickEvent event) {
		if(event.getComponentId().equalsIgnoreCase("echo")) {
			event.reply("Comming soon...").queue();
			return;
			/*if(OnOmeChannelJoin.echoUser.contains(event.getMember())) {
				
				
				
			}else {
				
			}*/
		}
	}

}
