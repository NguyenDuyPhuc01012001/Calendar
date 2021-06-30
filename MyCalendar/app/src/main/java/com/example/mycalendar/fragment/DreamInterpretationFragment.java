package com.example.mycalendar.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.mycalendar.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DreamInterpretationFragment extends Fragment {
    private String TAG = "DreamInterpretationFragment";
    Spinner alphabetPicker;
    Spinner dreamPicker;
    ArrayAdapter<CharSequence> dreamPickerArrayAdapter;
    String alphabetValue;
    TextView dreamDescription;
    String folderName = "dreaminterpretation";
    String dreamValue;
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_dream_interpretation,container,false);
        Mapping(view);
        alphabetPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                alphabetValue = parent.getItemAtPosition(position).toString();
                SetDreamPickerValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dreamPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dreamValue = parent.getItemAtPosition(position).toString();
                LoadDreamDescription();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SetAlphabetPickerValue();
        return view;
    }

    private void LoadDreamDescription() {
        String fileLocation = folderName+"/"+alphabetValue+"/"+dreamValue+".txt";
        Log.d(TAG, "LoadDreamDescription: "+fileLocation);
        try {
            InputStream inputStream = getContext().getAssets().open(fileLocation);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String data = "";
            if(inputStream!=null)
            {
                while((data= bufferedReader.readLine())!= null)
                {
                    stringBuffer.append(data+"\n");
                }
                dreamDescription.setText(stringBuffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void SetDreamPickerValue() {
        SetDreamPickerArrayAdapter();
        dreamPicker.setAdapter(dreamPickerArrayAdapter);
    }

    private void SetAlphabetPickerValue() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.alphabet_picker_array,
                R.layout.spinner_picker_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        alphabetPicker.setAdapter(adapter);
    }

    private void Mapping(View view) {
        alphabetPicker = view.findViewById(R.id.alphabetPicker);
        dreamPicker = view.findViewById(R.id.dreamPicker);
        dreamDescription = view.findViewById(R.id.dreamDescription);
    }
    private void SetDreamPickerArrayAdapter()
    {
        switch (alphabetValue) {
            case "A":
            dreamPickerArrayAdapter = ArrayAdapter.createFromResource(this.getContext(),
                    R.array.A,
                    R.layout.spinner_picker_item);
            break;
            case "B":
                dreamPickerArrayAdapter = ArrayAdapter.createFromResource(this.getContext(),
                        R.array.B,
                        R.layout.spinner_picker_item);
                break;
            case "C":
                dreamPickerArrayAdapter = ArrayAdapter.createFromResource(this.getContext(),
                        R.array.C,
                        R.layout.spinner_picker_item);
                break;
            case "D":
                dreamPickerArrayAdapter = ArrayAdapter.createFromResource(this.getContext(),
                        R.array.D,
                        R.layout.spinner_picker_item);
                break;
            case "E":
                dreamPickerArrayAdapter = ArrayAdapter.createFromResource(this.getContext(),
                        R.array.E,
                        R.layout.spinner_picker_item);
                break;
            case "G":
                dreamPickerArrayAdapter = ArrayAdapter.createFromResource(this.getContext(),
                        R.array.G,
                        R.layout.spinner_picker_item);
                break;
            case "H":
                dreamPickerArrayAdapter = ArrayAdapter.createFromResource(this.getContext(),
                        R.array.H,
                        R.layout.spinner_picker_item);
                break;
            case "K":
                dreamPickerArrayAdapter = ArrayAdapter.createFromResource(this.getContext(),
                        R.array.K,
                        R.layout.spinner_picker_item);
                break;
            case "L":
                dreamPickerArrayAdapter = ArrayAdapter.createFromResource(this.getContext(),
                        R.array.L,
                        R.layout.spinner_picker_item);
                break;
            case "M":
                dreamPickerArrayAdapter = ArrayAdapter.createFromResource(this.getContext(),
                        R.array.M,
                        R.layout.spinner_picker_item);
                break;
            case "N":
                dreamPickerArrayAdapter = ArrayAdapter.createFromResource(this.getContext(),
                        R.array.N,
                        R.layout.spinner_picker_item);
                break;
            case "O":
                dreamPickerArrayAdapter = ArrayAdapter.createFromResource(this.getContext(),
                        R.array.O,
                        R.layout.spinner_picker_item);
                break;
            case "P":
                dreamPickerArrayAdapter = ArrayAdapter.createFromResource(this.getContext(),
                        R.array.P,
                        R.layout.spinner_picker_item);
                break;
            case "Q":
                dreamPickerArrayAdapter = ArrayAdapter.createFromResource(this.getContext(),
                        R.array.Q,
                        R.layout.spinner_picker_item);
                break;
            case "R":
                dreamPickerArrayAdapter = ArrayAdapter.createFromResource(this.getContext(),
                        R.array.R,
                        R.layout.spinner_picker_item);
                break;
            case "S":
                dreamPickerArrayAdapter = ArrayAdapter.createFromResource(this.getContext(),
                        R.array.S,
                        R.layout.spinner_picker_item);
                break;
            case "T":
                dreamPickerArrayAdapter = ArrayAdapter.createFromResource(this.getContext(),
                        R.array.T,
                        R.layout.spinner_picker_item);
                break;
            case "U":
                dreamPickerArrayAdapter = ArrayAdapter.createFromResource(this.getContext(),
                        R.array.U,
                        R.layout.spinner_picker_item);
                break;
            case "V":
                dreamPickerArrayAdapter = ArrayAdapter.createFromResource(this.getContext(),
                        R.array.V,
                        R.layout.spinner_picker_item);
                break;
            case "X":
                dreamPickerArrayAdapter = ArrayAdapter.createFromResource(this.getContext(),
                        R.array.X,
                        R.layout.spinner_picker_item);
                break;
        }
        dreamPickerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

    }
}
