package io.github.ixhbinphoenix.Plutilties.commands

import io.github.ixhbinphoenix.Plutilties.getInstance
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TpaCommand : BaseCommand {
  private val plugin = getInstance()

  override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
    if (sender is Player) {
      if (args.isNotEmpty()) {
        val player = plugin.server.getPlayer(args[0])
        if (player is Player) {
          plugin.tpaManager.sendTPA(sender, player)
        } else {
          sender.sendMessage(Component.text(args[0]).color(NamedTextColor.YELLOW).append(Component.text(" is not an online player!").color(NamedTextColor.RED)))
        }
      } else {
        sender.sendMessage(Component.text("Not enough arguments! Usage: /tpa <player>").color(NamedTextColor.RED))
      }
    } else {
      sender.sendMessage(Component.text("This command can only be executed by players!").color(NamedTextColor.RED))
    }
    return true
  }

  override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
    if (args.isNotEmpty()) {
      return plugin.server.onlinePlayers.map { player ->  player.name }.filter { name -> name.startsWith(args[0], true) }.toMutableList()
    }
    return null
  }
}