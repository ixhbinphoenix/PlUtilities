package io.github.ixhbinphoenix.Plutilties.cooldown

import io.github.ixhbinphoenix.Plutilties.getInstance
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class SpawnWaitRunnable(val player: Player) : BukkitRunnable() {
    private val spawnManager = getInstance().spawnManager

    override fun run() {
        spawnManager.timers.remove(player.uniqueId)
        if (spawnManager.location is Location) {
            player.teleport(spawnManager.location!!)
            player.sendMessage(Component.text("Successfully teleported!").color(NamedTextColor.GOLD))
        } else {
            player.sendMessage(Component.text("Spawn was deleted while waiting!").color(NamedTextColor.RED))
        }
    }
}