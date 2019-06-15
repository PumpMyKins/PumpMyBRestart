package fr.pumpmybrestart;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import fr.pumpmybrestart.commands.BRestartCommandExecutor;
import fr.pumpmybrestart.commands.CancelSubCommand;
import fr.pumpmybrestart.commands.HelpMotdSubCommand;
import fr.pumpmybrestart.commands.SkipSubCommand;
import fr.pumpmybrestart.commands.StopSubCommand;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin{

	private ShutdownManager shutdownManager;
	public ShutdownManager getShutdownManager() {
		return this.shutdownManager;
	}
	
	@Override
	public void onEnable() {
		
		this.shutdownManager = new ShutdownManager(this);
		
		getProxy().getScheduler().schedule(this, new Runnable() {
			
			@Override
			public void run() {
				
				LocalDateTime local = LocalDateTime.now();
				
				if(local.getHour() == 4 && local.getMinute() == 50) {
					
					try {
						shutdownManager.start(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
			}
			
		}, 1 , 1, TimeUnit.MINUTES);
		
		BRestartCommandExecutor exec = new BRestartCommandExecutor(this);
		exec.addSubCommand("help", new HelpMotdSubCommand());
		exec.addSubCommand("skip", "pumpmybrestart.command.skip", new SkipSubCommand(this.shutdownManager));
		exec.addSubCommand("cancel", "pumpmybrestart.command.cancel", new CancelSubCommand(this.shutdownManager));
		exec.addSubCommand("stop", "pumpmybrestart.command.stop", new StopSubCommand(this.shutdownManager));
		
		getProxy().getPluginManager().registerCommand(this, exec);
		
	}
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}
	
}
