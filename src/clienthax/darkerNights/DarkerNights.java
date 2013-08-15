package clienthax.darkerNights;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * The main entry point for the DarkerNights plugin. 
 */
public class DarkerNights extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("darkernights").setExecutor(new DarkerNightsCommand(this));
	}

	/**
	 * Fires when a player moves.
	 * 
	 * @param event Event to handle.
	 */
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {

		// Get the player
		Player player = event.getPlayer();

		// Check to see if it is night time in the current world. If it is,
		// remove the blindness. Similarly, if the player has the bypass permission,
		// just remove the blindness.
		if (!isNighttimeInWorld(event.getPlayer().getWorld()) || 
				player.hasPermission("darkernights.bypass") ||
				player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
			
			player.removePotionEffect(PotionEffectType.BLINDNESS);
			return;
		}

		// Check the light levels
		if (darkUnderPlayer(event.getPlayer())) {
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 0));
		} else {
			event.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
		}

	}

	/**
	 * Fires when a player respawns.
	 * 
	 * @param event Event to handle.
	 */
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		event.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
	}

	/**
	 * Fires when a player leaves the server.
	 * 
	 * @param event Event to handle.
	 */
	@EventHandler
	public void onLeaveServer(PlayerQuitEvent event) {
		event.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
	}

	/**
	 * Fires when a player joins the server.
	 * 
	 * @param event Event to handle.
	 */
	@EventHandler
	public void onJoinServer(PlayerJoinEvent event) {
		event.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
	}

	/**
	 * Checks whether the light level that the player is in is low
	 * 
	 * @param player Player to check
	 * @return true if the light level is below 5 units
	 */
	private boolean darkUnderPlayer(Player player) {
		Block block = player.getLocation().getBlock();
		return block.getLightLevel() < 5; 
	}

	/**
	 * Checks whether it is night in the world.
	 * 
	 * @param world World to check
	 * @return true if it is night
	 */
	private boolean isNighttimeInWorld(World world) {
		/*
		 * <Riking> 0-12000 is day 
		 * <Riking> 12K-13K is sunset, approx 
		 * <Riking> 23K-[24K|0] is sunrise
		 */
		Long time = world.getTime();
		return (time >= 13000 && time <= 23000);
	}
}
