package abhishek.gupta.search.domain.model

data class DomainRecipe(
    val idMeal: String,
    val strArea: String,
    val strMeal: String,
    val strMealThumb: String,
    val strCategory: String,
    val strTags: String,
    val strYoutube: String,
    val strInstructions: String
)

data class DomainRecipeDetails(
    val idMeal: String,
    val strArea: String,
    val strMeal: String,
    val strMealThumb: String,
    val strCategory: String,
    val strTags: String?,
    val strYoutube: String?,
    val strInstructions: String,
    val ingredientsPair: List<Pair<String, String>>
)