package io.github.masyumero.ri_mekanized.common.core

import com.p_nsk.replicated_integration.api.addon.RegisterForgeReplicationAddonsEvent
import io.github.masyumero.ri_mekanized.common.adapter.evolved_mekanism.ReplicationEvolvedMekanismAddon
import io.github.masyumero.ri_mekanized.common.adapter.mekanism_more_machine.ReplicationMekanismMoreMachineAddon
import net.minecraftforge.eventbus.api.SubscribeEvent

object BuiltinReplicationAddons {
    @SubscribeEvent
    fun onRegisterReplicationAddons(event: RegisterForgeReplicationAddonsEvent) {
        event.register(ReplicationEvolvedMekanismAddon)
        event.register(ReplicationMekanismMoreMachineAddon)
    }
}