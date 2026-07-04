package io.github.masyumero.ri_mekanized.common.adapter.mekanism_more_machine

import com.jerry.mekmm.common.recipe.impl.FluidReplicatorIRecipe
import com.jerry.mekmm.common.recipe.impl.StamperIRecipe
import com.p_nsk.replicated_integration.adapter.vanilla.BuiltinNodeResolver
import com.p_nsk.replicated_integration.api.graph.RecipeConversionMapper
import io.github.masyumero.ri_mekanized.common.core.MekanizedRecipeMapperGroup
import net.minecraft.world.item.crafting.Recipe

object MekanismMoreMachineRecipeMappers : MekanizedRecipeMapperGroup() {

    val all: List<RecipeConversionMapper<Recipe<*>>> =
        listOf(
            singleOutput<StamperIRecipe> { recipe ->
                build(
                    recipe.id,
                    listOf(
                        recipe.input.toInputNode()
                    ),
                    recipe.outputDefinition.singleNodeAmountOrNull(BuiltinNodeResolver::itemAmount)
                )
            },
            singleOutput<FluidReplicatorIRecipe> { recipe ->
                build(
                    recipe.id,
                    listOf(
                        recipe.fluidInput.toInputNode(),
                        recipe.chemicalInput.toInputNode()
                    ),
                    recipe.outputDefinition.singleNodeAmountOrNull(BuiltinNodeResolver::fluidAmount)
                )
            },
        )
}