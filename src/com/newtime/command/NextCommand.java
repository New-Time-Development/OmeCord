package com.newtime.command;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.newtime.database.LiteSQL;
import com.newtime.listener.OnOmeChannelJoin;
import com.newtime.main.Main;
import com.newtime.system.Queue;
import com.newtime.util.Translations;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class NextCommand extends ListenerAdapter {

	Member m2 = null;

	@SuppressWarnings("unused")
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if (event.getMessage().getContentRaw().startsWith(Main.prefix + "next")) {
			// Angaben
			TextChannel ch = event.getChannel();
			Member m = event.getMember();
			Guild g = event.getGuild();
			Message mes = event.getMessage();
			List<Color> ColorList = Arrays.asList(Color.GREEN, Color.BLUE, Color.CYAN, Color.LIGHT_GRAY, Color.MAGENTA,
					Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW, Color.decode("#0b0064"));
			Color Color_RANDOM = ColorList.get(new Random().nextInt(ColorList.size()));
			String[] args = event.getMessage().getContentDisplay().split(" ");
			int length = args.length;
			StringBuilder strbuild = new StringBuilder();
			// 1 2
			for (int i = 2; i < length; i++)
				strbuild.append(args[i] + " "); // i = anzahl von suvfix: !.ichbincool

			String argsstring = strbuild.toString().trim();

			// Hier gehts los
			ResultSet getUserData = LiteSQL.onQuery("SELECT * FROM members WHERE userid = " + m.getIdLong());

			ResultSet languageSet = LiteSQL
					.onQuery("SELECT * FROM lang WHERE userid = " + event.getMember().getIdLong());

			String currentLanguage = null;

			try {
				currentLanguage = languageSet.getString("botLan");
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (getUserData.next()) {

					if (OnOmeChannelJoin.omechannels.containsValue(ch)) {
						HashMap<Member, Guild> user1 = new HashMap<>();
						user1.put(m, g);
						if (Queue.activeUser.containsKey(user1)) {

							HashMap<Member, Guild> user2 = Queue.activeUser.get(user1);

							user2.keySet().forEach(test -> {
								m2 = test;
							});

							Guild g2 = user2.get(m2);
							// Chat zeugs

							Queue.removeFromActiveList(m, m2, g, g2);

							TextChannel ch1 = OnOmeChannelJoin.omechannels
									.get(m.getVoiceState().getChannel().getIdLong());
							TextChannel ch2 = OnOmeChannelJoin.omechannels
									.get(m2.getVoiceState().getChannel().getIdLong());
							// Chat zeuegs
							Queue.discordnnectChats(ch1, ch2);
							Queue.disconnectFromGuild(g);
							Queue.disconnectFromGuild(g2);

							if (!Queue.matchUser(g, m)) {

								ResultSet getTheUserData = LiteSQL
										.onQuery("SELECT * FROM members WHERE userid = " + m.getIdLong());
								String lan = getTheUserData.getString("selcLan");
								String gender = getTheUserData.getString("gen");
								Queue.addUserToQueue(g, m, lan, gender);
								ch1.sendMessage(Translations.machError(currentLanguage)).queue();
							}
							if (!Queue.matchUser(g2, m2)) {
								ResultSet getTheUserData = LiteSQL
										.onQuery("SELECT * FROM members WHERE userid = " + m2.getIdLong());
								String lan = getTheUserData.getString("selcLan");
								String gender = getTheUserData.getString("gen");
								Queue.addUserToQueue(g2, m2, lan, gender);
								ch1.sendMessage(Translations.machError(currentLanguage)).queue();
							}

						} else if (Queue.activeUser.containsValue(user1)) {
							HashMap<Member, Guild> user2 = Queue.getKeyFromValue(Queue.activeUser, m);

							user2.keySet().forEach(test -> {
								m2 = test;
							});

							Guild g2 = user2.get(m2);
							// Chat zeugs

							Queue.removeFromActiveList(m2, m, g2, g);

							TextChannel ch1 = OnOmeChannelJoin.omechannels
									.get(m.getVoiceState().getChannel().getIdLong());
							TextChannel ch2 = OnOmeChannelJoin.omechannels
									.get(m2.getVoiceState().getChannel().getIdLong());
							// Chat zeuegs
							Queue.discordnnectChats(ch1, ch2);
							Queue.disconnectFromGuild(g);
							Queue.disconnectFromGuild(g2);

							if (!Queue.matchUser(g, m)) {

								ResultSet getTheUserData = LiteSQL
										.onQuery("SELECT * FROM members WHERE userid = " + m.getIdLong());
								String lan = getTheUserData.getString("selcLan");
								String gender = getTheUserData.getString("gen");
								Queue.addUserToQueue(g, m, lan, gender);
								ch1.sendMessage(Translations.machError(currentLanguage)).queue();
							}
							if (!Queue.matchUser(g2, m2)) {
								ResultSet getTheUserData = LiteSQL
										.onQuery("SELECT * FROM members WHERE userid = " + m2.getIdLong());
								String lan = getTheUserData.getString("selcLan");
								String gender = getTheUserData.getString("gen");
								Queue.addUserToQueue(g2, m2, lan, gender);
								ch1.sendMessage(Translations.machError(currentLanguage)).queue();
							}

						} else {

							Queue.matchUser(g, m);

						}
					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
}