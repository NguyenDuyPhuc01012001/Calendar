package com.example.mycalendar.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.mycalendar.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AlertDirectedDialog extends BottomSheetDialogFragment {
    private String TAG ="AlertDirectedDialog";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialog);
        Log.d(TAG, "onCreateDialog: created");

        builder.setTitle("Xác nhận điều hướng")
                .setMessage("Bạn có muốn mở trình duyệt không?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OpenSurveyLink();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: Cancel Clicked");
                    }
                });
        return builder.create();
    }

    private void OpenSurveyLink() {
        String surveyURI = "https://docs.google.com/forms/d/e/1FAIpQLSdh6kB5L60Yjp-kxk08KpbpJ4-Q2I0iknZAEe75XrjjMigKSA/viewform";
        Uri uri = Uri.parse(surveyURI);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

}
