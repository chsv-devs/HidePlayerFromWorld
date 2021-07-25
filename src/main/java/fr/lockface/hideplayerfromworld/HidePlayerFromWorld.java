package fr.lockface.hideplayerfromworld;


import org.bukkit.plugin.java.JavaPlugin;


public class HidePlayerFromWorld extends JavaPlugin
{
	public ConfigGestion config;
	
	
	@Override
	public void onEnable()
	{
		// Register event when the player change world
		WorldChangeEventListener event = new WorldChangeEventListener(this);
		getServer().getPluginManager().registerEvents(event, this);
		
		// register command, give it the event, because some manipulation require the event
		this.getCommand("hideplayerfromworld").setExecutor(new AdminCommands(this, event));
		
		// create config
		config = new ConfigGestion(this);
		
	}
	 
	@Override
	public void onDisable()
	{
	}
}
