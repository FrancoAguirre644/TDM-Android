package com.example.tdm_android.managers

import android.content.Context
import com.example.tdm_android.helpers.DBHelper
import com.example.tdm_android.models.FavouriteCharacter
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import java.lang.Exception
import java.sql.SQLException

class FavouriteCharacterManager(context: Context?) {
    private var dao: Dao<FavouriteCharacter?, Int>? = null

    @get:Throws(Exception::class)
    val favouriteCharacters: List<FavouriteCharacter?>
        get() = dao!!.queryForAll()

    @Throws(Exception::class)
    fun createFavouriteCharacter(favouriteCharacter: FavouriteCharacter?) {
        dao!!.create(favouriteCharacter)
    }

    @Throws(Exception::class)
    fun getById(id: Int): FavouriteCharacter? {
        return dao!!.queryForId(id)
    }

    @Throws(Exception::class)
    fun deleteFavouriteCharacter(id: Int): Int {
        return dao!!.deleteById(id)
    }

    companion object {
        private var instance: FavouriteCharacterManager? = null
        fun getInstance(context: Context): FavouriteCharacterManager? {
            if (instance == null) {
                instance = FavouriteCharacterManager(context)
            }
            return instance
        }
    }

    init {
        val helper: OrmLiteSqliteOpenHelper =
            OpenHelperManager.getHelper(context, DBHelper::class.java)
        try {
            dao = helper.getDao(
                FavouriteCharacter::class.java
            )
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}