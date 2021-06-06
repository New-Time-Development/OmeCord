package com.newtime.listener.buttons;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.newtime.database.LiteSQL;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class EmptyButton extends ListenerAdapter {

	@SuppressWarnings("unused")
	public void onButtonClick(ButtonClickEvent event) {
		if(event.getComponentId().equalsIgnoreCase("<prefix>")) {
			//Get informations
			Member m = event.getMember();
			Guild g = event.getGuild();
			Message mes = event.getMessage();
			Button button = event.getButton();
			
			ResultSet lanRS = LiteSQL.onQuery("SELECT * FROM lang WHERE userid = " + m.getIdLong());
			String lan = null;
			try {
				if(lanRS.next()) {
					lan = lanRS.getString("botLan");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(event.getChannelType().equals(ChannelType.TEXT)) {
				//Text Channel
				
			}else {
				//Private Message
				
			}
		}
		
	}
	
}
