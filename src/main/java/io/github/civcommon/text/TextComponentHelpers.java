package io.github.civcommon.text;

import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.NotNull;

public final class TextComponentHelpers {
    public static final Pattern LEGACY_FORMATTING_PATTERN = Pattern.compile("§.");

    public static @NotNull Component ofChildren(
        final @NotNull Component @NotNull ... children
    ) {
        final MutableComponent result = Component.empty();
        result.getSiblings().addAll(List.of(children));
        return result;
    }

    /// Gets the total text content of a given component, with all styling and legacy formatting stripped out.
    public static @NotNull String getPlainText(
        final @NotNull Component component
    ) {
        return LEGACY_FORMATTING_PATTERN
            .matcher(component.getString())
            .replaceAll("");
    }

    /// Tests whether a given component is a "plain" component whose text matches an expected string. Any styling
    /// (including eg hovertext) will cause the component to be considered non-plain and will immediately return false
    /// regardless of the text content.
    public static boolean matchesPlainText(
        final Component component,
        final @NotNull String expected,
        final boolean ignoreCase
    ) {
        if (component == null) {
            return false;
        }
        final var combined = new StringBuilder();
        for (final Component child : component.toFlatList()) {
            if (!Style.EMPTY.equals(child.getStyle())) {
                return false;
            }
            final String content = child.getString();
            if (LEGACY_FORMATTING_PATTERN.matcher(content).matches()) {
                return false;
            }
            combined.append(content);
        }
        return ignoreCase
            ? expected.equalsIgnoreCase(combined.toString())
            : expected.contentEquals(combined);
    }
}
