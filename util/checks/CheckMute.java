package de.sage.util.checks;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.sage.database.LiteSQL;
import de.sage.util.Translations;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.requests.RestAction;

public class CheckMute {
	
	public static boolean checkStatus(Member m, RestAction<PrivateChannel> channel, String lan) {
		
		ResultSet getMute = LiteSQL.onQuery("SELECT * FROM mute WHERE userid = " + m.getIdLong());
		
		try {
			if(getMute.next()) {
				long endMute = getMute.getLong("end");
				if(endMute == 0) return false;
				if(endMute <= System.currentTimeMillis()) {
					int mutes = getMute.getInt("val");
					LiteSQL.onUpdate("UPDATE val SET " + mutes++ + " WHERE userid = " + m.getIdLong());
					channel.queue(dm ->{
						dm.sendMessage(Translations.muteEnd(lan)).queue();
					});
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}

}
