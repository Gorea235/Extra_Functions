package gorea235.plugin.extra_functions;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Extra_FunctionsCommandExecutor implements CommandExecutor {

	public Extra_FunctionsCommandExecutor(Extra_Functions_Main plugin) {
		this.plugin = plugin;
	}

	private void commandError(CommandSender cmdSender, String str) {
		cmdSender.sendMessage("§cUsage: " + str);
	}

	private void permError(CommandSender cmdSender) {
		cmdSender.sendMessage("§cYou don't have the required permissions!");
	}

	private Extra_Functions_Main plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Boolean returnBool = false;
		if (cmd.getName().equalsIgnoreCase("team")) {
			returnBool = teamCommand(sender, cmd, label, args);
		}
		return returnBool;
	}

	private void teamCommandSender(String scoreCmd) {
		Bukkit.getServer().dispatchCommand(
				Bukkit.getServer().getConsoleSender(),
				"scoreboard teams" + scoreCmd);
	}

	private boolean teamCommand(CommandSender sender, Command cmd,
			String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			sender.sendMessage("§cError! Command not implemented yet!");
			// if (args.length > 0) {
			// if (args[0] == "join") {
			// plugin.Log("'/team join' run");
			// if (sender.hasPermission("extra_functions.team.join")) {
			// if (args.length != 2) {
			// teamCommandSender("join " + args[0]);
			// return true;
			// } else {
			// commandError(sender, "/team join <team name>");
			// return false;
			// }
			// } else {
			// permError(sender);
			// }
			// } else if (args[0] == "leave") {
			// if (sender.hasPermission("extra_functions.team.leave")) {
			// if (args.length != 2) {
			// teamCommandSender("leave " + args[1]);
			// return true;
			// } else {
			// commandError(sender, "/team leave <team name>");
			// return false;
			// }
			// } else {
			// permError(sender);
			// }
			// } else if (args[0] == "create") {
			// if (sender.hasPermission("extra_functions.team.create")) {
			// if (args.length != 2) {
			// teamCommandSender("create " + args[1] + args[2]);
			// teamCommandSender("option color dark_green");
			// teamCommandSender("option friendlyfire false");
			// teamCommandSender("option seeFriendlyInvisibles true");
			// return true;
			// } else {
			// commandError(sender,
			// "/team create <team name (no spaces)> [team display name]");
			// return false;
			// }
			// } else {
			// permError(sender);
			// }
			// } else if (args[0] == "delete") {
			// if (sender.hasPermission("extra_functions.team.delete")) {
			// if (args.length != 2) {
			// teamCommandSender("delete " + args[1]);
			// return true;
			// } else {
			// commandError(sender, "/team delete <team name>");
			// return false;
			// }
			// } else {
			// permError(sender);
			// }
			// } else if (args[0] == "option") {
			// if (sender.hasPermission("extra_functions.team.options")) {
			// if (args.length != 2) {
			// teamCommandSender("options " + args[1] + args[2]);
			// return true;
			// } else {
			// commandError(sender,
			// "/team options join <team name>");
			// return false;
			// }
			// } else {
			// permError(sender);
			// }
			// } else {
			// commandError(sender,
			// "/team <join/leave/create/option/delete>");
			// int argLength = args.length;
			// String argsStr = "";
			// for (int i = 0; i < args.length; i++) {
			// argsStr = argsStr + " " + args[i];
			// }
			// plugin.Log("argLength = " + argLength + ", argsStr ="
			// + argsStr);
			// plugin.Log(args[0]);
			// }
			// } else {
			// commandError(sender, "/team <join/leave/create/option/delete>");
			// }
		} else {
			plugin.Log("Error! Sender must be a player! Console executability will be added soon.");
			return true;
		}
		return false;
	}

}
