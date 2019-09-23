package com.xaroinc.primor_users_test.View_UI;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.xaroinc.primor_users_test.R;
import com.xaroinc.primor_users_test.View_UI.Database.PersonEntity;
import com.xaroinc.primor_users_test.View_UI.Database.PersonViewModel;
import com.xaroinc.primor_users_test.View_UI.Fragments.Nav_fragments.UsersListFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonEditActivity extends AppCompatActivity {

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

    String firstName, lastName, dateOfBirth1, gender1, address, pincode, city, state, mobileNo1, mobileNo2, emailId;
    int id;
    EditText et_FirstName, et_LastName, et_DateOfBirth, et_fullAddress,et_pincode,et_city, et_mobileNo1, et_mobileNo2, et_emaiId;
    Spinner spinner;

    String fullName, fName, lName, dateOfBirth, gender = "male";
    private Calendar myCalendar;
    boolean isClicked = true;
    RadioGroup rg;
    Button update_btn;
    RadioButton radio_b;

    PersonViewModel viewModel;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_edit);

        initViews(bundle);

    }


    private void initViews(Bundle bundle)
    {
        et_FirstName = findViewById(R.id.et_firstName_person_editActivty);
        et_LastName = findViewById(R.id.et_lastName_person_editActivty);
        et_DateOfBirth = findViewById(R.id.et_dateOfBirth_person_editActivty);
        rg = findViewById(R.id.radioGroup_person_editActivty);
        et_fullAddress = findViewById(R.id.et_fullAddress_person_editActivty);
        et_pincode = findViewById(R.id.et_pincode_person_editActivty);
        et_city = findViewById(R.id.et_city_person_editActivty);
        spinner = findViewById(R.id.spinner_person_editActivty);
        et_mobileNo1 = findViewById(R.id.et_mobieNo1_person_editActivty);
        et_mobileNo2 = findViewById(R.id.et_mobieNo2_person_editActivty);
        et_emaiId = findViewById(R.id.et_emailId_person_editActivty);
        update_btn = findViewById(R.id.update_btn_person_editActivty);
        

        //loadBundleData();
        loadBundleData(bundle);

        /*et_FirstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                et_FirstName.requestFocus();

            }
        });*/


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

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveIntoDatabase();
                saveIntoDatabase();

            }
        });

    }

    private void loadBundleData(Bundle bundle)
    {
        bundle = getIntent().getExtras();
        if (bundle != null) {
            id = (int) (Objects.requireNonNull(bundle.get(dbIdKey)));
            firstName = (Objects.requireNonNull(bundle.get(fNameKey))).toString();
            lastName = (Objects.requireNonNull(bundle.get(lNameKey))).toString();
            dateOfBirth1 = (Objects.requireNonNull(bundle.get(dateOfBirthKey))).toString();
            gender1 = (Objects.requireNonNull(bundle.get(genderKey))).toString();
            address = (Objects.requireNonNull(bundle.get(fullAddressKey))).toString();
            pincode = (Objects.requireNonNull(bundle.get(pincodeKey))).toString();
            city = (Objects.requireNonNull(bundle.get(cityKey))).toString();
            state = (Objects.requireNonNull(bundle.get(stateKey))).toString();
            mobileNo1 = (Objects.requireNonNull(bundle.get(mobileNo1Key))).toString();
            mobileNo2 = (Objects.requireNonNull(bundle.get(mobileNo2Key))).toString();
            emailId = (Objects.requireNonNull(bundle.get(emailIdKey))).toString();

            String bundleData = "Id: " + id + "\n" + firstName + "\n" + lastName + "\n" + dateOfBirth1 + "\n" +
                    gender1 + "\n" + address + "\n" + pincode + "\n" + city + "\n" + state + "\n" +
                    mobileNo1 + "\n" + mobileNo2 + "\n" + emailId + "\n";

            Toast.makeText(this, bundleData + " \n Bundle Data", Toast.LENGTH_SHORT).show();


            et_FirstName.setText(firstName);
            et_LastName.setText(lastName);
            et_DateOfBirth.setText(dateOfBirth1);

          /*
           RadioButton radio_Button;
            if (gender1.equals("male")){
               radio_Button = rg.findViewById(R.id.rb_male);
               radio_Button.setChecked(true);
            }else if(gender1.equals("female")) {
                radio_Button = rg.findViewById(R.id.rb_female);
                radio_Button.setChecked(true);
            }
            */

            et_fullAddress.setText(address);
            et_pincode.setText(pincode);
            et_city.setText(city);

            //setStateData();

            et_mobileNo1.setText(mobileNo1);
            et_mobileNo2.setText(mobileNo2);
            et_emaiId.setText(emailId);

        }
    }


    private void saveIntoDatabase() {
        fName = et_FirstName.getText().toString().trim();
        lName = et_LastName.getText().toString().trim();
        fullName = fName.concat(" " + lName);

        dateOfBirth1 = et_DateOfBirth.getText().toString().trim();

        address = et_fullAddress.getText().toString().trim();
        pincode = et_pincode.getText().toString().trim();
        city = et_city.getText().toString().trim();
        state = spinner.getSelectedItem().toString();

        mobileNo1 = et_mobileNo1.getText().toString().trim();
        mobileNo2 = et_mobileNo2.getText().toString().trim();
        emailId = et_emaiId.getText().toString().trim();

        Pattern pattern1 = Pattern.compile( "^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+");

        Matcher matcher1 = pattern1.matcher(emailId);


        // String data = address + "\n" + pincode + "\n" + city + "\n" + state + "\n" ;

        //getRadioBtnsValue(view);

        String data = fullName + "\n" + dateOfBirth1 + "\n" + gender + "\n";
        /*String sp_data = fName_sp + "\n" + lName_sp + "\n" + dateOfBirth_sp + "\n" +
                gender_sp + "\n" +fullAddress_sp + "\n" + pincode_sp + "\n" + city_sp + "\n" + state_sp + "\n"+
                mobileNo1_sp + "\n" + mobileNo2_sp + "\n" + emailId_sp + "\n";
        */


        if (fName.isEmpty()) {
            et_FirstName.setError(getResources().getString(R.string.firstName_error_msg));
        } else if (lName.isEmpty()) {
            et_LastName.setError(getResources().getString(R.string.lastName_error_msg));
        } else if (dateOfBirth1.isEmpty()) {
            et_DateOfBirth.setError(getResources().getString(R.string.dateOfBirth_error_msg));
        } else if (gender.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.gender_error_msg), Toast.LENGTH_SHORT).show();
        } else if (fName.length() <= 3) {
            et_FirstName.setError(getResources().getString(R.string.firstName_length_error_msg));
        } else if (lName.length() <= 3) {
            et_LastName.setError(getResources().getString(R.string.lastName_length_error_msg));
        } else  if (address.isEmpty()){
            et_fullAddress.setError(getResources().getString(R.string.fullAddress_error_msg));
        }else if (pincode.isEmpty()){
            et_pincode.setError(getResources().getString(R.string.pincode_error_msg));
        }else if (city.isEmpty()){
            et_city.setError(getResources().getString(R.string.city_error_msg));
        }else if (state.isEmpty()){
            Toast.makeText(this, getResources().getString(R.string.state_error_msg), Toast.LENGTH_SHORT).show();
        }else if (address.length() <= 3){
            et_fullAddress.setError(getResources().getString(R.string.address_length_error_msg));
        }else if (pincode.length() < 6){
            et_pincode.setError(getResources().getString(R.string.pincode_error_msg));
        }else if (city.length() <=3){
            et_city.setError(getResources().getString(R.string.address_length_error_msg));
        }else if (mobileNo1.isEmpty()){
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

            Toast.makeText(this, data + "Data Saved Successfully\n", Toast.LENGTH_SHORT).show();
            //callNextFragment();
            // saveInListPaseToNextFragment();
            // new AddressFragment(fName,lName,dateOfBirth,gender);

            //saveDataIntoSp(fName, lName, dateOfBirth1, gender);
            Log.d("db", "Updated: fName:  " + fName + " lName: " + lName + " dateOfBirth1: " + dateOfBirth1 + " gender: " + gender);

            //updateData();
        }
    }

    private void updateData()
    {
        viewModel = ViewModelProviders.of(this).get(PersonViewModel.class);


        viewModel.update(firstName,lastName,dateOfBirth,gender,address, pincode,city,state,mobileNo1,mobileNo2,emailId,id);
        Toast.makeText(this, "id: "+ id + "Updated", Toast.LENGTH_SHORT).show();

      /*  Fragment fragment = new UsersListFragment();
        if (fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
*/
        //finish();

    }



    private PersonEntity setValuesToEntity(String firstName, String lastName, String dateOfBirth, String gender, String address, String pincode, String city, String state, String mobileNo1, String mobileNo2, String emailId)
    {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setFirstName(firstName);
        personEntity.setLastName(lastName);
        personEntity.setDateOfBirth(dateOfBirth);
        personEntity.setGender(gender);
        personEntity.setAddress(address);
        personEntity.setPincode(pincode);
        personEntity.setCity(city);
        personEntity.setState(state);
        personEntity.setMobileNo1(mobileNo1);
        personEntity.setMobileNo2(mobileNo2);
        personEntity.setEmailId(emailId);

        return personEntity;
    }

    private void setStateData()
    {

        if (state.equals("Andhra Pradesh")){
            spinner.setSelection(0);
        }else if (state.equals("Arunachal Pradesh")){
            spinner.setSelection(1);
        }else if (state.equals("Assam")){
            spinner.setSelection(2);
        }else if (state.equals("Bihar")){
            spinner.setSelection(3);
        }else if (state.equals("Chhattisgarh")){
            spinner.setSelection(4);
        }else if (state.equals("Goa")){
            spinner.setSelection(5);
        }else if (state.equals("Gujarat")){
            spinner.setSelection(6);
        }else if (state.equals("Haryana")){
            spinner.setSelection(7);
        }else if (state.equals("Himachal Pradesh")){
            spinner.setSelection(8);
        }else if (state.equals("Jharkhand")){
            spinner.setSelection(9);
        }else if (state.equals("Karnataka")){
            spinner.setSelection(10);
        }else if (state.equals("Kerala")){
            spinner.setSelection(11);
        }else if (state.equals("Madhya Pradesh")){
            spinner.setSelection(12);
        }else if (state.equals("Maharashtra")){
            spinner.setSelection(13);
        }else if (state.equals("Manipur")){
            spinner.setSelection(14);
        }else if (state.equals("Meghalaya")){
            spinner.setSelection(15);
        }else if (state.equals("Mizoram")){
            spinner.setSelection(16);
        }else if (state.equals("Nagaland")){
            spinner.setSelection(17);
        }else if (state.equals("Odisha")){
            spinner.setSelection(18);
        }else if (state.equals("Punjab")){
            spinner.setSelection(19);
        }else if (state.equals("Rajasthan")){
            spinner.setSelection(20);
        }else if (state.equals("Sikkim")){
            spinner.setSelection(21);
        }else if (state.equals("Tamil Nadu")){
            spinner.setSelection(22);
        }else if (state.equals("Telangana")){
            spinner.setSelection(23);
        }else if (state.equals("Tripura")){
            spinner.setSelection(24);
        }else if (state.equals("Uttar Pradesh")){
            spinner.setSelection(25);
        }else if (state.equals("Uttarakhand")){
            spinner.setSelection(26);
        }else if (state.equals("West Bengal")){
            spinner.setSelection(27);
        }else if (state.equals("Andaman and Nicobar Islands")){
            spinner.setSelection(28);
        }else if (state.equals("Chandigarh")){
            spinner.setSelection(29);
        }else if (state.equals("Dadar and Nagar Haveli")){
            spinner.setSelection(30);
        }else if (state.equals("Daman and Diu")){
            spinner.setSelection(31);
        }else if (state.equals("Lakshadweep")){
            spinner.setSelection(32);
        }else if (state.equals("Puducherry")){
            spinner.setSelection(33);
        }

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
                    new DatePickerDialog(getApplicationContext(), date,
                            myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

        }

    }


    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateOfBirth = sdf.format(myCalendar.getTime());
        if (dateOfBirth.length() == 10) {
            et_DateOfBirth.setText(dateOfBirth);
            Toast.makeText(this, dateOfBirth, Toast.LENGTH_SHORT).show();
        }

    }

}
