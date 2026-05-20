package abhishek.gupta.search.data.local

import abhishek.gupta.search.domain.model.DomainRecipe
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertByDomainRecipe(domainRecipe: DomainRecipe)

//    @Delete
//    suspend fun deleteByRecipe(domainRecipe: DomainRecipe)

    @Query("DELETE FROM recipe WHERE idMeal = :id")
    suspend fun deleteById(id: String): Int

    @Query("SELECT *FROM recipe")
     fun getAllSavedRecipes(): Flow<List<DomainRecipe>>

}