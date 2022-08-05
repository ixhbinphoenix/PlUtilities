package io.github.ixhbinphoenix.Plutilties

import io.github.ixhbinphoenix.Plutilties.commands.*
import io.github.ixhbinphoenix.Plutilties.events.Events
import io.github.ixhbinphoenix.Plutilties.spawn.SpawnManager
import io.github.ixhbinphoenix.Plutilties.tpa.TpaManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class Main : JavaPlugin() {
  val tpaManager = TpaManager()
  val spawnManager = SpawnManager()
  init {
    instance = this
  }

  override fun onEnable() {
    saveDefaultConfig()
    val spawnLoc = config.getLocation("spawn")
    if (spawnLoc is Location) {
      spawnManager.setSpawn(spawnLoc)
    }
    val commands: HashMap<String, BaseCommand> = hashMapOf(
      "tpa" to TpaCommand(),
      "tpahere" to TpahereCommand(),
      "tpaccept" to TpacceptCommand(),
      "tpcancel" to TpcancelCommand(),
      "spawn" to SpawnCommand(),
      "setspawn" to SetSpawnCommand()
    )

    for (cmd in commands) {
        getCommand(cmd.key)?.setExecutor(cmd.value)
        getCommand(cmd.key)?.tabCompleter = cmd.value
    }
    server.pluginManager.registerEvents(Events(), this)

    server.consoleSender.sendMessage(Component.text("Plutilities enabled!").color(NamedTextColor.GREEN))
  }

  companion object {
    lateinit var instance: Main
  }
}

fun getInstance(): Main {
  return Main.instance
}