package de.sage.system;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import de.sage.LiteSQL.LiteSQL;
import de.sage.chat.ChattingHandler;
import de.sage.listener.OnOmeChannelJoin;
import de.sage.util.global.EchoHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class Queue {

	// User die warten
	public static HashMap<HashMap<Long, Long>, HashMap<String, String>> WaitingUsers = new HashMap<>();
	// User die aktiv sind
	public static HashMap<HashMap<Member, Guild>, HashMap<Member, Guild>> activeUser = new HashMap<>();
	
	// User zur Warteliste hinzufügen
	public static String addUserToQueue(Guild guild, Member member, String lan, String gesch) {
		if (guild != null && member != null && lan != null && gesch != null) {

			long guildid = guild.getIdLong();

			long userid = member.getIdLong();

			HashMap<String, String> properties = new HashMap<>();
			properties.put(lan, gesch);
			HashMap<Long, Long> userData = new HashMap<>();
			userData.put(guildid, userid);

			if (!WaitingUsers.containsKey(userData)) {
				WaitingUsers.put(userData, properties);
				System.out.println(WaitingUsers);
				return "Guild: " + guildid + " User: " + userid;
			} else
				return "Error: Contais";

		}

		return "nichts";
	}

	// User aus der Wartelist entfertnen
	public static boolean removeUserFromQueue(Guild guild, Member member) {

		if (guild != null && member != null) {

			long guildid = guild.getIdLong();

			long userid = member.getIdLong();

			HashMap<Long, Long> userData = new HashMap<>();
			userData.put(guildid, userid);
			if (WaitingUsers.containsKey(userData)) {
				WaitingUsers.remove(userData);
			} else
				return false;
		} else
			return false;

		return true;
	}

	public static boolean matchUser(Guild guild, Member member){
		//Reset audio
		disconnectFromGuild(guild);
		// Get user key
		HashMap<Long, Long> key = new HashMap<>();
		key.put(guild.getIdLong(), member.getIdLong());

		// Deine probs
		HashMap<String, String> probs = WaitingUsers.get(key);
		
		WaitingUsers.remove(key, probs);
		
		// Versuche
		int i = 0;
		boolean hasTryed = true;
		// connection
		while (hasTryed) {
			
			System.out.println(i);
			if(i == 3) {
				hasTryed = false;
				break;
			}
			i++;
			
			// Alle keys
			Object[] keyArray = WaitingUsers.keySet().toArray();
			// radom key
			try {
				var randomKey = keyArray[new Random().nextInt(keyArray.length)];
				
				// matched user probs
				HashMap<String, String> machProbs = WaitingUsers.get(randomKey);
				// wenn die passen
				if(probs != null) {
					if (probs.equals(machProbs)) {
						// du halt
					//	WaitingUsers.remove(key, probs);
						HashMap<Long, Long> user = getKeyFromValue(WaitingUsers, machProbs);
						if(user != null) {
							if(user != key) {
								Member m2 = null;
								Guild g2 = null;
								System.out.println(user);
								for (Guild g : OnOmeChannelJoin.activeGuilds) {
									System.out.println(g);
									long guildid = g.getIdLong();
									if(guildid != guild.getIdLong()) {
										System.out.println("id");
										if (user.containsKey(guildid)) {
											System.out.println("contains");
											long userid = user.get(guildid);
											m2 = g.getMemberById(userid);
											g2 = g;
										}
									}


								}
								
								disconnectFromGuild(g2);
								
								if(member != m2) {
									addToActiveList(member, m2, guild, g2);
									
									return true;
								}
								
								
								// was passiert?
							}
						}


					} 
						
				}
			}catch(IllegalArgumentException e1) {
				return false;
			}

			
		}

		i = 0;
		hasTryed = true;
		
		while (hasTryed) {
			if(i == 3) {
				hasTryed = false;
				break;
			}
			i++;
			ResultSet getUserData = LiteSQL.onQuery("SELECT * FROM members WHERE userid = " + member.getIdLong());
			
			try {
				String lan = getUserData.getString("selcLan");
				
				Object[] keyArray = WaitingUsers.keySet().toArray();
				// radom key
				try {
					var randomKey = keyArray[new Random().nextInt(keyArray.length)];
					// matched user probs
				HashMap<String, String> machProbs = WaitingUsers.get(randomKey);
				
				Object[] matchLanTemp = WaitingUsers.keySet().toArray();
				// radom key
				var matchLan = matchLanTemp[new Random().nextInt(matchLanTemp.length)];
				
				if(lan.equals(matchLan)) {
				
					//WaitingUsers.remove(key, probs);
					HashMap<Long, Long> user = getKeyFromValue(WaitingUsers, machProbs);
					if(user != key) {
						Member m2 = null;
						Guild g2 = null;
						System.out.println(user);
						for (Guild g : OnOmeChannelJoin.activeGuilds) {
							System.out.println(g);
							long guildid = g.getIdLong();
							if(guildid != guild.getIdLong()) {
								System.out.println("id");
								if (user.containsKey(guildid)) {
									System.out.println("contains");
									long userid = user.get(guildid);
									m2 = g.getMemberById(userid);
									g2 = g;
								}
							}


						}
						if(member != m2) {
							addToActiveList(member, m2, guild, g2);
							
							i = 3;
							return true;
							
						}
				}
			}
				}catch(IllegalArgumentException e1) {
					return false;
				}

				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		Object[] keyArray = WaitingUsers.keySet().toArray();
		// radom key
		try {
			var randomKey = keyArray[new Random().nextInt(keyArray.length)];
			
			HashMap<String, String> machProbs = WaitingUsers.get(randomKey);
			
			//WaitingUsers.remove(key, probs);
			
			HashMap<Long, Long> user = getKeyFromValue(WaitingUsers, machProbs);
			if(user != key) {
				Member m2 = null;
				Guild g2 = null;
				System.out.println(user);
				for (Guild g : OnOmeChannelJoin.activeGuilds) {
					System.out.println(g);
					long guildid = g.getIdLong();
					if(guildid != guild.getIdLong()) {
						System.out.println("id");
						if (user.containsKey(guildid)) {
							System.out.println("contains");
							long userid = user.get(guildid);
							m2 = g.getMemberById(userid);
							g2 = g;
						}
					}


				}
				if(member != m2) {
					addToActiveList(member, m2, guild, g2);
					
					return true;
				}
		}
		}catch(IllegalArgumentException e1) {
			return false;
		}

		
		return false;
	}

	public static boolean addToActiveList(Member m1, Member m2, Guild g1, Guild g2) {

		if (m1 != null && m2 != null && g1 != null && g2 != null) {
			removeUserFromQueue(g2, m2);
			removeUserFromQueue(g1, m1);

			HashMap<Member, Guild> user1 = new HashMap<>();
			user1.put(m1, g1);

			HashMap<Member, Guild> user2 = new HashMap<>();
			user2.put(m2, g2);

			
			
			activeUser.put(user1, user2);
			
			connectToGuild(m1.getVoiceState().getChannel(), g2);
			connectToGuild(m2.getVoiceState().getChannel(), g1);

			TextChannel channe1 = OnOmeChannelJoin.omechannels.get(m1.getVoiceState().getChannel().getIdLong());
			TextChannel channe2 = OnOmeChannelJoin.omechannels.get(m2.getVoiceState().getChannel().getIdLong());
			
			connectChats(channe1, channe2, m1, m2);
			
			System.out.println(m1 + " -- " + m2);

		} else
			return false;

		return true;
	}

	public static boolean removeFromActiveList(Member member1, Member member2, Guild guilid1, Guild guilid2) {

		if (member1 != null && member2 != null) {
			HashMap<Member, Guild> user1 = new HashMap<>();
			user1.put(member1, guilid1);

			HashMap<Member, Guild> user2 = new HashMap<>();
			user2.put(member2, guilid2);
			if(activeUser.containsKey(user1)) {
				activeUser.remove(user1, user2);
				return true;
			}else if(activeUser.containsValue(user1)) {
				activeUser.remove(user2, user1);
				return true;
			}
		}

		return false;
	}

    /*public static void connectTo(Guild g1, Guild g2) {
 
    	List<Guild> guilds = Arrays.asList(g1, g2);
    	
   	 EchoHandler handler = new EchoHandler();
	 
	  for(Guild g : guilds) {
		  if(g == g1) {
  			AudioManager sendM1 = g2.getAudioManager();
	        // Set the sending handler to our echo system
  			sendM1.setSendingHandler(handler); 
  			AudioManager sendM = g1.getAudioManager();
	        // Set the sending handler to our echo system
  			sendM.setSendingHandler(handler); 

		  }else {

  			AudioManager receiM = g2.getAudioManager();
	        // Set the receiving handler to the same echo system, otherwise we can't echo anything
  			receiM.setReceivingHandler(handler);
  		  AudioManager receiM1 = g1.getAudioManager();
	        // Set the receiving handler to the same echo system, otherwise we can't echo anything
	  receiM1.setReceivingHandler(handler);
		  }
			  
	  }
   }*/
	
	private static void connectToGuild(VoiceChannel channel, Guild g2) {
		// Guild1 != Guild2, Beide Guilds können unterschiedlich sein, Guild2 und Guild1
		// können alle Guilds sein.

		Guild g1 = channel.getGuild();

		EchoHandler handler = new EchoHandler();
		
		//Guild tempGuild = Main.jda.getGuildById(825726076457844846l);

		//List<Guild> guilds = Arrays.asList(g1, g2, tempGuild);

		// Guild1
		AudioManager guild1ReceivedManager = g1.getAudioManager();

		AudioManager guild2SendManager = g2.getAudioManager();
		// Set the sending handler to our echo system
		
		guild2SendManager.setSendingHandler(null);
		guild1ReceivedManager.setReceivingHandler(null);
		
		guild2SendManager.setSendingHandler(handler);

		guild1ReceivedManager.setReceivingHandler(handler);
		g1.getAudioManager().openAudioConnection(channel);
	}
	
	public static boolean disconnectFromGuild(Guild g1) {
			if(g1 != null) {
				AudioManager guildAudioManager = g1.getAudioManager();
				
				System.out.println(g1);
				
				guildAudioManager.setReceivingHandler(null);
				guildAudioManager.setSendingHandler(null);
			}
		
		return false;
	}
	
	public static boolean connectChats(TextChannel channel1, TextChannel channel2, Member m1, Member m2) {
		
		if(channel1 != null && channel2 != null) {
			if(channel1 != channel2) {
				ChattingHandler.chats.put(channel1, channel2);
				channel1.sendMessage(new EmbedBuilder()
						.setTitle("New Connection")
						.setColor(Color.GREEN)
						.setDescription("Guild: **" + channel2.getGuild().getName() + "** \n User: **" + m2.getUser().getAsTag() + "**")
						.build()).queue();
				channel2.sendMessage(new EmbedBuilder()
						.setTitle("New Connection")
						.setColor(Color.GREEN)
						.setDescription("Guild: **" + channel1.getGuild().getName() + "** \n User: **" + m1.getUser().getAsTag() + "**")
						.build()).queue();
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean discordnnectChats(TextChannel channel1, TextChannel channel2) {
		
		if(channel1 != null && channel2 != null) {
			if(channel1 != channel2) {
				
				if(ChattingHandler.chats.containsKey(channel1)) {
					ChattingHandler.chats.remove(channel1, channel2);
					return true;
				}else if(ChattingHandler.chats.containsValue(channel1)) {
					ChattingHandler.chats.remove(channel2, channel1);
					return true;
				}
				
			}
		}
		return false;
	}
	
	public static <K, V> K getKeyFromValue(Map<K, V> map, Object value) {

		// get all map keys using keySet method
		Set<K> keys = map.keySet();

		// iterate all keys
		for (K key : keys) {

			// if maps value for the current key matches, return the key
			if (map.get(key).equals(value)) {
				return key;
			}
		}

		// if no values matches, return null
		return null;
	}

}
