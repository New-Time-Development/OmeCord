package de.sage.chat;

import java.awt.Color;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import de.sage.LiteSQL.LiteSQL;
import de.sage.listener.OnOmeChannelJoin;
import de.sage.main.Main;
import de.sage.system.Queue;
import de.sage.util.Translations;
import de.sage.util.Übersetzer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class ChattingHandler {

	public static HashMap<TextChannel, TextChannel> chats = new HashMap<>();

	private static List<String> blockedExtensions = Arrays.asList("jar", "sh", "bat", "exe", "vbs", "msi", "py", "com", "cmd", "hta", "wscript");
	
	public static boolean sendingMessage(TextChannel chatChannel, Message message, Member chatter, String lan) {
		// int 	getSize() 	The size of the attachment in bytes.
		if(chats.containsKey(chatChannel)){
			TextChannel partnerChannel = chats.get(chatChannel);
			if(message.getAttachments().size() >= 1) {
				for(Attachment attachments : message.getAttachments()) {
					if(!blockedExtensions.contains(attachments.getFileExtension())) {
						CompletableFuture<File> fileFuture = attachments.downloadToFile();
						File sendedFile =  fileFuture.join();
						if(attachments.getSize() <= 5000000) {
							partnerChannel.sendMessage(new EmbedBuilder()
									.setTitle("Ome.tv | Files" , "https://en.wikipedia.org/wiki/Computer_virus")
									.setDescription("We accept no liability for the execution of all files and their effect on your device. ")
									.setFooter(Main.footer)
									.setAuthor(chatter.getUser().getName())
									.setColor(Color.decode("#df600a"))
									.setTimestamp(new Date().toInstant())
									.build()).queue();
							partnerChannel.sendFile(sendedFile, "Upload file from **" + chatter.getUser().getAsTag() + "**." + attachments.getFileExtension()).queue();
						}else if(attachments.getSize() <= 15000000) {
							ResultSet isPremiumMember = LiteSQL.onQuery("SELECT * FROM premium userid = " + chatter.getIdLong());
							try {
								if(isPremiumMember.next()) {
									partnerChannel.sendMessage(new EmbedBuilder()
											.setTitle("Ome.tv | Files" , "https://en.wikipedia.org/wiki/Computer_virus")
											.setDescription("We accept no liability for the execution of all files and their effect on your device. ")
											.setFooter(Main.footer)
											.setAuthor(chatter.getUser().getName())
											.setColor(Color.decode("#df600a"))
											.setTimestamp(new Date().toInstant())
											.build()).queue();
									partnerChannel.sendFile(sendedFile, "Upload file from **" + chatter.getUser().getAsTag() + "**." + attachments.getFileExtension()).queue();
								}else
									chatChannel.sendMessage("You can only upload files that are up to 5 megabyte in size. *With Premium you can upload up to 15 megabyte.*").queue();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else {
							if(Main.OwnerIds.contains(chatter.getIdLong())) {
								partnerChannel.sendMessage(new EmbedBuilder()
										.setTitle("Ome.tv | Files" , "https://en.wikipedia.org/wiki/Computer_virus")
										.setDescription("We accept no liability for the execution of all files and their effect on your device. ")
										.setFooter(Main.footer)
										.setAuthor(chatter.getUser().getName())
										.setColor(Color.decode("#df600a"))
										.setTimestamp(new Date().toInstant())
										.build()).queue();
								partnerChannel.sendFile(sendedFile, "Upload file from **" + chatter.getUser().getAsTag() + "**." + attachments.getFileExtension()).queue();
							}else
								chatChannel.sendMessage("You can only upload files that are up to 5 megabyte in size. *With Premium you can upload up to 15 megabyte.*").queue();
						}
						
					}else
						chatChannel.sendMessage(Translations.blockedType(lan)).queue();
				}
			}else {
				long parterVoiceLong = Queue.getKeyFromValue(OnOmeChannelJoin.omechannels, partnerChannel);
				VoiceChannel partnerVoice = Main.jda.getVoiceChannelById(parterVoiceLong);
				Member partner = null;
				for(Member mems : partnerVoice.getMembers()) {
					if(mems.getUser().isBot() == false) {
						partner = mems;
					}
				}
				
				ResultSet getData = LiteSQL.onQuery("SELECT * FROM members WHERE userid = " + partner.getIdLong());
				try {
					if(getData.next()) {
						String übersetzung = getData.getString("ueber");
						if(übersetzung.equalsIgnoreCase("yes")) {
							partnerChannel.sendMessage( "*" + chatter.getUser().getAsTag() + " :* " + Übersetzer.übersetzer(message.getContentDisplay(), lan)).queue();
						}else {
							partnerChannel.sendMessage( "*" + chatter.getUser().getAsTag() + " :* " + message.getContentRaw()).queue();
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}else if(chats.containsValue(chatChannel)) {
		
			TextChannel partnerChannel = Queue.getKeyFromValue(chats, chatChannel);
			System.out.println(partnerChannel);
			if(message.getAttachments().size() >= 1) {
				for(Attachment attachments : message.getAttachments()) {
					if(!blockedExtensions.contains(attachments.getFileExtension())) {
						CompletableFuture<File> fileFuture = attachments.downloadToFile();
						File sendedFile =  fileFuture.join();
						if(attachments.getSize() <= 5000000) {
							partnerChannel.sendMessage(new EmbedBuilder()
									.setTitle("Ome.tv | Files" , "https://en.wikipedia.org/wiki/Computer_virus")
									.setDescription("We accept no liability for the execution of all files and their effect on your device. ")
									.setFooter(Main.footer)
									.setAuthor(chatter.getUser().getName())
									.setColor(Color.decode("#df600a"))
									.setTimestamp(new Date().toInstant())
									.build()).queue();
							partnerChannel.sendFile(sendedFile, "Upload file from **" + chatter.getUser().getAsTag() + "**." + attachments.getFileExtension()).queue();
						}else if(attachments.getSize() <= 15000000) {
							ResultSet isPremiumMember = LiteSQL.onQuery("SELECT * FROM premium userid = " + chatter.getIdLong());
							try {
								if(isPremiumMember.next()) {
									partnerChannel.sendMessage(new EmbedBuilder()
											.setTitle("Ome.tv | Files" , "https://en.wikipedia.org/wiki/Computer_virus")
											.setDescription("We accept no liability for the execution of all files and their effect on your device. ")
											.setFooter(Main.footer)
											.setAuthor(chatter.getUser().getName())
											.setColor(Color.decode("#df600a"))
											.setTimestamp(new Date().toInstant())
											.build()).queue();
									partnerChannel.sendFile(sendedFile, "Upload file from **" + chatter.getUser().getAsTag() + "**." + attachments.getFileExtension()).queue();
								}else
									chatChannel.sendMessage("You can only upload files that are up to 5 megabyte in size. *With Premium you can upload up to 15 megabyte.*").queue();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else {
							if(Main.OwnerIds.contains(chatter.getIdLong())) {
								partnerChannel.sendMessage(new EmbedBuilder()
										.setTitle("Ome.tv | Files" , "https://en.wikipedia.org/wiki/Computer_virus")
										.setDescription("We accept no liability for the execution of all files and their effect on your device. ")
										.setFooter(Main.footer)
										.setAuthor(chatter.getUser().getName())
										.setColor(Color.decode("#df600a"))
										.setTimestamp(new Date().toInstant())
										.build()).queue();
								partnerChannel.sendFile(sendedFile, "Upload file from **" + chatter.getUser().getAsTag() + "**." + attachments.getFileExtension()).queue();
							}else
								chatChannel.sendMessage("You can only upload files that are up to 5 megabyte in size. *With Premium you can upload up to 15 megabyte.*").queue();
						}
						
					}else
						chatChannel.sendMessage(Translations.blockedType(lan)).queue();
				}
			}else {
				long parterVoiceLong = Queue.getKeyFromValue(OnOmeChannelJoin.omechannels, partnerChannel);
				VoiceChannel partnerVoice = Main.jda.getVoiceChannelById(parterVoiceLong);
				Member partner = null;
				for(Member mems : partnerVoice.getMembers()) {
					if(mems.getUser().isBot() == false) {
						partner = mems;
					}
				}
				ResultSet getData = LiteSQL.onQuery("SELECT * FROM members WHERE userid = " + partner.getIdLong());
				try {
					if(getData.next()) {
						String übersetzung = getData.getString("ueber");
						if(übersetzung.equalsIgnoreCase("yes")) {
							partnerChannel.sendMessage( "*" + chatter.getUser().getAsTag() + " :* " + Übersetzer.übersetzer(message.getContentDisplay(), lan)).queue();
						}else {
							partnerChannel.sendMessage( "*" + chatter.getUser().getAsTag() + " :* " + message.getContentRaw()).queue();
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return false;
	}
	
	public static boolean sendPing(TextChannel chatChannel, Member m) {
		if(chats.containsKey(chatChannel)){
			TextChannel partnerChannel = chats.get(chatChannel);
			
			Guild guild = chatChannel.getGuild();
			
			HashMap<Member, Guild> member1 = new HashMap<>();
			member1.put(m, guild);
			
			if(Queue.activeUser.containsKey(member1)) {
				HashMap<Member, Guild> member2 = Queue.activeUser.get(member1);
				Object[] keyArray = member2.keySet().toArray();
				// radom key
				var randomKey = keyArray[new Random().nextInt(keyArray.length)];
				
				partnerChannel.sendMessage("<@" + ((Member) randomKey).getIdLong() + ">").queue();
				
			}else if(Queue.activeUser.containsValue(member1)) {
				HashMap<Member, Guild> member2 = Queue.getKeyFromValue(Queue.activeUser, member1);
				Object[] keyArray = member2.keySet().toArray();
				// radom key
				var randomKey = keyArray[new Random().nextInt(keyArray.length)];
				
				partnerChannel.sendMessage("<@" + ((Member) randomKey).getIdLong() + ">").queue();
			}
			
			
		}else if(chats.containsValue(chatChannel)) {
			TextChannel partnerChannel = Queue.getKeyFromValue(chats, chatChannel);
			Guild guild = chatChannel.getGuild();
			
			HashMap<Member, Guild> member1 = new HashMap<>();
			member1.put(m, guild);
			
			if(Queue.activeUser.containsKey(member1)) {
				HashMap<Member, Guild> member2 = Queue.activeUser.get(member1);
				Object[] keyArray = member2.keySet().toArray();
				// radom key
				var randomKey = keyArray[new Random().nextInt(keyArray.length)];
				
				partnerChannel.sendMessage("<@" + ((Member) randomKey).getIdLong() + ">").queue();
				
			}else if(Queue.activeUser.containsValue(member1)) {
				HashMap<Member, Guild> member2 = Queue.getKeyFromValue(Queue.activeUser, member1);
				Object[] keyArray = member2.keySet().toArray();
				// radom key
				var randomKey = keyArray[new Random().nextInt(keyArray.length)];
				
				partnerChannel.sendMessage("<@" + ((Member) randomKey).getIdLong() + ">").queue();
			}
		}
		return false;
	}

}
