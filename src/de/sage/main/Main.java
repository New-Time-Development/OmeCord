package de.sage.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.logging.Logger;

import de.sage.LiteSQL.LiteSQL;
import de.sage.LiteSQL.SQLManger;
import de.sage.chat.ChattingListener;
import de.sage.cmd.NextCommand;
import de.sage.cmd.OmeCommand;
import de.sage.cmd.PremiumStatus;
import de.sage.cmd.RedeemCommand;
import de.sage.cmd.ReportCommand;
import de.sage.cmd.Setup;
import de.sage.cmd.StartCommand;
import de.sage.cmd.dev.CodeGen;
import de.sage.cmd.dev.PremiumRemove;
import de.sage.cmd.dev.Shutdown;
import de.sage.listener.LangRegist;
import de.sage.listener.OnOmeChannelJoin;
import de.sage.listener.StartUp;
import de.sage.listener.VoiceStatsUpdate;
import de.sage.tests.Join;
import de.sage.tests.Test1Comman;
import de.sage.tests.TestCommand;
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
	public static long id = 747028201745940491l;
	public static String footer = "";
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
