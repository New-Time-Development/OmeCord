package de.sage.listener;

import java.sql.SQLException;

import de.sage.database.LiteSQL;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LangRegist extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		try {
			if(e.getAuthor().isBot()) return;
			if(!LiteSQL.onQuery("SELECT * FROM lang WHERE userid = " + e.getMember().getIdLong()).next()) {
				LiteSQL.onUpdate("INSERT INTO lang(userid, botLan) VALUES(" + e.getMember().getIdLong() + ", 'en')");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
