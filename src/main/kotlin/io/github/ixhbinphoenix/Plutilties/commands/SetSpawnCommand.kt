package io.github.ixhbinphoenix.Plutilties.commands

import io.github.ixhbinphoenix.Plutilties.getInstance
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetSpawnCommand : BaseCommand {
    private val plugin = getInstance()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (sender.hasPermission("plutilities.spawn.set")) {
                plugin.spawnManager.setSpawn(sender.location)
                sender.location.world.spawnLocation = sender.location
                if (!sender.location.world.isAutoSave) {
                    sender.location.world.save()
                }
                sender.sendMessage(Component.text("Spawn successfully set!").color(NamedTextColor.GOLD))
            } else {
                sender.sendMessage(Component.text("Insufficient permissions!").color(NamedTextColor.RED))
            }
        } else {
            sender.sendMessage(Component.text("This command can only be executed by players!").color(NamedTextColor.RED))
        }
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return null
    }
}