package com.datechnologies.androidtest.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.datechnologies.androidtest.MainActivity;

public class LoginDialog extends AppCompatDialogFragment {
    public String codeD, messageD;


    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(this.codeD)
                .setMessage(messageD)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //here goes the intent to MainActivity
                        if(codeD.equals("Success")){
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
        return builder.create();
    }
}
