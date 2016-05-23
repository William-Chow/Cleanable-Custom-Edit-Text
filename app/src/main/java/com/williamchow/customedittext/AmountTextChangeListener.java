package com.williamchow.customedittext;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class AmountTextChangeListener implements TextWatcher {
	
	private EditText etPaymentAmount;
	private String current = "";
	
	/**
	 * Class constructor with EditText
	 * listening to amount text change
	 */
	public AmountTextChangeListener(EditText etPaymentAmount) {
		this.etPaymentAmount = etPaymentAmount;
	}
	
	/**
	 * Overriding method for after text changed
	 */
	@Override
	public void afterTextChanged(Editable editable) {
		//push the text selection to the last
//		etPaymentAmount.setSelection(etPaymentAmount.length());
	}
	
	/**
	 * Overriding method for before text changed
	 */
	@Override
	public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
		//push the text selection to the last
//		etPaymentAmount.setSelection(etPaymentAmount.length());
	}

	/**
	 * Overriding method for on text changed
	 */
	@Override
	public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
		//check the number then format the amount into currency form
//		Util.checkNumber(charSequence.toString(), etPaymentAmount);

		if(!charSequence.toString().equals(current)){
			etPaymentAmount.removeTextChangedListener(this);

			String cleanString = charSequence.toString().replaceAll("[$,.]", "");

			if(cleanString.length() == 0){
				cleanString = "0.00";
			}

//			double parsed = Double.parseDouble(cleanString);
//			String formatted = NumberFormat.getInstance(Locale.US).format((parsed/100));

			BigDecimal parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
//			String formatted = NumberFormat.getCurrencyInstance(Locale.US).format(parsed);

			NumberFormat nf = NumberFormat.getCurrencyInstance();
			String pattern = ((DecimalFormat) nf).toPattern();
			String newPattern = pattern.replace("\u00A4", "").trim();
			NumberFormat newFormat = new DecimalFormat(newPattern);

			String formatted = newFormat.format(parsed);

			current = formatted;

			if(!formatted.equalsIgnoreCase(String.valueOf("0.00"))){
				etPaymentAmount.setText(formatted);
				etPaymentAmount.setSelection(formatted.length());
			}

			etPaymentAmount.addTextChangedListener(this);
		}
	}
	
}
