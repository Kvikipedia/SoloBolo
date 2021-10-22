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


public class LoginFragment extends Fragment {

    EditText email, password;
    Button login;
    boolean isEmailValid, isPasswordValid;
    TextInputLayout emailError, passError;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onPause() {
        email.setText("");
        password.setText("");
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        email = (EditText) v.findViewById(R.id.email);
        password = (EditText) v.findViewById(R.id.password);
        login = (Button) v.findViewById(R.id.login);
        emailError = (TextInputLayout) v.findViewById(R.id.emailError);
        passError = (TextInputLayout) v.findViewById(R.id.passError);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();
            }
        });


        return v;
    }

    public void SetValidation() {
        // Check for a valid email address.
        if (email.getText().toString().isEmpty()) {
            emailError.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            emailError.setError(getResources().getString(R.string.error_invalid_email));
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
        } else {
            isPasswordValid = true;
            passError.setErrorEnabled(false);
        }

        if (isEmailValid && isPasswordValid) {
            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
            if (databaseHelper.checkUser(email.getText().toString().trim(), password.getText().toString().trim())) {
                databaseHelper.addLL(email.getText().toString().trim());
                Intent intent = new Intent(getActivity(), MainMenuActivity.class);
                intent.putExtra("email", email.getText().toString().trim());
                databaseHelper.close();
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "Incorrect Credentials", Toast.LENGTH_SHORT).show();
            }
        }
    }
}