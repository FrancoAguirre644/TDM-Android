package com.example.tdm_android.functions

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.preference.PreferenceManager
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LifecycleCoroutineScope
import com.bumptech.glide.Glide
import com.example.tdm_android.R
import com.example.tdm_android.activities.FavouritesActivity
import com.example.tdm_android.activities.FilterActivity
import com.example.tdm_android.activities.LoginActivity
import com.example.tdm_android.activities.ProfileActivity
import com.example.tdm_android.activities.*
import com.example.tdm_android.client.RetroFitClient
import com.example.tdm_android.constants.Constants
import com.example.tdm_android.models.Answer
import com.example.tdm_android.services.YesNoService
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun Context.messageShort(text: String){ //A partir del context, es decir, se puede llamar desde cualquier lado
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}


fun Context.showImageRestApiYesNo(answer: String, image: String, ivImage: ImageView, showGif: Boolean) {
    val uriParse = Uri.parse(image)
    if (showGif){
        Glide.with(this).load(uriParse).into(ivImage)
    } else {
        Picasso.get().load(uriParse).into(ivImage)
    }

    messageShort("Show image for response rest api yes/no = $answer")
}

fun Context.redirectToSplashScreenActivity(answer: Answer, origin: String, showGif: Boolean) {
    val intent = Intent(this, SplashScreenActivity::class.java)
    intent.putExtra(Constants.IMAGE_STR, answer.image)
    intent.putExtra(Constants.ANSWER_STR, answer.answer)
    intent.putExtra(Constants.SHOW_GIF_STR, showGif.toString())
    startActivity(intent)
}

fun Context.restApiYesNoConsumptionLogin(lifecycleScope: LifecycleCoroutineScope, imagePrincipal: ImageView, showGif: Boolean) {
    lifecycleScope.launch(Dispatchers.IO) {

        Log.e("THREAD", Thread.currentThread().name+" (Log.e on line 50, Extensions)")

        val apiYesNo = RetroFitClient.retrofit.create(YesNoService::class.java)

        apiYesNo.getAnswer().enqueue(object : Callback<Answer>{

            override fun onResponse(call: Call<Answer>, response: Response<Answer>) {
                val answer = response.body() as Answer
                messageShort("Response rest api yes/no = ${answer.answer}")
                if (answer.answer.equals(Constants.YES_STR)){ //Si de la rest api recibe "yes"
                    redirectToSplashScreenActivity(answer, Constants.LOGIN_STR, showGif) //Redirige al SplashScreenActivity enviando el origen (Login), es decir el punto de partida
                } else { //Si de la rest api recibo "no"
                    showImageRestApiYesNo(answer.answer.toString(), answer.image.toString(), imagePrincipal, showGif) //Si de la rest api recibe "no" y estoy en el login, muestro la imagen en la parte de arriba
                }
            }

            override fun onFailure(call: Call<Answer>, t: Throwable) {
                Log.e("Error: ", t.message ?: " ")
            }

        })

    }
}

fun Context.restApiYesNoConsumptionLogout(lifecycleScope: LifecycleCoroutineScope, showGif: Boolean) {
    lifecycleScope.launch(Dispatchers.IO) {

        Log.e("THREAD", Thread.currentThread().name+" (Log.e on line 50, Extensions)")

        val apiYesNo = RetroFitClient.retrofit.create(YesNoService::class.java)

        apiYesNo.getAnswer().enqueue(object : Callback<Answer>{

            override fun onResponse(call: Call<Answer>, response: Response<Answer>) {
                val answer = response.body() as Answer
                messageShort("Response rest api yes/no = ${answer.answer}")
                if (answer.answer.equals(Constants.YES_STR)){ //Si de la rest api recibe "yes"
                    val intent = Intent(this@restApiYesNoConsumptionLogout, LoginActivity::class.java) //Si de la rest api recibe "yes" y di clic en logout, directamente voy al LoginActivity
                    intent.putExtra(Constants.ORIGIN_STR, "restApiYesNoConsumption") //Le podría pasar cualquier cosa es solo para que no sea nulo
                    startActivity(intent)
                } else { //Si de la rest api recibo "no"
                    redirectToSplashScreenActivity(answer, Constants.LOGOUT_STR, showGif) //Redirige al SplashScreenActivity enviando el origen (Logout), es decir el punto de partida
                }
            }

            override fun onFailure(call: Call<Answer>, t: Throwable) {
                Log.e("Error: ", t.message ?: " ")
            }

        })

    }
}

fun Context.triggerByChoosingNavigationMenuItem(lifecycleScope: LifecycleCoroutineScope, navigationView: NavigationView, drawerLayout: DrawerLayout, origin: String="Parameter optional if the origin is not DetailActivity or IndexActivity") {

    navigationView.setNavigationItemSelectedListener { item: MenuItem ->
        when (item.itemId) {
            R.id.nav_home -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                val intent = Intent(this, FilterActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_favourites -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                val intent1 = Intent(this, FavouritesActivity::class.java)
                startActivity(intent1)
                if (origin == Constants.STR_ORIGIN_DETAIL || origin == Constants.STR_ORIGIN_INDEX){
                    drawerLayout.closeDrawer(GravityCompat.START)
                    val intent2 = Intent(this, ProfileActivity::class.java)
                    startActivity(intent2)
                }
            }
            R.id.nav_profile -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_logout -> {
                logoutUser()
                restApiYesNoConsumptionLogout(lifecycleScope, true)
            }
        }
        true
    }
}

fun Context.logoutUser() {
    val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
    val editor: SharedPreferences.Editor = pref.edit()
    editor.putString(Constants.STR_USERNAME, "")
    editor.putString(Constants.STR_PASSWORD, "")
    editor.putBoolean(Constants.STR_CHECK_REMEMBER_USER, false)
    editor.apply()
}

//Función en una línea que, como es a partir del context, se puede usar desde cualquier lado
fun Context.registrarPendiente(text: String) = Log.i("TODO", text)