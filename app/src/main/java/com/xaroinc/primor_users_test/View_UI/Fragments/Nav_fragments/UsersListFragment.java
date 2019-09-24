package com.xaroinc.primor_users_test.View_UI.Fragments.Nav_fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xaroinc.primor_users_test.R;
import com.xaroinc.primor_users_test.View_UI.Adapters.UsersListAapter;
import com.xaroinc.primor_users_test.View_UI.Database.PersonEntity;
import com.xaroinc.primor_users_test.View_UI.Database.PersonViewModel;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersListFragment extends Fragment {

    RecyclerView rv;
    private FloatingActionButton fab;

    PersonViewModel viewModel;

    SwipeRefreshLayout swipeRefreshLayout;


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

    Bundle bundle = new Bundle();

    public UsersListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_list, container, false);
        initViews(view);
        refresh_data(view);
        return view;
    }

    private void initViews(View view)
    {
        rv = view.findViewById(R.id.rv_userList);

        swipeRefreshLayout = view.findViewById(R.id.mainContent_swipeRef);

        rv.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        initViewModel();

        fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getActivity(), "You Clicked on FAB", Toast.LENGTH_SHORT).show();
                 callNewUserFragmeent();
            }
        });
    }


    private void refresh_data(final View view) {
        swipeRefreshLayout.setColorScheme(R.color.orange_buttom);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                initViews(view);
                Toast.makeText(getActivity(), getResources().getString(R.string.sync_success_refresh_msg), Toast.LENGTH_SHORT).show();

                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }

            }
        });
    }

    private void callNewUserFragmeent()
    {
        Fragment fragment = null;
        fragment = new NewUserFragment();

        parseDummyBundleData();

         fragment.setArguments(bundle);

        if (fragment != null){
            FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            //ft.detach(fragment);
            ft.replace(R.id.content_frame, fragment);
          //  ft.attach(fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        //getActivity().finish();

    }

    private void parseDummyBundleData()
    {
        bundle.putLong(dbIdKey,0);
        bundle.putString(fNameKey, "");
        bundle.putString(lNameKey, "");
        bundle.putString(dateOfBirthKey, "");
        bundle.putString(genderKey, "");
        bundle.putString(fullAddressKey, "");
        bundle.putString(pincodeKey, "");
        bundle.putString(cityKey, "");
        bundle.putString(stateKey, "");
        bundle.putString(mobileNo1Key, "");
        bundle.putString(mobileNo2Key, "");
        bundle.putString(emailIdKey,"");

    }

    private void initViewModel()
    {
        viewModel = ViewModelProviders.of(getActivity()).get(PersonViewModel.class);

        viewModel.getLive_list().observe(getActivity(), new Observer<List<PersonEntity>>() {
            @Override
            public void onChanged(@Nullable List<PersonEntity> personEntities) {

                UsersListAapter adapter = new UsersListAapter(getActivity(), personEntities);
                rv.setAdapter(adapter);

            }
        });

    }

}
