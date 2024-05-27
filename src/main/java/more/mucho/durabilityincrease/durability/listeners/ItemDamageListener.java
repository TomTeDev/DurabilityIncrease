package more.mucho.durabilityincrease.durability.listeners;

import more.mucho.durabilityincrease.Debug;
import more.mucho.durabilityincrease.durability.DurabilityHandler;
import more.mucho.durabilityincrease.durability.DurabilityUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

public class ItemDamageListener implements Listener {

    private final DurabilityHandler durabilityHandler;
    public ItemDamageListener(){
        durabilityHandler = new DurabilityHandler();
    }
    @EventHandler
    public void onDamage(PlayerItemDamageEvent event) {
        ItemStack itemStack = event.getItem();

        if(!DurabilityUtil.isDurable(event.getItem()))return;
        event.setCancelled(true);
        durabilityHandler.damageItem(itemStack);
    }
}
