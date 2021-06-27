package com.example.mycalendar.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mycalendar.R;
import com.example.mycalendar.database.DatabaseOpenHelper;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryEventFragment extends Fragment {

    EditText date;
    EditText month;
    public ListView listEvent;
    DatabaseOpenHelper dbHelper;
    Button vietHistorybtn;
    Button worldHistorybtn;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryEventFragment newInstance(String param1, String param2) {
        HistoryEventFragment fragment = new HistoryEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_event, container, false);

        date = view.findViewById(R.id.date);
        month = view.findViewById(R.id.month);
        listEvent = view.findViewById(R.id.listEvent);

        vietHistorybtn = view.findViewById(R.id.vietHistorybtn);
        worldHistorybtn = view.findViewById(R.id.worldHistorybtn);
        worldHistorybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    dbHelper = new DatabaseOpenHelper(getContext());
                    dbHelper.CreateDatabase();
                    dbHelper.openDatabase();
                    String d = date.getText().toString();
                    String m = month.getText().toString();
                    List<String> Events = dbHelper.WORLDQUERYHISTORY(d, m);
                    listEvent.setAdapter(new ArrayAdapter<String>(view.getContext(),
                            android.R.layout.simple_list_item_1 , Events));
                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ ngày và tháng!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        vietHistorybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    dbHelper = new DatabaseOpenHelper(getContext());
                    dbHelper.CreateDatabase();
                    dbHelper.openDatabase();
                    String d = date.getText().toString();
                    String m = month.getText().toString();
                    List<String> Events = dbHelper.VIETQUERYHISTORY(d, m);
                    listEvent.setAdapter(new ArrayAdapter<String>(view.getContext(),
                            android.R.layout.simple_list_item_1 , Events));
                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ ngày và tháng!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}