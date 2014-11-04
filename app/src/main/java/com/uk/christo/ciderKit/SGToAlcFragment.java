package com.uk.christo.ciderKit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class SGToAlcFragment extends Fragment {
	private EditText OGValue;
	private EditText TGValue;
	private TextView AlcValue;
	private View v;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		v = inflater.inflate(R.layout.activity_sgto_alc, container, false);

		OGValue = (EditText) v.findViewById(R.id.OGValue);
		TGValue = (EditText) v.findViewById(R.id.TGValue);
		AlcValue = (TextView) v.findViewById(R.id.AlcValue);

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
				processValues();
			}
		};

		OGValue.addTextChangedListener(watcher);
		TGValue.addTextChangedListener(watcher);
		
		return v;
	}



	public void processValues() {
		String OGstr = OGValue.getText().toString();
		String TGstr = TGValue.getText().toString();
		if (!OGstr.matches("") && !TGstr.matches("")) {
			Float OG = Float.parseFloat(OGstr);
			Float TG = Float.parseFloat(TGstr);

			Float alcPercent = (float) ((((1.05) * (OG - TG)) / 0.79) * 100);
			String s = String.format("%.2f", alcPercent);
			AlcValue.setText(s);
		}
	}
}
