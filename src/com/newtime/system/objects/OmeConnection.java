package com.newtime.system.objects;

import java.util.Arrays;
import java.util.List;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class OmeConnection {
	
	private Member m1;
	private Member m2;
	private Guild g1;
	private Guild g2;
	
	public OmeConnection(Member m1, Guild g1, Member m2, Guild g2) {
		
		this.m1 = m1;
		this.m2 = m2;
		this.g1 = g1;
		this.g2 = g2;
	}
	
	public List<Member> getMembers(){
		return Arrays.asList(m1, m2);
	}
	
	public List<Guild> getGuilds(){
		return Arrays.asList(g1, g2);
	}

	public Member getMember1() {
		return m1;
	}

	public void setMember1(Member m1) {
		this.m1 = m1;
	}

	public Member getMember2() {
		return m2;
	}

	public void setMember2(Member m2) {
		this.m2 = m2;
	}

	public Guild getGuild1() {
		return g1;
	}

	public void setGuild1(Guild g1) {
		this.g1 = g1;
	}

	public Guild getGuild2() {
		return g2;
	}

	public void setGuild2(Guild g2) {
		this.g2 = g2;
	}
	

}
