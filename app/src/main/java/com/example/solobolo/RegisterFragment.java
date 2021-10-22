package com.example.solobolo;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.solobolo.main.SectionsPagerAdapter;
import com.google.android.material.textfield.TextInputLayout;
import com.example.solobolo.DatabaseTables.User;

public class RegisterFragment extends Fragment {

    EditText name, email, password;
    Button register;
    boolean isNameValid, isEmailValid, isPasswordValid;
    TextInputLayout nameError, emailError, passError;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onPause() {
        name.setText("");
        email.setText("");
        password.setText("");
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_register, container, false);

        name = (EditText) v.findViewById(R.id.name);
        email = (EditText) v.findViewById(R.id.email);
        password = (EditText) v.findViewById(R.id.password);
        register = (Button) v.findViewById(R.id.register);
        nameError = (TextInputLayout) v.findViewById(R.id.nameError);
        emailError = (TextInputLayout) v.findViewById(R.id.emailError);
        passError = (TextInputLayout) v.findViewById(R.id.passError);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();
            }
        });

        return v;
    }

    public void SetValidation() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        // Check for a valid name.
        if (name.getText().toString().isEmpty()) {
            nameError.setError(getResources().getString(R.string.name_error));
            isNameValid = false;
        } else  {
            isNameValid = true;
            nameError.setErrorEnabled(false);
        }

        // Check for a valid email address.
        if (email.getText().toString().isEmpty()) {
            emailError.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            emailError.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
        } else if (databaseHelper.checkUser(email.getText().toString().trim())) {
            emailError.setError(getResources().getString(R.string.error_email_taken));
            isEmailValid = false;
        } else {
            isEmailValid = true;
            emailError.setErrorEnabled(false);
        }

        // Check for a valid password.
        if (password.getText().toString().isEmpty()) {
            passError.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else if (password.getText().length() < 6) {
            passError.setError(getResources().getString(R.string.error_invalid_password));
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
            passError.setErrorEnabled(false);
        }

        if (isNameValid && isEmailValid && isPasswordValid) {
            User user;
            try {
                user = new User(-1, name.getText().toString().trim(),
                        email.getText().toString().trim(), password.getText().toString().trim());
                databaseHelper.addUser(user);
                Toast.makeText(getActivity(), "Registration Successful",
                        Toast.LENGTH_SHORT).show();
            }
            catch (Exception e) {
                Toast.makeText(getActivity(),
                        "Something went wrong!\n Error Message:" + e,
                        Toast.LENGTH_SHORT).show();
            }
        }
        databaseHelper.close();
    }
}