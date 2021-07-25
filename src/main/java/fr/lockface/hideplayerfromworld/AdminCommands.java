package fr.lockface.hideplayerfromworld;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommands implements CommandExecutor {

	private HidePlayerFromWorld plugin;
	private WorldChangeEventListener listener;
	
	public AdminCommands(HidePlayerFromWorld pl, WorldChangeEventListener e)
	{
		plugin = pl;
		listener = e;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (sender instanceof Player)
		{
			Player p = (Player) sender;
			if (!hasAnyPerm(p))
			{
				sendNoPerm(p);
				return true;
			}
			else
			{
				if (args.length == 0)
				{
					sendUsage(sender);
					return true;
				}

				if (args[0].equalsIgnoreCase("addWorld"))
				{
					if (!p.hasPermission("hpw.admin"))
					{
						sendNoPerm(p);
						return true;
					}
					String world = p.getWorld().getName();
					if (args.length >= 2)
					{
						world = args[1];
					}
					if (!plugin.config.setHidden(world))
						p.sendMessage("§e[HPW] §6World §c" + world + " §6is already a hidden world.");
					else
						p.sendMessage("§e[HPW] §6Succesfully added §c" + world + " §6to the hidden worlds.");
					listener.reloadAll();
					return true;
				}
				else if (args[0].equalsIgnoreCase("removeWorld"))
				{
					if (!p.hasPermission("hpw.admin"))
					{
						sendNoPerm(p);
						return true;
					}
					String world = p.getWorld().getName();
					if (args.length >= 2)
					{
						world = args[1];
					}
					if (!plugin.config.removeWorld(world))
						p.sendMessage("§e[HPW] §6World §c" + world + " §6 is not a hidden world.");
					else
						p.sendMessage("§e[HPW] §6Successfully removed §c" + world + "§6 from hidden worlds");
					listener.reloadAll();
					return true;
				}
				else if (args[0].equalsIgnoreCase("list"))
				{
					if (!p.hasPermission("hpw.admin"))
					{
						sendNoPerm(p);
						return true;
					}
					List<String> l = plugin.config.getList();
					p.sendMessage("§e[HPW] §6List of hidden worlds:");
					for (String w : l)
						p.sendMessage("§7- §a" + w);
					return true;
				}
				else if (args[0].equalsIgnoreCase("makeVisible"))
				{
					if (args.length > 1)
					{
						if (args[1].equals("on"))
						{
							listener.setVisible(p);
							p.sendMessage("§e[HPW] §6You are now visible in hidden worlds");
						}
						else if (args[1].equals("off"))
						{
							listener.setInvisible(p);
							p.sendMessage("§e[HPW] §6You are now invible in hidden worlds");
						}
						else
						{
							sendUsage(p);
							return false;
						}
						return true;
					}
					else
					{
						if (listener.isVisible(p))
						{
							listener.setInvisible(p);
							p.sendMessage("§e[HPW] §6You are now invisible in hidden worlds");
						}
						else
						{
							listener.setVisible(p);
							p.sendMessage("§e[HPW] §6You are now visible in hidden worlds");
						}
						return true;
					}
				}
				else if (args[0].equalsIgnoreCase("showPlayers"))
				{
					if (args.length > 1)
					{
						if (args[1].equals("on"))
						{
							listener.setBypass(p);
							p.sendMessage("§e[HPW] §6You are now able to see everybody in hidden worlds");
						}
						else if (args[1].equals("off"))
						{
							listener.removeBypass(p);
							p.sendMessage("§e[HPW] §6You are no longer able to see players in hidden worlds");
						}
						else
						{
							sendUsage(p);
							return false;
						}
						return true;
					}
					else
					{
						if (listener.isBypass(p))
						{
							listener.removeBypass(p);
							p.sendMessage("§e[HPW] §6You are no longer able to see players in hidden worlds");
						}
						else
						{
							listener.setBypass(p);
							p.sendMessage("§e[HPW] §6You are now able to see players in hidden worlds");
						}
						return true;
					}
				}
				else
				{
					sendUsage(p);
					return true;
				}
			}
		}
		else
		{
			if (!hasAnyPerm(sender))
				sendNoPerm(sender);
			if (args.length > 0)
			{
				
				if (args[0].equalsIgnoreCase("showplayers") || args[0].equalsIgnoreCase("makevisible"))
				{
					sender.sendMessage("§e[HPW] §6You can't use these commands in console!");
					sender.sendMessage("§6Here are the commands you are allowed to use here:");
					sender.sendMessage("§7- §c/hpw help");
					sender.sendMessage("§7- §c/hpw addworld");
					sender.sendMessage("§7- §c/hpw removeworld");
					sender.sendMessage("§7- §c/hpw list");
					return true;
				}
				if (args[0].equalsIgnoreCase("addworld"))
				{
					if (!sender.hasPermission("hpw.admin"))
					{
						sendNoPerm(sender);
						return true;
					}
					String world = null;
					if (args.length >= 2)
					{
						world = args[1];
					}
					else
					{
						sender.sendMessage("§e[HPW] §6Please specify a world.");
						return true;
					}
					if (!plugin.config.setHidden(world))
						sender.sendMessage("§e[HPW] §6World §c" + world + " §6is already a hidden world.");
					else
						sender.sendMessage("§e[HPW] §6Succesfully added §c" + world + " §6to the hidden worlds.");
					listener.reloadAll();
					return true;
				}
				else if (args[0].equalsIgnoreCase("removeworld"))
				{
					if (!sender.hasPermission("hpw.admin"))
					{
						sendNoPerm(sender);
						return true;
					}
					String world = null;
					if (args.length >= 2)
					{
						world = args[1];
					}
					else
					{
						sender.sendMessage("§e[HPW] §6Please specify a world.");
						return true;
					}
					if (!plugin.config.removeWorld(world))
						sender.sendMessage("§e[HPW] §6World §c" + world + " §6 is not a hidden world.");
					else
						sender.sendMessage("§e[HPW] §6Successfully removed §c" + world + "§6 from hidden worlds");
					listener.reloadAll();
					return true;
				}
				else if (args[0].equalsIgnoreCase("list"))
				{
					if (!sender.hasPermission("hpw.admin"))
					{
						sendNoPerm(sender);
						return true;
					}
					List<String> l = plugin.config.getList();
					sender.sendMessage("§e[HPW] §6List of hidden worlds:");
					for (String w : l)
						sender.sendMessage("§7- §a" + w);
					return true;
				}
				else
				{
					sendUsage(sender);
				}
			}
			else
			{
				sendUsage(sender);
			}
		}
		return true;
	}

	private void sendNoPerm(CommandSender p)
	{
		p.sendMessage("§cYou are not allowed to do that!");
	}
	
	private void sendUsage(CommandSender sender)
	{
		if (hasAnyPerm(sender))
		{
			sender.sendMessage("§e[HPW] Usage:");
			sender.sendMessage("§6/hpw help§e : Show this help message");
			if (sender.hasPermission("hpw.admin"))
			{
				sender.sendMessage(
						"§6/hpw addworld [world=current]§e : Add the world given or current world to the hidden worlds");
				sender.sendMessage("§6/hpw removeworld [world=current]§e : Remove the given world or current world from the hidden worlds");
				sender.sendMessage("§6/hpw list §e: List all hidden worlds");
			}
			if (sender.hasPermission("hpw.seeplayers"))
				sender.sendMessage("§6/hpw showplayers [on/off] §e: Make you able to see all the other players in hidden worlds");
			if (sender.hasPermission("hpw.bevisible"))
				sender.sendMessage("§6/hpw makevisible [on/off]§e : Make you visible in hidden worlds");
		}
	}

	private boolean hasAnyPerm(CommandSender p)
	{
		return p.hasPermission("hpw.command") || p.hasPermission("hpw.seeplayers") || p.hasPermission("hpw.admin") || p.hasPermission("hpw.bevisible");
	}
}
