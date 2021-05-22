package de.sage.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.logging.Logger;

import de.sage.beta.Join;
import de.sage.beta.Test1Comman;
import de.sage.beta.TestCommand;
import de.sage.command.AboutCommand;
import de.sage.command.CustomJoin;
import de.sage.command.HelpCommand;
import de.sage.command.InviteCommand;
import de.sage.command.LanCommand;
import de.sage.command.NextCommand;
import de.sage.command.OmeCommand;
import de.sage.command.PingComand;
import de.sage.command.PremiumStatus;
import de.sage.command.RedeemCommand;
import de.sage.command.ReportCommand;
import de.sage.command.Setup;
import de.sage.command.StartCommand;
import de.sage.command.devs.ChangeCommand;
import de.sage.command.devs.CodeGen;
import de.sage.command.devs.Mute;
import de.sage.command.devs.PremiumRemove;
import de.sage.command.devs.Shutdown;
import de.sage.database.LiteSQL;
import de.sage.database.SQLManger;
import de.sage.listener.AutoJoinRole;
import de.sage.listener.LangRegist;
import de.sage.listener.OnOmeChannelJoin;
import de.sage.listener.StartUp;
import de.sage.listener.VoiceStatsUpdate;
import de.sage.system.ChattingListener;
import de.sage.system.securety.CatecoryPermUpdate;
import de.sage.system.securety.ChannelDeleteDatabase;
import de.sage.system.securety.GuildLeave;
import de.sage.system.securety.OmeChannelDelete;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
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
	    //Set Owners
	    OwnerIds.add(678260187454242826l);
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
	}
	

}
