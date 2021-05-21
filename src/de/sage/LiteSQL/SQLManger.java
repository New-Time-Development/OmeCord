package de.sage.LiteSQL;

public class SQLManger {

	public static void onCreate() {
		
		//id  guildid  channelid  messageid  emote  rolleid

		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS vote(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, userid INTEGER, coins INTEGER, votes INTEGER, time LONG)");
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS mute(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, userid INTEGER, end LONG, val INTEGER)");
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS premium(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, userid INTEGER, time LONG, code VARCHAR, end LONG)");
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS codes(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, userid INTEGER, time LONG, code VARCHAR, end LONG)");
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS lang(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, userid INTEGER, botLan STRING)");
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS members(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, userid INTEGER, selcLan STRING, gen VARCHAR, ueber INTEGER, mute INTEGER)");
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS guilds(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, guildid INTEGER, channelid STRING, mute INTEGR)");

	}
	
}
