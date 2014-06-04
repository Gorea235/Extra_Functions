package gorea235.plugin.extra_functions;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Extra_FunctionsInventoryKeeper implements Listener {
	private Map<String, ItemStack[]> playerItems = new HashMap<String, ItemStack[]>();
	private Map<String, ItemStack[]> playerArmour = new HashMap<String, ItemStack[]>();

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		PlayerInventory plyInv = event.getEntity().getInventory();
		playerItems.put(event.getEntity().getName(), plyInv.getContents());
		playerArmour.put(event.getEntity().getName(), plyInv.getArmorContents());
		int count = 0;
		for (ItemStack stack : event.getDrops()) {
			stack.setAmount(0);
			count++;
		}
		Extra_Functions_Main.Log(event.getEntity().getName() + " died with " + count + " items, giving them back on respawn...");
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		PlayerInventory inv = player.getInventory();
		inv.setContents(playerItems.get(player.getName()));
		inv.setArmorContents(playerArmour.get(player.getName()));
		Extra_Functions_Main.Log("Player " + player.getName() + " respawned, giving them their items back");
	}
}
