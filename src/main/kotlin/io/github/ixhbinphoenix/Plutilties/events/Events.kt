package io.github.ixhbinphoenix.Plutilties.events

import io.github.ixhbinphoenix.Plutilties.getInstance
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class Events : Listener {
  private val plugin = getInstance()

  @EventHandler
  fun onPlayerMove(event: PlayerMoveEvent) {
    val reqs = plugin.tpaManager.getRunningRequests(event.player)
    if (reqs.isNotEmpty()) {
      for (req in reqs) {
        plugin.tpaManager.abortTPA(req)
      }
    }
  }
}