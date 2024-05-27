package more.mucho.durabilityincrease;

import more.mucho.durabilityincrease.durability.commands.DurabilityCommand;
import more.mucho.durabilityincrease.durability.listeners.ItemDamageListener;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;

public final class DurabilityIncrease extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerEvents();
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new ItemDamageListener(), this);
    }

    private void registerCommands() {
        registerCommand("setdur",new DurabilityCommand());
    }
    private void registerCommand(String command, CommandExecutor executor){
        registerCommand(command,executor,null);
    }
    private void registerCommand(String command, CommandExecutor executor, @Nullable TabCompleter completer){
        PluginCommand pluginCommand = getCommand(command);
        if(pluginCommand == null)return;
        pluginCommand.setExecutor(executor);
        if(completer!=null){
            pluginCommand.setTabCompleter(completer);
        }
    }


}
