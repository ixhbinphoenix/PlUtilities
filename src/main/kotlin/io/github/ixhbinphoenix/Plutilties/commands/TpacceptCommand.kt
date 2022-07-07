package io.github.ixhbinphoenix.Plutilties.commands

import io.github.ixhbinphoenix.Plutilties.getInstance
import io.github.ixhbinphoenix.Plutilties.tpa.TpRequest
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TpacceptCommand : BaseCommand {
  private val plugin = getInstance()

  override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
    if (sender is Player) {
      if (args.isNotEmpty()) {
        val player = plugin.server.getPlayer(args[0])
        if (player is Player) {
          val request = plugin.tpaManager.requests.firstOrNull { tpRequest -> tpRequest.sender.uniqueId == player.uniqueId && tpRequest.receiver.uniqueId == sender.uniqueId }
          if (request is TpRequest) {
            plugin.tpaManager.acceptTPA(request)
          } else {
            sender.sendMessage(Component.text("There is no active request from ").color(NamedTextColor.RED)
              .append(Component.text(args[0]).color(NamedTextColor.YELLOW)))
          }
        } else {
          sender.sendMessage(Component.text(args[0]).color(NamedTextColor.YELLOW)
            .append(Component.text(" is not online!").color(NamedTextColor.RED)))
        }
      } else {
        sender.sendMessage(Component.text("Not enough arguments! Usage: /tpaccept <player>").color(NamedTextColor.RED))
      }
    } else {
      sender.sendMessage(Component.text("This command can only be executed by players!").color(NamedTextColor.RED))
    }
    return true
  }

  override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
    if (sender is Player && args.isNotEmpty()) {
      return plugin.tpaManager.requests.map { request -> request.sender.name }.filter { name -> name.startsWith(args[0]) }.toMutableList()
    }
    return null
  }
}