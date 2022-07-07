package io.github.ixhbinphoenix.Plutilties

import io.github.ixhbinphoenix.Plutilties.commands.*
import io.github.ixhbinphoenix.Plutilties.tpa.TpaManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class Main : JavaPlugin() {
  val tpaManager = TpaManager()
  init {
    instance = this
  }

  override fun onEnable() {
    val commands: HashMap<String, BaseCommand> = hashMapOf(
      "tpa" to TpaCommand(),
      "tpahere" to TpahereCommand(),
      "tpaccept" to TpacceptCommand(),
      "tpcancel" to TpcancelCommand()
    )

    for (cmd in commands) {
        getCommand(cmd.key)?.setExecutor(cmd.value)
        getCommand(cmd.key)?.tabCompleter = cmd.value
    }

    server.consoleSender.sendMessage(Component.text("Plutilities enabled!").color(NamedTextColor.GREEN))
  }

  companion object {
    lateinit var instance: Main
  }
}

fun getInstance(): Main {
  return Main.instance
}