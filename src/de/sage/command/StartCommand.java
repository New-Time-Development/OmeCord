package de.sage.command;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import de.sage.database.LiteSQL;
import de.sage.listener.OnOmeChannelJoin;
import de.sage.main.Main;
import de.sage.system.Queue;
import de.sage.util.Translations;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class StartCommand extends ListenerAdapter {

	@SuppressWarnings("unused")
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if (event.getMessage().getContentRaw().startsWith(Main.prefix + "start")) {
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

			try {
				if (getUserData.next()) {

					if (OnOmeChannelJoin.omechannels.containsValue(ch)) {

						String lan = getUserData.getString("selcLan");
						String gender = getUserData.getString("gen");
						int isMuted = getUserData.getInt("mute");
						
						if(isMuted == 1) {
							ch.sendMessage(Translations.Mute(lan)).queue();
							g.kickVoiceMember(m).queue();
							return;
						}
						
						ch.sendMessage(new EmbedBuilder()
								.setTitle("Queue")
								.setDescription(Translations.Start(lan))
								.setColor(Color.ORANGE)
								.setAuthor("OmeCord Team")
								.build()).queue();
						
						Queue.addUserToQueue(g, m, lan, gender);
					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
}