package io.github.ixhbinphoenix.Plutilties.utils

import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import org.bukkit.entity.Player

enum class Rank {
  OWNER,
  ADMIN,
  DEVELOPER,
  MODERATOR,
  SUPPORTER,
  DEFAULT
}

fun isPlayerInRank(player: Player, group: String): Boolean {
  return player.hasPermission("group.$group")
}

fun getPlayerRank(player: Player): Rank {
  for (rank in Rank.values()) {
    if (player.hasPermission("group.${rank.name.lowercase()}")) {
      return rank
    }
  }
  return Rank.DEFAULT
}

fun getRankColor(rank: Rank): TextColor {
  return when(rank) {
    Rank.OWNER -> NamedTextColor.YELLOW
    Rank.ADMIN -> NamedTextColor.RED
    Rank.DEVELOPER -> NamedTextColor.LIGHT_PURPLE
    Rank.MODERATOR -> NamedTextColor.DARK_BLUE
    Rank.SUPPORTER -> NamedTextColor.BLUE
    Rank.DEFAULT -> NamedTextColor.GRAY
  }
}