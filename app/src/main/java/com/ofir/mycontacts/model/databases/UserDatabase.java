package com.ofir.mycontacts.model.databases;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ofir.mycontacts.ApplicationContext;
import com.ofir.mycontacts.model.User;
import com.ofir.mycontacts.model.interfaces.IUserDao;

@Database(entities = {User.class}, version = 1)
@TypeConverters({ContactsConverter.class})
public abstract class UserDatabase extends RoomDatabase {

    public abstract IUserDao userDao();
    private static final String DATABASE_NAME = "user_db";
    private static UserDatabase m_Instance;

    public static UserDatabase getInstance()
    {
        if(m_Instance == null)
        {
            synchronized (UserDatabase.class)
            {
                if(m_Instance == null)
                {
                    m_Instance = Room.databaseBuilder(ApplicationContext.getContext(),
                            UserDatabase.class, DATABASE_NAME).addTypeConverter(new ContactsConverter()).build();
                }
            }
        }

        return m_Instance;
    }
}
