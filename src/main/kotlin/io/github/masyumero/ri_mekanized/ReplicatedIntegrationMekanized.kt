package io.github.masyumero.ri_mekanized

import com.mojang.logging.LogUtils
import io.github.masyumero.ri_mekanized.common.core.BuiltinReplicationAddons
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import org.slf4j.Logger

@Mod(ReplicatedIntegrationMekanized.MODID)
object ReplicatedIntegrationMekanized {
    const val MODID: String = "replicated_integration_mekanized"
    val LOGGER: Logger = LogUtils.getLogger()

    init {
        MinecraftForge.EVENT_BUS.register(BuiltinReplicationAddons)
    }
}