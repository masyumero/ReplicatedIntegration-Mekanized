package io.github.masyumero.ri_mekanized

import com.mojang.logging.LogUtils
import net.minecraftforge.fml.common.Mod
import org.slf4j.Logger

@Mod(ReplicatedIntegrationMekanized.MODID)
object ReplicatedIntegrationMekanized {
    const val MODID: String = "replicated_integration_mekanized"
    val LOGGER: Logger = LogUtils.getLogger()
}