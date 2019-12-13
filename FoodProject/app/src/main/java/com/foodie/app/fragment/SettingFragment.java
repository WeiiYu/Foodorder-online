package com.foodie.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import com.foodie.app.Activity.LoginActivity;
import com.foodie.app.R;
import com.foodie.app.util.PrefUtils;

public class SettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        if(PrefUtils.get("user","userId",getActivity())==null){
            if (findPreference("logout")!=null){
                getPreferenceScreen().removePreference(findPreference("logout"));
            }
        }
        final CheckBoxPreference saveFlow = (CheckBoxPreference) getPreferenceManager()
                .findPreference("save_flow");

        saveFlow.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean checked = Boolean.valueOf(newValue.toString());
                if (checked==true){
                    Toast.makeText(getActivity(),"Open the province traffic mode",Toast.LENGTH_LONG).show();
                    // Open the province traffic mode method
                }else{
                    Toast.makeText(getActivity(),"Close the province traffic mode",Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
        final CheckBoxPreference allowPush = (CheckBoxPreference) getPreferenceManager()
                .findPreference("allow_push");
        // push button

        allowPush.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean checked = Boolean.valueOf(newValue.toString());
                if (checked==true){
                    Toast.makeText(getActivity(),"Open message push",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(),"Turn off message push",Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
        if (findPreference("logout")!=null){
            Preference myPref=getPreferenceManager().findPreference("logout");
            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Toast.makeText(getActivity(),"Exit the success",Toast.LENGTH_LONG).show();
                    //PrefUtils.remove("user","userId",getActivity());
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    return true;
                }
            });
        }
    }
}
