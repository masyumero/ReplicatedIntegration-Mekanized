package io.github.masyumero.ri_mekanized.common.adapter.mekanism_more_machine

import com.p_nsk.replicated_integration.api.addon.ReplicationAddonEnvironment
import com.p_nsk.replicated_integration.api.addon.ReplicationAddonLoadSafetyContract
import com.p_nsk.replicated_integration.api.graph.IConversionSink
import com.p_nsk.replicated_integration.core.ForgeReplicationAddon
import com.p_nsk.replicated_integration.core.ForgeReplicationAddonContext
import net.minecraft.world.item.crafting.Recipe

@OptIn(ReplicationAddonLoadSafetyContract::class)
object ReplicationMekanismMoreMachineAddon : ForgeReplicationAddon {
    override val id: String = "mekmm"

    override fun isEnabled(environment: ReplicationAddonEnvironment): Boolean =
        environment.isModLoaded(id)

    override fun collectConversions(context: ForgeReplicationAddonContext, collector: IConversionSink) {
        for (recipe in context.recipeManager.recipes) {
            val mapper = MekanismMoreMachineRecipeMappers.all.firstOrNull { it.supports(recipe) } ?: continue
            mapper.collect(recipe as Recipe<*>, collector)
        }
    }
}