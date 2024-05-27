package more.mucho.durabilityincrease.durability;

import com.google.common.base.Preconditions;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;


public class DurabilityUtil {
    public static String TAG_CURRENT_DURABILITY = "m_tag_current_durability";
    public static String TAG_MAX_DURABILITY = "m_tag_max_durability";

    public static boolean isDurable(ItemStack itemStack) {
        Preconditions.checkNotNull(itemStack, "ItemStack cannot be null");
        if(itemStack.getItemMeta() == null)return false;
        return getCurrentDurability(itemStack) >= 0;
    }


    public static int getCurrentDurability(ItemStack itemStack) {
        if (!hasTag(itemStack, TAG_CURRENT_DURABILITY)) return -1;
        return getTagInt(itemStack, TAG_CURRENT_DURABILITY, -1);
    }
    public static int getMaxDurability(ItemStack itemStack) {
        if (!hasTag(itemStack, TAG_MAX_DURABILITY)) return -1;
        return getTagInt(itemStack, TAG_MAX_DURABILITY, -1);
    }

    public static void setMaxDurability(ItemStack itemStack, int durability) {
        setTagInt(itemStack, TAG_MAX_DURABILITY, durability);
    }

    public static void setCurrentDurability(ItemStack itemStack, int durability) {
        setTagInt(itemStack, TAG_CURRENT_DURABILITY, durability);
    }





    public static boolean hasTag(ItemStack itemStack, String tag) {
        net.minecraft.world.item.ItemStack nms = CraftItemStack.asNMSCopy(itemStack);
        return nms.getOrCreateTag().contains(tag);
    }

    public static int getTagInt(ItemStack itemStack, String tag, int defValue) {
        net.minecraft.world.item.ItemStack nms = CraftItemStack.asNMSCopy(itemStack);
        if (!hasTag(itemStack, tag)) return defValue;
        return nms.getOrCreateTag().getInt(tag);
    }

    public static void setTagInt(ItemStack itemStack, String tag, int value) {
        net.minecraft.world.item.ItemStack nms = CraftItemStack.asNMSCopy(itemStack);
        nms.getOrCreateTag().putInt(tag, value);
        itemStack.setItemMeta(CraftItemStack.asBukkitCopy(nms).getItemMeta());
    }

}
