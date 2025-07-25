package com.fancyinnovations.fancyholograms.trait.builtin;

import com.fancyinnovations.fancyholograms.api.data.TextHologramData;
import com.fancyinnovations.fancyholograms.api.trait.HologramTrait;
import com.fancyinnovations.fancyholograms.api.trait.HologramTraitClass;
import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@ApiStatus.Experimental
@HologramTraitClass(traitName = "file_content_trait")
public class FileContentTrait extends HologramTrait {

    private static final Configuration DEFAULT_CONFIG = new Configuration(
            "server.properties",
            1000
    );

    private Configuration config;

    @Override
    public void onAttach() {
        if (!(hologram.getData() instanceof TextHologramData)) {
            throw new IllegalStateException("Hologram must be text hologram to use FileContentTrait");
        }

        load();

        if (config.refreshInterval > 0) {
            hologramThread.scheduleWithFixedDelay(
                    this::updateHologram,
                    0,
                    config.refreshInterval(), java.util.concurrent.TimeUnit.MILLISECONDS);
        } else {
            updateHologram();
        }
    }

    public void updateHologram() {
        Path path = Paths.get(config.filePath());
        if (!path.toFile().exists()) {
            logger.warn("File does not exist: " + config.filePath());
            return;
        }

        try {
            String content = Files.readString(path);
            List<String> lines = List.of(content.split("\\r?\\n"));
            ((TextHologramData) hologram.getData()).setText(lines);
        } catch (IOException e) {
            logger.error("Failed to read file content of: " + config.filePath());
            logger.error(e);
        }
    }

    @Override
    public void load() {
        try {
            config = storage.get(hologram.getData().getName(), Configuration.class);
        } catch (IOException e) {
            logger.error("Failed to load configuration for FileContentTrait");
            logger.error(e);
        }
        if (config == null) {
            config = DEFAULT_CONFIG;
            save();
        }
    }

    @Override
    public void save() {
        try {
            storage.set(hologram.getData().getName(), config);
        } catch (IOException e) {
            logger.error("Failed to save configuration for FileContentTrait");
            logger.error(e);
        }
    }

    record Configuration(
            String filePath,
            long refreshInterval
    ) {

    }
}
