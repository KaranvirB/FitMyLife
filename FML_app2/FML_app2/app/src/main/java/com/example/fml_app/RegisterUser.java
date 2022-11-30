package com.example.fml_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity {

    private EditText name_reg, email_reg, height_reg, weight_reg, password_reg, rePassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        //Initialize views
        Button but_button_reg = findViewById(R.id.registerUser);
        name_reg = findViewById(R.id.fullName);
        email_reg = findViewById(R.id.emailRegister);
        height_reg = findViewById(R.id.heightRegister);
        weight_reg = findViewById(R.id.weightRegister);
        password_reg = findViewById(R.id.passwordRegister);
        rePassword = findViewById(R.id.RePassword);

        //Register the user
        but_button_reg.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String name = name_reg.getText().toString().trim();
        String email = email_reg.getText().toString().trim();
        String height = height_reg.getText().toString().trim();
        String weight = weight_reg.getText().toString().trim();
        String password = password_reg.getText().toString().trim();
        String Repassword = rePassword.getText().toString().trim();

        //Input Validation
        if (name.isEmpty()){
            name_reg.setError("Name is required!");
            name_reg.requestFocus();
            return;
        }
        if (email.isEmpty()){
            email_reg.setError("Email is required!");
            email_reg.requestFocus();
            return;
        }
        if (height.isEmpty()){
            height_reg.setError("Height is required!");
            height_reg.requestFocus();
            return;
        }
        try {
            Integer.parseInt(height);
        } catch(NumberFormatException e){
            height_reg.setError("Height must be a number!");
            height_reg.requestFocus();
            return;
        }
        if (weight.isEmpty()){
            weight_reg.setError("Weight is required!");
            weight_reg.requestFocus();
            return;
        }
        try {
            Integer.parseInt(weight);
        } catch(NumberFormatException e){
            weight_reg.setError("Weight must be a number!");
            weight_reg.requestFocus();
            return;
        }
        if (password.isEmpty()){
            password_reg.setError("Password is required!");
            password_reg.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_reg.setError("Provide valid Email!");
            email_reg.requestFocus();
            return;
        }
        if (password.length() < 6){
            password_reg.setError("Password must be at least 6 characters!");
            password_reg.requestFocus();
            return;
        }
        if (!Repassword.equals(password)){
            rePassword.setError("Password must match!");
            rePassword.requestFocus();
            return;
        }

        //Create user in FireBase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterUser.this, task -> {
                    if (task.isSuccessful()) {

                        //Add user attributes to realtime database
                        User user = new User(name,email,Double.parseDouble(height),Double.parseDouble(weight),0);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user);

                        Toast.makeText(RegisterUser.this, "Registration Success!", Toast.LENGTH_SHORT).show();

                        //Send user back to login page
                        startActivity(new Intent(RegisterUser.this, MainActivity.class));

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(RegisterUser.this, "Error in Registration!", Toast.LENGTH_SHORT).show();
                    }
                });



    }
}