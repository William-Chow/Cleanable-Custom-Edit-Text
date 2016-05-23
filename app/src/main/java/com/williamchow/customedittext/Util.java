package com.williamchow.customedittext;

import android.widget.EditText;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by William Chow on 5/2/2016.
 */
public class Util {

    public static final String REGEX_AMOUNT = "(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})";

    public static void checkNumber(String s, EditText editText) {
        String amount = editText.getText().toString();
        if (!s.toString().matches(Util.REGEX_AMOUNT)) {
            editText.setText(Util.formatAmountCurrency(amount).toString());
        }
    }

    public static String formatAmountCurrency(String userInput) {
        if (userInput.length() == 0) {
            return "0.00";
        } else {
            boolean isNegative = false;
            if (userInput.charAt(0) == '-') {
                isNegative = true;
            }

            if (!userInput.contains(".")) {
                userInput += ".00";
            }

            String amountInput = userInput.replaceAll("[^\\d]", "");

            StringBuilder payAmountBuilder = new StringBuilder(amountInput);

            while (payAmountBuilder.length() > 3
                    && payAmountBuilder.charAt(0) == '0')
                payAmountBuilder.deleteCharAt(0);

            while (payAmountBuilder.length() < 3)
                payAmountBuilder.insert(0, '0');

            // add decimal sign
            payAmountBuilder.insert(payAmountBuilder.length() - 2, '.');

            // add thousand sign
            if (payAmountBuilder.length() > 6)
                payAmountBuilder.insert(payAmountBuilder.length() - 6, ',');
            if (payAmountBuilder.length() > 10)
                payAmountBuilder.insert(payAmountBuilder.length() - 10, ',');

            String amount = isNegative ? "-" : "";
            amount += payAmountBuilder.toString();
            return amount;
        }
    }
}
