package com.newtime.command;

import org.discordbots.api.client.DiscordBotListAPI;

import com.newtime.main.DONOTOPEN;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class VoteCommand {
	
	public static DiscordBotListAPI api = new DiscordBotListAPI.Builder()
			.token(DONOTOPEN.TopToken)
			.botId("838062574963523644")
			.build();
	
	public static void setServer(int server) {
		api.setStats(server);
	}

}
