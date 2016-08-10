package edu.utd.cs.bdma.appfinn.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.ui.TextBox;

public class Utils {
	public static boolean CheckRequiredField(ArrayList requiredControlsList) {

		boolean requiredData = false;
		for (Object widget : requiredControlsList) {
			if (widget instanceof TextBox) {
				TextBox currTextBox = (TextBox) widget;
				if (currTextBox.getText().trim().isEmpty()) {
					requiredData = true;
					currTextBox.setStyleName("TextBoxRed");
				} else {
					currTextBox.setStyleName("TextBox");
				}
			}
		}
		return requiredData;

	}

	public static boolean CheckValidField(HashMap<Object, String> validControlList) {

		boolean invalidData = false;
		NameValidator nameValidator = null;
		for (Object widget : validControlList.keySet()) {
			if (widget instanceof TextBox) {
				String validationOption = validControlList.get(widget);
//				System.out.println("hashmap value:" + validationOption);
				TextBox currTextBox = (TextBox) widget;
				nameValidator = new NameValidator(validationOption);
				if (!nameValidator.validate(currTextBox.getText().trim())) {
					invalidData = true;
					currTextBox.setStyleName("TextBoxRed");
				} else {
					currTextBox.setStyleName("TextBox");
				}

			}
		}
		return invalidData;
	}
}
