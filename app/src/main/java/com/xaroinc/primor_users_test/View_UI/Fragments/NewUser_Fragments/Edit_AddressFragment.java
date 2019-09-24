package com.xaroinc.primor_users_test.View_UI.Fragments.NewUser_Fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.xaroinc.primor_users_test.R;
import com.xaroinc.primor_users_test.View_UI.Database.PersonEntity;
import com.xaroinc.primor_users_test.View_UI.Database.PersonViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class Edit_AddressFragment extends Fragment {

    EditText et_fullAddress,et_pincode,et_city;
    Spinner spinner;
    Button saveBtn;
    String fullAddress,pincode,city,state;
    PersonViewModel viewModel;

    ArrayList<PersonEntity> personEntityArrayList;

    String fName,lName,dateOfBirth,gender;

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

    private String fName_sp,lName_sp,dateOfBirth_sp,gender_sp,fullAddress_sp,pincode_sp,city_sp,state_sp;
    private long id_bunndle;

    ArrayAdapter<String> dataAdapter;

    public Edit_AddressFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public Edit_AddressFragment(ArrayList<PersonEntity> personEntityArrayList) {
        this.personEntityArrayList = personEntityArrayList;
    }

    @SuppressLint("ValidFragment")
    public Edit_AddressFragment(String fName, String lName, String dateOfBirth, String gender) {
        this.fName = fName;
        this.lName = lName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        personEntityArrayList = new ArrayList<>();

        sp = Objects.requireNonNull(getActivity()).getSharedPreferences(spFile, Context.MODE_PRIVATE);

        initViews(view);
        return view;
    }

    private void initViews(View view)
    {
        et_fullAddress = view.findViewById(R.id.et_fullAddress_address_fragment);
        et_pincode = view.findViewById(R.id.et_pincode_address_fragment);
        et_city = view.findViewById(R.id.et_city_address_fragment);
        spinner = view.findViewById(R.id.spinner_address_fragment);
        saveBtn = view.findViewById(R.id.save_btn_address_fragment);

        et_fullAddress.setFocusable(true);

        List<String> list = new ArrayList<String>();

        list.add("Andhra Pradesh");
        list.add("Arunachal Pradesh");
        list.add("Assam");
        list.add("Bihar");
        list.add("Chhattisgarh");
        list.add("Goa");
        list.add("Gujarat");
        list.add("Haryana");
        list.add("Himachal Pradesh");
        list.add("Jharkhand");
        list.add("Karnataka");
        list.add("Kerala");
        list.add("Madhya Pradesh");
        list.add("Maharashtra");
        list.add("Manipur");
        list.add("Meghalaya");
        list.add("Mizoram");
        list.add("Nagaland");
        list.add("Odisha");
        list.add("Punjab");
        list.add("Rajasthan");
        list.add("Sikkim");
        list.add("Tamil Nadu");
        list.add("Telangana");
        list.add("Tripura");
        list.add("Uttar Pradesh");
        list.add("Uttarakhand");
        list.add("West Bengal");
        list.add("Andaman and Nicobar Islands");
        list.add("Chandigarh");
        list.add("Dadar and Nagar Haveli");
        list.add("Daman and Diu");
        list.add("Lakshadweep");
        list.add("Puducherry");

        dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        loadBundleData();

        et_fullAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                et_fullAddress.requestFocus();
            }
        });


       /*
        String[] states = Objects.requireNonNull(getActivity()).getResources().getStringArray(R.array.array_states);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout,R.id.text_spinner, states);
        spinner.setAdapter(adapter);
        */

        if (sp !=null){
            saveBtn.setVisibility(View.VISIBLE);
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    fullAddress = et_fullAddress.getText().toString().trim();
                    pincode = et_pincode.getText().toString().trim();
                    city = et_city.getText().toString().trim();
                    state = spinner.getSelectedItem().toString();

                    String data = fullAddress + "\n" + pincode + "\n" + city + "\n" + state + "\n" ;

                    if (fullAddress.isEmpty()){
                        et_fullAddress.setError(getResources().getString(R.string.fullAddress_error_msg));
                    }else if (pincode.isEmpty()){
                        et_pincode.setError(getResources().getString(R.string.pincode_error_msg));
                    }else if (city.isEmpty()){
                        et_city.setError(getResources().getString(R.string.city_error_msg));
                    }else if (state.isEmpty()){
                        Toast.makeText(getActivity(), getResources().getString(R.string.state_error_msg), Toast.LENGTH_SHORT).show();
                    }else if (fullAddress.length() <= 3){
                        et_fullAddress.setError(getResources().getString(R.string.address_length_error_msg));
                    }else if (pincode.length() < 6){
                        et_pincode.setError(getResources().getString(R.string.pincode_error_msg));
                    }else if (city.length() <=3){
                        et_city.setError(getResources().getString(R.string.address_length_error_msg));
                    }else {
                        //viewModel.update(setValuesToEntity());
                        //  Toast.makeText(getActivity(), data + "Address Data Saved Successfully", Toast.LENGTH_SHORT).show();

                        // saveData_in_ArryList_PaseToNextFragment();

                      /*  new CommunicationFragment(fName,lName,dateOfBirth,gender,fullAddress,pincode,city,state);
                        Toast.makeText(getActivity(), "fragment called", Toast.LENGTH_SHORT).show();
                        */

                        saveDataIntoSp(fullAddress,pincode,city,state);
                    }

                }
            });
        }else {
            saveBtn.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(), "Please go and Fill Personal Details", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadBundleData()
    {
        Bundle bundle = this.getArguments();
        if (bundle != null && getArguments().containsKey(dbIdKey)) {

            id_bunndle = (long) (Objects.requireNonNull (bundle.get(dbIdKey)));
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

            Log.d("db", "Address fragment: bundle data: \n" + bundleData);

            // Toast.makeText(getActivity(), bundleData + " \n Bundle Data in Address Fragment", Toast.LENGTH_SHORT).show();


            if (!firstName_bundle.equals(null)){
                Toast.makeText(getActivity(), "first Name: " +firstName_bundle, Toast.LENGTH_SHORT).show();
            }


            if (address_bundle.length() != 0 && pincode_bundle.length() != 0 && city_bundle.length() !=0){
                et_fullAddress.setText(address_bundle);
                et_pincode.setText(pincode_bundle);
                et_city.setText(city_bundle);
                setSpinnerValue(state_bundle);
            }else {
                et_fullAddress.setText(null);
                et_pincode.setText(null);
                et_city.setText(null);
            }


        }else {
            et_fullAddress.setText(null);
            et_pincode.setText(null);
            et_city.setText(null);
        }

    }

    private void setSpinnerValue(String state_bundle)
    {
        if (state_bundle != null) {
            int spinnerPosition = dataAdapter.getPosition(state_bundle);
            spinner.setSelection(spinnerPosition);
        }
    }

    private void saveDataIntoSp(String fullAddress, String pincode, String city, String state)
    {
        editor = sp.edit();
        editor.putString(fullAddressKey,fullAddress);
        editor.putString(pincodeKey,pincode);
        editor.putString(cityKey,city);
        editor.putString(stateKey,state);
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
        }else {
            fName_sp = sp.getString(fNameKey, "Prasanth");
            lName_sp = sp.getString(lNameKey, "Kumar");
            dateOfBirth_sp = sp.getString(dateOfBirthKey, "20/03/1995");
            gender_sp = sp.getString(genderKey, "male");
            fullAddress_sp = sp.getString(fullAddressKey, "Madanapalli");
            pincode_sp = sp.getString(pincodeKey, "517350");
            city_sp = sp.getString(cityKey, "Tirupathi");
            state_sp = sp.getString(stateKey, "AP");
        }
        Log.d("db","First Name: " + fName_sp);
        Log.d("db","Last Name: " + lName_sp);
        Log.d("db", "Date of Birth: "+ dateOfBirth_sp);
        Log.d("db","Gender: " + gender_sp);
        Log.d("db","Full Address: " + fullAddress_sp);
        Log.d("db","Pincode: " + pincode_sp);
        Log.d("db","City: " + city_sp);
        Log.d("db","State: " + state_sp);
        String sp_data = fName_sp + "\n" + lName_sp + "\n" + dateOfBirth_sp + "\n" +
                gender_sp + "\n" +fullAddress_sp + "\n" + pincode_sp + "\n" + city_sp + "\n" + state_sp + "\n" ;
        Log.d("db","SP Data: Address: \n"+ sp_data);
        Toast.makeText(getActivity(), sp_data + "Data Saved Successfully\n Go to Communication", Toast.LENGTH_SHORT).show();


        clearDataFields();

    }

    private void clearDataFields()
    {
        et_fullAddress.setText(null);
        et_pincode.setText(null);
        et_city.setText(null);

    }


    private void saveData_in_ArryList_PaseToNextFragment()
    {
        PersonEntity personEntity = new PersonEntity();
        //personEntity.setFirstName(firstName);
        // personEntity.setLastName(lastName);
        // personEntity.setGender(gender);
        personEntity.setAddress(fullAddress);
        personEntity.setPincode(pincode);
        personEntity.setCity(city);
        personEntity.setState(state);
        personEntityArrayList.add(personEntity);

        new CommunicationFragment(personEntityArrayList);
    }


    private PersonEntity setValuesToEntity()
    {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setId(1);
        // personEntity.setFirstName(firstName);
        //personEntity.setLastName(lastName);
        //personEntity.setDateOfBirth(dateOfBirth);
        //personEntity.setGender(gender);
        personEntity.setAddress(fullAddress);
        personEntity.setPincode(pincode);
        personEntity.setCity(city);
        personEntity.setState(state);
        // personEntity.setMobileNo1(Integer.parseInt(mobileNo1));
        // personEntity.setMobileNo2(Integer.parseInt(mobileNo2));
        // personEntity.setEmailId(emailId);
        return personEntity;
    }

    protected void displayReceivedData(String message)
    {
        // txtData.setText("Data received: "+message);

        Toast.makeText(getActivity(), "Data Received: " + message, Toast.LENGTH_SHORT).show();
    }

}
