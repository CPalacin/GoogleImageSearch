package com.crubio.googleimagesearch.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.crubio.googleimagesearch.R;
import com.crubio.googleimagesearch.activity.SearchActivity;
import com.crubio.googleimagesearch.model.SearchConfiguration;

public class     SettingsDialog  extends DialogFragment {

        private SearchConfiguration configuration;
        private String color;
        private String size;
        private String type;
        private EditText etSite;
        private OnSaveConfigurationListener onSaveConfigurationListener;

    public static SettingsDialog newInstance(SearchConfiguration configuration) {
        SettingsDialog frag = new SettingsDialog();
        Bundle args = new Bundle();
        args.putParcelable(SearchActivity.CONFIGURATION, configuration);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configuration = getArguments().getParcelable(SearchActivity.CONFIGURATION);
        String title = getActivity().getString(R.string.filter_title);
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        Button saveButton = (Button)  view.findViewById(R.id.bt_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                dismiss();
            }
        });

        Button cancelButton = (Button)  view.findViewById(R.id.bt_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        etSite = (EditText) view.findViewById(R.id.et_site);
        etSite.setText(configuration.getSite());
        etSite.requestFocus();
        Spinner spColor = (Spinner) view.findViewById(R.id.sp_color);
        spColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                color = parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter<CharSequence> adapterColor = ArrayAdapter.createFromResource(getActivity(),
                R.array.color_array, android.R.layout.simple_spinner_item);
        adapterColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spColor.setAdapter(adapterColor);
        final String color = configuration.getColor();
        if (color != null) {
            int spinnerPosition = adapterColor.getPosition(color);
            spColor.setSelection(spinnerPosition);
        }

        Spinner spSize = (Spinner) view.findViewById(R.id.sp_image_size);
        spSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                size = parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter<CharSequence> adapterSize = ArrayAdapter.createFromResource(getActivity(),
                R.array.size_array, android.R.layout.simple_spinner_item);
        adapterSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSize.setAdapter(adapterSize);
        final String size = configuration.getSize();
        if (size != null) {
            int spinnerPosition = adapterSize.getPosition(size);
            spSize.setSelection(spinnerPosition);
        }

        Spinner spType = (Spinner) view.findViewById(R.id.sp_image_type);
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                type = parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(getActivity(),
                R.array.type_array, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(adapterType);
        final String type = configuration.getType();
        if (type != null) {
            int spinnerPosition = adapterType.getPosition(type);
            spType.setSelection(spinnerPosition);
        }
    }


    private void save(){
        configuration.setColor(color);
        configuration.setSize(size);
        configuration.setType(type);
        configuration.setSite(etSite.getText().toString());
        onSaveConfigurationListener.onSaveConfiguration(configuration);
    }

    public void setOnSaveConfigurationListener(OnSaveConfigurationListener onSaveConfigurationListener){
        this.onSaveConfigurationListener = onSaveConfigurationListener;
    }

    public interface OnSaveConfigurationListener{
        public void onSaveConfiguration(SearchConfiguration configuration);
    }
}
