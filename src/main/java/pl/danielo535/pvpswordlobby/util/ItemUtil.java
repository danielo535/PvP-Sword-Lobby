package pl.danielo535.pvpswordlobby.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemUtil {
    private ItemUtil() {}
    public static Map<String, Object> serialize(ItemStack item) {
        final Map<String, Object> configurationSection = new HashMap<>();
        configurationSection.put("type", item.getType().name());
        configurationSection.put("amount", item.getAmount());
        if (item.hasItemMeta()) {
            final ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta.hasDisplayName()) configurationSection.put("name", itemMeta.getDisplayName());
            if (itemMeta.hasLore()) configurationSection.put("lore", itemMeta.getLore());
            if (!itemMeta.getEnchants().isEmpty()) {
                final Map<String, Integer> enchantments = new HashMap<>();
                itemMeta.getEnchants().forEach((enchantment, integer) -> enchantments.put(enchantment.getName(), integer));
                configurationSection.put("enchantments", enchantments);
            }
            if (!itemMeta.getItemFlags().isEmpty()) {
                final List<String> itemFlags = new ArrayList<>();
                itemMeta.getItemFlags().forEach(itemFlag -> itemFlags.add(itemFlag.name()));
                configurationSection.put("itemFlags", itemFlags);
            }
            if (itemMeta.hasCustomModelData()) {
                configurationSection.put("customModelData", itemMeta.getCustomModelData());
            }
            if (item.getType() == Material.PLAYER_HEAD) {
                SkullMeta skullMeta = (SkullMeta) itemMeta;
                if (skullMeta.getPlayerProfile() != null && !skullMeta.getPlayerProfile().getProperties().isEmpty()) {
                    ProfileProperty property = skullMeta.getPlayerProfile().getProperties().iterator().next();
                    configurationSection.put("value", property.getValue());
                    configurationSection.put("signature", property.getSignature());
                } else if (skullMeta.hasOwner()) {
                    configurationSection.put("skullOwner", skullMeta.getOwningPlayer().getName());
                }
            }
            if (itemMeta.hasAttributeModifiers()) {
                Map<String, String> attributes = new HashMap<>();
                itemMeta.getAttributeModifiers().forEach((attribute, attributeModifier) -> {
                    String name = attributeModifier.getName();
                    String getSlot = attributeModifier.getSlot() != null ? attributeModifier.getSlot().name() : null;
                    String amount = String.valueOf(attributeModifier.getAmount());
                    String operation = attributeModifier.getOperation().name();
                    String attributeDataFormat = name + ":" + amount + ":" + operation + ":" + getSlot + ":" + attributeModifier.getUniqueId();
                    attributes.put(attribute.name(), attributeDataFormat);
                });
                configurationSection.put("attributes", attributes);
            }
        }
        return configurationSection;
    }

    public static ItemStack deserialize(ConfigurationSection map) {
        final ItemStack itemStack;
        try {
            itemStack = new ItemStack(Material.valueOf(map.getString("type")), map.getInt("amount"));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Failed to deserialize ItemStack type: " + map.getString("type") + " as it does not exist");
        }

        if (map.isString("name") || map.isList("lore") || map.isConfigurationSection("enchantments") || map.isList("itemFlags") || map.isConfigurationSection("attributes") || map.isInt("customModelData") || map.isString("value") || map.isString("signature") || map.isString("skullOwner")) {
            final ItemMeta itemMeta = itemStack.getItemMeta();

            if (map.isString("name")) itemMeta.setDisplayName(TextUtil.colorize(map.getString("name")));
            if (map.isList("lore")) itemMeta.setLore(TextUtil.colorize(map.getStringList("lore")));

            if (map.isConfigurationSection("enchantments")) {
                final ConfigurationSection enchantmentsSection = map.getConfigurationSection("enchantments");
                for (String key : enchantmentsSection.getKeys(false)) {
                    final Enchantment enchantment = Enchantment.getByName(key.toUpperCase());
                    if (enchantment == null)
                        throw new IllegalArgumentException("Failed to deserialize enchantment: " + key + " as it does not exist");
                    final int level = enchantmentsSection.getInt(key);
                    itemMeta.addEnchant(enchantment, level, true);
                }
            }

            if (map.isList("itemFlags")) {
                final Collection<String> itemFlags = map.getStringList("itemFlags");
                itemFlags.forEach(key -> {
                    final ItemFlag itemFlag = ItemFlag.valueOf(key);
                    itemMeta.addItemFlags(itemFlag);
                });
            }

            if (map.isInt("customModelData")) {
                itemMeta.setCustomModelData(map.getInt("customModelData"));
            }

            if (map.isConfigurationSection("attributes")) {
                final ConfigurationSection attributes = map.getConfigurationSection("attributes");
                attributes.getKeys(false).forEach(key -> {
                    String[] attributeData = attributes.getString(key).split(":");
                    Attribute attribute = Attribute.valueOf(key);
                    double amount = Double.parseDouble(attributeData[1]);
                    AttributeModifier.Operation operation = AttributeModifier.Operation.valueOf(attributeData[2]);
                    EquipmentSlot slot = attributeData[3] != null && !attributeData[3].isEmpty() ? EquipmentSlot.valueOf(attributeData[3]) : null;
                    itemMeta.addAttributeModifier(attribute, new AttributeModifier(UUID.fromString(attributeData[4]), attributeData[0], amount, operation, slot));
                });
            }

            if (map.isString("skullOwner")) {
                SkullMeta skullMeta = (SkullMeta) itemMeta;
                String skullOwner = map.getString("skullOwner");
                skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(skullOwner));
                itemStack.setItemMeta(skullMeta);
            } else if (map.isString("value") && map.isString("signature")) {
                SkullMeta skullMeta = (SkullMeta) itemMeta;
                String value = map.getString("value");
                String signature = map.getString("signature");
                PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), null);
                profile.setProperty(new ProfileProperty("textures", value, signature));
                skullMeta.setPlayerProfile(profile);
            }

            itemStack.setItemMeta(itemMeta);
        }

        return itemStack;
    }
}
