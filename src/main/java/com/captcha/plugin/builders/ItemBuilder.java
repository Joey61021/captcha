package com.captcha.plugin.builders;

import com.captcha.plugin.CaptchaAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBuilder {
	private final Material material;
	private final List<String> lore = new ArrayList<>();
	private final List<PersistentEntry> persistentEntries = new ArrayList<>();
	private final Map<Enchantment, Integer> enchantments = new HashMap<>();
	private int amount = 1;
	private String displayName;
	private boolean glow = false;
	private OfflinePlayer skullOwner;
	private boolean unbreakable = false;
	private int damage = -1;

	public ItemBuilder(Material material) {
		this.material = material;
	}

	public ItemBuilder(Material material, int amount) {
		this.material = material;
		this.amount = amount;
	}

	public ItemBuilder addEnchant(Enchantment enchantment, int level) {
		this.enchantments.put(enchantment, level);
		return this;
	}

	public ItemBuilder setUnbreakable() {
		this.unbreakable = true;
		return this;
	}

	public ItemBuilder setDisplayName(String displayName) {
		this.displayName = displayName;
		return this;
	}

	public ItemBuilder setLore(List<String> lore) {
		for (String value : lore) {
			this.lore.add(CaptchaAPI.color("&7" + value));
		}
		return this;
	}

	public ItemBuilder setGlow() {
		this.glow = true;
		return this;
	}

	public ItemBuilder setSkull(String skullOwner) {
		this.skullOwner = Bukkit.getOfflinePlayer(skullOwner);
		return this;
	}

	public ItemBuilder setSkull(Player player) {
		this.skullOwner = player;
		return this;
	}

	public ItemBuilder addPersistentContainer(NamespacedKey key, String value) {
		persistentEntries.add(new PersistentEntry(key, PersistentDataType.STRING, value));
		return this;
	}

	public <T, Z> ItemBuilder addPersistentContainer(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
		persistentEntries.add(new PersistentEntry(key, type, value));
		return this;
	}

	public ItemBuilder addLore(String value) {
		this.lore.add(CaptchaAPI.color("&7" + value));
		return this;
	}

	@Deprecated
	public ItemBuilder setDurability(short durability) {
		this.damage = durability;
		return this;
	}

	public ItemBuilder setDamage(int damage) {
		this.damage = Math.max(0, damage);
		return this;
	}

	public ItemBuilder setRemainingDurability(int remainingDurability) {
		int maxDurability = material.getMaxDurability();
		if (maxDurability <= 0) {
			return this;
		}

		int remaining = Math.max(0, Math.min(remainingDurability, maxDurability));
		this.damage = maxDurability - remaining;
		return this;
	}

	public ItemStack build() {
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();

		if (meta == null) {
			return item;
		}

		if (skullOwner != null && meta instanceof SkullMeta skullMeta) {
			skullMeta.setOwningPlayer(skullOwner);
		}

		for (PersistentEntry entry : persistentEntries) {
			entry.apply(meta);
		}

		meta.setUnbreakable(unbreakable);

		if (displayName != null) {
			meta.setDisplayName(CaptchaAPI.color(displayName));
		}

		if (!lore.isEmpty()) {
			meta.setLore(lore);
		}

		if (glow) {
			meta.addEnchant(Enchantment.UNBREAKING, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}

		enchantments.forEach((enchant, level) -> meta.addEnchant(enchant, level, true));

		if (damage >= 0 && meta instanceof Damageable damageMeta) {
			damageMeta.setDamage(damage);
		}

		item.setItemMeta(meta);
		return item;
	}

	private record PersistentEntry(NamespacedKey key, PersistentDataType<?, ?> type, Object value) {

		@SuppressWarnings("unchecked")
		private void apply(ItemMeta meta) {
			PersistentDataType<Object, Object> castType = (PersistentDataType<Object, Object>) type;
			meta.getPersistentDataContainer().set(key, castType, value);
		}
	}
}
