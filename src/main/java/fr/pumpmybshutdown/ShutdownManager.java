package fr.pumpmybshutdown;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.scheduler.ScheduledTask;

public class ShutdownManager {

	private List<ScheduledTask> list;
	private Main main;
	private boolean started;
	private boolean skiped;
	
	public ShutdownManager(Main m) {
		this.main = m;
		this.list = new ArrayList<ScheduledTask>();
		
		this.started = false;
		this.skiped = false;
		
	}
	
	private static final int[] time = {600,300,120,60,30,20,10,5,4,3,2,1};
	private static final int max_time = time[0];
	
	private void addScheduler(ScheduledTask st,int delay) {
		this.main.getLogger().info("Shutdown scheduler added (run task in " + delay + " secondes)");
		this.list.add(st);
	}
	
	public void start(boolean journalie) throws Exception {
		
		if(this.started) {
			throw new Exception("§l§6[§2Pump§eMy§4BShutdown§6]§r§f Un redémarrage " + (journalie ? "journalié " : "") + "est déjà en cours !");
		}
		
		if(this.skiped) {
			this.skiped = false;
			throw new Exception("§l§6[§2Pump§eMy§4BShutdown§6]§r§f Ce redémarrage " + (journalie ? "journalié " : "") + "a été sauté par un utilisateur !");
		}
		
		for (int t : time) {
			
			int delay = max_time - t;
			
			ScheduledTask scheduleTask = this.main.getProxy().getScheduler().schedule(this.main, new Runnable() {
				
				@Override
				public void run() {
					
					main.getProxy().broadcast(new TextComponent("§l§6[§2Pump§eMy§4BShutdown§6]§r§f Redémarrage (très rapide) " + (journalie ? "journalié " : "") + "du proxy dans " + t + " secondes."));
					
				}
			}, delay, TimeUnit.SECONDS);
			this.addScheduler(scheduleTask, delay);
			
		}
		
		ScheduledTask scheduleTask = this.main.getProxy().getScheduler().schedule(this.main, new Runnable() {
			
			@Override
			public void run() {
				
				main.getProxy().stop("§l§6[§2Pump§eMy§4BShutdown§6]§r§f Redémarrage " + (journalie ? "journalié " : "") + "du proxy (très rapide) !");
				
			}
		}, (max_time), TimeUnit.SECONDS);
		
		this.addScheduler(scheduleTask, max_time);		
		this.started = true;
		
	}

	public void skip() throws Exception {
		
		if(this.started) {
			throw new Exception("§l§6[§2Pump§eMy§4BShutdown§6]§r§f Un redémarrage est déjà en cours, utilisez \"cancel\" pour l'annuler !");
		}
		
		this.skiped = true;
		
		main.getProxy().broadcast(new TextComponent("§l§6[§2Pump§eMy§4BShutdown§6]§r§f Le prochain redémarrage du proxy a été annulé !"));
		
	}

	public void cancel() throws Exception {
		
		if(!this.started) {
			throw new Exception("§l§6[§2Pump§eMy§4BShutdown§6]§r§f Aucun redémarrage n'est en cours !");
		}
		
		this.started = false;
		
		for (ScheduledTask scheduledTask : this.list) {
			
			scheduledTask.cancel();
			
		}
		
		this.list.clear();
		
		main.getProxy().broadcast(new TextComponent("§l§6[§2Pump§eMy§4BShutdown§6]§r§f Le redémarrage journalié du proxy a été annulé !"));
		
	}

	
	
}
