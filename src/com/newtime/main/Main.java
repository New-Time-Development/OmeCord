package com.newtime.main;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

import com.newtime.beta.Join;
import com.newtime.beta.SendmENU;
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
import com.newtime.command.SettingsCommand;
import com.newtime.command.Setup;
import com.newtime.command.StartCommand;
import com.newtime.command.VoteCommand;
import com.newtime.command.devs.ChangeCommand;
import com.newtime.command.devs.CodeGen;
import com.newtime.command.devs.EmbedCommand;
import com.newtime.command.devs.Mute;
import com.newtime.command.devs.OnlineCommand;
import com.newtime.command.devs.PremiumButtonCommand;
import com.newtime.command.devs.PremiumRemove;
import com.newtime.command.devs.Shutdown;
import com.newtime.command.slash.AboutSlahCommand;
import com.newtime.command.slash.BetaSlashCommand;
import com.newtime.command.slash.DevSlashCommand;
import com.newtime.command.slash.EftronSlashCommand;
import com.newtime.command.slash.HelpShlashCommand;
import com.newtime.command.slash.InviteSlashCommand;
import com.newtime.command.slash.JoinSlashCommand;
import com.newtime.command.slash.LanguageSlashCommand;
import com.newtime.command.slash.NextSlashCommand;
import com.newtime.command.slash.OmeSlashCommand;
import com.newtime.command.slash.PingSlashCommand;
import com.newtime.command.slash.PremiumSlashCommand;
import com.newtime.command.slash.RedeemSlashCommand;
import com.newtime.command.slash.ReportSlashCommand;
import com.newtime.command.slash.SettingsSlashCommand;
import com.newtime.command.slash.SetupSlashCommand;
import com.newtime.command.slash.StartSlashCommand;
import com.newtime.command.slash.VoteSlashCommand;
import com.newtime.database.LiteSQL;
import com.newtime.database.SQLManger;
import com.newtime.listener.AutoJoinRole;
import com.newtime.listener.GithubListener;
import com.newtime.listener.LangRegist;
import com.newtime.listener.OnOmeChannelJoin;
import com.newtime.listener.StartUp;
import com.newtime.listener.buttons.BetaButton;
import com.newtime.listener.buttons.EchoTestButton;
import com.newtime.listener.buttons.NextButton;
import com.newtime.listener.buttons.PremiumButton;
import com.newtime.listener.buttons.SettingsButton;
import com.newtime.listener.buttons.ShutdownButton;
import com.newtime.listener.buttons.StartStopButton;
import com.newtime.listener.buttons.VoteButton;
import com.newtime.listener.menus.ToSListener;
import com.newtime.logger.Logs;
import com.newtime.system.ChattingListener;
import com.newtime.system.UpdateStatusEmbed;
import com.newtime.system.security.CatecoryDelete;
import com.newtime.system.security.CatecoryPermUpdate;
import com.newtime.system.security.ChannelDeleteDatabase;
import com.newtime.system.security.GuildLeave;
import com.newtime.system.security.OmeChannelDelete;
import com.newtime.system.security.UserJoin;
import com.newtime.util.TopGG;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.interactions.commands.Command.Choice;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.privileges.CommandPrivilege;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import okhttp3.OkHttpClient;

public class Main {

	//Static Things
	public static JDA jda;
	public static JDABuilder builder;
	public static ArrayList<Long> OwnerIds = new ArrayList<Long>();
	public static String prefix = "!!";
	public static long id = 838062574963523644l;
	public static String footer = "Version 1.0 || New-Time-Development";
	public static boolean Debug = false;
	
	public static void main(String[] args) throws IOException{
		
		/*if(args.length == 0) {
			 Debug = false;
		}else{
			if(args[0].equalsIgnoreCase("-debug")) {
				 Debug = true;
			}
		}*/
		
		//Debug
		 Debug = true;
		 
		//Sql
    	LiteSQL.connect();
    	SQLManger.onCreate();
		
    	//Logger
    	Logger.getLogger(OkHttpClient.class.getName()).setLevel(java.util.logging.Level.FINE);
    	
    	//Set Token
		builder = JDABuilder.createDefault(Tokens.DevToken);

		//Set Online Stats
		builder.setActivity(Activity.listening("...loading..."));
		builder.setAutoReconnect(true);
		
		builder.setStatus(OnlineStatus.ONLINE);
		
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
		builder.addEventListeners(new OnlineCommand());
		builder.addEventListeners(new PremiumButtonCommand());
		builder.addEventListeners(new SettingsCommand());
		builder.addEventListeners(new GithubListener());
		builder.addEventListeners(new EmbedCommand());
		builder.addEventListeners(new VoteCommand());
		
		//Securety
		builder.addEventListeners(new ChannelDeleteDatabase());
		builder.addEventListeners(new CatecoryPermUpdate());
		builder.addEventListeners(new OmeChannelDelete());
		builder.addEventListeners(new GuildLeave());
		builder.addEventListeners(new CatecoryDelete());
		builder.addEventListeners(new UserJoin());
		
		//Slash Command Listener
		builder.addEventListeners(new AboutSlahCommand());
    	builder.addEventListeners(new HelpShlashCommand());
		builder.addEventListeners(new RedeemSlashCommand());
    	builder.addEventListeners(new OmeSlashCommand());
		builder.addEventListeners(new SettingsSlashCommand());
    	builder.addEventListeners(new BetaSlashCommand());
    	builder.addEventListeners(new PremiumSlashCommand());
		builder.addEventListeners(new SetupSlashCommand());
    	builder.addEventListeners(new NextSlashCommand());
		builder.addEventListeners(new InviteSlashCommand());
    	builder.addEventListeners(new StartSlashCommand());
		builder.addEventListeners(new ReportSlashCommand());
    	builder.addEventListeners(new EftronSlashCommand());
		builder.addEventListeners(new DevSlashCommand());
		builder.addEventListeners(new PingSlashCommand());
		builder.addEventListeners(new LanguageSlashCommand());
		builder.addEventListeners(new JoinSlashCommand());
		builder.addEventListeners(new VoteSlashCommand());
		
    	//Buttons
		builder.addEventListeners(new ShutdownButton());
		builder.addEventListeners(new NextButton());
		builder.addEventListeners(new StartStopButton());
		builder.addEventListeners(new EchoTestButton());
		builder.addEventListeners(new SettingsButton());
		builder.addEventListeners(new PremiumButton());
		builder.addEventListeners(new BetaButton());
		builder.addEventListeners(new VoteButton());
		
		//Menus
		builder.addEventListeners(new ToSListener());
		
	    //Set Owners
	    OwnerIds.add(401059500972441600l);
	    OwnerIds.add(660887621169446964l);
	    
	   //Only Debug Commands
	    if(Debug) {
	    	builder.addEventListeners(new SendmENU());
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
	    
	    
	    //Get Slah Command Action
	    CommandListUpdateAction clua = jda.updateCommands();
	    
	    
	    //Add Slash Commands
	    clua.addCommands(new CommandData("vote", "Show informations about the top.gg page").addOptions(new OptionData(OptionType.STRING, "command", "Checks if you have voted!", false).addChoice("check", "check").addChoice("list", "list")));
	    clua.addCommands(new CommandData("about", "Information about the bot & contact")).queue();
	    clua.addCommands(new CommandData("setup", "Sets up the bot automatically"));
	    clua.addCommands(new CommandData("join", "Sets your custom join message || only Premium").addOption(OptionType.STRING, "color", "Your custom join embed color", true).addOption(OptionType.STRING, "message", "The custom jojn message", true));
	    clua.addCommands(new CommandData("next", "Connects you to a new user"));
	    clua.addCommands(new CommandData("start", " Adds you to the queue"));
	    clua.addCommands(new CommandData("ping", "Show you the bot ping"));
	    clua.addCommands(new CommandData("report", "Repot a user").addOption(OptionType.STRING, "message", "Your report message"));
	    clua.addCommands(new CommandData("invite", "Sends you the invitation link"));
	    clua.addCommands(new CommandData("help", "Show you the help menu"));
	    clua.addCommands(new CommandData("game", "Find a gamer").addOptions(new OptionData(OptionType.STRING, "game", "Your game", true).addChoice("minecraft", "minecraft")).setDefaultEnabled(false));
	    clua.addCommands(new CommandData("premium", "Shows you all premium features for the bot").addSubcommands(new SubcommandData("functions", "lists the premium functions")).addSubcommands(new SubcommandData("status", "Show if you have premium and how long")));
	    clua.addCommands(new CommandData("redeem", "Activates the premium functions").addOptions(new OptionData(OptionType.STRING, "code", "Your premium code", true)));
	    clua.addCommands(new CommandData("ome", "Creates a user for you").addOptions(new OptionData(OptionType.STRING, "language", "The language code of your language", true).addChoice("de", "de").addChoice("en", "en").addChoice("es", "es").addChoice("fr", "fr").addChoice("ru", "ru").addChoice("trk", "trk"), new OptionData(OptionType.STRING, "gender", "Your gender", true).addChoice("male", "male").addChoice("female", "female").addChoice("couple", "couple"), new OptionData(OptionType.BOOLEAN, "tranlations", "Yes for automatic translations", true)));
	    clua.addCommands(new CommandData("eftron", "Show more infos about the eftron bots").addOptions(new OptionData(OptionType.STRING, "bot", "Select a bot").addChoices(new Choice("Eftron", "EftronNormal"), new Choice("Eftron 24/7", "Eftron24"))));
	    clua.addCommands(new CommandData("settings", "You can edit you settings"));
	    clua.addCommands(new CommandData("language", "Set your bot language").addSubcommands(new SubcommandData("list", "lists the languages")).addSubcommands(new SubcommandData("set", "Set your language").addOptions(new OptionData(OptionType.STRING, "lang", "The language you want", true).addChoice("de", "de").addChoice("en", "en"))));
	 
	    //Send the Slah Update
	    clua.queue();
		
		//Open voice channel connections and starts the functions
	    start();
	    
	    //Load Top.gg api
	    TopGG.loadAPI(false);
	    
	    Logs.createFiles();
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
		
		Timer timer = new Timer(); 
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				Guild omecord = jda.getGuildById(845327767565631559l);
				omecord.upsertCommand("beta", "Sends you informations about our beta programm").queue();

				//Start update message
				 UpdateStatusEmbed.start();
				
				//Only Debug Slash-Commands
			    if(Debug) {
			    	List<Long> DevGuilds = Arrays.asList(845327767565631559l, 809699466734075904l);
			    	jda.upsertCommand(new CommandData("dev", "Only Debug/Developer Command")).setDefaultEnabled(false).queue(command ->{
			    		for(long guildid : DevGuilds) {
			    			Guild devGuild = jda.getGuildById(guildid);
			    			for(long id : OwnerIds) {
				    			User dev = jda.getUserById(id);
				    			command.updatePrivileges(devGuild, CommandPrivilege.enable(dev)).queue();
				    		}
			    		}
			    		
			    	});
			    }
			}
		}, 3000);
	
		
		for(Guild guilds : Main.jda.getGuilds()) {
			ResultSet set = LiteSQL.onQuery("SELECT * FROM guilds WHERE guildid = " + guilds.getIdLong());							
			
			try {
				if(set.next()) {
					long channelid = set.getLong("channelid");
					
					VoiceChannel setupCh = Main.jda.getVoiceChannelById(channelid);
					setupCh.getManager().putPermissionOverride(guilds.getPublicRole(), null, null).queue();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
		}
		
	
}
