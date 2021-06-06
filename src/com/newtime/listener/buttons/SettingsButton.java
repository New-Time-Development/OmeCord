package com.newtime.listener.buttons;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.newtime.command.SettingsCommand;
import com.newtime.database.LiteSQL;
import com.newtime.main.Main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class SettingsButton extends ListenerAdapter {

	@SuppressWarnings("unused")
	public void onButtonClick(ButtonClickEvent event) {
		
			//Get informations
			Member m = event.getMember();
			Guild g = event.getGuild();
			Message mes = event.getMessage();
			Button button = event.getButton();
			
			ResultSet lanRS = LiteSQL.onQuery("SELECT * FROM lang WHERE userid = " + m.getIdLong());
			String lan = null;
			try {
				if(lanRS.next()) {
					lan = lanRS.getString("botLan");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			ResultSet premiumRS = LiteSQL.onQuery("SELECT * FROM premium WHERE userid = " + m.getIdLong());
			boolean premium = true;
			try {
				if(premiumRS.next()) {
					premium = false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(event.getChannelType().equals(ChannelType.TEXT)) {
				//Text Channel
				if(SettingsCommand.messages.get(event.getMember().getIdLong()) != null) {
					if(SettingsCommand.messages.get(event.getMember().getIdLong()) == event.getMessageIdLong()) {
						ResultSet userData = LiteSQL.onQuery("SELECT * FROM members WHERE userid = " + event.getMember().getIdLong());
						try {
							if(userData.next()) {
								String lang = userData.getString("selcLan");
								String gender = userData.getString("gen");
								String trans = userData.getString("ueber");
								if(event.getComponentId().equalsIgnoreCase("bot-settings")) {
									Emoji genderEmote = null;
									if(gender.equalsIgnoreCase("male")) {
										genderEmote = Emoji.fromUnicode("👨");
									}else if(gender.equalsIgnoreCase("female")) {
										genderEmote = Emoji.fromUnicode("👩");
									}else if(gender.equalsIgnoreCase("couple")) {
										genderEmote = Emoji.fromUnicode("👫");
									}
									
									Emoji languageEmote = null;
									if(lang.equalsIgnoreCase("de")) {
										languageEmote = Emoji.fromUnicode("🇩🇪");
									}else if(lang.equalsIgnoreCase("en")) {
										languageEmote = Emoji.fromUnicode("🇬🇧");
									}else if(lang.equalsIgnoreCase("fr")) {
										languageEmote = Emoji.fromUnicode("🇫🇷");
									}else if(lang.equalsIgnoreCase("ru")) {
										languageEmote = Emoji.fromUnicode("🇷🇺");
									}else if(lang.equalsIgnoreCase("trk")) {
										languageEmote = Emoji.fromUnicode("🇹🇷");
									}else if(lang.equalsIgnoreCase("es")) {
										languageEmote = Emoji.fromUnicode("🇱🇰");
									}
									
									Emoji translatorEmote = null;
									if(trans.equalsIgnoreCase("yes")) {
										translatorEmote = Emoji.fromUnicode("🗺️");
									}else
										translatorEmote = Emoji.fromUnicode("🏳️");
									mes.editMessage(new EmbedBuilder()
											.setTitle("OmeCord || Settings")
											.setDescription("Here you can **change** your settings. Click the **appropriate button** to configure your user. Press <:error123:845331037138976809> to **cancel** the configuration!\r\n"
													+ "\n"
													+ "**Assistance:**\n\r"
													+ genderEmote.getAsMention() + " **»** By clicking on this **button** you can configure your **gender**.\r\n"
													+ languageEmote.getAsMention() + " **»** By clicking on this **button** you can configure your **language**.\r\n"
													+ translatorEmote.getAsMention() +" **»** By clicking this **button** you can enable/disable the **automatic translation**.")
											.setColor(Color.decode("#e05e36"))
											.setFooter(Main.footer)
											.setTimestamp(new Date().toInstant())
											.build()).setActionRow(
													Button.secondary("settings-back", Emoji.fromMarkdown("<:error123:845331037138976809>")),
													Button.secondary("settings-gender", genderEmote),
													Button.secondary("settings-lang", languageEmote),
													Button.secondary("settings-trans", translatorEmote)
													
													).queue();
									
								}else if(event.getComponentId().equalsIgnoreCase("bot-language")) {
									Emoji languageEmote = null;
									if(lan.equalsIgnoreCase("de")) {
										languageEmote = Emoji.fromUnicode("🇩🇪");
									}else if(lan.equalsIgnoreCase("en")) {
										languageEmote = Emoji.fromUnicode("🇬🇧");
									}
									
									mes.editMessage(new EmbedBuilder()
											.setTitle("Bot Language")
											.setColor(Color.RED)
											.setDescription("Choose your bot language")
											.build()).setActionRow(
													Button.secondary("settings-back",  Emoji.fromMarkdown("<:error123:845331037138976809>")),
													Button.secondary("language", languageEmote)
													).queue();
								}else if(event.getComponentId().equalsIgnoreCase("settings-back")) {
									Emoji botLang = null;
									if(lan.equalsIgnoreCase("de")) {
										botLang = Emoji.fromUnicode("🇩🇪");
									}else if(lan.equalsIgnoreCase("en")) {
										botLang = Emoji.fromUnicode("🇬🇧");
									}
									
									Emoji botsettingsEmote = Emoji.fromUnicode("🌍");
									Emoji premiumEmote = Emoji.fromUnicode("⭐");
									
									mes.editMessage(new EmbedBuilder()
											.setTitle("Settings")
											//.setDescription("Here you can **change** your settings. Click the **appropriate button** to configure \nyour user. If you have **activated** Premium, you can click on the star(:star:) and **configure**\n Premium. Press <:error123:845331037138976809> to **cancel** the configuration!")
											.setColor(Color.decode("#e05e36"))
											
											
											.build()).setActionRow(
													Button.secondary("bot-language", botLang),
													Button.secondary("bot-settings", botsettingsEmote),
													Button.secondary("premium-functions", premiumEmote).withDisabled(premium)
													).queue();
								}else if(event.getComponentId().equalsIgnoreCase("premium-functions")) {
									mes.editMessage(new EmbedBuilder()
											.setTitle("Settings")
											.setDescription("Here you can **change** your settings. Click the **appropriate button** to configure \nyour user. If you have **activated** Premium, you can click on the star(:star:) and **configure**\n Premium. Press <:error123:845331037138976809> to **cancel** the configuration!")
											.setColor(Color.decode("#e05e36"))
											.addField("Assistance:", "", false)
											.build()).setActionRow(
													Button.secondary("settings-back",  Emoji.fromMarkdown("<:error123:845331037138976809>")),
													Button.secondary("custom-join", "Comming soon")
													).queue();
								}else if(event.getComponentId().equalsIgnoreCase("settings-trans")) {
									
									if(event.getButton().getEmoji().equals(Emoji.fromUnicode("🗺️"))) {
										LiteSQL.onUpdate("UPDATE members SET ueber = 'no' WHERE userid = " + event.getMember().getIdLong());
									}else {
										LiteSQL.onUpdate("UPDATE members SET ueber = 'yes' WHERE userid = " + event.getMember().getIdLong());
									}
									
									reload(mes, m, "bot-settings");
								
								}else if(event.getComponentId().equalsIgnoreCase("settings-gender")) {
									
									if(event.getButton().getEmoji().equals(Emoji.fromUnicode("👨"))) {
										LiteSQL.onUpdate("UPDATE members SET gen = 'female' WHERE userid = " + event.getMember().getIdLong());
									}else if(event.getButton().getEmoji().equals(Emoji.fromUnicode("👩"))) {
										LiteSQL.onUpdate("UPDATE members SET gen = 'couple' WHERE userid = " + event.getMember().getIdLong());
									}else if(event.getButton().getEmoji().equals(Emoji.fromUnicode("👫"))) {{
										LiteSQL.onUpdate("UPDATE members SET gen = 'male' WHERE userid = " + event.getMember().getIdLong());
									}
									
									}
									
									reload(mes, m, "bot-settings");
									
									//event.getHook().deleteOriginal().queue();
									
									//event.getChannel().sendMessage("Your settings are updated").queue();
									
								}else if(event.getComponentId().equalsIgnoreCase("settings-lang")) {
									if(event.getButton().getEmoji().equals(Emoji.fromUnicode("🇩🇪"))) {
										LiteSQL.onUpdate("UPDATE members SET selcLan = 'en' WHERE userid = " + event.getMember().getIdLong());
									}else if(event.getButton().getEmoji().equals(Emoji.fromUnicode("🇬🇧"))) {
										LiteSQL.onUpdate("UPDATE members SET selcLan = 'fr' WHERE userid = " + event.getMember().getIdLong());
									}else if(event.getButton().getEmoji().equals(Emoji.fromUnicode("🇫🇷"))) {
										LiteSQL.onUpdate("UPDATE members SET selcLan = 'ru' WHERE userid = " + event.getMember().getIdLong());
									}else if(event.getButton().getEmoji().equals(Emoji.fromUnicode("🇷🇺"))) {
										LiteSQL.onUpdate("UPDATE members SET selcLan = 'trk' WHERE userid = " + event.getMember().getIdLong());
									}else if(event.getButton().getEmoji().equals(Emoji.fromUnicode("🇹🇷"))) {
										LiteSQL.onUpdate("UPDATE members SET selcLan = 'es' WHERE userid = " + event.getMember().getIdLong());
									}else if(event.getButton().getEmoji().equals(Emoji.fromUnicode("🇱🇰"))) {
										LiteSQL.onUpdate("UPDATE members SET selcLan = 'de' WHERE userid = " + event.getMember().getIdLong());
									}
									
									reload(mes, m, "bot-settings");
									
									//event.getHook().deleteOriginal().queue();
									
									//event.getChannel().sendMessage("Your settings are updated").queue();
								}else if(event.getComponentId().equalsIgnoreCase("language")) {
									if(event.getButton().getEmoji().equals(Emoji.fromUnicode("🇩🇪"))) {
										LiteSQL.onUpdate("UPDATE lang SET botLan = 'en' WHERE userid = " + event.getMember().getIdLong());
									}else if(event.getButton().getEmoji().equals(Emoji.fromUnicode("🇬🇧"))) {
										LiteSQL.onUpdate("UPDATE lang SET botLan = 'de' WHERE userid = " + event.getMember().getIdLong());
									}
									
									reload(mes, m, "bot-language");
								}else if(event.getComponentId().equalsIgnoreCase("custom-join")) {
									event.getChannel().sendMessage("Custom join message in the settings command is comming soon").queue();
									reload(mes, m, "premium");
								}
							}

						}catch(SQLException e) {
							e.printStackTrace();
						}
					}

				}

			}else {
				//Private Message
				
			}
		}
	
	private void reload(Message mes, Member m, String reloaded) {
		
		ResultSet lanRS = LiteSQL.onQuery("SELECT * FROM lang WHERE userid = " + m.getIdLong());
		String lan = null;
		try {
			if(lanRS.next()) {
				lan = lanRS.getString("botLan");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ResultSet premiumRS = LiteSQL.onQuery("SELECT * FROM premium WHERE userid = " + m.getIdLong());
		@SuppressWarnings("unused")
		boolean premium = true;
		try {
			if(premiumRS.next()) {
				premium = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(reloaded.equalsIgnoreCase("bot-settings")) {
			ResultSet userData = LiteSQL.onQuery("SELECT * FROM members WHERE userid = " + m.getIdLong());
			try {
				if(userData.next()) {
					String lang = userData.getString("selcLan");
					String gender = userData.getString("gen");
					String trans = userData.getString("ueber");
						Emoji genderEmote = null;
						if(gender.equalsIgnoreCase("male")) {
							genderEmote = Emoji.fromUnicode("👨");
						}else if(gender.equalsIgnoreCase("female")) {
							genderEmote = Emoji.fromUnicode("👩");
						}else if(gender.equalsIgnoreCase("couple")) {
							genderEmote = Emoji.fromUnicode("👫");
						}
						
						Emoji languageEmote = null;
						if(lang.equalsIgnoreCase("de")) {
							languageEmote = Emoji.fromUnicode("🇩🇪");
						}else if(lang.equalsIgnoreCase("en")) {
							languageEmote = Emoji.fromUnicode("🇬🇧");
						}else if(lang.equalsIgnoreCase("fr")) {
							languageEmote = Emoji.fromUnicode("🇫🇷");
						}else if(lang.equalsIgnoreCase("ru")) {
							languageEmote = Emoji.fromUnicode("🇷🇺");
						}else if(lang.equalsIgnoreCase("trk")) {
							languageEmote = Emoji.fromUnicode("🇹🇷");
						}else if(lang.equalsIgnoreCase("es")) {
							languageEmote = Emoji.fromUnicode("🇱🇰");
						}
						
						Emoji translatorEmote = null;
						if(trans.equalsIgnoreCase("yes")) {
							translatorEmote = Emoji.fromUnicode("🗺️");
						}else
							translatorEmote = Emoji.fromUnicode("🏳️");
						mes.editMessage(new EmbedBuilder()
								.setTitle("OmeCord || Settings")
								.setDescription("Here you can **change** your settings. Click the **appropriate button** to configure your user. Press <:error123:845331037138976809> to **cancel** the configuration!\r\n"
										+ "\n"
										+ "**Assistance:**\n\r"
										+ genderEmote.getAsMention() + " **»** By clicking on this **button** you can configure your **gender**.\r\n"
										+ languageEmote.getAsMention() + " **»** By clicking on this **button** you can configure your **language**.\r\n"
										+ translatorEmote.getAsMention() +" **»** By clicking this **button** you can enable/disable the **automatic translation**.")
								.setColor(Color.decode("#e05e36"))
								.setFooter(Main.footer)
								.setTimestamp(new Date().toInstant())
								.build()).setActionRow(
										Button.secondary("settings-back", Emoji.fromMarkdown("<:error123:845331037138976809>")),
										Button.secondary("settings-gender", genderEmote),
										Button.secondary("settings-lang", languageEmote),
										Button.secondary("settings-trans", translatorEmote)
										
										).queue();
					}	
				}catch (SQLException e) {
					e.printStackTrace();
				}
						
		}else if(reloaded.equalsIgnoreCase("bot-language")) {
			Emoji languageEmote = null;
			if(lan.equalsIgnoreCase("de")) {
				languageEmote = Emoji.fromUnicode("🇩🇪");
			}else if(lan.equalsIgnoreCase("en")) {
				languageEmote = Emoji.fromUnicode("🇬🇧");
			}
			
			mes.editMessage(new EmbedBuilder()
					.setTitle("Bot Language")
					.setColor(Color.RED)
					.setDescription("Choose your bot language")
					.build()).setActionRow(
							Button.secondary("settings-back",  Emoji.fromMarkdown("<:error123:845331037138976809>")),
							Button.secondary("language", languageEmote)
							).queue();
		}else if(reloaded.equalsIgnoreCase("premium")) {
			mes.editMessage(new EmbedBuilder()
					.setTitle("Settings")
					.setDescription("Here you can **change** your settings. Click the **appropriate button** to configure \nyour user. If you have **activated** Premium, you can click on the star(:star:) and **configure**\n Premium. Press <:error123:845331037138976809> to **cancel** the configuration!")
					.setColor(Color.decode("#e05e36"))
					.addField("Assistance:", "", false)
					.build()).setActionRow(
							Button.secondary("settings-back",  Emoji.fromMarkdown("<:error123:845331037138976809>")),
							Button.secondary("custom-join", "Comming soon")
							).queue();
		}
		
	}
}
