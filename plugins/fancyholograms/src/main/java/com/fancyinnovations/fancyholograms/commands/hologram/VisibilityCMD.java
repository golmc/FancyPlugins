package com.fancyinnovations.fancyholograms.commands.hologram;

import com.fancyinnovations.fancyholograms.api.data.property.Visibility;
import com.fancyinnovations.fancyholograms.api.hologram.Hologram;
import com.fancyinnovations.fancyholograms.commands.Subcommand;
import com.fancyinnovations.fancyholograms.main.FancyHologramsPlugin;
import de.oliver.fancylib.MessageHelper;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class VisibilityCMD implements Subcommand {

    @Override
    public List<String> tabcompletion(@NotNull CommandSender player, @Nullable Hologram hologram, @NotNull String[] args) {
        return Arrays.stream(
                Visibility.values()
        ).map(Objects::toString).toList();
    }

    @Override
    public boolean run(@NotNull CommandSender player, @Nullable Hologram hologram, @NotNull String[] args) {

        if (!(player.hasPermission("fancyholograms.hologram.edit.visibility"))) {
            MessageHelper.error(player, "You don't have the required permission to edit a hologram");
            return false;
        }

        final var optionalVisibility = Visibility.byString(args[3]);
        if (hologram == null || optionalVisibility.isEmpty()) {
            return false;
        }
        final var visibility = optionalVisibility.get();

        final var copied = hologram.getData().copy(hologram.getData().getName());
        copied.setVisibility(visibility);

        if (hologram.getData().getVisibility() == copied.getVisibility()) {
            MessageHelper.warning(player, "This hologram already has visibility set to " + visibility);
            return false;
        }

        hologram.getData().setVisibility(copied.getVisibility());

        if (FancyHologramsPlugin.get().getHologramConfiguration().isSaveOnChangedEnabled()) {
            FancyHologramsPlugin.get().getStorage().save(hologram.getData());
        }

        MessageHelper.success(player, "Changed visibility to " + visibility);
        return true;
    }
}
