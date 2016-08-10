package edu.utd.cs.bdma.appfinn.client;

public class Testing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NameValidator nameValidator = new NameValidator("name");
		String name = "khale";
		System.out.println("Is " + name + " a valid name? "+nameValidator.validate(name));

		nameValidator = new NameValidator("email");
		String email = "khale@d.c@@";
		System.out.println("Is " + email + " a valid email? "+nameValidator.validate(email));
		
		nameValidator = new NameValidator("phone");
		String phone = "111111111d";
		System.out.println("Is " + phone + " a valid phone? "+nameValidator.validate(phone));
		
		nameValidator = new NameValidator("pass");
		String pass = "khale@d.1_Ac@@";
		System.out.println("Is " + pass + " a valid password? "+nameValidator.validate(pass));
	}

}
