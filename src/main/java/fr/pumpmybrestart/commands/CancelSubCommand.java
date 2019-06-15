package fr.pumpmybrestart.commands;

import java.util.List;

import fr.pumpmybrestart.ShutdownManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class CancelSubCommand implements ISubCommand {

	private ShutdownManager shutdownManager;

	public CancelSubCommand(ShutdownManager shutdownManager) {
		this.shutdownManager = shutdownManager;		
	}

	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {
		
		try {
			this.shutdownManager.cancel();
		} catch (Exception e) {
			e.printStackTrace();
			sender.sendMessage(new TextComponent(e.getMessage()));
		}

	}

}
