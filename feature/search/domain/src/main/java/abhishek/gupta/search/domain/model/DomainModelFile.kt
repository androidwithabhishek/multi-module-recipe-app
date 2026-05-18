package abhishek.gupta.search.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DomainRecipe(
    @PrimaryKey(autoGenerate = false)
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