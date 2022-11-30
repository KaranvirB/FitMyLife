package com.example.fml_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    //Navigation Buttons
    ImageView search_Button, home_Button;

    //Logout, update weight
    Button logout, update_weight_button;

    //Custom user data
    TextView user_name, user_email, user_weight, user_height, user_BMI;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    EditText update_weight;
    double oldWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Navigation buttons
        search_Button = findViewById(R.id.search_Button);
        home_Button = findViewById(R.id.home_Button);
        home_Button.setOnClickListener(view -> {
            startActivity(new Intent(Profile.this, HomePage.class));
        });

        //Logout button
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Profile.this, MainActivity.class));
        });

        //Custom user data
        user_name = findViewById(R.id.user_name);
        user_email = findViewById(R.id.user_email);
        user_weight = findViewById(R.id.user_weight);
        user_height = findViewById(R.id.user_height);
        user_BMI = findViewById(R.id.user_BMI);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    String name = userProfile.Name;
                    String email = userProfile.email;
                    double height = userProfile.height;
                    double weight = userProfile.weight;

                    user_name.setText(name);
                    user_email.setText(email);
                    user_weight.setText(Double.toString(weight));
                    user_height.setText(Double.toString(height));
                    user_BMI.setText(Double.toString(calculate_BMI(weight, height)));

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "An error has occurred!", Toast.LENGTH_SHORT).show();
            }
        });

        update_weight_button = findViewById(R.id.update_weight_button);
        update_weight_button.setOnClickListener(view -> {

            user = FirebaseAuth.getInstance().getCurrentUser();
            reference = FirebaseDatabase.getInstance().getReference("Users");
            userID = user.getUid();
            update_weight= findViewById(R.id.update_weight);

            double pounds = Double.parseDouble(update_weight.getText().toString());

            reference.child(userID).child("weight").setValue(pounds).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(Profile.this, "Successfully updated weight!", Toast.LENGTH_SHORT).show();
                    oldWeight = Double.parseDouble(user_weight.getText().toString());
                    reference.child(userID).child("weight2").setValue(oldWeight);
                    user_weight.setText(Double.toString(pounds));
                }
            });



        });
    }

    private double calculate_BMI(double weight, double height){
        //Imperial
        return Math.round(((703 * weight) / Math.pow(height,2)) * 100.0) / 100.0;
    }

}