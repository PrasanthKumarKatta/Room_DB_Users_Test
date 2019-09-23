package com.xaroinc.primor_users_test.View_UI.Fragments.NewUser_Fragments;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.xaroinc.primor_users_test.R;
import com.xaroinc.primor_users_test.View_UI.Database.PersonEntity;
import com.xaroinc.primor_users_test.View_UI.Database.PersonViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {

    EditText et_FirstName, et_LastName, et_DateOfBirth;
    String fullName, fName, lName, dateOfBirth, gender = "male";
    private Calendar myCalendar;
    boolean isClicked = true;
    RadioGroup rg;
    Button save_btn;
    RadioButton radio_b;

    PersonViewModel viewModel;
    private TextWatcher tw;
    ArrayList<PersonEntity> personEntityArrayList;

    //SendMessage SM;

    SharedPreferences sp;
    private static final String spFile = "spFile_contacts";
    SharedPreferences.Editor editor;
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

    private static final String dbIdKey = "dbIdKey";

    private String dateOfBirth1;

    List<PersonEntity> personEntityList;

    String firstName_bundle, lastName_bundle, dateOfBirth_bundle, gender_bundle, address_bundle, pincode_bundle, city_bundle, state_bundle, mobileNo1_bundle, mobileNo2_bundle, emailId_bundle;

    int id_bunndle;

    public PersonalFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public PersonalFragment(List<PersonEntity> personEntityList) {
        this.personEntityList = personEntityList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        personEntityArrayList = new ArrayList<>();

        sp = Objects.requireNonNull(getActivity()).getSharedPreferences(spFile, Context.MODE_PRIVATE);

       // loadingData();

        initViews(view);
        return view;
    }

    private void loadingData()
    {
        if (personEntityList.size() != 0){
            et_FirstName.setText(personEntityList.get(0).getFirstName());
            et_LastName.setText(personEntityList.get(0).getLastName());
            et_DateOfBirth.setText(personEntityList.get(0).getDateOfBirth());
            gender = personEntityList.get(0).getGender();
        } else {
            Toast.makeText(getActivity(), "No Data Came", Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews(View view) {
        et_FirstName = view.findViewById(R.id.et_firstName_personal_fragment);
        et_LastName = view.findViewById(R.id.et_lastName_personal_fragment);
        et_DateOfBirth = view.findViewById(R.id.et_dateOfBirth_personal_fragment);
        rg = view.findViewById(R.id.radioGroup);
        save_btn = view.findViewById(R.id.save_btn_personal_fragment);

        et_FirstName.setFocusable(true);

        //loadBundleData();

        et_FirstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                et_FirstName.requestFocus();
            }
        });


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.rb_male) {
                        gender = "male";
                    } else if (checkedId == R.id.rb_female) {
                        gender = "female";
                    }
            }
        });

        //initViewModel(); //initialize for viewmodel

        getDateOfBirth();

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIntoDatabase();
            }
        });

    }

    private void loadBundleData()
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

            Log.d("db", "personal fragment: bundle data: \n" + bundleData);

            Toast.makeText(getActivity(), bundleData + " \n Bundle Data in Personal Fragment", Toast.LENGTH_SHORT).show();


            /*if (!firstName_bundle.equals(null)){
                Toast.makeText(getActivity(), "first Name: " +firstName_bundle, Toast.LENGTH_SHORT).show();
            }
            */


            if (firstName_bundle.length() != 0 && lastName_bundle.length() != 0 && dateOfBirth_bundle.length() !=0){
                et_FirstName.setText(firstName_bundle);
                et_LastName.setText(lastName_bundle);
                et_DateOfBirth.setText(dateOfBirth_bundle);
            }/*else {
                et_FirstName.setText(null);
                et_LastName.setText(null);
                et_DateOfBirth.setText(null);
            }*/


        }else {
            et_FirstName.setText(null);
            et_LastName.setText(null);
            et_DateOfBirth.setText(null);
        }

    }

    private void saveIntoDatabase() {
        fName = et_FirstName.getText().toString().trim();
        lName = et_LastName.getText().toString().trim();
        fullName = fName.concat(" " + lName);

        dateOfBirth1 = et_DateOfBirth.getText().toString().trim();


        //getRadioBtnsValue(view);

        String data = fullName + "\n" + dateOfBirth1 + "\n" + gender + "\n";

        if (fName.isEmpty()) {
            et_FirstName.setError(getResources().getString(R.string.firstName_error_msg));
        } else if (lName.isEmpty()) {
            et_LastName.setError(getResources().getString(R.string.lastName_error_msg));
        } else if (dateOfBirth1.isEmpty()) {
            et_DateOfBirth.setError(getResources().getString(R.string.dateOfBirth_error_msg));
        } else if (gender.isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.gender_error_msg), Toast.LENGTH_SHORT).show();
        } else if (fName.length() <= 3) {
            et_FirstName.setError(getResources().getString(R.string.firstName_length_error_msg));
        } else if (lName.length() <= 3) {
            et_LastName.setError(getResources().getString(R.string.lastName_length_error_msg));
        } else {
            //viewModel.insert(setValuesToEntity()); //Todo: data saving
            Toast.makeText(getActivity(), data + "Data Saved Successfully\n", Toast.LENGTH_SHORT).show();
            //callNextFragment();
            // saveInListPaseToNextFragment();
            // new AddressFragment(fName,lName,dateOfBirth,gender);

            saveDataIntoSp(fName, lName, dateOfBirth1, gender);
            Log.d("PersonFrg", "saveIntoDatabase: fName:  " + fName + " lName: " + lName + " dateOfBirth1: " + dateOfBirth1 + " gender: " + gender);
        }
    }

    private void saveDataIntoSp(String fName, String lName, String dateOfBirth1, String gender) {
        editor = sp.edit();
        editor.putString(fNameKey, fName);
        editor.putString(lNameKey, lName);
        editor.putString(dateOfBirthKey, dateOfBirth1);
        editor.putString(genderKey, gender);
        editor.apply();

        clearDataFields();
    }

    private void clearDataFields()
    {
        et_FirstName.setText(null);
        et_LastName.setText(null);
        et_DateOfBirth.setText(null);
    }


    private void saveInListPaseToNextFragment() {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setFirstName(fName);
        personEntity.setLastName(lName);
        personEntity.setDateOfBirth(dateOfBirth);
        personEntity.setGender(gender);
        personEntityArrayList.add(personEntity);

        new AddressFragment(personEntityArrayList);
    }
/*
    private void callNextFragment()
    {
        List<PersonEntity> personEntityList = viewModel.getPersonEntityList();
        for (int k =0; k < personEntityList.size(); k++){
            if (personEntityList.get(k).getId() == ){
                Toast.makeText(getActivity(), "Id deleted", Toast.LENGTH_SHORT).show();
                // viewModel.update(setValuesToEntity());

                firstName = personEntityList.get(k).getFirstName();
                lastName = personEntityList.get(k).getLastName();
                dateOfBirth  = personEntityList.get(k).getDateOfBirth();
                gender = personEntityList.get(k).getGender();
                address = personEntityList.get(k).getAddress();
                pincode = personEntityList.get(k).getPincode();
                city = personEntityList.get(k).getCity();
                state = personEntityList.get(k).getState();
                mobileNo1 = String.valueOf(personEntityList.get(k).getMobileNo1());
                mobileNo2 = String.valueOf(personEntityList.get(k).getMobileNo2());
                emailId = personEntityList.get(k).getEmailId();

                viewModel.delete(setValuesToEntity(j));
            }else {
                Toast.makeText(context, "Id not exists", Toast.LENGTH_SHORT).show();
            }
        }


        new AddressFragment();
    }*/

    private PersonEntity setValuesToEntity() {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setFirstName(fName);
        personEntity.setLastName(lName);
        personEntity.setGender(gender);
        return personEntity;
    }

    private void getRadioBtnsValue(View view) {
        // get selected radio button from radioGroup
        int selectedId = rg.getCheckedRadioButtonId();

        // find the radio button by returned id
        RadioButton radioButton = (RadioButton) view.findViewById(selectedId);

        gender = radioButton.getText().toString();
        // Toast.makeText(getActivity(), gender, Toast.LENGTH_SHORT).show();

    }

    private void getDateOfBirth() {
        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

                // et_DateOfBirth.addTextChangedListener(tw);
                //ValidateFormateOfDate();

                isClicked = true;
            }
        };

        if (isClicked) {
            et_DateOfBirth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isClicked = false;
                    new DatePickerDialog(Objects.requireNonNull(getContext()), date,
                            myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

        }

    }

    private void ValidateFormateOfDate() {
        tw = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();
            //When user changes text of the EditText

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        if (mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    et_DateOfBirth.setText(current);
                    et_DateOfBirth.setSelection(sel < current.length() ? sel : current.length());
                }
            }
            // We also implement the other two functions because we have to

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateOfBirth = sdf.format(myCalendar.getTime());
        if (dateOfBirth.length() == 10) {
            et_DateOfBirth.setText(dateOfBirth);
            Toast.makeText(getActivity(), dateOfBirth, Toast.LENGTH_SHORT).show();
        }

    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(PersonViewModel.class);
    }

   /* interface SendMessage{
        void sendData(String message);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            SM = (SendMessage) getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException("Error in retrieving data. Please try again");
        }


    }
*/

}
