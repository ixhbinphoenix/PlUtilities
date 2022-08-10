package io.github.ixhbinphoenix.Plutilties.chat

import io.github.ixhbinphoenix.Plutilties.utils.Rank
import io.github.ixhbinphoenix.Plutilties.utils.getPlayerRank
import io.github.ixhbinphoenix.Plutilties.utils.getRankColor
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import org.bukkit.entity.Player

fun getPlayerMessage(player: Player, message: Component): Component {
  val rank = getPlayerRank(player)
  // TODO: Nations badges
  val badges = ArrayList<Component>()
  badges.add(
    getRankBadge(rank)
  )
  var msg = Component.empty()
  for (badge in badges) {
    msg = msg.append(badge)
  }

  player.displayName(getDisplayName(player))

  return msg
    .append(Component.text(player.name).color(getRankColor(rank)))
    .append(Component.text(" >> ").color(NamedTextColor.GRAY))
    .append(message)
}

fun getRankBadge(rank: Rank): Component {
  val rankColor = getRankColor(rank)
  return when(rank) {
    Rank.OWNER -> createBadge(NamedTextColor.YELLOW, NamedTextColor.GOLD, "✧").hoverEvent(HoverEvent.showText(Component.text("Server Owner").color(rankColor)))
    Rank.ADMIN -> createBadge(NamedTextColor.DARK_RED, NamedTextColor.RED, "✧").hoverEvent(HoverEvent.showText(Component.text("Server Administrator").color(rankColor)))
    Rank.DEVELOPER -> createBadge(NamedTextColor.LIGHT_PURPLE, NamedTextColor.DARK_PURPLE, "⚒").hoverEvent(HoverEvent.showText(Component.text("Developer").color(rankColor)))
    Rank.MODERATOR -> createBadge(NamedTextColor.AQUA, NamedTextColor.BLUE, "\uD83D\uDEE1").hoverEvent(HoverEvent.showText(Component.text("Moderator").color(rankColor)))
    Rank.SUPPORTER -> createBadge(NamedTextColor.BLUE, NamedTextColor.DARK_BLUE, "\uD83D\uDEE1").hoverEvent(HoverEvent.showText(Component.text("Supporter").color(rankColor)))
    Rank.DEFAULT -> createBadge(NamedTextColor.GRAY, NamedTextColor.DARK_GRAY, "❖")
  }
}

fun createBadge(primary: TextColor, secondary: TextColor, text: String): Component {
  return Component.text("[").color(secondary)
    .append(Component.text(text).color(primary))
    .append(Component.text("] ").color(secondary))
}

fun getDisplayName(player: Player): Component {
  val rank = getPlayerRank(player)
  return getRankBadge(rank)
    .append(Component.text(player.name).color(getRankColor(rank)))
}