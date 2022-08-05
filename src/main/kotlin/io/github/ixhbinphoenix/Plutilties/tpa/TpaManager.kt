package io.github.ixhbinphoenix.Plutilties.tpa

import io.github.ixhbinphoenix.Plutilties.cooldown.TpaWaitRunnable
import io.github.ixhbinphoenix.Plutilties.getInstance
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask

class TpaManager {
  val requests = ArrayList<TpRequest>()
  val timers = HashMap<TpRequest, BukkitTask>()

  fun getRunningRequests(sender: Player): List<TpRequest> {
    return requests.filter { req -> (req.sender == sender || req.receiver == sender) && timers.containsKey(req) }
  }

  fun sendTPA(sender: Player, receiver: Player) {
    val request = TpRequest(sender, receiver, false)
    if (!requests.any { req -> req.receiver == receiver && req.sender == sender }) {
      sender.sendMessage(
        Component.text("You've sent a TPA to ").color(NamedTextColor.GOLD)
          .append(Component.text(receiver.name).color(NamedTextColor.YELLOW))
      )
      receiver.sendMessage(
        Component.text(sender.name).color(NamedTextColor.YELLOW)
          .append(Component.text(" wants to teleport to you!").color(NamedTextColor.GOLD))
          .append(Component.text("\nYou can accept using ").color(NamedTextColor.GOLD)
              .append(Component.text("/tpaccept ${sender.name}").color(NamedTextColor.YELLOW).clickEvent(ClickEvent.runCommand("/tpaccept ${sender.name}"))))
      )
      requests.add(request)
    } else {
      sender.sendMessage(Component.text("You've already sent a tp request to ").color(NamedTextColor.RED)
        .append(Component.text(receiver.name).color(NamedTextColor.YELLOW))
        .append(Component.text("\nYou can cancel it using ").color(NamedTextColor.RED)
          .append(Component.text("/tpcancel ${receiver.name}").color(NamedTextColor.YELLOW).clickEvent(ClickEvent.runCommand("/tpcancel ${receiver.name}")))))
    }
  }

  fun sendTPAHere(sender: Player, receiver: Player) {
    val request = TpRequest(sender, receiver, true)
    if (!requests.any { req -> req.receiver == receiver && req.sender == sender }) {
      sender.sendMessage(
        Component.text("You've sent a TPAHere request to ").color(NamedTextColor.GOLD)
          .append(Component.text(receiver.name).color(NamedTextColor.YELLOW))
      )
      receiver.sendMessage(
        Component.text(sender.name).color(NamedTextColor.YELLOW)
          .append(Component.text(" wants you to teleport to them!").color(NamedTextColor.GOLD))
          .append(Component.text("\nYou can accept using ").color(NamedTextColor.GOLD)
              .append(Component.text("/tpaccept ${sender.name}").color(NamedTextColor.YELLOW).clickEvent(ClickEvent.runCommand("/tpaccept ${sender.name}"))))
      )
      requests.add(request)
    } else {
      sender.sendMessage(Component.text("You've already sent a tp request to ").color(NamedTextColor.RED)
        .append(Component.text(receiver.name).color(NamedTextColor.YELLOW))
        .append(Component.text("\nYou can cancel it using ").color(NamedTextColor.RED)
          .append(Component.text("/tpcancel ${receiver.name}").color(NamedTextColor.YELLOW).clickEvent(ClickEvent.runCommand("/tpcancel ${receiver.name}"))))
      )
    }
  }

  fun cancelTPA(request: TpRequest) {
    requests.remove(request)
    timers[request]!!.cancel()
    timers.remove(request)
    if (request.sender.isOnline) {
      request.sender.sendMessage(Component.text("You've cancelled your TP request to ").color(NamedTextColor.GOLD)
        .append(Component.text(request.receiver.name).color(NamedTextColor.YELLOW)))
    }
    if (request.receiver.isOnline) {
      request.receiver.sendMessage(Component.text(request.sender.name).color(NamedTextColor.YELLOW)
        .append(Component.text(" has cancelled their TP request to you").color(NamedTextColor.GOLD)))
    }
  }

  fun acceptTPA(request: TpRequest) {
    if (!request.toSender) {
      if (request.receiver.isOnline) {
        request.receiver.sendMessage(Component.text("Accepted TPA, please stand still for 5 seconds.").color(NamedTextColor.GOLD))
        request.sender.sendMessage(Component.text(request.receiver.name).color(NamedTextColor.YELLOW).append(Component.text(" accepted your TPA, please stand still for 5 seconds").color(NamedTextColor.GOLD)))
        val plugin = getInstance()
        timers[request] = TpaWaitRunnable(request).runTaskLater(plugin, 100)
      } else {
        request.sender.sendMessage(
          Component.text(request.receiver.name).color(NamedTextColor.YELLOW)
            .append(Component.text(" is no longer online!").color(NamedTextColor.RED))
        )
      }
    } else {
      request.receiver.sendMessage(Component.text("Accepted TPA, please stand still for 5 seconds.").color(NamedTextColor.GOLD))
      request.sender.sendMessage(Component.text(request.receiver.name).color(NamedTextColor.YELLOW).append(Component.text(" accepted your TPA, please stand still for 5 seconds").color(NamedTextColor.GOLD)))
      val plugin = getInstance()
      timers[request] = TpaWaitRunnable(request).runTaskLater(plugin, 100)
    }
  }

  fun runTPA(request: TpRequest) {
    timers.remove(request)
    requests.remove(request)
    if (request.sender.isOnline) {
      if (request.toSender) {
        request.receiver.teleport(request.sender)
        request.receiver.sendMessage(Component.text("You've been teleported to ").color(NamedTextColor.GOLD)
          .append(Component.text(request.sender.name).color(NamedTextColor.YELLOW)))
        request.sender.sendMessage(Component.text(request.receiver.name).color(NamedTextColor.YELLOW)
          .append(Component.text(" has been teleported to you!").color(NamedTextColor.GOLD)))
      } else {
        request.sender.teleport(request.receiver)
        request.sender.sendMessage(Component.text("You've been teleported to ").color(NamedTextColor.GOLD)
          .append(Component.text(request.receiver.name).color(NamedTextColor.YELLOW)))
        request.receiver.sendMessage(Component.text(request.sender.name).color(NamedTextColor.YELLOW)
          .append(Component.text(" has been teleported to you!").color(NamedTextColor.GOLD)))
      }
    } else {
      request.receiver.sendMessage(Component.text(request.sender.name).color(NamedTextColor.YELLOW)
        .append(Component.text(" is no longer online!").color(NamedTextColor.RED)))
    }
  }

  fun abortTPA(request: TpRequest) {
    requests.remove(request)
    timers[request]!!.cancel()
    timers.remove(request)
    if (request.sender.isOnline) {
      request.sender.sendMessage(Component.text("One of you has moved. The TP Request was cancelled.").color(NamedTextColor.RED))
    }
    if (request.receiver.isOnline) {
      request.receiver.sendMessage(Component.text("One of you has moved. The TP Request was cancelled.").color(NamedTextColor.RED))
    }
  }
}