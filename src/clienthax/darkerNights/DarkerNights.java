package clienthax.darkerNights;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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

public class DarkerNights extends JavaPlugin implements Listener {
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler()
	public void onPlayerMove(PlayerMoveEvent event)
	{	
		if(!isNighttimeInWorld(event.getPlayer().getWorld()))
		{
			event.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
			return;
		}
		
		if(darkUnderPlayer(event.getPlayer()))
		{
			if(!event.getPlayer().hasPermission("darkernights.bypass"))
			{
			PotionEffect blindness = new PotionEffect(PotionEffectType.BLINDNESS, 999999, 0);
			event.getPlayer().addPotionEffect(blindness);
			}
		}
		else
		{
				event.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
		}
			
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event)
	{
		event.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
	}
	
	@EventHandler
	public void onLeaveServer(PlayerQuitEvent event)
	{
		event.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
	}
	
	@EventHandler
	public void onJoinServer(PlayerJoinEvent event)
	{
		event.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
	}

	public boolean torchInArea(Player player, int distance)
	{
		Location playerLocation = player.getLocation();
		Location locA = playerLocation.clone().add(-distance, -distance, -distance);
		Location locB = playerLocation.clone().add(distance, distance, distance);
		
	
		for(int x = locA.getBlockX(); x < locB.getBlockX(); x++)
		for(int y = locA.getBlockY(); y < locB.getBlockY(); y++)
		for(int z = locA.getBlockZ(); z < locB.getBlockZ(); z++)
		{
			Block block = player.getWorld().getBlockAt(x,y,z);
			if(block != null && !block.isEmpty())
			{
				if(block.getType().equals(Material.TORCH))
				{
					getLogger().info("torch");
					return true;
				}
			}
		}
		
		return false;
				
	}
	
	public boolean darkUnderPlayer(Player player)
	{
		Block block = player.getLocation().getBlock();
		if(block.getLightLevel() < 5)
			return true;
		return false;
	}
	

	public boolean darkInArea(Player player, int distance, int lightLevel)
	{
		Location playerLocation = player.getLocation();
		Location locA = playerLocation.clone().add(-distance, -distance, -distance);
		Location locB = playerLocation.clone().add(distance, distance, distance);
		
	
		for(int x = locA.getBlockX(); x < locB.getBlockX(); x++)
		for(int y = locA.getBlockY(); y < locB.getBlockY(); y++)
		for(int z = locA.getBlockZ(); z < locB.getBlockZ(); z++)
		{
			Block block = player.getWorld().getBlockAt(x,y,z);
			if(block != null && block.isEmpty())
			{
				if(block.getRelative(BlockFace.UP).isEmpty())
				{
					
					if(block.getLightLevel() < lightLevel)
					return true;
				}
				
			}
		}
		
		return false;
				
	}
	
	public boolean isNighttimeInWorld(World world)
	{
		/*
		<Riking> 0-12000 is day
		<Riking> 12K-13K is sunset, approx
		<Riking> 23K-[24K|0] is sunrise
		 */
		Long time = world.getTime();
		
		if(time >= 13000 && time <= 23000)
			return true;
		
		return false;
		
	}
}
