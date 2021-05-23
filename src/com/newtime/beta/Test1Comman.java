package com.newtime.beta;

import java.util.Arrays;
import java.util.List;

import com.newtime.main.Main;
import com.newtime.system.handler.EchoHandler;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class Test1Comman extends ListenerAdapter {

	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		if (e.getMessage().getContentDisplay().equalsIgnoreCase("!test1")) {

			connectTo(e.getMember().getVoiceState().getChannel());
		}
	}

	// channel = guild
	private void connectTo(VoiceChannel channel) {

		Guild g1 = channel.getGuild();

		// G1 ist anders
		// G2 kann nicht g1
		// G2 sind eigentlich alle server
		// Was ist immer verscieden:
		// Beide sind verschieden

		EchoHandler handler = new EchoHandler();
		for (Guild g2 : Main.jda.getGuilds()) {
			if (!g2.equals(g1)) {
				System.out.println(g2);
				AudioManager sendM = g2.getAudioManager();
				// Set the sending handler to our echo system
				sendM.setSendingHandler(handler);
			}

		}

		AudioManager receiM = g1.getAudioManager();
		// Set the receiving handler to the same echo system, otherwise we can't echo
		// anything
		receiM.setReceivingHandler(handler);
		// Connect to the voice channel
		// channel.getGuild().getAudioManager().openAudioConnection(channel);

	}

	@SuppressWarnings("unused")
	private void connectToWithGuild(VoiceChannel channel, Guild g2) {

		Guild g1 = channel.getGuild();

		// G1 ist anders
		// G2 kann nicht g1
		// G2 sind eigentlich alle server
		// Was ist immer verscieden:
		// Beide sind verschieden

		EchoHandler handler = new EchoHandler();

		System.out.println(g2);
		AudioManager sendM = g2.getAudioManager();
		// Set the sending handler to our echo system
		sendM.setSendingHandler(handler);

		AudioManager receiM = g1.getAudioManager();
		// Set the receiving handler to the same echo system, otherwise we can't echo
		// anything
		receiM.setReceivingHandler(handler);
		// Connect to the voice channel
		// channel.getGuild().getAudioManager().openAudioConnection(channel);

	}

	@SuppressWarnings("unused")
	private void connectToGuild(VoiceChannel channel, Guild g2) {
		// Guild1 != Guild2, Beide Guilds können unterschiedlich sein, Guild2 und Guild1
		// können alle Guilds sein.

		Guild g1 = channel.getGuild();

		EchoHandler handler = new EchoHandler();
		
		Guild tempGuild = Main.jda.getGuildById(825726076457844846l);

		List<Guild> guilds = Arrays.asList(g1, g2, tempGuild);

		// Guild1
		AudioManager guild1ReceivedManager = g1.getAudioManager();

		System.out.println(g2);
		AudioManager guild2SendManager = g2.getAudioManager();
		// Set the sending handler to our echo system
		guild2SendManager.setSendingHandler(handler);

		guild1ReceivedManager.setReceivingHandler(handler);
		g1.getAudioManager().openAudioConnection(channel);
	}
}
