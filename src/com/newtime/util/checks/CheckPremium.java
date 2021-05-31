package com.newtime.util.checks;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.newtime.database.LiteSQL;
import com.newtime.util.Translations;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public class CheckPremium {
	
	public static boolean checkStatus(Member m, TextChannel channel, String lan) {
		
		long unix = System.currentTimeMillis();
		
		ResultSet isPremiumMember = LiteSQL.onQuery("SELECT * FROM premium WHERE userid = " + m.getIdLong());
		try {
			if(isPremiumMember.next()) {
				long ending = isPremiumMember.getLong("end");
				System.out.println(ending);
				System.out.println(unix);
				if(ending == 0) return false;
				if(unix >= ending) {
					LiteSQL.onUpdate("DELETE FROM customJoin WHERE userid = " + m.getIdLong());
					LiteSQL.onUpdate("DELETE FROM premium WHERE userid = " + m.getIdLong());
					m.getUser().openPrivateChannel().queue(dm ->{
						
						dm.sendMessage(new EmbedBuilder()
								.setTitle("Premium Info")
								.setDescription(Translations.PremiumEnd(lan))
								.setColor(Color.MAGENTA)
								.build()).queue(null , fail ->{
									dm.sendMessage(new EmbedBuilder()
											.setTitle("Premium Info")
											.setDescription(Translations.PremiumEnd(lan))
											.setColor(Color.MAGENTA)
											.build()).queue();
								});
					});
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
