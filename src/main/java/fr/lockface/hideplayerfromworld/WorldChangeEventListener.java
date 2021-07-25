package fr.lockface.hideplayerfromworld;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

@SuppressWarnings("static-access")
public class WorldChangeEventListener implements Listener
{
	HidePlayerFromWorld plugin;
	private ArrayList<String> byPassPlayers = new ArrayList<>();
	private ArrayList<String> visiblePlayers = new ArrayList<>();
	
	public WorldChangeEventListener(HidePlayerFromWorld main)
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
		byPassPlayers.add(player.getName());
		for (Player p2 : Bukkit.getServer().getOnlinePlayers())
			player.showPlayer(plugin, p2);
	}
	
	public void removeBypass(Player player)
	{
		if (byPassPlayers.contains(player.getName()))
		{
			byPassPlayers.remove(player.getName());
			applyHidding(player);
		}
	}
	
	public boolean isBypass(Player player)
	{
		return byPassPlayers.contains(player.getName());
	}
	
	public void setVisible(Player player)
	{
		visiblePlayers.add(player.getName());
		for (Player p2 : Bukkit.getServer().getOnlinePlayers())
			p2.showPlayer(plugin, player);
	}
	
	public void setInvisible(Player player)
	{
		if (visiblePlayers.contains(player.getName()))
		{
			visiblePlayers.remove(player.getName());
			applyHidding(player);
		}
	}
	
	public boolean isVisible(Player player)
	{
		return visiblePlayers.contains(player.getName());
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
				if (!byPassPlayers.contains(p2.getName()))
					// if the current player is seen by everybody
					if (!visiblePlayers.contains(p.getName()))
						p2.hidePlayer(plugin, p);
				
				// if the target player is seen by everybody
				if (!visiblePlayers.contains(p2.getName()))
					// if the current player ByPass and is able to see everybody
					if (!byPassPlayers.contains(p.getName()))
						p.hidePlayer(plugin, p2);
			}
		}
		
	}
	
	private void showAllPlayersFromPlayer(Player p)
	{
		for (Player p2 : Bukkit.getServer().getOnlinePlayers())
		{
			p2.showPlayer(p);
			p.showPlayer(p2);
		}
	}

}
