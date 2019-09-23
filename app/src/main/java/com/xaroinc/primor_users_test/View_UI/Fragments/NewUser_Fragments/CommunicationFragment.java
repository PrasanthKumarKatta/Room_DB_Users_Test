package com.xaroinc.primor_users_test.View_UI.Fragments.NewUser_Fragments;


import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xaroinc.primor_users_test.R;
import com.xaroinc.primor_users_test.View_UI.Adapters.UsersListAapter;
import com.xaroinc.primor_users_test.View_UI.Database.PersonEntity;
import com.xaroinc.primor_users_test.View_UI.Database.PersonViewModel;
import com.xaroinc.primor_users_test.View_UI.Fragments.Nav_fragments.UsersListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunicationFragment extends Fragment {

    EditText et_mobileNo1, et_mobileNo2, et_emaiId;
    Button savebtn;

    PersonViewModel viewModel;
    String firstName, lastName, dateOfBirth, gender, address, pincode, city, state, mobileNo1, mobileNo2, emailId;

    ArrayList<PersonEntity> personEntityArrayList;

    SharedPreferences sp;
    private static final String spFile = "spFile_contacts" ;
    SharedPreferences.Editor editor;

    private static final String dbIdKey = "dbIdKey";
    private static final String fNameKey = "fNameKey";
    private static final String lNameKey = "lNameKey";
    private static final String dateOfBirthKey = "dateOfBirthKey";
    private static final String genderKey = "genderKey";
    private static final String fullAddressKey = "fullAddressKey";
    private static final String pincodeKey = "pincodeKey";
    private static final String cityKey = "cityKey";
    private static final String stateKey = "stateKey";
    private static final String mobileNo1Key = "mobileNo1Key";
    private static final String mobileNo2Key = "mobileNo2Key";
    private static final String emailIdKey = "emailIdKey";

    String firstName_bundle, lastName_bundle, dateOfBirth_bundle, gender_bundle, address_bundle, pincode_bundle, city_bundle, state_bundle, mobileNo1_bundle, mobileNo2_bundle, emailId_bundle;
    private int id_bunndle;

    private String fName_sp,lName_sp,dateOfBirth_sp,gender_sp,fullAddress_sp,pincode_sp,city_sp,state_sp,mobileNo1_sp,mobileNo2_sp,emailId_sp;



    public CommunicationFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CommunicationFragment(ArrayList<PersonEntity> personEntityArrayList) {
        this.personEntityArrayList = personEntityArrayList;
    }


    @SuppressLint("ValidFragment")
    public CommunicationFragment(String firstName, String lastName, String dateOfBirth, String gender, String address, String pincode, String city, String state)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.pincode = pincode;
        this.city = city;
        this.state = state;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_communication, container, false);
        personEntityArrayList = new ArrayList<>();

        sp = Objects.requireNonNull(getActivity()).getSharedPreferences(spFile, Context.MODE_PRIVATE);

        initViews(view);
        return view;
    }

    private void initViews(View view)
    {
        et_mobileNo1 = view.findViewById(R.id.et_mobieNo1_communication_fragment);
        et_mobileNo2 = view.findViewById(R.id.et_mobieNo2_communication_fragment);
        et_emaiId = view.findViewById(R.id.et_emailId_communication_fragment);
        savebtn = view.findViewById(R.id.save_btn_communication_fragment);

        et_mobileNo1.setFocusable(true);

        //loadBundleData();

        et_mobileNo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                et_mobileNo1.requestFocus();
            }
        });
        //initViewModel();

        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(PersonViewModel.class);

        if (sp !=null){

            savebtn.setVisibility(View.VISIBLE);

            savebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mobileNo1 = et_mobileNo1.getText().toString().trim();
                    mobileNo2 = et_mobileNo2.getText().toString().trim();
                    emailId = et_emaiId.getText().toString().trim();

                    Pattern pattern1 = Pattern.compile( "^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+");

                    Matcher matcher1 = pattern1.matcher(emailId);

                    if (mobileNo1.isEmpty()){
                        et_mobileNo1.setError(getResources().getString(R.string.mobileNo1_error_msg));
                    }else if (mobileNo2.isEmpty()){
                        et_mobileNo2.setError(getResources().getString(R.string.mobileNo2_error_msg));
                    }else if (emailId.isEmpty()){
                        et_emaiId.setError(getResources().getString(R.string.emailid_error_msg));
                    }else if (!matcher1.matches()) {
                        //show your message if not matches with email pattern
                        et_emaiId.setError(getResources().getString(R.string.emailPatern_error_msg));
                    }else if (mobileNo1.length() <10){
                        et_mobileNo1.setError(getResources().getString(R.string.mobileNo_length_error_msg));
                    }else if (mobileNo2.length() <10){
                        et_mobileNo2.setError(getResources().getString(R.string.mobileNo_length_error_msg));
                    }else {
                        String data = mobileNo1 + "\n" + mobileNo2 + "\n" + emailId ;
                      //  Toast.makeText(getActivity(), data + "\n" + "Communication Data Stored Successfully", Toast.LENGTH_SHORT).show();

                    /* //Todo: hide for list based checking
                   // viewModel.insert(setValuesToEntity());
                    List<PersonEntity> personEntityList = viewModel.getPersonEntityList();
                    for (int j =0; j < personEntityList.size(); j++){
                        if (personEntityList.get(j).getId() == 1){
                            Toast.makeText(getActivity(), "Id exists", Toast.LENGTH_SHORT).show();
                            viewModel.update(setValuesToEntity());
                        }else {
                            Toast.makeText(getActivity(), "Please go and first Fill Personal Details", Toast.LENGTH_SHORT).show();
                        }
                    }*/

                        // saveDatainRoomDatabase();  //Todo: arrayListSaveInRoomDb


                        saveDataIntoSp(mobileNo1,mobileNo2,emailId);
                    }
                }
            });
        }
    }

 /*   private void loadBundleData()
    {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id_bunndle = (int) (Objects.requireNonNull (bundle.get(dbIdKey)));
            firstName_bundle = (Objects.requireNonNull(bundle.get(fNameKey))).toString();
            lastName_bundle = (Objects.requireNonNull(bundle.get(lNameKey))).toString();
            dateOfBirth_bundle = (Objects.requireNonNull(bundle.get(dateOfBirthKey))).toString();
            gender_bundle = (Objects.requireNonNull(bundle.get(genderKey))).toString();
            address_bundle = (Objects.requireNonNull(bundle.get(fullAddressKey))).toString();
            pincode_bundle = (Objects.requireNonNull(bundle.get(pincodeKey))).toString();
            city_bundle = (Objects.requireNonNull(bundle.get(cityKey))).toString();
            state_bundle = (Objects.requireNonNull(bundle.get(stateKey))).toString();
            mobileNo1_bundle = (Objects.requireNonNull(bundle.get(mobileNo1Key))).toString();
            mobileNo2_bundle = (Objects.requireNonNull(bundle.get(mobileNo2Key))).toString();
            emailId_bundle = (Objects.requireNonNull(bundle.get(emailIdKey))).toString();

            String bundleData = id_bunndle + "\n" + firstName_bundle + "\n" + lastName_bundle + "\n" + dateOfBirth_bundle + "\n" +
                    gender_bundle + "\n" +address_bundle + "\n" + pincode_bundle + "\n" + city_bundle + "\n" + state_bundle + "\n"+
                    mobileNo1_bundle + "\n" + mobileNo2_bundle + "\n" + emailId_bundle + "\n";

            Log.d("db", "Communication fragment: bundle data: \n" + bundleData);

            Toast.makeText(getActivity(), bundleData + " \n Bundle Data in Communication Fragment", Toast.LENGTH_SHORT).show();


            *//*if (!firstName_bundle.equals(null)){
                Toast.makeText(getActivity(), "first Name: " +firstName_bundle, Toast.LENGTH_SHORT).show();
            }
            *//*

            if (mobileNo1_bundle.length() != 0 && mobileNo2_bundle.length() != 0 && emailId_bundle.length() !=0){
                et_mobileNo1.setText(mobileNo1_bundle);
                et_mobileNo2.setText(mobileNo2_bundle);
                et_emaiId.setText(emailId_bundle);
            }else {
                et_mobileNo1.setText(null);
                et_mobileNo2.setText(null);
                et_emaiId.setText(null);
            }


        }else {
            et_mobileNo1.setText(null);
            et_mobileNo2.setText(null);
            et_emaiId.setText(null);
        }

    }
*/
    private void saveDataIntoSp(String mobileNo1, String mobileNo2, String emailId)
    {
        editor = sp.edit();
        editor.putString(mobileNo1Key,mobileNo1);
        editor.putString(mobileNo2Key,mobileNo2);
        editor.putString(emailIdKey,emailId);
        editor.apply();

        if (sp != null){
            fName_sp = Objects.requireNonNull(sp).getString(fNameKey, "");
            lName_sp = Objects.requireNonNull(sp).getString(lNameKey, "");
            dateOfBirth_sp = Objects.requireNonNull(sp).getString(dateOfBirthKey, "");
            gender_sp = Objects.requireNonNull(sp).getString(genderKey, "");
            fullAddress_sp = Objects.requireNonNull(sp).getString(fullAddressKey, "");
            pincode_sp = Objects.requireNonNull(sp).getString(pincodeKey, "");
            city_sp = Objects.requireNonNull(sp).getString(cityKey, "");
            state_sp = Objects.requireNonNull(sp).getString(stateKey, "");
            mobileNo1_sp = Objects.requireNonNull(sp).getString(mobileNo1Key, "");
            mobileNo2_sp = Objects.requireNonNull(sp).getString(mobileNo2Key, "");
            emailId_sp = Objects.requireNonNull(sp).getString(emailIdKey, "");
        }else {
            fName_sp = sp.getString(fNameKey, "Prasanth");
            lName_sp = sp.getString(lNameKey, "Kumar");
            dateOfBirth_sp = sp.getString(dateOfBirthKey, "20/03/1995");
            gender_sp = sp.getString(genderKey, "male");
            fullAddress_sp = sp.getString(fullAddressKey, "Madanapalli");
            pincode_sp = sp.getString(pincodeKey, "517350");
            city_sp = sp.getString(cityKey, "Tirupathi");
            state_sp = sp.getString(stateKey, "AP");

            mobileNo1_sp = Objects.requireNonNull(sp).getString(mobileNo1Key, "");
            mobileNo2_sp = Objects.requireNonNull(sp).getString(mobileNo2Key, "");
            emailId_sp = Objects.requireNonNull(sp).getString(emailIdKey, "");
        }
        Log.d("db","First Name: " + fName_sp);
        Log.d("db","Last Name: " + lName_sp);
        Log.d("db", "Date of Birth: "+ dateOfBirth_sp);
        Log.d("db","Gender: " + gender_sp);
        Log.d("db","Full Address: " + fullAddress_sp);
        Log.d("db","Pincode: " + pincode_sp);
        Log.d("db","City: " + city_sp);
        Log.d("db","State: " + state_sp);
        Log.d("db","Mobile No1: " + mobileNo1_sp);
        Log.d("db","Mobile No2: " + mobileNo2_sp);
        Log.d("db","Email ID: " + emailId_sp);

        String sp_data = fName_sp + "\n" + lName_sp + "\n" + dateOfBirth_sp + "\n" +
                gender_sp + "\n" +fullAddress_sp + "\n" + pincode_sp + "\n" + city_sp + "\n" + state_sp + "\n"+
                mobileNo1_sp + "\n" + mobileNo2_sp + "\n" + emailId_sp + "\n";
        Toast.makeText(getActivity(), sp_data + "Communication Data Saved in SP", Toast.LENGTH_SHORT).show();


        //checkRowId_in_RoomDb();

        viewModel.insert(setValuesToEntity(fName_sp,lName_sp,dateOfBirth_sp,gender_sp,fullAddress_sp,
                pincode_sp,city_sp,state_sp,mobileNo1_sp,mobileNo2_sp,emailId_sp));


        Toast.makeText(getActivity(), "Full profile data saved in Room DB", Toast.LENGTH_SHORT).show();

        //Todo: Clearing data after saving in roomDb
        editor.clear().apply();

        clearDataFields();

        Fragment fragment = new UsersListFragment();
        if (fragment != null){
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

    }

    private void checkRowId_in_RoomDb()
    {
        viewModel = ViewModelProviders.of(getActivity()).get(PersonViewModel.class);

        viewModel.getLive_list().observe(getActivity(), new Observer<List<PersonEntity>>() {
            @Override
            public void onChanged(@Nullable List<PersonEntity> personEntities) {

                for (int k=0; k < personEntities.size() ; k++){
                    if (id_bunndle == personEntities.get(k).getId()){
                        Toast.makeText(getActivity(), "This Profile ID Exists", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(getActivity(), "New Profile", Toast.LENGTH_SHORT).show();
                    }
                    /*else {
                        viewModel.insert(setValuesToEntity(fName_sp,lName_sp,dateOfBirth_sp,gender_sp,fullAddress_sp,
                                pincode_sp,city_sp,state_sp,mobileNo1_sp,mobileNo2_sp,emailId_sp));
                    }*/
                }

            }
        });
    }

    private void clearDataFields()
    {
        et_mobileNo1.setText(null);
        et_mobileNo2.setText(null);
        et_emaiId.setText(null);
    }

    private void saveDatainRoomDatabase()
    {

        /*firstName = personEntityArrayList.get(0).getFirstName();
        lastName = personEntityArrayList.get(0).getLastName();
        dateOfBirth = personEntityArrayList.get(0).getDateOfBirth();
        gender = personEntityArrayList.get(0).getDateOfBirth();
        address = personEntityArrayList.get(0).getAddress();
        pincode = personEntityArrayList.get(0).getPincode();
        city = personEntityArrayList.get(0).getCity();
        state = personEntityArrayList.get(0).getState();*/

        if (firstName.length() != 0 && lastName.length() !=0 && gender.length() !=0 &&
                address.length() !=0 && pincode.length() !=0 && city.length() !=0 &&
                mobileNo1.length()!=0 && mobileNo2.length() !=0 && emailId.length() !=0)
        {
            PersonEntity personEntity = new PersonEntity();
            personEntity.setFirstName(firstName);
            personEntity.setLastName(lastName);
            personEntity.setGender(gender);
            personEntity.setAddress(address);
            personEntity.setPincode(pincode);
            personEntity.setCity(city);
            personEntity.setState(state);
            //personEntity.setMobileNo1(Integer.parseInt(mobileNo1));
          //  personEntity.setMobileNo2(Integer.parseInt(mobileNo2));
            personEntity.setEmailId(emailId);

            viewModel.insert(personEntity);
            Toast.makeText(getActivity(), "Ful profile data saved", Toast.LENGTH_SHORT).show();

        }

    }

    private PersonEntity setValuesToEntity(String fName_sp, String lName_sp, String dateOfBirth_sp,
                                           String gender_sp, String fullAddress_sp, String pincode_sp,
                                           String city_sp, String state_sp, String mobileNo1_k,
                                           String mobileNo2_k, String emailId_k)
    {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setFirstName(fName_sp);
        personEntity.setLastName(lName_sp);
        personEntity.setDateOfBirth(dateOfBirth_sp);
        personEntity.setGender(gender_sp);
        personEntity.setAddress(fullAddress_sp);
        personEntity.setPincode(pincode_sp);
        personEntity.setCity(city_sp);
        personEntity.setState(state_sp);
        personEntity.setMobileNo1(mobileNo1_k);
        personEntity.setMobileNo2(mobileNo2_k);
        personEntity.setEmailId(emailId_k);

        return personEntity;
    }

    private void initViewModel()
    {
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(PersonViewModel.class);
    }

}
