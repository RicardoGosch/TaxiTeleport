package net.walkingcraft.cmd;

import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.walkingcraft.TaxiTeleport;
import net.walkingcraft.model.ModelTaxi;
import net.walkingcraft.system.SystemParallel;
import net.walkingcraft.system.SystemTaxiTeleport;

public class CmdSetTeleport implements CommandExecutor {
	// ****************
	// * Atributes
	// ****************
	TaxiTeleport taxiTeleport;
	ModelTaxi taxi = new ModelTaxi();
	SystemParallel system = new SystemParallel();
	SystemTaxiTeleport systemTaxiTeleport = new SystemTaxiTeleport(taxiTeleport);

	public CmdSetTeleport(TaxiTeleport instanceMain) {
		taxiTeleport = instanceMain;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Comando somente para Players!");
			return true;
		}
		switch (args.length) {
		case 1:
			try {
				String arg = args[0];
				if (!system.verifyCaracteres(arg)) {
					sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Caracteres inv�lidos!");
					return false;
				}
				Player player = (Player) sender;
				taxi.setUser_name(sender.getName());
				taxi.setHouse_name(arg);
				systemTaxiTeleport.setTaxi(taxi);
				if (!system.hasPermission(player, systemTaxiTeleport.getListTeleport().size())) {
					sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Voc� alcan�ou o limite de teleportes.");
					return true;
				}
				setValues(player, arg);
				systemTaxiTeleport.setTaxi(taxi);
				ModelTaxi taxiReturn = systemTaxiTeleport.getTeleport();
				if(taxiReturn != null){
					systemTaxiTeleport.delTeleport();
				}
				systemTaxiTeleport.setTeleport();
				sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Teleporte '"+taxi.getHouse_name()+"' criado com sucesso!");
				
			} catch (SQLException | CloneNotSupportedException e) {
				e.printStackTrace();
			}
			break;
		}

		return false;
	}

	private void setValues(Player player, String arg) {
		Location locationPlayer = player.getLocation();
		taxi.setHouse_mode(0);
		taxi.setHouse_name(arg);
		taxi.setUser_name(player.getName());
		taxi.setHouse_world(locationPlayer.getWorld().getName());
		Double x = locationPlayer.getX();
		Double y = locationPlayer.getY();
		Double z = locationPlayer.getZ();
		Float pitch = locationPlayer.getPitch();
		taxi.setHouse_x(x.toString());
		taxi.setHouse_y(y.toString());
		taxi.setHouse_z(z.toString());
		taxi.setHouse_pitch(pitch.toString());
	}

}
