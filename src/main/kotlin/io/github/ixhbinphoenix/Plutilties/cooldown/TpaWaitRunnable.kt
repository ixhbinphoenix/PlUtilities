package io.github.ixhbinphoenix.Plutilties.cooldown

import io.github.ixhbinphoenix.Plutilties.getInstance
import io.github.ixhbinphoenix.Plutilties.tpa.TpRequest
import org.bukkit.scheduler.BukkitRunnable

class TpaWaitRunnable(private val request: TpRequest) : BukkitRunnable() {
  private val tpaManager = getInstance().tpaManager
  override fun run() {
    tpaManager.runTPA(request)
  }
}