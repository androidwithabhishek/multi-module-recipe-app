package abhishek.gupta.search.data.mapper


import abhishek.gupta.search.data.model.RecipeDTO
import abhishek.gupta.search.domain.model.DomainRecipe
import abhishek.gupta.search.domain.model.DomainRecipeDetails
import kotlin.Pair
import kotlin.String
import kotlin.collections.List
import kotlin.collections.map

fun List<RecipeDTO>.toDomainRecipe(): List<DomainRecipe> {


    val returns  = map {

        DomainRecipe(
            idMeal = it.idMeal,
            strArea = it.strArea,
            strMeal = it.strMeal,
            strMealThumb = it.strMealThumb,
            strCategory = it.strCategory,
            strTags = it.strTags ?: "",
            strYoutube = it.strYoutube ?: "",
            strInstructions = it.strInstructions
        )

    }
    return returns

}

fun RecipeDTO.toDomainRecipeDetails(): DomainRecipeDetails {

    return DomainRecipeDetails(
        idMeal = idMeal,
        strArea = strArea,
        strMeal = strMeal,
        strMealThumb = strMealThumb,
        strCategory = strCategory,
        strTags = strTags ?: "",
        strYoutube = strYoutube ?: "",
        strInstructions = strInstructions,
        ingredientsPair = getIngredientsPairWithMeasure()
    )

}

fun RecipeDTO.getIngredientsPairWithMeasure(): List<Pair<String, String>> {


    val list = mutableListOf<Pair<String, String>>()

    list.add(Pair(strIngredient1.getOrEmpty(), strMeasure1.getOrEmpty()))
    list.add(Pair(strIngredient2.getOrEmpty(), strMeasure2.getOrEmpty()))
    list.add(Pair(strIngredient3.getOrEmpty(), strMeasure3.getOrEmpty()))
    list.add(Pair(strIngredient4.getOrEmpty(), strMeasure4.getOrEmpty()))
    list.add(Pair(strIngredient5.getOrEmpty(), strMeasure5.getOrEmpty()))
    list.add(Pair(strIngredient6.getOrEmpty(), strMeasure6.getOrEmpty()))
    list.add(Pair(strIngredient7.getOrEmpty(), strMeasure7.getOrEmpty()))
    list.add(Pair(strIngredient8.getOrEmpty(), strMeasure8.getOrEmpty()))
    list.add(Pair(strIngredient9.getOrEmpty(), strMeasure9.getOrEmpty()))
    list.add(Pair(strIngredient10.getOrEmpty(), strMeasure10.getOrEmpty()))
    list.add(Pair(strIngredient11.getOrEmpty(), strMeasure11.getOrEmpty()))
    list.add(Pair(strIngredient12.getOrEmpty(), strMeasure12.getOrEmpty()))
    list.add(Pair(strIngredient13.getOrEmpty(), strMeasure13.getOrEmpty()))
    list.add(Pair(strIngredient14.getOrEmpty(), strMeasure14.getOrEmpty()))
    list.add(Pair(strIngredient15.getOrEmpty(), strMeasure15.getOrEmpty()))
    list.add(Pair(strIngredient16.getOrEmpty(), strMeasure16.getOrEmpty()))
    list.add(Pair(strIngredient17.getOrEmpty(), strMeasure17.getOrEmpty()))
    list.add(Pair(strIngredient18.getOrEmpty(), strMeasure18.getOrEmpty()))
    list.add(Pair(strIngredient19.getOrEmpty(), strMeasure19.getOrEmpty()))
    list.add(Pair(strIngredient20.getOrEmpty(), strMeasure20.getOrEmpty()))

    return list
}

fun String?.getOrEmpty() = this?.ifEmpty { "" } ?: ""