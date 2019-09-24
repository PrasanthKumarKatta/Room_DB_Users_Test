package com.xaroinc.primor_users_test.View_UI.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class PersonRepository
{
    private PersonDao mPersonDao;
    private LiveData<List<PersonEntity>> mReadAllPersons;
    private LiveData<List<PersonEntity>> mReadSinglePerson;

    private List<PersonEntity> personEntityList;

    PersonRepository(Application application)
    {
        PersonDatabase db = PersonDatabase.getDatabase(application);
        mPersonDao = db.mPersonDao();
        mReadAllPersons = mPersonDao.readAllPersons();
        personEntityList = mPersonDao.readAllPersons_list();

    }

    LiveData<List<PersonEntity>> getmReadAllPersons(){
        return mReadAllPersons;
    }

    List<PersonEntity> getPersonEntityList(){
        return personEntityList;
    }

    void insert(PersonEntity personEntity)
    {
        new InsertAsyncTask(mPersonDao).execute(personEntity);
    }

    private static class InsertAsyncTask extends AsyncTask<PersonEntity, Void, Void>
    {
        private PersonDao mPersonDao;

        InsertAsyncTask(PersonDao mPersonDao)
        {
            this.mPersonDao = mPersonDao;
        }

        @Override
        protected Void doInBackground(PersonEntity... personEntities) {

            mPersonDao.savePerson(personEntities[0]);
            return null;
        }
    }

   /* void update(PersonEntity personEntity){
        new UpdateAsyncTask(mPersonDao).execute(personEntity);
    }*/
   void update( String firstName,String lastName,String dateOfBirth,String gender,String address,String pincode,String city,String state,String mobileNo1,String mobileNo2,String emailId, int id){
       new UpdateAsyncTask(mPersonDao, firstName, lastName, dateOfBirth, gender, address, pincode, city, state, mobileNo1, mobileNo2, emailId, id).execute();
   }

    private static class UpdateAsyncTask extends AsyncTask<PersonEntity, Void, Void> {
        private PersonDao mPersonDao;
        String firstName, lastName, dateOfBirth, gender, address, pincode, city, state, mobileNo1, mobileNo2, emailId;
        int id;

        public UpdateAsyncTask(PersonDao mPersonDao, String firstName, String lastName, String dateOfBirth, String gender, String address, String pincode, String city, String state, String mobileNo1, String mobileNo2, String emailId, int id)
        {
            this.mPersonDao = mPersonDao;
            this.firstName = firstName;
            this.lastName = lastName;
            this.dateOfBirth = dateOfBirth;
            this.gender = gender;
            this.address = address;
            this.pincode = pincode;
            this.city = city;
            this.state = state;
            this.mobileNo1 = mobileNo1;
            this.mobileNo2 = mobileNo2;
            this.emailId = emailId;
            this.id = id;
        }

        @Override
        protected Void doInBackground(PersonEntity... personEntities) {

            //mPersonDao.updatePersons(personEntities[0]);
            mPersonDao.update(firstName, lastName, dateOfBirth, gender, address, pincode, city, state, mobileNo1, mobileNo2, emailId,id);
            return null;
        }
    }

    void delete(PersonEntity personEntity){
        new DeleteAsyncTask(mPersonDao).execute(personEntity);
    }

    private static class DeleteAsyncTask extends AsyncTask<PersonEntity,Void, Void>{

        private PersonDao mPersonDao;

        DeleteAsyncTask(PersonDao mPersonDao)
        {
            this.mPersonDao = mPersonDao;
        }

        @Override
        protected Void doInBackground(PersonEntity... personEntities) {
            mPersonDao.removeFromTable(personEntities[0]);
            return null;
        }
    }

    public void deleteItemById(long idItem){
       new DeleteByIdAsyncTask(mPersonDao).execute(idItem);
    }

    private static class DeleteByIdAsyncTask extends AsyncTask<Long, Void, Void> {

       private PersonDao mAsyncTaskDao;

       DeleteByIdAsyncTask(PersonDao mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }


        @Override
        protected Void doInBackground(Long... longs) {
           mAsyncTaskDao.deleteByItemId(longs[0]);
            return null;
        }
    }

    public void updateByItemId(PersonEntity personEntity){
       new UpdateByItemIdAsyncTask(mPersonDao).execute(personEntity);
    }

    private class UpdateByItemIdAsyncTask extends AsyncTask<PersonEntity, Void, Void> {

       private PersonDao mUpdateAsyncTaskDao;

        public UpdateByItemIdAsyncTask(PersonDao mUpdateAsyncTaskDao) {
            this.mUpdateAsyncTaskDao = mUpdateAsyncTaskDao;
        }

        @Override
        protected Void doInBackground(PersonEntity... personEntities) {
            mUpdateAsyncTaskDao.updateByItemId(personEntities[0]);
            return null;
        }
    }
}
