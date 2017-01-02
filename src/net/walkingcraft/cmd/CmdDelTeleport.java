package net.walkingcraft.cmd;

import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.walkingcraft.TaxiTeleport;
import net.walkingcraft.model.ModelTaxi;
import net.walkingcraft.system.SystemTaxiTeleport;

public class CmdDelTeleport implements CommandExecutor {
	// ****************
	// * Atributes
	// ****************
	TaxiTeleport taxiTeleport;
	ModelTaxi taxi = new ModelTaxi();
	SystemTaxiTeleport systemTaxiTeleport = new SystemTaxiTeleport(taxiTeleport);

	public CmdDelTeleport(TaxiTeleport instanceMain) {
		taxiTeleport = instanceMain;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Comando somente para Players!");
			return false;
		}

		switch (args.length) {
		case 1:
			try {

				String arg = args[0];
				taxi.setUser_name(sender.getName());
				taxi.setHouse_name(arg);
				systemTaxiTeleport.setTaxi(taxi);

				if (systemTaxiTeleport.getTeleport() == null) {
					sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Teleporte n�o encontrado!");
					return true;
				}
				systemTaxiTeleport.delTeleport();
				sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Teleporte '" + taxi.getHouse_name()
						+ "' deletado com sucesso!");
				return true;

			} catch (SQLException | CloneNotSupportedException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}

		return false;
	}

}
