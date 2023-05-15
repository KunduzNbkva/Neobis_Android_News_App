package kg.kunduznbkva.newsapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kg.kunduznbkva.newsapp.model.Article
import kg.kunduznbkva.newsapp.utils.Converters

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao

    companion object {
        private var INSTANCE: NewsDatabase? = null
        fun getInstance(context: Context): NewsDatabase? {
            if (INSTANCE == null) {
                synchronized(NewsDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context = context.applicationContext,
                        klass = NewsDatabase::class.java,
                        name = "news_app_db"
                    ).build()
                }

            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}