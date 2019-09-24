package com.xaroinc.primor_users_test.View_UI.Fragments.Nav_fragments;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xaroinc.primor_users_test.R;
import com.xaroinc.primor_users_test.View_UI.Database.PersonEntity;
import com.xaroinc.primor_users_test.View_UI.Fragments.NewUser_Fragments.AddressFragment;
import com.xaroinc.primor_users_test.View_UI.Fragments.NewUser_Fragments.CommunicationFragment;
import com.xaroinc.primor_users_test.View_UI.Fragments.NewUser_Fragments.Edit_AddressFragment;
import com.xaroinc.primor_users_test.View_UI.Fragments.NewUser_Fragments.Edit_CommunicationFragment;
import com.xaroinc.primor_users_test.View_UI.Fragments.NewUser_Fragments.Edit_PersonalFragment;
import com.xaroinc.primor_users_test.View_UI.Fragments.NewUser_Fragments.PersonalFragment;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditUserFragment extends Fragment {

    ViewPager vp;
    TabLayout tabLayout;
    List<PersonEntity> personEntityList;
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

    String firstName, lastName, dateOfBirth, gender, address, pincode, city, state, mobileNo1, mobileNo2, emailId;
    long id;

    public EditUserFragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public EditUserFragment(List<PersonEntity> personEntityList) {
        this.personEntityList = personEntityList;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       /* if (getFragmentManager() != null) {
            getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
*/
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_user, container, false);
        initViews(view);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initViews(View view) {

        loadBundleData();

        vp = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);

        FragmentManager cfManager=getChildFragmentManager();
        NewUserPagerAdapter adapter = new NewUserPagerAdapter(cfManager);

        vp.setAdapter(adapter);

        tabLayout.setupWithViewPager(vp);


    }

    private void loadBundleData()
    {
        Bundle bundle = this.getArguments();
        if (bundle != null && getArguments().containsKey(dbIdKey)) {

            id = (long) (Objects.requireNonNull(bundle.get(dbIdKey)));
            firstName = (Objects.requireNonNull(bundle.get(fNameKey))).toString();
            lastName = (Objects.requireNonNull(bundle.get(lNameKey))).toString();
            dateOfBirth = (Objects.requireNonNull(bundle.get(dateOfBirthKey))).toString();
            gender = (Objects.requireNonNull(bundle.get(genderKey))).toString();
            address = (Objects.requireNonNull(bundle.get(fullAddressKey))).toString();
            pincode = (Objects.requireNonNull(bundle.get(pincodeKey))).toString();
            city = (Objects.requireNonNull(bundle.get(cityKey))).toString();
            state = (Objects.requireNonNull(bundle.get(stateKey))).toString();
            mobileNo1 = (Objects.requireNonNull(bundle.get(mobileNo1Key))).toString();
            mobileNo2 = (Objects.requireNonNull(bundle.get(mobileNo2Key))).toString();
            emailId = (Objects.requireNonNull(bundle.get(emailIdKey))).toString();

            String bundleData = "Id: " + id + "\n" +firstName + "\n" + lastName + "\n" + dateOfBirth + "\n" +
                    gender + "\n" +address + "\n" + pincode + "\n" + city + "\n" + state + "\n"+
                    mobileNo1 + "\n" + mobileNo2 + "\n" + emailId + "\n";
            //  Toast.makeText(getActivity(), bundleData + " \n Bundle Data", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(getActivity(), "No Data in Bundle: ", Toast.LENGTH_SHORT).show();
        }
    }
    class NewUserPagerAdapter extends FragmentPagerAdapter {

        NewUserPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Bundle bundle1 = new Bundle();
            passDataToFragments(bundle1);

            Fragment fragment = null;

            switch (i) {
                case 0:
                    /*
                    if (personEntityList.size() != 0){
                        return new PersonalFragment(personEntityList);
                    }else {
                        return new PersonalFragment();
                    }*/
                    fragment = new Edit_PersonalFragment();

                    //Remove all the previous fragments in back stack
                    //getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    fragment.setArguments(bundle1);

                    return fragment;
                case 1:
                    //return new AddressFragment();
                    fragment = new Edit_AddressFragment();

                    //  getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                  // Toast.makeText(getActivity(), "Address Fragment", Toast.LENGTH_SHORT).show();

                    fragment.setArguments(bundle1);

                    return fragment;
                case 2:
                    //return new CommunicationFragment();
                    fragment = new Edit_CommunicationFragment();

                    //   getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    fragment.setArguments(bundle1);

                    return fragment;
            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Personal";
                case 1:
                    return "Address";
                case 2:
                    return "Communication";
            }

            return super.getPageTitle(position);
        }
    }

    private void passDataToFragments(Bundle bundle1)
    {

        bundle1.putLong(dbIdKey, id);
        bundle1.putString(fNameKey, firstName);
        bundle1.putString(lNameKey, lastName);
        bundle1.putString(dateOfBirthKey, dateOfBirth);
        bundle1.putString(genderKey, gender);
        bundle1.putString(fullAddressKey,address);
        bundle1.putString(pincodeKey, pincode);
        bundle1.putString(cityKey, city);
        bundle1.putString(stateKey,state);
        bundle1.putString(mobileNo1Key, mobileNo1);
        bundle1.putString(mobileNo2Key, mobileNo2);
        bundle1.putString(emailIdKey, emailId);

    }

   /*
   @Override
        public void onDestroyView () {
            super.onDestroyView();

            //Toast.makeText(getActivity(), "OnDestroyView", Toast.LENGTH_SHORT).show();
        }
   */


}

