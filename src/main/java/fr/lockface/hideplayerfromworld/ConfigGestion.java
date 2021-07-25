package fr.lockface.hideplayerfromworld;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigGestion
{
	HidePlayerFromWorld plugin;
	FileConfiguration config;
	
	public ConfigGestion(HidePlayerFromWorld pl)
	{
		plugin = pl;
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveDefaultConfig();
		config = plugin.getConfig();
	}
	
	public boolean isHiddenWorld(String world_name)
	{
		for (String world : config.getStringList("hidden_worlds"))
		{
			if (world.equals(world_name))
				return true;
		}
		return false;
	}
	
	public boolean setHidden(String world)
	{
		List<String> hiddenWorlds = config.getStringList("hidden_worlds");
		if (hiddenWorlds.contains(world))
		{
			return false;
		}
		hiddenWorlds.add(world);
		config.set("hidden_worlds", hiddenWorlds);
		save();
		return true;
	}
	
	public List<String> getList()
	{
		return config.getStringList("hidden_worlds");
	}
	
	public boolean removeWorld(String world)
	{
		List<String> curr = config.getStringList("hidden_worlds");
		if (curr.contains(world))
		{
			curr.remove(world);
			config.set("hidden_worlds", curr);
			save();
			return true;
		}
		return false;
	}
	
	private void save()
	{
		plugin.saveConfig();
	}
}
