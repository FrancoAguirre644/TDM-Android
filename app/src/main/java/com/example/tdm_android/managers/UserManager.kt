package com.example.tdm_android.managers

import android.content.Context
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import kotlin.Throws
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.example.tdm_android.helpers.DBHelper
import com.example.tdm_android.models.User
import java.lang.Exception
import java.sql.SQLException

class UserManager(context: Context?) {
    private var dao: Dao<User?, Int>? = null

    @get:Throws(Exception::class)
    val users: List<User?>
        get() = dao!!.queryForAll()

    @Throws(Exception::class)
    fun createUser(user: User?) {
        dao!!.create(user)
    }

    @Throws(Exception::class)
    fun getOneUserByField(field: String?, username: String?): User? {
        return dao!!.queryForEq(field, username)[0]
    }

    @Throws(Exception::class)
    fun updateUser(user: User?) {
        dao!!.update(user)
    }

    companion object {
        private var instance: UserManager? = null
        fun getInstance(context: Context): UserManager? {
            if (instance == null) {
                instance = UserManager(context)
            }
            return instance
        }
    }

    init {
        val helper: OrmLiteSqliteOpenHelper =
            OpenHelperManager.getHelper(context, DBHelper::class.java)
        try {
            dao = helper.getDao(
                User::class.java
            )
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}