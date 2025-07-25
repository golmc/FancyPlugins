package com.fancyinnovations.fancyholograms.storage.json.model;

import org.bukkit.entity.Display;

public record JsonDisplayHologramData(
        JsonVec3f scale,
        JsonVec3f translation,
        Float shadow_radius,
        Float shadow_strength,
        JsonBrightness brightness,
        Display.Billboard billboard
) {
}

