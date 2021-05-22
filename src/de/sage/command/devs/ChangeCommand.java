package de.sage.command.devs;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.sage.listener.OnOmeChannelJoin;
import de.sage.main.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ChangeCommand extends ListenerAdapter{
	
	
	private boolean isActive = false;
	private Timer timer;
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		if(e.getMessage().getContentDisplay().equalsIgnoreCase(Main.prefix + "change")) {
			if(Main.OwnerIds.contains(e.getMember().getIdLong())) {
				if(isActive) {
					e.getChannel().sendMessage("Der Statuschange ist aus!").queue();
					stopChange();
					isActive = false;
				}else {
					e.getChannel().sendMessage("Der Statuschange ist aktiviert!").queue();
					startChange(e.getJDA());
					isActive = true;
				}
					
			}
		}
		
	}
	
	private void startChange(JDA jda) {
		//timer.cancel();
		timer = new Timer();
		
		timer.schedule(new TimerTask() {
			Random r = new Random();
			
			@Override
			public void run() {
				
				List<String> StatusList = Arrays.asList("a race", "Updates", "to " +  jda.getUsers().size() + " voices", "games with " + OnOmeChannelJoin.activeGuilds.size() + " active users", "movies on " + jda.getGuilds().size() + " guilds", "!help"," on twitch");
				
			   int randomInt = r.nextInt(StatusList.size());
			   String RandomStaus = StatusList.get(randomInt);

			   if(RandomStaus.startsWith("a")) {
				   jda.getPresence().setActivity(Activity.competing(RandomStaus));
			   }else if(RandomStaus.startsWith("Updates") || RandomStaus.startsWith("movies")) {
				   jda.getPresence().setActivity(Activity.watching(RandomStaus));
			   }else if(RandomStaus.startsWith("to") || RandomStaus.startsWith("!help") ) {
				   jda.getPresence().setActivity(Activity.listening(RandomStaus));
			   }else if(RandomStaus.startsWith("games")) {
				   jda.getPresence().setActivity(Activity.playing(RandomStaus));
			   }else if(RandomStaus.startsWith("on")) {
				   jda.getPresence().setActivity(Activity.streaming(RandomStaus, "https://www.twitch.tv/sagesphinx63920"));
			   }
				
			}
		}, 1000, 30000);
	}
	
	private void stopChange() {
		timer.cancel();
		
	}

}
