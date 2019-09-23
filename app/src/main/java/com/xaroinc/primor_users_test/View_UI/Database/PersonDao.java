package com.xaroinc.primor_users_test.View_UI.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PersonDao
{
    @Insert
    void savePerson(PersonEntity personEntity);

    @Query("select * from PersonEntity ORDER BY id ASC")
    LiveData<List<PersonEntity>> readAllPersons();


    /**
     * Updating all information
     * By order id
     */

    @Query("UPDATE PersonEntity SET firstName = :firstName, lastName = :lastName,dateOfBirth = :dateOfBirth,gender = :gender,address = :address,pincode = :pincode,city = :city,state = :state,mobileNo1 = :mobileNo1, mobileNo2 = :mobileNo2, emailId = :emailId WHERE id =:id")
    void update(String firstName, String lastName, String dateOfBirth, String gender, String address, String pincode, String city,  String state, String mobileNo1, String mobileNo2, String emailId, int id);


    @Update
    public void updatePersons(PersonEntity users);

    @Query("SELECT * from PersonEntity where id = :id")
    LiveData<List<PersonEntity>> readSinglePerson(int id);

    @Query("SELECT * FROM PersonEntity")
    List<PersonEntity> readAllPersons_list();

    @Delete
    void removeFromTable(PersonEntity personEntity);

}
