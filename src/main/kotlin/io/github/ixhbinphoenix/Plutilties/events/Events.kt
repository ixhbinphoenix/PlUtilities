package io.github.ixhbinphoenix.Plutilties.events

import io.github.ixhbinphoenix.Plutilties.chat.Renderer
import io.github.ixhbinphoenix.Plutilties.getInstance
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.scheduler.BukkitTask

class Events : Listener {
  private val plugin = getInstance()
  private val renderer = Renderer()

  @EventHandler
  fun onPlayerMove(event: PlayerMoveEvent) {
    if (event.hasChangedBlock()) {
      val spawnQ = plugin.spawnManager.timers[event.player.uniqueId]
      if (spawnQ is BukkitTask) {
        plugin.spawnManager.cancelRequest(event.player.uniqueId)
        event.player.sendMessage(Component.text("You moved! Cancelled the teleport!").color(NamedTextColor.RED))
      }
      val reqs = plugin.tpaManager.getRunningRequests(event.player)
      if (reqs.isNotEmpty()) {
        for (req in reqs) {
          plugin.tpaManager.abortTPA(req)
        }
      }
    }
  }

  @EventHandler
  fun onChat(event: AsyncChatEvent) {
    event.renderer(renderer)
  }
}