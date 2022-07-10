package net.plazmix.gungame.util;

import net.plazmix.game.utility.hotbar.GameHotbar;
import net.plazmix.game.utility.hotbar.GameHotbarBuilder;
import net.plazmix.utility.ItemUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public final class GameKit {

    private final GameHotbar PLAYER_DEFAULT_KIT = GameHotbarBuilder.newBuilder()
            .setMoveItems(true)
            .addItem(1, ItemUtil.newBuilder(Material.WOOD_AXE)
                    .setUnbreakable(true)
                    .addEnchantment(Enchantment.DURABILITY, 3)
                    .build())
            .build();

    private final GameHotbar PLAYER_2_KIT = GameHotbarBuilder.newBuilder()
            .setMoveItems(true)
            .addItem(1, ItemUtil.newBuilder(Material.WOOD_AXE)
                    .setUnbreakable(true)
                    .addEnchantment(Enchantment.DURABILITY, 3)
                    .addEnchantment(Enchantment.DAMAGE_ALL, 1)
                    .build())
            .build();

    private final GameHotbar PLAYER_3_KIT = GameHotbarBuilder.newBuilder()
            .setMoveItems(true)
            .addItem(1, ItemUtil.newBuilder(Material.WOOD_SWORD)
                    .addEnchantment(Enchantment.DURABILITY, 3)
                    .build())
            .build();

    private final GameHotbar PLAYER_4_KIT = GameHotbarBuilder.newBuilder()
            .setMoveItems(true)
            .addItem(1, ItemUtil.newBuilder(Material.WOOD_SWORD)
                    .addEnchantment(Enchantment.DURABILITY, 3)
                    .addEnchantment(Enchantment.DAMAGE_ALL, 1)
                    .build())
            .build();

    private final GameHotbar PLAYER_5_KIT = GameHotbarBuilder.newBuilder()
            .setMoveItems(true)
            .addItem(1, ItemUtil.newBuilder(Material.WOOD_SWORD)
                    .addEnchantment(Enchantment.DURABILITY, 3)
                    .addEnchantment(Enchantment.DAMAGE_ALL, 1)
                    .build())
            .build();

    private final GameHotbar PLAYER_8_KIT = GameHotbarBuilder.newBuilder()
            .setMoveItems(true)
            .addItem(1, ItemUtil.newBuilder(Material.STONE_SWORD)
                    .addEnchantment(Enchantment.DURABILITY, 3)
                    .build())
            .build();

    private final GameHotbar PLAYER_9_KIT = GameHotbarBuilder.newBuilder()
            .setMoveItems(true)
            .addItem(1, ItemUtil.newBuilder(Material.STONE_SWORD)
                    .addEnchantment(Enchantment.DURABILITY, 3)
                    .addEnchantment(Enchantment.DAMAGE_ALL, 1)
                    .build())
            .build();


    public void giveLevelKit(Player player) {
        int level = player.getLevel();
        PlayerInventory playerInventory = player.getInventory();

        switch (level) {
            case 1:
                PLAYER_DEFAULT_KIT.setHotbarTo(player);
                break;
            case 2:
                PLAYER_2_KIT.setHotbarTo(player);
                break;
            case 3:
                PLAYER_3_KIT.setHotbarTo(player);
                break;
            case 4:
                PLAYER_4_KIT.setHotbarTo(player);
                break;
            case 5:
                PLAYER_5_KIT.setHotbarTo(player);
                playerInventory.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                playerInventory.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                break;
            case 6:
                PLAYER_5_KIT.setHotbarTo(player);
                playerInventory.setHelmet(new ItemStack(Material.LEATHER_HELMET));
                playerInventory.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                playerInventory.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                break;
            case 7:
                PLAYER_5_KIT.setHotbarTo(player);
                playerInventory.setHelmet(new ItemStack(Material.LEATHER_HELMET));
                playerInventory.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                playerInventory.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                playerInventory.setBoots(new ItemStack(Material.LEATHER_BOOTS));
                break;
            case 8:
                PLAYER_8_KIT.setHotbarTo(player);
                playerInventory.setHelmet(new ItemStack(Material.LEATHER_HELMET));
                playerInventory.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                playerInventory.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                playerInventory.setBoots(new ItemStack(Material.LEATHER_BOOTS));
                break;
            case 9:
                PLAYER_9_KIT.setHotbarTo(player);
                playerInventory.setHelmet(new ItemStack(Material.LEATHER_HELMET));
                playerInventory.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                playerInventory.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                playerInventory.setBoots(new ItemStack(Material.LEATHER_BOOTS));
                break;
            case 10:
                PLAYER_9_KIT.setHotbarTo(player);
                playerInventory.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
                playerInventory.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                playerInventory.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                playerInventory.setBoots(new ItemStack(Material.LEATHER_BOOTS));
                break;
            case 11:
                PLAYER_9_KIT.setHotbarTo(player);
                playerInventory.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
                playerInventory.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                playerInventory.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                playerInventory.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                break;
            case 12:
                PLAYER_9_KIT.setHotbarTo(player);
                playerInventory.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
                playerInventory.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                playerInventory.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                playerInventory.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                break;
            case 13:
                PLAYER_9_KIT.setHotbarTo(player);
                playerInventory.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
                playerInventory.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                playerInventory.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                playerInventory.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                break;
            case 14:
                PLAYER_9_KIT.setHotbarTo(player);
                playerInventory.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
                playerInventory.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                playerInventory.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                playerInventory.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                break;
            case 15:
                PLAYER_9_KIT.setHotbarTo(player);
                playerInventory.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
                playerInventory.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                playerInventory.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                playerInventory.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                break;
            default:
                PLAYER_DEFAULT_KIT.setHotbarTo(player);
        }
    }
}
