package com.newtime.main;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.logging.Logger;

import com.newtime.beta.Join;
import com.newtime.beta.Test1Comman;
import com.newtime.beta.TestCommand;
import com.newtime.command.AboutCommand;
import com.newtime.command.CustomJoin;
import com.newtime.command.HelpCommand;
import com.newtime.command.InviteCommand;
import com.newtime.command.LanCommand;
import com.newtime.command.NextCommand;
import com.newtime.command.OmeCommand;
import com.newtime.command.PingComand;
import com.newtime.command.PremiumStatus;
import com.newtime.command.RedeemCommand;
import com.newtime.command.ReportCommand;
import com.newtime.command.Setup;
import com.newtime.command.StartCommand;
import com.newtime.command.devs.ChangeCommand;
import com.newtime.command.devs.CodeGen;
import com.newtime.command.devs.Mute;
import com.newtime.command.devs.PremiumRemove;
import com.newtime.command.devs.Shutdown;
import com.newtime.database.LiteSQL;
import com.newtime.database.SQLManger;
import com.newtime.listener.AutoJoinRole;
import com.newtime.listener.LangRegist;
import com.newtime.listener.OnOmeChannelJoin;
import com.newtime.listener.StartUp;
import com.newtime.listener.VoiceStatsUpdate;
import com.newtime.system.ChattingListener;
import com.newtime.system.security.CatecoryDelete;
import com.newtime.system.security.CatecoryPermUpdate;
import com.newtime.system.security.ChannelDeleteDatabase;
import com.newtime.system.security.GuildLeave;
import com.newtime.system.security.OmeChannelDelete;
import com.newtime.system.security.UserJoin;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import okhttp3.OkHttpClient;

public class Main {

	//Static Things
	public static JDA jda;
	public static JDABuilder builder;
	public static ArrayList<Long> OwnerIds = new ArrayList<Long>();
	public static String prefix = "!";
	public static long id = 838062574963523644l;
	public static String footer = "Version 1.0.0 PRE || New-Time-Development";
	public static boolean Debug = false;
	
	public static void main(String[] args) throws IOException{
		//Debug
		 Debug = true;
		 
		//Sql
    	LiteSQL.connect();
    	SQLManger.onCreate();
		
    	//Logger
    	Logger.getLogger(OkHttpClient.class.getName()).setLevel(java.util.logging.Level.FINE);
    	
    	//Set Token
		builder = JDABuilder.createDefault(DONOTOPEN.Token);

		//Set Online Stats
		builder.setActivity(Activity.listening(" ...loading... voices"));
		builder.setAutoReconnect(true);
		
		builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
		
		//Enable Caches
	    builder.setMemberCachePolicy(MemberCachePolicy.ALL);
	    
	    builder.enableCache(EnumSet.allOf(CacheFlag.class));
	    builder.enableIntents(EnumSet.allOf(GatewayIntent.class));

	    //Register Events
	    builder.addEventListeners(new TestCommand());
	    builder.addEventListeners(new Join());
	    builder.addEventListeners(new Shutdown());
	    builder.addEventListeners(new Test1Comman());
	    builder.addEventListeners(new StartUp());
	    builder.addEventListeners(new CodeGen());
	    builder.addEventListeners(new VoiceStatsUpdate());
	    builder.addEventListeners(new ReportCommand());
	    builder.addEventListeners(new Setup());
	    builder.addEventListeners(new OnOmeChannelJoin());
	    builder.addEventListeners(new LangRegist());
	    builder.addEventListeners(new OmeCommand());
	    builder.addEventListeners(new StartCommand());
	    builder.addEventListeners(new NextCommand());
	    builder.addEventListeners(new RedeemCommand());
	    builder.addEventListeners(new ChattingListener());
	    builder.addEventListeners(new PremiumRemove());
	    builder.addEventListeners(new PremiumStatus());
		builder.addEventListeners(new LanCommand());
		builder.addEventListeners(new AboutCommand());
		builder.addEventListeners(new InviteCommand());
	    builder.addEventListeners(new PingComand());
		builder.addEventListeners(new Mute());
		builder.addEventListeners(new HelpCommand());
		builder.addEventListeners(new ChangeCommand());
		builder.addEventListeners(new CustomJoin());
		builder.addEventListeners(new AutoJoinRole());
		
		//Securety
		builder.addEventListeners(new ChannelDeleteDatabase());
		builder.addEventListeners(new CatecoryPermUpdate());
		builder.addEventListeners(new OmeChannelDelete());
		builder.addEventListeners(new GuildLeave());
		builder.addEventListeners(new CatecoryDelete());
		builder.addEventListeners(new UserJoin());
		
	    //Set Owners
	    OwnerIds.add(401059500972441600l);
	    OwnerIds.add(660887621169446964l);
	    
	   //Only Debug Commands
	    if(Debug) {
	    	
	    }
	    
	    try {
	    	//Online
			System.out.println("+++++\nOme.tv Bot ist online YEAY \n+++++\n\n");
			jda = builder.build();			
		} catch (Exception e) {
			//Error
			System.out.println("-----\nOme.tv Bot hat einen Fehler \n-----\n\n");
			e.printStackTrace();
		}	
	    
	    
	    start();
	    
	   // Scanners.scanners();
	}
	
	public static void shutdown() {
		for(Guild guilds : OnOmeChannelJoin.activeGuilds) {
			for(Member members : guilds.getMembers()) {
				try {
					VoiceChannel voiceChannel = members.getVoiceState().getChannel();
					if(OnOmeChannelJoin.omechannels.containsKey(voiceChannel.getIdLong())) {
						guilds.kickVoiceMember(members).queue();
						OnOmeChannelJoin.omechannels.remove(voiceChannel.getIdLong(), OnOmeChannelJoin.omechannels.get(voiceChannel.getIdLong()));
						ResultSet set = LiteSQL.onQuery("SELECT * FROM guilds WHERE guildid = " + guilds.getIdLong());							
						
							if(set.next()) {
								long channelid = set.getLong("channelid");
								
								VoiceChannel setupCh = Main.jda.getVoiceChannelById(channelid);
								setupCh.getManager().putPermissionOverride(guilds.getPublicRole(), null, EnumSet.of(Permission.VOICE_CONNECT)).queue();
							}
					}
				}catch(Exception e) {
					
				}
			}
		}
	}
	
	public static void start() {
		for(Guild guilds : Main.jda.getGuilds()) {
			ResultSet set = LiteSQL.onQuery("SELECT * FROM guilds WHERE guildid = " + guilds.getIdLong());							
			
			try {
				if(set.next()) {
					long channelid = set.getLong("channelid");
					
					VoiceChannel setupCh = Main.jda.getVoiceChannelById(channelid);
					setupCh.getManager().putPermissionOverride(guilds.getPublicRole(), null, null).queue();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
		}
		
	
}
