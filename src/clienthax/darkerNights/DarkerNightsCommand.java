package clienthax.darkerNights;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DarkerNightsCommand implements CommandExecutor {

	private DarkerNights plugin = null;
	
	public DarkerNightsCommand(DarkerNights plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return false;
	}

}
