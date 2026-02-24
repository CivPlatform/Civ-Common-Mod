package io.github.civcommon.items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record ItemDurability(
    int damage,
    int maxDamage
) {
    /// Damage is stored starting at 0 and increasing until reaching MAX, whereas items display the durability starting
    /// at MAX and decreasing until it reaches 0 and breaks. This returns the latter.
    public int getVisualDurability() {
        return maxDamage() - damage();
    }

    public static @Nullable ItemDurability from(
        final ItemStack item
    ) {
        if (item == null || item.isEmpty()) {
            return null;
        }
        if (item.has(DataComponents.UNBREAKABLE)) {
            return null;
        }
        if (!(item.get(DataComponents.MAX_DAMAGE) instanceof final Integer max)) {
            return null;
        }
        if (!(item.get(DataComponents.DAMAGE) instanceof final Integer damage)) {
            return null;
        }
        return new ItemDurability(
            damage,
            max
        );
    }
}
