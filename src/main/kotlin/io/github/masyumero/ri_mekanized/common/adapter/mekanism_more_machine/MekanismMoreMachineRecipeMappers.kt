package io.github.masyumero.ri_mekanized.common.adapter.mekanism_more_machine

import com.jerry.mekmm.common.recipe.impl.FluidReplicatorIRecipe
import com.jerry.mekmm.common.recipe.impl.StamperIRecipe
import com.p_nsk.replicated_integration.adapter.mekanism.MekanismNodeResolver
import com.p_nsk.replicated_integration.adapter.vanilla.BuiltinNodeResolver
import com.p_nsk.replicated_integration.api.graph.RecipeConversionMapper
import com.p_nsk.replicated_integration.api.model.InputNodes
import com.p_nsk.replicated_integration.api.model.NodeAmount
import com.p_nsk.replicated_integration.api.node.NodeKey
import com.p_nsk.replicated_integration.core.RecipeMapperGroup
import mekanism.api.chemical.ChemicalStack
import mekanism.api.recipes.ingredients.ChemicalStackIngredient
import mekanism.api.recipes.ingredients.FluidStackIngredient
import mekanism.api.recipes.ingredients.InputIngredient
import mekanism.api.recipes.ingredients.ItemStackIngredient
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Recipe
import net.minecraftforge.fluids.FluidStack

object MekanismMoreMachineRecipeMappers : RecipeMapperGroup() {

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