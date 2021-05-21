package de.sage.util;

import java.awt.Color;

import de.sage.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class Translations {
	
	public static String UserReport(String lan, String stat, String repo) {
		
		String temp = "Invaild";
		
		if(stat.equalsIgnoreCase("dm")) {
			if(lan.equalsIgnoreCase("de")) {
				temp = "Dein Report: **" + repo + "** wurde gesendet! Danke <3";
				}else if(lan.equalsIgnoreCase("en")) {
				temp = "Your Report: **" + repo + "** was send. Thank you <3";
				}
		}else if(stat.equalsIgnoreCase("dmSen")) {
			if(lan.equalsIgnoreCase("de")) {
				temp = "Du hast eine DM bekommen";
				}else if(lan.equalsIgnoreCase("en")) {
				temp = "You got a dm :)";
				}
		}
		
		return temp;
		
	}
	
	public static String OmeCmd(String lan, String stat) {
		
		String temp = "Invaild";
		
		if(stat.equalsIgnoreCase("lang")) {
			if(lan.equalsIgnoreCase("de")) {
				temp = "Bitte nutze eine der folgenden Sprach-Kürtzel: **de**, **en**, **trk**, **fr**, **...**";
				}else if(lan.equalsIgnoreCase("en")) {
				temp = "Please use one of the following language codes: **de**, **en**, **trk**, **fr**, **...**";
				}
		}else if(stat.equalsIgnoreCase("gen")) {
			if(lan.equalsIgnoreCase("de")) {
				temp = "Bitte nutze eine der folgenden Geschlechte: **man**, **woman**, **paar**";
				}else if(lan.equalsIgnoreCase("en")) {
				temp = "Please use one of the following gender **man**, **woman**, **paar**";
				}
		}else if(stat.equalsIgnoreCase("insert")) {
			if(lan.equalsIgnoreCase("de")) {
				temp = "Du wurdest erfolgreich registriert. Jetzt kannst du den Bot uneingeschränkt nutzen";
				}else if(lan.equalsIgnoreCase("en")) {
				temp = "You have been successfully registered. Now you can use the bot without restrictions.";
				}
		}else if(stat.equalsIgnoreCase("update")) {
			if(lan.equalsIgnoreCase("de")) {
				temp = "Deine Informationen wurden aktualisiert. Du kannst nun den Bot wieder nutzen";
				}else if(lan.equalsIgnoreCase("en")) {
				temp = "Your information has been updated. You can now use the bot again";
				}
		}
		
		return temp;
		
	}
	
	public static String ChatTopic(String lan) {

		String temp = "Invaild";
		
			if(lan.equalsIgnoreCase("de")) {
				temp = "Regeln des Chats: \r\n"
						+ " -Keine Commands von Eftron und/oder anderen Bots \r\n"
						+ " -Keine Links zu schlimmen/bösen Seiten \r\n"
						+ " -Keine +18 bzw. icht jungfreie Inhalte \r\n"
						+ " -Keine Passwörter/Accounts leaken \r\n"
						+ " -Keine Werbung \r\n"
						+ " -Kein Spam \r\n"
						+ " -An diese Regeln halten xD\r\n"
						+ "-------------------------------------------------------------\r\n"
						+ "Links:\r\n"
						+ "Bot Invite:\r\n"
						+ "Server:\r\n"
						+ "Ome website\r\n"
						+ "Top.gg\r\n"
						+ "-------------------------------------------------------------\r\n"
						+ "Wichtige Commands:\r\n"
						+ "!report\r\n"
						+ "!setup\r\n"
						+ "!help\r\n"
						+ "-------------------------------------------------------------\r\n"
						+ "Bot Team:\r\n"
						+ "Devs:\r\n"
						+ "Sage\r\n"
						+ "Tom\r\n"
						+ "Mods:\r\n"
						+ "...\r\n"
						+ "------------------------------------------------------------\r\n"
						+ "Danke fürs nutzen des Bots";
				}else if(lan.equalsIgnoreCase("en")) {
				temp = "Chat rules: \r\n"
						+ " -No commands from Eftron and/or other bots. \r\n"
						+ " -No links to bad/evil sites \r\n"
						+ " -No +18 or non-virgin content \r\n"
						+ " -Do not leak passwords/accounts \r\n"
						+ " -No advertising \r\n"
						+ " -No spam \r\n"
						+ " -No bad Software\r\n"
						+ " -Keep to these rules xD\r\n"
						+ "-------------------------------------------------------------\r\n"
						+ "Links:\r\n"
						+ "Bot Invite:\r\n"
						+ "Server:\r\n"
						+ "Ome website\r\n"
						+ "Top.gg\r\n"
						+ "-------------------------------------------------------------\r\n"
						+ "Important Commands:\r\n"
						+ "!report\r\n"
						+ "!setup\r\n"
						+ "!help\r\n"
						+ "-------------------------------------------------------------\r\n"
						+ "Bot Team:\r\n"
						+ "Devs:\r\n"
						+ "Sage\r\n"
						+ "Tom\r\n"
						+ "Mods:\r\n"
						+ "...\r\n"
						+ "------------------------------------------------------------\r\n"
						+ "Taanks for using our bot!";
				}
		return temp;
		
	}
	
	public static void Fehler(String code, TextChannel ch, String lan) {

		if(lan.equalsIgnoreCase("de")) {
			ch.sendMessage(new EmbedBuilder() .setDescription("Ein Fehler ist aufgetreten! Bitte schau doch auf unsere (Website)[discord.eftron.???] vorbei. Fehlercode: " + code).setColor(Color.RED).setFooter(Main.footer).setTitle(":x: Fehler :x:").build()).queue();
			}else if(lan.equalsIgnoreCase("en")) {
				ch.sendMessage(new EmbedBuilder().setDescription("An unexpected error occurred! \n Error code: **" + code + "**. \n Please check our [website](https://discord.com/j8emH5ap3k) for details.").setColor(Color.RED).setFooter(Main.footer).setTitle(":x: Error :x:").build()).queue();
			}
		
	}
	
	public static String Start(String lan) {
		
		String temp = "Invaild";
		
		if(lan.equalsIgnoreCase("de")) {
			temp = "Du bist jetzt in der Warteschlange. Bitte nutze !next um einen Partner zu bekommen oder warte bis dir jemand zugewiesen wird.";
			}else if(lan.equalsIgnoreCase("en")) {
			temp = "You are now in the queue. Please use !next to get a partner or wait until someone is assigned to you.";
			}
		return temp;
		
	}
	
	public static String PremiumEnd(String lan) {
		
		String temp = "Invaild";
		
		if(lan.equalsIgnoreCase("de")) {
			temp = "Dein premium Status ist abgelaufen. Du kannst nun keine Premium Funktionen mehr nutzen.";
			}else if(lan.equalsIgnoreCase("en")) {
			temp = "Your Premium status has expired. You can no longer use premium fuctions.";
			}
		return temp;
		
	}
	
	public static String PremiumEndByAdmin(String lan) {
		
		String temp = "Invaild";
		
		if(lan.equalsIgnoreCase("de")) {
			temp = "Dein Premium Status wurde Entfernt. Du kannst nun keine Premium Funktionen mehr nutzen. Bitte stelle einen Antrag auf unserem SUpport Discord";
			}else if(lan.equalsIgnoreCase("en")) {
			temp = "Your Premium status has been removed. You will no longer be able to use Premium features. Please make a request on our support Discord";
			}
		return temp;
		
	}
	
	public static String database(String lan) {
		
		String temp = "Invaild";
		
		if(lan.equalsIgnoreCase("de")) {
			temp = "Du bist nicht in unserer Datenbank eingetragen. Bitte nutze !ome um dich einzutragen!";
			}else if(lan.equalsIgnoreCase("en")) {
			temp = "You are not registered in our database. Please use !ome to register yourself!";
			}
		return temp;
		
	}
	
	
	public static String Mute(String lan) {
		
		String temp = "Invaild";
		
		if(lan.equalsIgnoreCase("de")) {
			temp = "Du bist gemuted. Bitte verlasse das System und stelle einen Antrag auf Entbannung auf unserem Discord:<link>";
			}else if(lan.equalsIgnoreCase("en")) {
			temp = "You are muted. Please leave the system and make a request for unbanning on our Discord:<link>";
			}
		return temp;
		
	}
	
	public static String blockedType(String lan) {
		
		String temp = "Invaild";
		
		if(lan.equalsIgnoreCase("de")) {
			temp = "Du sendest eine von den Entwicklern blockierte Dateiendung. Wir bitten um Verständnis, dass wir keine Ausführbaren Dateien zulassen";
			}else if(lan.equalsIgnoreCase("en")) {
			temp = "You are sending a file extension blocked by the developers. We ask for your understanding that we dont allow executable files.";
			}
		return temp;
		
	}
	
	public static String muteEnd(String lan) {
		
		String temp = "Invaild";
		
		if(lan.equalsIgnoreCase("de")) {
			temp = "Dein Mute wurde aufgehoben. Wir bitten dich, dass soetwas nicht nochmal passiert!";
			}else if(lan.equalsIgnoreCase("en")) {
			temp = "Your mute has been canceled. We ask you that this not happen again!";
			}
		return temp;
		
	}
	
	public static String premiumStatus(String lan, String end, boolean isPremium) {
		
		String temp = "Invaild";
		
		if(isPremium) {
			if(end.equalsIgnoreCase("endlos")) {
				if(lan.equalsIgnoreCase("de")) {
					temp = "Du hast zurzeit Premium auf deinem Account. \n Dein Premium Status endet nicht, außer ein Admin entfert es dir!";
					}else if(lan.equalsIgnoreCase("en")) {
					temp = "You currently have Premium on your account. \n Your premium status will not end unless an admin removes it!";
					}
			}else {
				if(lan.equalsIgnoreCase("de")) {
					temp = "Du hast zurzeit Premium auf deinem Account. \n Dein Premium Status endet am " + end + " (MEZ)! Ein Admin kann es dir jedoch wieder entfernen";
					}else if(lan.equalsIgnoreCase("en")) {
					temp = "You currently have Premium on your account. \n Your Premium status ends on " + end + " (MEZ)! However, an admin can remove it from you again";
					}
			}
		}else {
			if(lan.equalsIgnoreCase("de")) {
				temp = "Du hast zurzeit keine Premium Funktionen freigeschaltet! Auf unserem Support Server findest du Infos darüber, wie du es bekommst. \n *Versucht mal !about*";
				}else if(lan.equalsIgnoreCase("en")) {
				temp = "You currently do not have any premium features unlocked! On our support server you can find info about how to get it. \\n *Try !about*";
				}
		}
		
	
		return temp;
		
	}
	
	public static String Namen(String lan, String stat) {
		
		String temp = "Invaild";
		
		if(lan.equalsIgnoreCase("de")) {
			temp = "Deutscher Text";
			}else if(lan.equalsIgnoreCase("en")) {
			temp = "Englischer Text";
			}
		return temp;
		
	}

}
