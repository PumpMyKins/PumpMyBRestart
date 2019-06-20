package fr.pumpmybshutdown.commands;

import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class HelpMotdSubCommand implements ISubCommand{

	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {

		sender.sendMessage(new ComponentBuilder("PumpMyBRestart commmand : \"brestart [help/skip/stop]\"").color(ChatColor.AQUA).create());

	}

	public void onSubCommand(Command motdCommandExecutor, CommandSender sender) {

		this.onSubCommand(motdCommandExecutor, sender, null);

	}


}
