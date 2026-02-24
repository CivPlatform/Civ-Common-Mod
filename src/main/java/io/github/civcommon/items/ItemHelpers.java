package io.github.civcommon.items;

import java.util.function.Consumer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class ItemHelpers {
    /// Creates an item with patched components. This is useful for any mods that assign categories to items upon its
    /// construction.
    ///
    /// @param amount Passing in `null` will create a max-stack item.
    public static @NotNull ItemStack createItem(
        final @NotNull Item material,
        Integer amount,
        final @NotNull Consumer<@NotNull PatchedDataComponentMap> patcher
    ) {
        final var components = new PatchedDataComponentMap(material.components());
        patcher.accept(components);
        if (amount == null) {
            if (components.get(DataComponents.MAX_STACK_SIZE) instanceof final Integer maxStackSize) {
                amount = maxStackSize;
            }
            else {
                amount = material.getDefaultMaxStackSize();
            }
        }
        return new ItemStack(
            material.builtInRegistryHolder(),
            amount,
            components.asPatch()
        );
    }
}
