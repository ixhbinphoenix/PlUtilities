package io.github.ixhbinphoenix.Plutilties.commands

import io.github.ixhbinphoenix.Plutilties.getInstance
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SpawnCommand : BaseCommand {
    private val plugin = getInstance()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            plugin.spawnManager.queueRequest(sender)
            sender.sendMessage(Component.text("Please stand still for 5 seconds").color(NamedTextColor.GOLD))
        } else {
            sender.sendMessage(Component.text("This command can only be executed by players!").color(NamedTextColor.RED))
        }
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return null
    }
}