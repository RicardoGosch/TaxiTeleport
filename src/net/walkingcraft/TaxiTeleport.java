package net.walkingcraft;

import org.bukkit.plugin.java.JavaPlugin;

import net.walkingcraft.cmd.CmdDelTeleport;
import net.walkingcraft.cmd.CmdMainTeleport;
import net.walkingcraft.cmd.CmdSetTeleport;

public class TaxiTeleport extends JavaPlugin {
	@Override
	public void onEnable() {
		getCommand("home").setExecutor(new CmdMainTeleport(this));
		getCommand("sethome").setExecutor(new CmdSetTeleport(this));
		getCommand("delhome").setExecutor(new CmdDelTeleport(this));
	}

	@Override
	public void onDisable() {
		super.onDisable();
	
	}
}
