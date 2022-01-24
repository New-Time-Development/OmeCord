package com.newtime.system.objects;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class OmeUser {
	
	private Guild guild;
	private User user;
	private String language;
	private String gender;
	private Member member;
	
	public OmeUser(Guild guild, User member, String language, String gender) {
		
		this.guild = guild;
		this.user = member;
		this.language = language;
		this.gender = gender;
		this.member = guild.getMember(member);
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Guild getGuild() {
		return guild;
	}

	public void setGuild(Guild guild) {
		this.guild = guild;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User member) {
		this.user = member;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
