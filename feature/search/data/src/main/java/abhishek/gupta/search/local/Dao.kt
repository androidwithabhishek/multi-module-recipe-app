package abhishek.gupta.search.local

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
    suspend fun insertRecipe(domainRecipe: DomainRecipe)

    @Delete
    suspend fun deleteRecipe(domainRecipe: DomainRecipe)

    @Query("SELECT *FROM recipe")
    suspend fun getAllSavedRecipes(): Flow<List<DomainRecipe>>

}