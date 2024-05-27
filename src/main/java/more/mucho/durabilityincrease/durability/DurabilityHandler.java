package more.mucho.durabilityincrease.durability;

import more.mucho.durabilityincrease.Debug;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class DurabilityHandler {
    private final Random random;

    public DurabilityHandler(Random random) {
        this.random = random;
    }

    public DurabilityHandler() {
        this(new Random());
    }

    //https://minecraft.wiki/w/Unbreaking

    private boolean canDamage(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (!DurabilityUtil.isDurable(itemStack)) return false;
        if (itemStack.getItemMeta() == null) return false;
        if (DurabilityUtil.getCurrentDurability(itemStack) <= 0) return false;
        int unbreaking = itemStack.getItemMeta().getEnchants().getOrDefault(Enchantment.DURABILITY, 0);
        if (ignoreDamage(unbreaking)) return false;

        return true;
    }

    private boolean ignoreDamage(int unbreakingLevel) {
        int chance = 100 / (unbreakingLevel + 1);
        return random.nextInt(101) > chance;
    }

    public void damageItem(ItemStack itemStack) {
        if (!canDamage(itemStack)) return;

        int currentDurability = DurabilityUtil.getCurrentDurability(itemStack);
        if (currentDurability == 1) {
            itemStack.setAmount(0);
            return;
        }
        DurabilityUtil.setCurrentDurability(itemStack, currentDurability - 1);

        ItemMeta meta = itemStack.getItemMeta();
        if (meta instanceof Damageable damageable) {
            net.minecraft.world.item.ItemStack nms = CraftItemStack.asNMSCopy(itemStack);
            int maxDamage = nms.getMaxDamage();

            int maxDur = DurabilityUtil.getMaxDurability(itemStack);
            int currentDur = DurabilityUtil.getCurrentDurability(itemStack);
            double damageProgress = (double)maxDur / currentDur;

            double newDamage = maxDamage/damageProgress;
            int reverseDamage = maxDamage- (int)newDamage;
            if(reverseDamage>maxDamage)reverseDamage = maxDamage;
            if(reverseDamage<0)reverseDamage = 0;
            damageable.setDamage(reverseDamage);
            itemStack.setItemMeta(meta);
        }
    }

    public void healItem(ItemStack itemStack, int durability) {
        DurabilityUtil.setMaxDurability(itemStack, durability);
        DurabilityUtil.setCurrentDurability(itemStack, durability);
        ItemMeta meta = itemStack.getItemMeta();
        if (meta instanceof Damageable damageable) {
            damageable.setDamage(0);
            itemStack.setItemMeta(meta);
        }
    }
}
