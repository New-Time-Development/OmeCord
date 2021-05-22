package de.sage.listener;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import de.sage.database.LiteSQL;
import de.sage.main.Main;
import de.sage.system.Queue;
import de.sage.system.handler.ChattingHandler;
import de.sage.util.Translations;
import de.sage.util.checks.CheckMute;
import de.sage.util.checks.CheckPremium;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class OnOmeChannelJoin extends ListenerAdapter {

	public static HashMap<Long, TextChannel> omechannels = new HashMap<>();
	public static List<Guild> activeGuilds  = new ArrayList<>();
	
	@Override
	public void onGuildVoiceJoin(GuildVoiceJoinEvent e) {
		onJoin(e.getChannelJoined(),  e.getEntity());
		
	}
	
	@Override
	public void onGuildVoiceLeave(GuildVoiceLeaveEvent e) {
		onLeave(e.getChannelLeft(), e.getMember());

		
	}
	@Override
	public void onGuildVoiceMove(GuildVoiceMoveEvent e) {
		onLeave(e.getChannelLeft(), e.getMember());
		onJoin(e.getChannelJoined(), e.getEntity());
	}
	
	public void onJoin(VoiceChannel joined, Member member) {
		
		ResultSet set = LiteSQL.onQuery("SELECT * FROM guilds WHERE guildid = " + joined.getGuild().getIdLong());

		try {
			
		
			if(set.next()) {
			long channelid = set.getLong("channelid");
			if(joined.getIdLong() == channelid) {
				if(!activeGuilds.contains(joined.getGuild())) {
					
					
					
					ResultSet getUserData = LiteSQL.onQuery("SELECT * FROM 'members' WHERE userid = " + member.getIdLong());
					
					
					if(getUserData.next()) {
						
						int isMuted = getUserData.getInt("mute");
						
						ResultSet lanRS = LiteSQL.onQuery("SELECT * FROM lang WHERE userid = " + member.getIdLong());
						String lan = null;
						try {
							if(lanRS.next()) {
							lan = lanRS.getString("botLan");
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						boolean isPremium = false;

						CheckMute.checkStatus(member, member.getUser().openPrivateChannel(), lan);
						
						if(isMuted == 1) {
							joined.getGuild().kickVoiceMember(member).queue();
							member.getUser().openPrivateChannel().queue(privateChannel ->{
									
							});
							
							return;
						}
						
						activeGuilds.add(joined.getGuild());

						Category cat = joined.getParent();
						VoiceChannel vc = cat.createVoiceChannel("Ome.tv - " + member.getEffectiveName()).complete();

						vc.getManager().setUserLimit(2).queue();

				
						vc.getManager().putPermissionOverride(member, EnumSet.of( Permission.VOICE_SPEAK, Permission.VOICE_USE_VAD, Permission.VIEW_CHANNEL, Permission.VOICE_CONNECT) , null).queue();


						//vc.getManager().putPermissionOverride(joined.getGuild().getPublicRole(), null , null).queue();
				    	
				    		
						
						vc.getGuild().moveVoiceMember(member, vc).queue();
						
						//vc.getGuild().getAudioManager().openAudioConnection(vc);
						
						TextChannel tc = cat.createTextChannel("Ome.tv - Chat").complete();
						
						tc.sendMessage(member.getAsMention()).complete().delete().queueAfter(1, TimeUnit.SECONDS);
						

		
						tc.getManager().setTopic(Translations.ChatTopic(lan)).queue();
						tc.sendMessage(new EmbedBuilder()
								.setTitle("Rules")
								.setDescription(Translations.ChatTopic(lan))
								.setColor(Color.YELLOW)
								.build()).queue();
						
						
						tc.getManager().putPermissionOverride(member, EnumSet.of(Permission.MESSAGE_WRITE, Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_ATTACH_FILES, Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_EXT_EMOJI), null).queue();
					//	tc.getManager().putPermissionOverride(joined.getGuild().getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_WRITE, Permission.MESSAGE_HISTORY)).queue();
						
						
						CheckPremium.checkStatus(member, tc, lan);
						
						ResultSet isPremiumUser = LiteSQL.onQuery("SELECT * FROM premium WHERE userid = " + member.getIdLong());
						
						if(isPremiumUser.next()) {
							isPremium = true;
						}
						
						if(isPremium == false) {
							tc.getManager().setSlowmode(5).queue();
						}
						
						if(isPremium == true) {
							vc.getManager().setBitrate(vc.getGuild().getMaxBitrate()).queue();
						}else
							vc.getManager().setBitrate(32000).queue();
						
						VoiceChannel setupCh = Main.jda.getVoiceChannelById(channelid);
						setupCh.getManager().putPermissionOverride(joined.getGuild().getPublicRole(), null, EnumSet.of(Permission.VOICE_CONNECT)).queue();
						
						omechannels.put(vc.getIdLong(), tc);
						
						isPremium = false;
						
					}else {
					
						member.getUser().openPrivateChannel().queue(privateChannwl->{
							ResultSet lanRS = LiteSQL.onQuery("SELECT * FROM lang WHERE userid = " + member.getIdLong());
							String lan = null;
							try {
								if(lanRS.next()) {
								lan = lanRS.getString("botLan");
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							privateChannwl.sendMessage(Translations.database(lan)).queue(null , null
							);
						});
					}
				}else {
					member.getUser().openPrivateChannel().queue(privateChannwl->{
						ResultSet lanRS = LiteSQL.onQuery("SELECT * FROM lang WHERE userid = " + member.getIdLong());
						String lan = null;
						try {
							if(lanRS.next()) {
							lan = lanRS.getString("botLan");
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						privateChannwl.sendMessage(Translations.isInUse(lan)).queue(null, null);
					});
				}


			 }
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void onLeave(VoiceChannel ch, Member leaved) {
	
	
		if(ch.getMembers().size() <= 1) {
			if(omechannels.containsKey(ch.getIdLong())) {
				
				ch.getGuild().getAudioManager().closeAudioConnection();
				
				TextChannel val = omechannels.get(ch.getIdLong());
				omechannels.remove(ch.getIdLong(), val);
				val.delete().queue();
				ch.delete().queue();
				
				HashMap<Member, Guild> user = new HashMap<>();
				user.put(leaved, ch.getGuild());
				
				HashMap<Long, Long> userLongs = new HashMap<>();
				userLongs.put(leaved.getIdLong(), ch.getGuild().getIdLong());
				
				
				
				if(Queue.WaitingUsers.containsKey(userLongs)) {
					Queue.removeUserFromQueue(ch.getGuild(), leaved);
				}
				
				if(ChattingHandler.chats.containsKey(val)){
					TextChannel channel2 = ChattingHandler.chats.get(val);
					ChattingHandler.chats.remove(val, channel2);
				}else if(ChattingHandler.chats.containsValue(val)) {
					TextChannel channel2 = Queue.getKeyFromValue(ChattingHandler.chats, val);
					ChattingHandler.chats.remove(channel2, val);
				}
				
				if(Queue.activeUser.containsKey(user)) {
					HashMap<Member, Guild> user2 = Queue.activeUser.get(user);
					
					Object[] keyArray = user2.keySet().toArray();
					// radom key
					var randomKey = keyArray[new Random().nextInt(keyArray.length)];
					
					Guild g1 = user2.get(randomKey);
					
					Queue.activeUser.remove(user, user2);
					
					Queue.matchUser(g1, (Member) randomKey);
				}else if(Queue.activeUser.containsValue(user)){
					System.out.println(user);
					HashMap<Member, Guild> user2 = Queue.getKeyFromValue(Queue.activeUser, user);
					
					System.out.println(user2);
					
					Object[] keyArray = user2.keySet().toArray();
					// radom key
					var randomKey = keyArray[new Random().nextInt(keyArray.length)];
					
					Guild g1 = user2.get(randomKey);
					
					Queue.activeUser.remove(user2, user);
					
					Queue.matchUser(g1, (Member) randomKey);
				}
				
				activeGuilds.remove(ch.getGuild());
				
				System.out.println(Queue.WaitingUsers);
				System.out.println(Queue.activeUser);
				
				ResultSet set = LiteSQL.onQuery("SELECT * FROM guilds WHERE guildid = " + ch.getGuild().getIdLong());
				long channelid;
				try {
					channelid = set.getLong("channelid");
					VoiceChannel setupCh = Main.jda.getVoiceChannelById(channelid);
					setupCh.getManager().putPermissionOverride(ch.getGuild().getPublicRole(),  EnumSet.of(Permission.VOICE_CONNECT), null).queue();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
		}
	}
}