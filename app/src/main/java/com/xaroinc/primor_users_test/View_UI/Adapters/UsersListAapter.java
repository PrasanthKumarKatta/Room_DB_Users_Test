package com.xaroinc.primor_users_test.View_UI.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xaroinc.primor_users_test.R;
import com.xaroinc.primor_users_test.View_UI.Database.PersonEntity;
import com.xaroinc.primor_users_test.View_UI.Database.PersonViewModel;
import com.xaroinc.primor_users_test.View_UI.PersonEditActivity;

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

        userInfo.user_edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "clicked on " + personEntityList.get(i).getFirstName(), Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putInt(dbIdKey,personEntityList.get(i).getId());
                bundle.putString(fNameKey, personEntityList.get(i).getFirstName());
                bundle.putString(lNameKey, personEntityList.get(i).getLastName());
                bundle.putString(dateOfBirthKey, personEntityList.get(i).getDateOfBirth());
                bundle.putString(genderKey, personEntityList.get(i).getGender());
                bundle.putString(fullAddressKey, personEntityList.get(i).getAddress());
                bundle.putString(pincodeKey, personEntityList.get(i).getPincode());
                bundle.putString(cityKey, personEntityList.get(i).getCity());
                bundle.putString(stateKey, personEntityList.get(i).getState());
                bundle.putString(mobileNo1Key, personEntityList.get(i).getMobileNo1());
                bundle.putString(mobileNo2Key, personEntityList.get(i).getMobileNo2());
                bundle.putString(emailIdKey, personEntityList.get(i).getEmailId());

                Intent i = new Intent(context, PersonEditActivity.class);
                i.putExtras(bundle);
                context.startActivity(i);

                //Todo: working for fragments
                /*
                Fragment fragment = null;
                fragment= new NewUserFragment(personEntityList);

                if (fragment != null) {

                    String data = personEntityList.get(i).getFirstName() + "\n" + personEntityList.get(i).getLastName() + "\n" +
                            personEntityList.get(i).getDateOfBirth() + "\n" +personEntityList.get(i).getGender() + "\n" +
                            personEntityList.get(i).getAddress() + "\n"+personEntityList.get(i).getPincode() + "\n"+
                            personEntityList.get(i).getCity() + "\n"+ personEntityList.get(i).getState() + "\n"+
                            personEntityList.get(i).getMobileNo1() + "\n" +personEntityList.get(i).getMobileNo2() + "\n"+
                            personEntityList.get(i).getEmailId() + "\n";
                   // Toast.makeText(context, data +"", Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putInt(dbIdKey,personEntityList.get(i).getId());
                    bundle.putString(fNameKey, personEntityList.get(i).getFirstName());
                    bundle.putString(lNameKey, personEntityList.get(i).getLastName());
                    bundle.putString(dateOfBirthKey, personEntityList.get(i).getDateOfBirth());
                    bundle.putString(genderKey, personEntityList.get(i).getGender());
                    bundle.putString(fullAddressKey, personEntityList.get(i).getAddress());
                    bundle.putString(pincodeKey, personEntityList.get(i).getPincode());
                    bundle.putString(cityKey, personEntityList.get(i).getCity());
                    bundle.putString(stateKey, personEntityList.get(i).getState());
                    bundle.putString(mobileNo1Key, personEntityList.get(i).getMobileNo1());
                    bundle.putString(mobileNo2Key, personEntityList.get(i).getMobileNo2());
                    bundle.putString(emailIdKey, personEntityList.get(i).getEmailId());

                    fragment.setArguments(bundle);

                    FragmentTransaction ft = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.addToBackStack(null);
                    ft.commit();

                }*/

            }
        });

        userInfo.user_firstName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int id = personEntityList.get(i).getId();

                Toast.makeText(context, "you clicked on " + i + "\n" + "id: " + id, Toast.LENGTH_SHORT).show();
                callDeleteDialog(id);
                return false;
            }
        });

    }

    private void callDeleteDialog(int j) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.delete_alert_title);
        builder.setMessage(R.string.delete_alert_mesage);
        builder.setIcon(R.drawable.ic_delete_red_24dp);
        builder.setPositiveButton(R.string.alert_yes_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int j) {

                deletePerson(j);

                Toast.makeText(context, R.string.delete_success_msg, Toast.LENGTH_SHORT).show();
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


    private void deletePerson(int j) {

        List<PersonEntity> personEntityList = viewModel.getPersonEntityList();
        for (int k = 0; k < personEntityList.size(); k++) {
            if (personEntityList.get(k).getId() == j) {
                Toast.makeText(context, "Id deleted", Toast.LENGTH_SHORT).show();
                // viewModel.update(setValuesToEntity());

                firstName = personEntityList.get(k).getFirstName();
                lastName = personEntityList.get(k).getLastName();
                dateOfBirth = personEntityList.get(k).getDateOfBirth();
                gender = personEntityList.get(k).getGender();
                address = personEntityList.get(k).getAddress();
                pincode = personEntityList.get(k).getPincode();
                city = personEntityList.get(k).getCity();
                state = personEntityList.get(k).getState();
                mobileNo1 = String.valueOf(personEntityList.get(k).getMobileNo1());
                mobileNo2 = String.valueOf(personEntityList.get(k).getMobileNo2());
                emailId = personEntityList.get(k).getEmailId();

                viewModel.delete(setValuesToEntity(j));
            } else {
                Toast.makeText(context, "Id not exists", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private PersonEntity setValuesToEntity(int j) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setId(j);
        personEntity.setFirstName(firstName);
        personEntity.setLastName(lastName);
        /*personEntity.setDateOfBirth(dateOfBirth);
        personEntity.setGender(gender);
        personEntity.setAddress(address);
        personEntity.setPincode(pincode);
        personEntity.setCity(city);
        personEntity.setState(state);
        personEntity.setMobileNo1(Integer.parseInt(mobileNo1));
        personEntity.setMobileNo2(Integer.parseInt(mobileNo2));
        personEntity.setEmailId(emailId);
        */
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
