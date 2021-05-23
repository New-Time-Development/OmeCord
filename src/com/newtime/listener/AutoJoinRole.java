package com.newtime.listener;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AutoJoinRole extends ListenerAdapter{
	
	public void onGuildMemberJoin(GuildMemberJoinEvent e) {
		if(e.getGuild().getIdLong() == 845327767565631559l) {
			e.getGuild().addRoleToMember(e.getMember().getIdLong(), e.getGuild().getRoleById(845339935065767997l)).queue();
		}
	}

}
