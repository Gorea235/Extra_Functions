package gorea235.plugin.extra_functions;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class Extra_Functions_Main extends JavaPlugin {
	public int saveTime;
	public int restartTime;
	public int tellTime;
	public int restartSchedule;
	private Extra_Functions_Main plugin = Extra_Functions_Main.this;

	public void broadcastMessage(String msg) {
		Bukkit.broadcastMessage("[Extra_Functions] " + msg);
	}

	public void Log(String toLog) {
		getLogger().info(toLog);
	}

	public void loadConfig() {
		this.saveDefaultConfig();
		saveTime = this.getConfig().getInt("savetime");
		if (saveTime < -1) {
			this.getConfig().set("savetime", -1);
			saveTime = -1;
		} else if (saveTime != -1) {
			saveTime = saveTime * 20;
		}
		restartTime = this.getConfig().getInt("restarttime");
		if (restartTime < -1) {
			this.getConfig().set("restarttime", -1);
			restartTime = -1;
		} else if (restartTime != -1) {
			restartTime = restartTime * 20;
		}
		tellTime = this.getConfig().getInt("telltime");
		if (tellTime < -1) {
			this.getConfig().set("telltime", -1);
			tellTime = -1;
		} else if (tellTime != -1) {
			tellTime = tellTime * 20;
		}
		if (tellTime > restartTime) {
			tellTime = restartTime;
		}
		if (restartTime != -1) {
			if (tellTime != -1) {
				restartSchedule = restartTime - tellTime;
			} else {
				restartSchedule = restartTime;
			}
		} else {
			restartSchedule = -1;
		}
	}

	public void dispatchCommand(String cmd) {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
	}
	
	public String toTime(int ticks) {
		
		return null;
	}

	public void onEnable() {
		getCommand("team")
				.setExecutor(new Extra_FunctionsCommandExecutor(this));
		loadConfig();
		final BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		if (saveTime != -1) {
			Log("Scheduling save task for every " + saveTime / 20
					+ " seconds (approximately every "
					+ Math.round((saveTime / 20) / 60) + " minutes)");
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
			}, 20L, saveTime);
		}
		if (restartSchedule != -1) {
			Log("Scheduling restart for every " + restartTime / 20
					+ " seconds (approximately every "
					+ Math.round((restartTime / 20) / 60) + " minutes)");
			scheduler.scheduleSyncRepeatingTask(this, new Runnable() {

				@Override
				public void run() {
					if (tellTime != -1) {
						scheduler.scheduleSyncDelayedTask(plugin,
								new Runnable() {

									@Override
									public void run() {
										dispatchCommand("restart");
									}

								}, tellTime);
						broadcastMessage("Restarting the server in roughly "
								+ Math.round((tellTime / 20) / 60)
								+ " minutes.");
					} else {
						dispatchCommand("restart");
					}
				}

			}, restartSchedule, restartSchedule);
		}
	}
}