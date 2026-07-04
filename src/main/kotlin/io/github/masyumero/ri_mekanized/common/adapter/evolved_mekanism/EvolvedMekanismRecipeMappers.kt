package io.github.masyumero.ri_mekanized.common.adapter.evolved_mekanism

import com.p_nsk.replicated_integration.adapter.mekanism.MekanismNodeResolver
import com.p_nsk.replicated_integration.adapter.vanilla.BuiltinNodeResolver
import com.p_nsk.replicated_integration.api.graph.RecipeConversionMapper
import com.p_nsk.replicated_integration.api.model.InputNodes
import com.p_nsk.replicated_integration.api.model.NodeAmount
import com.p_nsk.replicated_integration.api.node.NodeKey
import com.p_nsk.replicated_integration.core.RecipeMapperGroup
import fr.iglee42.evolvedmekanism.recipes.AlloyerRecipe
import fr.iglee42.evolvedmekanism.recipes.ChemixerRecipe
import fr.iglee42.evolvedmekanism.recipes.SolidificationRecipe
import mekanism.api.chemical.ChemicalStack
import mekanism.api.recipes.ingredients.ChemicalStackIngredient
import mekanism.api.recipes.ingredients.FluidStackIngredient
import mekanism.api.recipes.ingredients.InputIngredient
import mekanism.api.recipes.ingredients.ItemStackIngredient
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Recipe
import net.minecraftforge.fluids.FluidStack

object EvolvedMekanismRecipeMappers: RecipeMapperGroup() {

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

    private fun ItemStackIngredient.toInputNode(): InputNodes =
        ingredientToInputNode(
            ingredient = this as InputIngredient<ItemStack>,
            nodeOf = BuiltinNodeResolver::itemNode,
        )

    private fun FluidStackIngredient.toInputNode(): InputNodes =
        ingredientToInputNode(
            ingredient = this as InputIngredient<FluidStack>,
            nodeOf = BuiltinNodeResolver::fluidNode,
        )

    @Suppress("UNCHECKED_CAST")
    private fun ChemicalStackIngredient<*, *>.toInputNode(scale: Long = 1L): InputNodes =
        ingredientToInputNode(
            ingredient = this as InputIngredient<ChemicalStack<*>>,
            nodeOf = MekanismNodeResolver::chemicalNode,
            scale = scale,
        )

    private fun <T> ingredientToInputNode(
        ingredient: InputIngredient<T>,
        nodeOf: (T) -> NodeKey?,
        scale: Long = 1L,
    ): InputNodes =
        ingredient.representations.mapNotNull { representation ->
            val node = nodeOf(representation) ?: return@mapNotNull null
            val needed = ingredient.getNeededAmount(representation) * scale
            if (needed <= 0) {
                null
            } else {
                NodeAmount(node, needed)
            }
        }.let(::InputNodes)

    private fun <T> List<T>.singleNodeAmountOrNull(mapper: (T) -> NodeAmount?): NodeAmount? =
        singleOrNull()?.let(mapper)
}