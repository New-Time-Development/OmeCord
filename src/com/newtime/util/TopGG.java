package com.newtime.util;

import org.discordbots.api.client.DiscordBotListAPI;

import com.newtime.main.Tokens;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class TopGG {
	
	private static DiscordBotListAPI api;
	private static boolean isLoaded = false;
	
	public static void loadAPI(boolean force) {
		if(force) {
				api = new DiscordBotListAPI.Builder()
						.token(Tokens.TopToken)
						.botId("838062574963523644")
						.build();
				isLoaded = true;
		}else {
			if(!isLoaded) {
				api = new DiscordBotListAPI.Builder()
						.token(Tokens.TopToken)
						.botId("838062574963523644")
						.build();
				isLoaded = true;
			}
		}

	}
	
	public static void setServer(int server) {
		if(isLoaded) {
			api.setStats(server);
		}
		
	}
	
	public static DiscordBotListAPI getApi() {
		if(isLoaded) {
			return api;
		}else
			throw new NullPointerException();
	}

}
