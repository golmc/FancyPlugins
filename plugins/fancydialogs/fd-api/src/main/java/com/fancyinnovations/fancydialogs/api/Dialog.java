package com.fancyinnovations.fancydialogs.api;

import com.fancyinnovations.fancydialogs.api.data.DialogData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Dialog {

    protected String id;
    protected DialogData data;
    protected Set<UUID> viewers;

    public Dialog(String id, DialogData data) {
        this.id = id;
        this.data = data;
        this.viewers = ConcurrentHashMap.newKeySet();
    }

    public Dialog() {
    }

    /**
     * Opens the dialog for the specified player.
     *
     * @param player the player to open the dialog for
     */
    abstract public void open(Player player);

    /**
     * Closes the dialog for the specified player.
     *
     * @param player the player to close the dialog for
     */
    abstract public void close(Player player);

    public String getId() {
        return id;
    }

    public DialogData getData() {
        return data;
    }

    /**
     * @return a set of UUIDs of players who have this dialog opened
     */
    public Set<UUID> getViewers() {
        return Set.copyOf(viewers);
    }

    /**
     * Checks if the dialog is opened for a specific player by UUID.
     *
     * @param uuid of the player to check
     * @return true if the dialog is opened for the player, false otherwise
     */
    public boolean isOpenedFor(UUID uuid) {
        if (uuid == null) {
            return false;
        }

        return viewers.contains(uuid);
    }

    /***
     * Checks if the dialog is opened for a specific player.
     *
     * @param player the player to check
     * @return true if the dialog is opened for the player, false otherwise
     */
    public boolean isOpenedFor(Player player) {
        if (player == null) {
            return false;
        }

        return isOpenedFor(player.getUniqueId());
    }

    @ApiStatus.Internal
    public void addViewer(Player player) {
        if (player == null) {
            return;
        }
        viewers.add(player.getUniqueId());
    }

    @ApiStatus.Internal
    public void removeViewer(Player player) {
        if (player == null) {
            return;
        }
        viewers.remove(player.getUniqueId());
    }
}
