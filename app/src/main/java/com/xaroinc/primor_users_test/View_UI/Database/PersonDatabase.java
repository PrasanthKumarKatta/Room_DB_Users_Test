package com.xaroinc.primor_users_test.View_UI.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {PersonEntity.class}, version = 2, exportSchema = false)
public abstract class PersonDatabase extends RoomDatabase
{
    private static final String DATABAE = "person_database";

    public abstract PersonDao mPersonDao();

    private static volatile PersonDatabase INATANCE;

    static PersonDatabase getDatabase(final Context context){
        if (INATANCE == null){
            synchronized (PersonDatabase.class){
                if (INATANCE == null){
                    INATANCE = Room.databaseBuilder(context.getApplicationContext(), PersonDatabase.class, DATABAE)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INATANCE;
    }
}
