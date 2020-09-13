package hr.ferit.srdandragas.kunst

import android.app.Application
import android.content.Context

class KunstApp: Application() {

    companion object {
        lateinit var ApplicationContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        ApplicationContext = this
    }

}