package com.example.keshavanarasappa.androidproject.User

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by keshava.narasappa on 24/03/18.
 */
class RealmManager {

    private var realm: Realm? = null

    private var realmConfiguration: RealmConfiguration? = null

    fun initializeRealmConfig(applicationContext: Context) {
        if (realmConfiguration == null) {
            Realm.init(applicationContext)
            val realmConfig = RealmConfiguration.Builder().name(Realm.DEFAULT_REALM_NAME)
                    .schemaVersion(0)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            setRealmConfiguration(realmConfig)
        }
    }

    private fun setRealmConfiguration(realmConfiguration: RealmConfiguration) {
        RealmManager.instance.realmConfiguration = realmConfiguration
        Realm.setDefaultConfiguration(realmConfiguration)
    }

    fun getRealm(): Realm? {
        return Realm.getDefaultInstance()
    }


    companion object {
        val instance = RealmManager()
    }
}