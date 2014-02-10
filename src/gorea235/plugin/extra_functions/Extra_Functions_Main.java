package gorea235.plugin.extra_functions;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.event.Listener;

public final class Extra_Functions_Main extends JavaPlugin {
	public int saveTime;
	public int restartTime;
	public int tellTime;
	public int restartSchedule;
	private Extra_Functions_Main plugin = Extra_Functions_Main.this;

	public void broadcastMessage(String msg) {
		Bukkit.broadcastMessage("[Extra_Functions] " + msg);
	}

	public static void Log(String toLog) {
		Bukkit.getLogger().info(toLog);
	}

	public void loadConfig() {
		this.saveDefaultConfig();
		saveTime = this.getConfig().getInt("savetime");
		if (saveTime < 1) {
			this.getConfig().set("savetime", -1);
			saveTime = -1;
		} else if (saveTime != -1) {
			saveTime = saveTime * 20;
		}
		restartTime = this.getConfig().getInt("restarttime");
		if (restartTime < 1) {
			this.getConfig().set("restarttime", -1);
			restartTime = -1;
		} else if (restartTime != -1) {
			restartTime = restartTime * 20;
		}
		tellTime = this.getConfig().getInt("telltime");
		if (tellTime < 1) {
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
		int seconds = ticks / 20;
		if (seconds < 60) {
			if (seconds > 1) {
				return Math.round(seconds) + " seconds";
			} else {
				return Math.round(seconds) + " second";
			}
		} else {
			float minutes = seconds / 60;
			if (minutes < 60) {
				if (minutes > 1) {
					if (Math.round(minutes) != minutes) {
						return "approximately " + Math.round(minutes)
								+ " minutes";
					} else {
						return Math.round(minutes) + " minutes";
					}
				} else {
					return Math.round(minutes) + " minute";
				}
			} else {
				float hours = minutes / 60;
				if (hours > 1) {
					if (Math.round(hours) != hours) {
						return "approximately " + Math.round(hours) + " hours";
					} else {
						return Math.round(hours) + " hours";
					}
				} else {
					return Math.round(hours) + " hour";
				}
			}
		}
	}

	public void onEnable() {
		getCommand("team")
				.setExecutor(new Extra_FunctionsCommandExecutor(this));
		loadConfig();
		final BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		if (saveTime != -1) {
			Log("Scheduling save task every " + toTime(saveTime));
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
			Log("Scheduling restart in " + toTime(restartTime));
			scheduler.scheduleSyncDelayedTask(this, new Runnable() {

				@Override
				public void run() {
					scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

						@Override
						public void run() {
							dispatchCommand("restart");
						}

					}, tellTime);
					if (tellTime != -1) {
						broadcastMessage("Restarting the server in "
								+ toTime(tellTime) + ".");
					}
				}

			}, restartSchedule);
		}
		getServer().getPluginManager().registerEvents(
				new Extra_FunctionsInventoryKeeper(), this);
	}
}