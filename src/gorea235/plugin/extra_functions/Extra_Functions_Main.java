package gorea235.plugin.extra_functions;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class Extra_Functions_Main extends JavaPlugin {
	public void broadcastMessage(String msg) {
		Bukkit.broadcastMessage("[Extra_Functions] " + msg);
	}

	public void Log(String toLog) {
		getLogger().info(toLog);
	}

	public void onEnable() {
		getCommand("team").setExecutor(new Extra_FunctionsCommandExecutor(this));
		Log("Scheduling save task for every 72000 ticks (every hour)");
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				broadcastMessage("Saving players and worlds...");
				Bukkit.savePlayers();

				for (World world : Bukkit.getWorlds()) {
					world.save();
				}
				broadcastMessage("Saved all players and worlds");
			}
			// Do something
		}, 20L, 72000L);
	}

}