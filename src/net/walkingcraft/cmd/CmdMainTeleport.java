package net.walkingcraft.cmd;

import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.walkingcraft.TaxiTeleport;
import net.walkingcraft.model.ModelTaxi;
import net.walkingcraft.system.SystemTaxiTeleport;

public class CmdMainTeleport implements CommandExecutor {
	// ****************
	// * Atributes
	// ****************
	TaxiTeleport taxiTeleport;
	ModelTaxi taxi = new ModelTaxi();
	SystemTaxiTeleport systemTaxiTeleport = new SystemTaxiTeleport(taxiTeleport);

	public CmdMainTeleport(TaxiTeleport instanceMain) {
		taxiTeleport = instanceMain;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Comando somente para Players!");
			return false;
		}
		switch (args.length) {
		case 0:
			try {
				taxi.setUser_name(sender.getName());
				systemTaxiTeleport.setTaxi(taxi);
				HashMap<Integer, ModelTaxi> mapTaxi = systemTaxiTeleport.getListTeleport();
				if (mapTaxi.size() == 0) {
					sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Nenhum teleporte encontrado!");
					return true;
				}
				String msg = "";
				for(int a = 0; mapTaxi.size() > a; a++){
					msg += ", "+mapTaxi.get(a).getHouse_name();
				}
				sender.sendMessage("");
				sender.sendMessage(ChatColor.GOLD + "Teleportes Privados: " + ChatColor.RESET + msg.replaceFirst(", ", ""));
				sender.sendMessage("");
			} catch (CloneNotSupportedException | SQLException e) {
				e.printStackTrace();
			}
			break;

		case 1:
			try {
				String arg = args[0];
				if (arg.contains(":")) {
					// Comando será utilizado pela staff para ver a home de
					// outro player
					return true;
				}
				taxi.setHouse_name(arg);
				taxi.setUser_name(sender.getName());
				systemTaxiTeleport.setTaxi(taxi);
				ModelTaxi taxiReturn = systemTaxiTeleport.getTeleport();
				if (taxiReturn == null) {
					sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Teleporte não encontrado!");
				} else {
					Player player = (Player) sender;
					World world = Bukkit.getWorld(taxiReturn.getHouse_world());
					Double x = Double.parseDouble(taxiReturn.getHouse_x());
					Double y = Double.parseDouble(taxiReturn.getHouse_y());
					Double z = Double.parseDouble(taxiReturn.getHouse_z());
					Float pitch = Float.parseFloat(taxiReturn.getHouse_pitch());
					Location loc = new Location(world, x, y, z, 0, pitch);
					player.teleport(loc);
					sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Teleportado para "
							+ taxiReturn.getHouse_name() + "!");
				}

				
				
				
			} catch (CloneNotSupportedException | SQLException e) {
				e.printStackTrace();
			}
			break;
		}

		return false;
	}

}
