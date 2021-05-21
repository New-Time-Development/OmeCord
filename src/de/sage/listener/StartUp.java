package de.sage.listener;

import de.sage.main.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class StartUp extends ListenerAdapter{
	
	 public void onReady(ReadyEvent event) {

	        String out = "\nDer Bot läuft auf folgenden Servern: \n";

	        for (Guild g : event.getJDA().getGuilds() ) {
	            out += g.getName() + " (" + g.getId() + ") \n " + g.getOwner().getUser().getAsTag() + " (" + g.getOwnerIdLong() + ") \n \n";
	            
	        }

	        System.out.println(out);
	        
	        if(Main.Debug == true) {
	        	String out1 = "\nDer Bot kennt folgende User: \n";
	            for (User u : event.getJDA().getUsers()) {
	            	 out1 += u.getName() + " (" + u.getId() + ") \n";
	            }
	            System.out.println(out1);
	        }
	    }

}
