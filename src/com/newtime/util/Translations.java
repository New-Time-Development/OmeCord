package com.newtime.util;

import java.awt.Color;

import com.newtime.main.Main;

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
				temp = "Du hast eine Direktnachricht erhalten!";
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
				temp = "Bitte nutze eine der folgenden Sprach-Kürtzel: **de**, **en**, **trk**, **fr**, **es**, **ru**";
				}else if(lan.equalsIgnoreCase("en")) {
				temp = "Please use one of the following language codes: **de**, **en**, **trk**, **fr**, **es**, **ru**";
				}
		}else if(stat.equalsIgnoreCase("gen")) {
			if(lan.equalsIgnoreCase("de")) {
				temp = "Bitte nutze eine der folgenden Geschlechte: **Männlich**, **Weiblich**, **Paare**";
				}else if(lan.equalsIgnoreCase("en")) {
				temp = "Please use one of the following gender **Male**, **Female**, **Couples**";
				}
		}else if(stat.equalsIgnoreCase("insert")) {
			if(lan.equalsIgnoreCase("de")) {
				temp = "Du wurdest erfolgreich registriert. Jetzt kannst du den Bot uneingeschränkt nutzen. Weil du das erste mal hier bist, bekommst du 3 Tage Premium gratis!";
				}else if(lan.equalsIgnoreCase("en")) {
				temp = "You have been successfully registered. Now you can use the bot without restrictions. Because you are here for the first time, you get 3 days Premium for free!";
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
						+ "Sage (SageSphinx63920#7195)\r\n"
						+ "verion (verion#0612)\r\n"
						+ "Mods:\r\n"
						+ "» Bewirb dich jetzt!\r\n"
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
						+ "Sage (SageSphinx63920#7195)\r\n"
						+ "verion (verion#0612)\r\n"
						+ "Mods:\r\n"
						+ "» Apply now!\r\n"
						+ "------------------------------------------------------------\r\n"
						+ "Thanks for using our bot!";
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
	
	public static String PremiumFUnktions(String lan) {
		
		String temp = "Invaild";
		
		if(lan.equalsIgnoreCase("de")) {
			temp = "Premium Funktionen:\r\n"
					+ "-Du hast eine bessere Audio Qualität in den Sprachkanälen(die beste, die der Server zu bieten hat)\r\n"
					+ "-Du bist nicht an den Cooldown des Systems gebunden.\r\n"
					+ "-Du kannst deine eigene Personalisierte Startnachricht setzen\r\n"
					+ "-Du kannst bis zu 15 Megabyte an Daten auf einmal senden(8 ohne Nitro)\r\n"
					+ "-Einen Rang auf dem Support Discord mit Zugang zu neuen vorab Funktionen.";
			}else if(lan.equalsIgnoreCase("en")) {
			temp = "Premium features:\r\n"
					+ "-You will have better audio quality in the voice channels(the best the server can have).\r\n"
					+ "-You are not bound to the system's cooldown.\r\n"
					+ "-You can set your own personalized start message.\r\n"
					+ "-You can send up to 15 megabytes of data at once (8 without Nitro).\r\n"
					+ "-A rank on the Support Discord with access to beta functions";
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
	
	public static String machError(String lan) {
		
		String temp = "Invaild";
		
		if(lan.equalsIgnoreCase("de")) {
			temp = "Ein Fehler ist aufgetreten bei dem Versuch dich mit einem neuen User zu verbinden. Dies kann auftreten, wenn kein User verfügbar ist. Bitte warte etwas oder nutze !next um es erneut zu versuchen. Wir bitten um Verständnis. ";
			}else if(lan.equalsIgnoreCase("en")) {
			temp = "An error occurred when trying to connect to a new user. This can occur when no user is available. Please wait a while or use !next to try again. We ask for your understanding. ";
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
	
	public static String isInUse(String lan) {
		
		String temp = "Invaild";
		
		if(lan.equalsIgnoreCase("de")) {
			temp = "Ein anderer User nutzt zurzeit den Bot. Warte bis der Bot wieder frei ist oder lad ihn auf deinen ";
			}else if(lan.equalsIgnoreCase("en")) {
			temp = "You are not registered in our database. Please use !ome to register yourself!";
			}
		return temp;
		
	}
	
	public static String SendMute(String lan, String end) {
		
		String temp = "Invaild";
		
		if(end.equalsIgnoreCase("endlos")) {
			if(lan.equalsIgnoreCase("de")) {
				temp = "Du bist permant gemuted. Bitte verlasse das System und stelle einen Antrag auf Entbannung auf unserem Discord: https://discord.gg/zEwGEJUPRC";
				}else if(lan.equalsIgnoreCase("en")) {
				temp = "You are permant muted. Please leave the system and make a request for unbanning on our Discord: https://discord.gg/zEwGEJUPRC";
				}
		}else {
			if(lan.equalsIgnoreCase("de")) {
				temp = "Du bist bis zum **" + end + "** (MEZ) gemuted. Bitte verlasse das System.";
				}else if(lan.equalsIgnoreCase("en")) {
				temp = "You are muted until **" + end +"** (MEZ). Please leave the system.";
				}
		}
		
		return temp;
		
	}
	
	public static String Mute(String lan) {
		
		String temp = "Invaild";
		
		if(lan.equalsIgnoreCase("de")) {
			temp = "Du bist gemuted. Bitte verlasse das System und stelle einen Antrag auf Entbannung auf unserem Discord: https://discord.gg/zEwGEJUPRC";
			}else if(lan.equalsIgnoreCase("en")) {
			temp = "You are muted. Please leave the system and make a request for unbanning on our Discord: https://discord.gg/zEwGEJUPRC";
			}
		return temp;
		
	}
	
	public static String onlyPre(String lan) {
		
		String temp = "Invaild";
		
		if(lan.equalsIgnoreCase("de")) {
			temp = "Diese Funktion ist nur für Premium user zugänglich. Du kannst es in unserem Pateron kaufen. Schreibe einfach !about";
			}else if(lan.equalsIgnoreCase("en")) {
			temp = "This function is only available for Premium users. You can buy it in our pateron. Just type !about";
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
				temp = "Du hast zurzeit keine Premium Funktionen freigeschaltet! Auf unserem Support Server findest du Infos darüber, wie du es bekommst.  *Versucht mal !about*";
				}else if(lan.equalsIgnoreCase("en")) {
				temp = "You currently do not have any premium features unlocked! On our support server you can find info about how to get it.  *Try !about*";
				}
		}
		
	
		return temp;
		
	}
	
	public static String Setup(String lan, String stat, long id) {
		
		String temp = "Invaild";
		
		if(stat.equalsIgnoreCase("off")) {
			if(lan.equalsIgnoreCase("de")) {
				temp = "Der OmeCord Bot Funktionen wurde von diesem Server deaktiviert. Hoffentlich nicht lange ;) ";
				}else if(lan.equalsIgnoreCase("en")) {
				temp = "The OmeCord Bot functions has been disabled from this server. Hopefully not for long ;) ";
				}
		}else {
			if(lan.equalsIgnoreCase("de")) {
				temp = "Ich habe alles erstellt. Geh einfach in <#" + id + "> , um dem Netzwerk beizutreten. **WICHTIG:** Du kannst jederzeit die Kategorie oder die Kanäle umbennen du darfst sie nur nicht verschieben, löschen oder die Berechtigung verändern!";
				}else if(lan.equalsIgnoreCase("en")) {
				temp = "Added Chatecory. Join <#" + id + "> to connect to the network. **IMPORTANT:** You can rename the category or the channels at any time, but you must never move them, delete them or change the permissions!";
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
