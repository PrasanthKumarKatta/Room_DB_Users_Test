package com.xaroinc.primor_users_test.View_UI.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class PersonViewModel extends AndroidViewModel
{
    private PersonRepository mPersonRepository;
    private LiveData<List<PersonEntity>> live_list;
    private List<PersonEntity> personEntityList;

    public PersonViewModel(@NonNull Application application)
    {
        super(application);
        mPersonRepository = new PersonRepository(application);
        live_list = mPersonRepository.getmReadAllPersons();
        personEntityList = mPersonRepository.getPersonEntityList();
    }

    public LiveData<List<PersonEntity>> getLive_list() {
        return live_list;
    }

    public List<PersonEntity> getPersonEntityList(){
        return personEntityList;
    }

    public void insert(PersonEntity  personEntity)
    {
        mPersonRepository.insert(personEntity);
    }

    /*public void update(PersonEntity  personEntity)
    {
        mPersonRepository.update(personEntity);
    }*/
    public void update(String firstName,String lastName,String dateOfBirth,String gender,String address,String pincode,String city,String state,String mobileNo1,String mobileNo2,String emailId, int id){
        mPersonRepository.update(firstName, lastName, dateOfBirth, gender, address, pincode, city, state, mobileNo1, mobileNo2, emailId, id);
    }

    public void delete(PersonEntity personEntity){
        mPersonRepository.delete(personEntity);
    }
}
