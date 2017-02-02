package edu.utd.cs.bdma.appfinn.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

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
	
	public static boolean CheckNumberRange(HashMap<Object, String> validControlList) {

		boolean invalidData = false;
		for (Object widget : validControlList.keySet()) {
			if (widget instanceof TextBox) {
				String validationOption = validControlList.get(widget); // 1_10 between 1 and 10
				TextBox currTextBox = (TextBox) widget;

				if (!ValidNumberRange(currTextBox.getText().trim(), validationOption) ) {
					invalidData = true;
					currTextBox.setStyleName("TextBoxRed");
				} else {
					currTextBox.setStyleName("TextBox");
				}

			}
		}
		return invalidData;
	}
	
	public static boolean ValidNumberRange(String textValue, String validationOption){
		int val = Integer.parseInt(textValue);
		
		String[] options = validationOption.split("_");
		int num1 = Integer.parseInt(options[0]);
		int num2 = Integer.parseInt(options[1]);
		
		if (val < num1 || val > num2)
			return false;
		
		return true;
	}

}