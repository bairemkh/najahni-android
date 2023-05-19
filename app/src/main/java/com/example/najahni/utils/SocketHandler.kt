package com.example.najahni.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.najahni.R
import com.example.najahni.models.CurrentUser
import com.example.najahni.models.Message
import com.example.najahni.models.User
import com.example.najahni.services.implementation.MessageService
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.net.URISyntaxException
import java.util.*

class SocketHandler() : Service() {
    companion object {
        lateinit var mSocket: Socket

        fun sendMessage(message: String,id:String){
            val messageJson = JSONObject().apply {
                put("msgContent", message)
                put("senderid", CurrentUser._id)
                put("receiverid", id)
                put("_id",  UUID.randomUUID().toString())

            }
            val notifJson = JSONObject().apply {
                put("msgContent", message)
                put("senderid", CurrentUser.firstname + " " + CurrentUser.lastname)
                put("receiverid", id)
                put("_id",  UUID.randomUUID().toString())
            }
            mSocket.emit("onMessage",messageJson)
            mSocket.emit("onMessageNotif",notifJson)

        }

        fun initSocket(user: User,action: (Message)->Unit){
            val gson = Gson()
                mSocket.on(user._id) { data ->
                    val dataJson = gson.fromJson(data[0].toString(), JsonObject::class.java).getAsJsonObject("msg")
                    action(MessageService.makeCourseFromJson(dataJson))
                    Log.e("data =======",data[0].toString())

                }

                mSocket.on("${CurrentUser._id}Msg") { data ->
                    val dataJson = gson.fromJson(data[0].toString(), JsonObject::class.java).getAsJsonObject("msg")
                    action(MessageService.makeCourseFromJson(dataJson))
                    Log.e("data2 =======",data.toString())
                }

        }
    }
    @Synchronized
    fun setSocket(){
        try {
            mSocket = IO.socket(Consts.BASE_URL)
        }catch (e:URISyntaxException){

        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
        return null
    }

  /*  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        setSocket()
        mSocket.connect()
        return START_STICKY
    }
*/
    override fun onCreate() {
        super.onCreate()
            setSocket()
            mSocket.connect()
      mSocket.on("${CurrentUser._id}") { data ->
          val gson = Gson()
          Log.e("notif","${data[0]}")
          //val dataJson = gson.fromJson(data[0].toString(), JsonObject::class.java).getAsJsonObject("msg")["msgContent"].asString
          //val sender = gson.fromJson(data[0].toString(), JsonObject::class.java).getAsJsonObject("msg")["senderid"].asString
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
              val channelId = "SocketServiceChannel"
              val channelName = "SocketServiceChannel"
              val importance = NotificationManager.IMPORTANCE_DEFAULT

              val channel = NotificationChannel(channelId, channelName, importance)

              val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
              notificationManager.createNotificationChannel(channel)
          }
          val notification = NotificationCompat.Builder(this, "SocketServiceChannel")
              .setContentTitle("New Message from ")
              .setContentText("text")
              .setSmallIcon(R.drawable.logo_najahni)
              .build()

          startForeground(1, notification)
      }
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket.disconnect()
    }

    /*  @Synchronized
      fun getSocket(): Socket {
          return mSocket
      }

      @Synchronized
      fun establishConnection() {
          mSocket.connect()
      }

      @Synchronized
      fun closeConnection() {
          mSocket.disconnect()
      }*/
}