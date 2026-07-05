package io.github.masyumero.ri_mekanized.common.adapter.astral_mekanism

import astral_mekanism.recipes.irecipe.ReconstructionIRecipe
import astral_mekanism.recipes.recipe.AstralCraftingRecipe
import astral_mekanism.recipes.recipe.FluidFluidToFluidRecipe
import astral_mekanism.recipes.recipe.GasInfusionToFluidRecipe
import astral_mekanism.recipes.recipe.MekanicalTransformRecipe
import com.p_nsk.replicated_integration.adapter.vanilla.BuiltinNodeResolver
import com.p_nsk.replicated_integration.api.graph.RecipeConversionMapper
import com.p_nsk.replicated_integration.api.model.InputNodes
import io.github.masyumero.ri_mekanized.common.core.MekanizedRecipeMapperGroup
import net.minecraft.world.item.crafting.Recipe

object AstralMekanismRecipeMappers : MekanizedRecipeMapperGroup() {

    val all: List<RecipeConversionMapper<Recipe<*>>> =
        listOf(
            singleOutput<AstralCraftingRecipe> { recipe ->
                val consumeAlternatives = mutableListOf(
                    recipe.inputGas.toInputNode(),
                    recipe.inputFluid.toInputNode()
                )
                for (ingredient in recipe.inputItems) {
                    consumeAlternatives.add(ingredient.toInputNode())
                }
                build(
                    recipe.id,
                    consumeAlternatives,
                    recipe.outputDefinition.singleNodeAmountOrNull(BuiltinNodeResolver::itemAmount)
                )
            },
            singleOutput<FluidFluidToFluidRecipe> { recipe ->
                build(
                    recipe.id,
                    listOf(
                        recipe.inputA.toInputNode(),
                        recipe.inputB.toInputNode()
                    ),
                    recipe.outputDefinition.singleNodeAmountOrNull(BuiltinNodeResolver::fluidAmount)
                )
            },
            singleOutput<GasInfusionToFluidRecipe> { recipe ->
                build(
                    recipe.id,
                    listOf(
                        recipe.infusionInput.toInputNode(),
                        recipe.gasInput.toInputNode()
                    ),
                    recipe.outputDefinition.singleNodeAmountOrNull(BuiltinNodeResolver::fluidAmount)
                )
            },
            singleOutput<MekanicalTransformRecipe> { recipe ->
                val consumeAlternatives = mutableListOf<InputNodes>()
                if (!recipe.isItemACatalyst) consumeAlternatives.add(recipe.inputItemA.toInputNode())
                if (!recipe.isItemBCatalyst) consumeAlternatives.add(recipe.inputItemB.toInputNode())
                if (!recipe.isItemCCatalyst) consumeAlternatives.add(recipe.inputItemC.toInputNode())
                if (!recipe.isFluidACatalyst) consumeAlternatives.add(recipe.inputFluidA.toInputNode())
                if (!recipe.isFluidBCatalyst) consumeAlternatives.add(recipe.inputFluidB.toInputNode())
                build(
                    recipe.id,
                    consumeAlternatives,
                    recipe.outputDefinition.singleNodeAmountOrNull { itemFluidOutput ->
                        BuiltinNodeResolver.itemAmount(itemFluidOutput.item)
                    }
                )
            },
            singleOutput<ReconstructionIRecipe> { recipe ->
                build(
                    recipe.id,
                    listOf(
                        recipe.inputFluid.toInputNode(),
                        recipe.inputSolid.toInputNode(),
                        recipe.inputGas.toInputNode()
                    ),
                    recipe.outputDefinition.singleNodeAmountOrNull { output ->
                        BuiltinNodeResolver.itemAmount(output.item)
                    }
                )
            },
        )
}