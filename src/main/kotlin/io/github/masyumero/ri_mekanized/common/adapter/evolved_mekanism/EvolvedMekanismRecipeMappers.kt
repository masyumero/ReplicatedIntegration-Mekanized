package io.github.masyumero.ri_mekanized.common.adapter.evolved_mekanism

import com.p_nsk.replicated_integration.adapter.vanilla.BuiltinNodeResolver
import com.p_nsk.replicated_integration.api.graph.RecipeConversionMapper
import fr.iglee42.evolvedmekanism.recipes.AlloyerRecipe
import fr.iglee42.evolvedmekanism.recipes.ChemixerRecipe
import fr.iglee42.evolvedmekanism.recipes.SolidificationRecipe
import io.github.masyumero.ri_mekanized.common.core.MekanizedRecipeMapperGroup
import net.minecraft.world.item.crafting.Recipe

object EvolvedMekanismRecipeMappers: MekanizedRecipeMapperGroup() {

    val all: List<RecipeConversionMapper<Recipe<*>>> =
        listOf(
            singleOutput<AlloyerRecipe> { recipe ->
                build(
                    recipe.id,
                    listOf(
                        recipe.mainInput.toInputNode(),
                        recipe.extraInput.toInputNode(),
                        recipe.tertiaryExtraInput.toInputNode()
                    ),
                    recipe.outputDefinition.singleNodeAmountOrNull(BuiltinNodeResolver::itemAmount)
                )
            },
            singleOutput<ChemixerRecipe> { recipe ->
                build(
                    recipe.id,
                    listOf(
                        recipe.inputMain.toInputNode(),
                        recipe.inputGas.toInputNode(),
                        recipe.inputExtra.toInputNode()
                    ),
                    recipe.outputDefinition.singleNodeAmountOrNull(BuiltinNodeResolver::itemAmount)
                )
            },
            singleOutput<SolidificationRecipe> { recipe ->
                build(
                    recipe.id,
                    listOf(
                        recipe.inputFluid.toInputNode(),
                        recipe.fluidInputExtra.toInputNode()
                    ),
                    recipe.outputDefinition.singleNodeAmountOrNull(BuiltinNodeResolver::itemAmount)
                )
            }
        )
}