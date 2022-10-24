package com.example.tdm_android.notificatios

import android.app.*
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.tdm_android.R

class ServiceNotifications : Service(){

    private var channelId : String = "Remember user"

    private fun stop() {
        stopNotification() //Termino las notificación, si hace falta
        stopSelf() //Paro el servicio
    }

    override fun onDestroy() {//además de lo que hace por defecto, debe parar la música y la notificación
        stopNotification()
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)//Esta notación es para poder verificar la versión de código que tiene android
    private fun createChanelNotifications(){
        val importance = NotificationManager.IMPORTANCE_LOW
        val canal = NotificationChannel(channelId, "Service remember user", importance)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(canal)
    }

    private fun createNotifications(): Notification{
        //Stop
        val intentStop = Intent(this, ServiceNotifications::class.java) //desde acá hasta acá mismo
        intentStop.putExtra("stop", true)
        val piStop = PendingIntent.getService(this, 2, intentStop, PendingIntent.FLAG_UPDATE_CURRENT) //Que esté pendiente y actualiza el estado actual del servicio

        val stopAction = NotificationCompat.Action.Builder(
            R.drawable.ic_close, "Close", piStop
        ).build() //.build() para que se construya

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Remember user, selected")
            .setContentText("We will remember your credentials")
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.got))
            .addAction(stopAction)
            .setOngoing(true) //Para que se siga visualizando
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_info)
            .setColor(resources.getColor(R.color.purple_500))
            .setColorized(true)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = createNotifications()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //Si la versión de SDK es mayor o igual a la versión de código que tiene android
            createChanelNotifications() //Creo el canal, si es necesario
            startForeground(1, notification) //Lanzo la notificación
        } else {
            NotificationManagerCompat.from(this).notify(1, notification) //Directamente lanzo la notificación
        }

        if (intent?.getBooleanExtra("stop", false) == true) { //Si el intent que recibimos por parámetros tiene un boolean que se llama stop en true
            stop() //Paramos el servicio (el "stop" del getBooleanExtra no lo cargamos nosotros, ya que lo carga el "stopService(intent)" que pusimos en el MainActivity)
        }

        return START_STICKY
    }

    //Solo es necesario parar la notificación cuando la ntificación es de las anteriores,
    // si no usamos CHANNEL (y no es necesario crearlo), quiere decir que estamos usando
    // el manager anterior y, por ende, en ese caso se debe terminar la notificación
    private fun stopNotification(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) { //Si no usamos CHANEL (la versión de SDK es menor a la versión de código que tiene android)
            NotificationManagerCompat.from(this).cancel(1)
        }
    }

}