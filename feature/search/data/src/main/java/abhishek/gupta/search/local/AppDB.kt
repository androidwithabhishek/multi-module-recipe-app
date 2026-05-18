package abhishek.gupta.search.local

import abhishek.gupta.search.domain.model.DomainRecipe
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.jvm.java

@Database(entities = [DomainRecipe::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {
    companion object {
        fun getInstance(context: Context) =
            Room.databaseBuilder(context, AppDB::class.java, "fev_recipe_db")
                .fallbackToDestructiveMigration().build()
    }

}