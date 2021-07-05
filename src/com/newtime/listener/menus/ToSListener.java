package com.newtime.listener.menus;

import java.util.HashMap;
import java.util.Arrays;

import com.newtime.command.OmeCommand;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class ToSListener extends ListenerAdapter{
	
	public static HashMap<Long, Boolean> accepted = new HashMap<>();
	
	public void onSelectionMenu(SelectionMenuEvent event) {
		if(event.getComponentId().equalsIgnoreCase("tos:menu")) {
				if(event.getValues().get(0).equalsIgnoreCase("yes")) {
					if(OmeCommand.messages.containsKey(event.getMember().getIdLong())) {
						if(OmeCommand.messages.containsValue(event.getMessage().getIdLong())) {
							SelectionMenu menu = SelectionMenu.create("tos:menu")
									.setRequiredRange(1, 1)
								    .addOption("Yes", "yes", "I read and apply the ToS and Privacy", Emoji.fromMarkdown("<:check_mark:845331036737241120>"))
								    .addOption("No", "no", "I dont apply the ToS/Privacy(You cant use the bot)", Emoji.fromMarkdown("<:error123:845331037138976809>"))
									.setDefaultOptions(Arrays.asList(SelectOption.of("Yes", "yes")))
								    .setPlaceholder("Do you accept it?")
								    .build();
							accepted.put(event.getMember().getIdLong(), true);
							event.reply("You have accepted the ToS and Privacy! Just retype you command to register!").queue();
							event.editSelectionMenu(menu.asDisabled()).queue();
						}
					}
					
				}else {
					SelectionMenu menu = SelectionMenu.create("tos:menu")
							.setRequiredRange(1, 1)
						    .addOption("Yes", "yes", "I read and apply the ToS and Privacy", Emoji.fromMarkdown("<:check_mark:845331036737241120>"))
						    .addOption("No", "no", "I dont apply the ToS/Privacy(You cant use the bot)", Emoji.fromMarkdown("<:error123:845331037138976809>"))
							.setDefaultOptions(Arrays.asList(SelectOption.of("No", "no")))
						    .setPlaceholder("Do you accept it?")
						    .build();
					event.reply("You cant use the bot without accepting the ToS and Privacy! Please try the command again and accept it to use the bot").queue();
					event.editSelectionMenu(menu.asDisabled()).queue();
				}
			
		}
	}

}
