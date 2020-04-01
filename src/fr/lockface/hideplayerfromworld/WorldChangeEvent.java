package fr.lockface.hideplayerfromworld;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

@SuppressWarnings("static-access")
public class WorldChangeEvent implements Listener
{
	HidePlayerFromWorld plugin;
	private ArrayList<String> ByPassPlayers = new ArrayList<String>();
	private ArrayList<String> VisiblePlayers = new ArrayList<String>();
	
	public WorldChangeEvent(HidePlayerFromWorld main)
	{
		plugin = main;
	}
	
	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent e)
	{
		applyHidding(e.getPlayer());
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		applyHidding(e.getPlayer());
	}
	
	public void setBypass(Player player)
	{
		ByPassPlayers.add(player.getName());
		for (Player p2 : Bukkit.getServer().getOnlinePlayers())
			player.showPlayer(plugin.getPlugin(plugin.getClass()), p2);
	}
	
	public void removeBypass(Player player)
	{
		if (ByPassPlayers.contains(player.getName()))
		{
			ByPassPlayers.remove(player.getName());
			applyHidding(player);
		}
	}
	
	public boolean isBypass(Player player)
	{
		return ByPassPlayers.contains(player.getName());
	}
	
	public void setVisible(Player player)
	{
		VisiblePlayers.add(player.getName());
		for (Player p2 : Bukkit.getServer().getOnlinePlayers())
			p2.showPlayer(plugin.getPlugin(plugin.getClass()), player);
	}
	
	public void setUnvible(Player player)
	{
		if (VisiblePlayers.contains(player.getName()))
		{
			VisiblePlayers.remove(player.getName());
			applyHidding(player);
		}
	}
	
	public boolean isVisible(Player player)
	{
		return VisiblePlayers.contains(player.getName());
	}
	
	public void reloadAll()
	{
		for (Player p : Bukkit.getServer().getOnlinePlayers())
			applyHidding(p);
	}
	
	private void applyHidding(Player p)
	{
		
		if (plugin.config.isHiddenWorld(p.getWorld().getName()))
			hideAllPlayersFromPlayer(p);
		else
			showAllPlayersFromPlayer(p);
	}
	
	
	private void hideAllPlayersFromPlayer(Player p)
	{
		for (Player p2 : Bukkit.getServer().getOnlinePlayers())
		{
			if (!p.getName().equals(p2.getName()))
			{
				// if the second player ByPass and is able to see everybody
				if (!ByPassPlayers.contains(p2.getName()))
					// if the current player is seen by everybody
					if (!VisiblePlayers.contains(p.getName()))
						p2.hidePlayer(plugin.getPlugin(plugin.getClass()), p);
				
				// if the target player is seen by everybody
				if (!VisiblePlayers.contains(p2.getName()))
					// if the current player ByPass and is able to see everybody
					if (!ByPassPlayers.contains(p.getName()))
						p.hidePlayer(plugin.getPlugin(plugin.getClass()), p2);
			}
		}
		
	}
	
	private void showAllPlayersFromPlayer(Player p)
	{
		for (Player p2 : Bukkit.getServer().getOnlinePlayers())
		{
			p2.showPlayer(plugin.getPlugin(plugin.getClass()), p);
			p.showPlayer(plugin.getPlugin(plugin.getClass()), p2);
		}
	}

}
