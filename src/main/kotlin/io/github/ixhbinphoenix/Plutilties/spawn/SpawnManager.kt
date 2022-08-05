package io.github.ixhbinphoenix.Plutilties.spawn

import io.github.ixhbinphoenix.Plutilties.cooldown.SpawnWaitRunnable
import io.github.ixhbinphoenix.Plutilties.getInstance
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import java.util.UUID

class SpawnManager {
    var location: Location? = null
    val timers = HashMap<UUID, BukkitTask>()

    fun setSpawn(location: Location) {
        val plugin = getInstance()
        this.location = location
        plugin.config.set("spawn", location)
        plugin.saveConfig()
    }

    fun queueRequest(player: Player) {
        val plugin = getInstance()
        timers[player.uniqueId] = SpawnWaitRunnable(player).runTaskLater(plugin, 100)
    }

    fun cancelRequest(uuid: UUID) {
        timers.remove(uuid)
    }
}