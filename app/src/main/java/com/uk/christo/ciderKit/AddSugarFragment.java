package com.uk.christo.ciderKit;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
//import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class AddSugarFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
	private EditText startingSG;
	private EditText targetSG;
	private EditText volume;
	private EditText addSugar;
	private RadioGroup radioGroup;
	private RadioButton buttonMetric;
	private RadioButton buttonImperial;
	private TextView volumeLabel;
	private TextView addSugarLabel;

	private View v;

	public void processValues() {
		Float sugarToAdd = Float.parseFloat("0.0");
		Float startBrix, endBrix;
		String stringOG = startingSG.getText().toString();
		String stringSG = targetSG.getText().toString();
		String stringVolume = volume.getText().toString();

		if (!stringOG.matches("") && !stringSG.matches("")
				&& !stringVolume.matches("")) {
			Float floatOG = Float.parseFloat(stringOG);
			Float floatSG = Float.parseFloat(stringSG);
			Float floatVol = Float.parseFloat(stringVolume);

			// If imperial radio is selected, convert floatVol to L from pint
			if (buttonImperial.isChecked()) {
				floatVol = floatVol / (float) 1.7598;
			}

			// using Bx = (((182.4601*S -775.6821)*S +1262.7794)*S -669.5622)
			startBrix = (float) (((182.4601 * floatOG - 775.6831) * floatOG + 1262.7794)
					* floatOG - 669.5622);
			endBrix = (float) (((182.4601 * floatSG - 775.6831) * floatSG + 1262.7794)
					* floatSG - 669.5622);
			sugarToAdd = (float) (floatVol * floatOG * (endBrix - startBrix) / (100 - endBrix));

			/*
			 * If imperial radio is selected, convert sugarToAdd from kg to lb
			 */
			if (buttonImperial.isChecked()) {
				sugarToAdd = (float) 2.20462 * sugarToAdd;
			}

			String s = String.format("%.2f", sugarToAdd);
			addSugar.setText(s);
		}
	}

	public void resetLabels() {
		if (buttonImperial.isChecked()) {
			volumeLabel.setText(R.string.volumeLabelImperial);
			addSugarLabel.setText(R.string.addSugarLabelImperial);
		} else {
			volumeLabel.setText(R.string.volumeLabelMetric);
			addSugarLabel.setText(R.string.addSugarLabelMetric);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		v = inflater.inflate(R.layout.fragment_addsugar, container, false);
		startingSG = (EditText) v.findViewById(R.id.startingSGValue);
		targetSG = (EditText) v.findViewById(R.id.targetSGValue);
		volume = (EditText) v.findViewById(R.id.volumeValue);
		addSugar = (EditText) v.findViewById(R.id.addSugarValue);
		buttonMetric = (RadioButton) v.findViewById(R.id.radioMetric);
		buttonImperial = (RadioButton) v.findViewById(R.id.radioImperial);
		volumeLabel = (TextView) v.findViewById(R.id.volumeLabel);
		addSugarLabel = (TextView) v.findViewById(R.id.addSugarLabel);

		buttonMetric.setChecked(true);
		buttonImperial.setChecked(false);

		radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup1);
        radioGroup.setOnCheckedChangeListener(this);

		startingSG.setHint(R.string.enterValueHint);
		targetSG.setHint(R.string.enterValueHint);
		volume.setHint(R.string.enterValueHint);
		addSugar.setHint(R.string.noText);

		volumeLabel.setText(R.string.volumeLabelMetric);
		addSugarLabel.setText(R.string.addSugarLabelMetric);

		TextWatcher watcher = new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence charSequence, int i,
					int i1, int i2) {
			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1,
					int i2) {
			}

			@Override
			public void afterTextChanged(Editable editable) {
				resetLabels();
				processValues();
			}
		};

		startingSG.addTextChangedListener(watcher);
		targetSG.addTextChangedListener(watcher);
		volume.addTextChangedListener(watcher);

		// Inflate the layout for this fragment
		return v;
	}


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        resetLabels();

        Float floatVol;
        EditText volume;

        switch (checkedId) {
            case R.id.radioMetric:
                volume = (EditText) v.findViewById(R.id.volumeValue);
                if (!volume.getText().toString().matches("")) {
                    floatVol = Float
                            .parseFloat(volume.getText().toString());
                    floatVol = floatVol / (float) 1.7598;
                    String s = String.format("%.2f", floatVol);
                    volume.setText(s);
                }
                break;
            case R.id.radioImperial:
                volume = (EditText) v.findViewById(R.id.volumeValue);
                if (!volume.getText().toString().matches("")) {
                    floatVol = Float
                            .parseFloat(volume.getText().toString());
                    floatVol = floatVol * (float) 1.7598;
                    String s = String.format("%.2f", floatVol);
                    volume.setText(s);
                }
                processValues();
                break;
        }
    }
}