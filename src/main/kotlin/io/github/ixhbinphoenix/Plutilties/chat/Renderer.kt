package io.github.ixhbinphoenix.Plutilties.chat

import io.papermc.paper.chat.ChatRenderer
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

class Renderer : ChatRenderer {
  override fun render(source: Player, sourceDisplayName: Component, message: Component, viewer: Audience): Component {
    return getPlayerMessage(source, message)
  }
}