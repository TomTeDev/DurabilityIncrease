package more.mucho.durabilityincrease.durability.commands;

import more.mucho.durabilityincrease.Debug;
import more.mucho.durabilityincrease.PERMISSION;
import more.mucho.durabilityincrease.durability.DurabilityHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class DurabilityCommand implements CommandExecutor {


    private final DurabilityHandler durabilityHandler;

    public DurabilityCommand() {
        this.durabilityHandler = new DurabilityHandler();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(ChatColor.RED + "Only players can use this command");
            return false;
        }
        if (!player.hasPermission(PERMISSION.SET_DUR_COMMAND.permissionNode)) return false;
        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Please specify the durability");
            return false;
        }
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (!isDamageable(itemStack)) {
            player.sendMessage(ChatColor.RED + "This item is not damageable");
            return false;
        }

        int durability = Integer.parseInt(args[0]);
        if(durability<=0){
            player.sendMessage(ChatColor.RED+"Durability must be greater than 0");
            return false;
        }

        durabilityHandler.healItem(itemStack, durability);
        player.sendMessage(ChatColor.GREEN + "Durability set to " + durability);

        return false;
    }

    public boolean isDamageable(ItemStack itemStack) {
        if (itemStack == null || itemStack.getItemMeta()==null) return false;
        return itemStack.getItemMeta() instanceof Damageable;
    }
}
