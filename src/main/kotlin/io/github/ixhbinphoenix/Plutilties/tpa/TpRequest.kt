package io.github.ixhbinphoenix.Plutilties.tpa

import org.bukkit.entity.Player

data class TpRequest (val sender: Player, val receiver: Player, val toSender: Boolean)