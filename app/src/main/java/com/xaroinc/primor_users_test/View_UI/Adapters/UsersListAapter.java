package com.xaroinc.primor_users_test.View_UI.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xaroinc.primor_users_test.MainActivity;
import com.xaroinc.primor_users_test.R;
import com.xaroinc.primor_users_test.View_UI.Database.PersonEntity;
import com.xaroinc.primor_users_test.View_UI.Database.PersonViewModel;
import com.xaroinc.primor_users_test.View_UI.Fragments.Nav_fragments.EditUserFragment;

import java.util.List;

public class UsersListAapter extends RecyclerView.Adapter<UsersListAapter.UserInfo> {
    private Context context;
    List<PersonEntity> personEntityList;
    PersonViewModel viewModel;

    String mobileNo1, mobileNo2, emailId;

    String firstName, lastName, dateOfBirth, gender, address, pincode, city, state;

    int id;

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
    private long id_bundle;

    public UsersListAapter(Context context, List<PersonEntity> personEntityList) {
        this.context = context;
        this.personEntityList = personEntityList;
    }

    @NonNull
    @Override
    public UserInfo onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_user_list, viewGroup, false);
        return new UserInfo(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserInfo userInfo, final int i) {
        userInfo.user_Id.setText(personEntityList.get(i).getId() + ".");
        userInfo.user_firstName.setText(personEntityList.get(i).getFirstName() + " " + personEntityList.get(i).getLastName() /*+ "\n" + personEntityList.get(i).getEmailId()*/);

        getdataToParse(i);

        if (gender.equals("female")){
            userInfo.user_edit_img.setBackground(null);
            userInfo.user_edit_img.setBackground(context.getResources().getDrawable(R.drawable.user_profile_female_edit));
        }else if (gender.equals("male")){
            userInfo.user_edit_img.setBackground(null);
            userInfo.user_edit_img.setBackground(context.getResources().getDrawable(R.drawable.user_profile_edit));
        }

        userInfo.user_edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "clicked on " + personEntityList.get(i).getFirstName(), Toast.LENGTH_SHORT).show();

               getdataToParse(i);

                Bundle bundle = new Bundle();
                bundle.putLong(dbIdKey,id_bundle);
                bundle.putString(fNameKey, firstName);
                bundle.putString(lNameKey, lastName);
                bundle.putString(dateOfBirthKey, dateOfBirth);
                bundle.putString(genderKey, gender);
                bundle.putString(fullAddressKey, address);
                bundle.putString(pincodeKey, pincode);
                bundle.putString(cityKey, city);
                bundle.putString(stateKey, state);
                bundle.putString(mobileNo1Key, mobileNo1);
                bundle.putString(mobileNo2Key, mobileNo2);
                bundle.putString(emailIdKey,emailId);

              /*  Intent i = new Intent(context, PersonEditActivity.class);
                i.putExtras(bundle);
                context.startActivity(i);
                */

                //Todo: working for fragments
                Fragment fragment = null;
                //fragment= new NewUserFragment();

                fragment= new EditUserFragment();

                if (fragment != null) {

                    String data = personEntityList.get(i).getFirstName() + "\n" + personEntityList.get(i).getLastName() + "\n" +
                            personEntityList.get(i).getDateOfBirth() + "\n" +personEntityList.get(i).getGender() + "\n" +
                            personEntityList.get(i).getAddress() + "\n"+personEntityList.get(i).getPincode() + "\n"+
                            personEntityList.get(i).getCity() + "\n"+ personEntityList.get(i).getState() + "\n"+
                            personEntityList.get(i).getMobileNo1() + "\n" +personEntityList.get(i).getMobileNo2() + "\n"+
                            personEntityList.get(i).getEmailId() + "\n";
                   // Toast.makeText(context, data +"", Toast.LENGTH_SHORT).show();
                    Log.d("db","adapter: bundle Data\n" +data);

                    fragment.setArguments(bundle);

                    FragmentTransaction ft = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.addToBackStack(null);
                    ft.commit();

                }

            }
        });

        userInfo.user_firstName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                long id = personEntityList.get(i).getId();

                Toast.makeText(context, "you clicked on " + i + "\n" + "id: " + id, Toast.LENGTH_SHORT).show();
                callDeleteDialog(i,id);
                return false;
            }
        });

    }

    private void getdataToParse(int i)
    {
        id_bundle = personEntityList.get(i).getId();
        firstName = personEntityList.get(i).getFirstName();
        lastName = personEntityList.get(i).getLastName();
        dateOfBirth = personEntityList.get(i).getDateOfBirth();
        gender = personEntityList.get(i).getGender();
        address = personEntityList.get(i).getAddress();
        pincode = personEntityList.get(i).getPincode();
        city = personEntityList.get(i).getCity();
        state = personEntityList.get(i).getState();
        mobileNo1 = personEntityList.get(i).getMobileNo1();
        mobileNo2 = personEntityList.get(i).getMobileNo2();
        emailId = personEntityList.get(i).getEmailId();
    }

    private void callDeleteDialog(final int pos, final long itemId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.delete_alert_title);
        builder.setMessage(R.string.delete_alert_mesage);
        builder.setIcon(R.drawable.ic_delete_red_24dp);
        builder.setPositiveButton(R.string.alert_yes_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               // Toast.makeText(context, "id: " + itemId , Toast.LENGTH_SHORT).show();

                getdataToParse(pos);

              //  viewModel.delete(setValuesToEntity());

              //  Toast.makeText(context, R.string.delete_success_msg, Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton(R.string.alert_no_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int j) {
                dialogInterface.dismiss();
                // Toast.makeText(context, R.string.delete_cancel_message, Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();

    }

    private PersonEntity setValuesToEntity() {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setId(id_bundle);
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

    @Override
    public int getItemCount() {
        return personEntityList.size();
    }

    class UserInfo extends RecyclerView.ViewHolder {
        TextView user_Id, user_firstName;
        ImageView user_edit_img;

        UserInfo(@NonNull View itemView) {
            super(itemView);
            user_Id = itemView.findViewById(R.id.user_id_list);
            user_firstName = itemView.findViewById(R.id.user_firstName_list);
            user_edit_img = itemView.findViewById(R.id.user_edit_img_list);

        }
    }
}
