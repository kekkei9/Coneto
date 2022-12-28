package com.example.coneto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.coneto.Common.Common;
import com.example.coneto.Model.UserModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.edit_first_name)
    TextInputEditText edit_first_name;

    @BindView(R.id.edit_last_name)
    TextInputEditText edit_last_name;

    @BindView(R.id.edit_phone_number)
    TextInputEditText edit_phone_number;

    @BindView(R.id.edit_birthday)
    TextInputEditText edit_birthday;

    @BindView(R.id.edit_bio)
    TextInputEditText edit_bio;

    @BindView(R.id.register_btn)
    Button register_btn;

    FirebaseDatabase database;
    DatabaseReference userRef;

    MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker().build();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy");
    Calendar calendar = Calendar.getInstance();
    boolean isSelectedBirthday = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        setDefaultData();
    }

    private void setDefaultData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        edit_phone_number.setText(user.getPhoneNumber());
        edit_phone_number.setEnabled(false);

        edit_birthday.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                materialDatePicker.show(getSupportFragmentManager(), materialDatePicker.toString());
            }
        });

        register_btn.setOnClickListener(view -> {
            if (!isSelectedBirthday) {
                Toast.makeText(this, "Please enter birthday", Toast.LENGTH_SHORT).show();
                return;
            }

            UserModel userModel = new UserModel();

            userModel.setFirstName(edit_first_name.getText().toString());
            userModel.setLastName(edit_last_name.getText().toString());
            userModel.setBio(edit_bio.getText().toString());
            userModel.setPhone(edit_phone_number.getText().toString());
            userModel.setBirthday(calendar.getTimeInMillis());
            userModel.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());

            userRef.child(userModel.getUid())
                    .setValue(userModel)
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    })
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show();
                        Common.currentUser = userModel;
                        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                        finish();
                    });
        });
    }

    private void init() {
        ButterKnife.bind(this);
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(Common.USER_REFERENCES);
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            calendar.setTimeInMillis(selection);
            edit_birthday.setText(simpleDateFormat.format(selection));
            isSelectedBirthday = true;
        });
    }
}