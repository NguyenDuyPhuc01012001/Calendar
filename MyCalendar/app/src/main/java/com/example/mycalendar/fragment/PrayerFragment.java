package com.example.mycalendar.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mycalendar.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrayerFragment extends Fragment {
    TextView textView;
    ImageButton zoomIn;
    ImageButton zoomOut;
    float scale = 20;
    String response;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PrayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrayerFragment newInstance(String param1, String param2) {
        PrayerFragment fragment = new PrayerFragment();
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
        View view = inflater.inflate(R.layout.fragment_prayer, container, false);

        textView = view.findViewById(R.id.textview);
        textView.setMovementMethod(new ScrollingMovementMethod());

        zoomOut = view.findViewById(R.id.zoomOut);  //thu nhỏ
        zoomIn = view.findViewById(R.id.zoomIn);    //phóng to
        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scale -= 2;
                textView.setTextSize(scale);
            }
        });
        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scale += 2;
                textView.setTextSize(scale);
            }
        });

        //Văn khấn dịp lễ tết Nguyên Đán
        Button btnVKLOT = view.findViewById(R.id.btnVKLOT);
        Button btnVKLGT = view.findViewById(R.id.btnVKLGT);
        Button btnVKTN = view.findViewById(R.id.btnVKTN);
        Button btnVKLTM = view.findViewById(R.id.btnVKLTM);
        Button btnVKTT = view.findViewById(R.id.btnVKTT);
        Button btnVKTL = view.findViewById(R.id.btnVKTL);
        Button btnVKNM = view.findViewById(R.id.btnVKNM);

        btnVKLOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKLOT.txt");
                textView.setText(response);
            }
        });
        btnVKLGT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKLGT.txt");
                textView.setText(response);
            }
        });
        btnVKTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKTN.txt");
                textView.setText(response);
            }
        });
        btnVKLTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKLTM.txt");
                textView.setText(response);
            }
        });
        btnVKTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKTT.txt");
                textView.setText(response);
            }
        });
        btnVKTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKTL.txt");
                textView.setText(response);
            }
        });
        btnVKNM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKNM.txt");
                textView.setText(response);
            }
        });

        //Văn khấn khi đi lễ Chùa, Đình, Miếu
        Button btnVKLP = view.findViewById(R.id.btnVKLP);
        Button btnVKQABT = view.findViewById(R.id.btnVKQABT);
        Button btnVKTH = view.findViewById(R.id.btnVKTH);
        Button btnVKMTN = view.findViewById(R.id.btnVKMTN);
        Button btnVKTMLH = view.findViewById(R.id.btnVKTMLH);
        Button btnVKLDTT = view.findViewById(R.id.btnVKLDTT);
        Button btnVKBCKH = view.findViewById(R.id.btnVKBCKH);
        Button btnVKDO = view.findViewById(R.id.btnVKDO);
        Button btnVKDTH = view.findViewById(R.id.btnVKDTH);
        btnVKLP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKLP.txt");
                textView.setText(response);
            }
        });
        btnVKQABT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKQABT.txt");
                textView.setText(response);
            }
        });
        btnVKTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKTH.txt");
                textView.setText(response);
            }
        });
        btnVKMTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKMTN.txt");
                textView.setText(response);
            }
        });
        btnVKTMLH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKTMLH.txt");
                textView.setText(response);
            }
        });
        btnVKLDTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKLDTT.txt");
                textView.setText(response);
            }
        });
        btnVKBCKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKBCKH.txt");
                textView.setText(response);
            }
        });
        btnVKDO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKDO.txt");
                textView.setText(response);
            }
        });
        btnVKDTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKDTH.txt");
                textView.setText(response);
            }
        });

        //Văn khấn lễ rằm, mồng 1 & các lễ khác
        Button btnVKLNT = view.findViewById(R.id.btnVKLNT);
        Button btnVKLTMTTM = view.findViewById(R.id.btnVKLTMTTM);
        Button btnVKTHT = view.findViewById(R.id.btnVKTHT);
        Button btnVKTDN = view.findViewById(R.id.btnVKTDN);
        Button btnVKTTT = view.findViewById(R.id.btnVKTTT);
        Button btnVKRT7 = view.findViewById(R.id.btnVKRT7);
        Button btnVKN115 = view.findViewById(R.id.btnVKN115);
        Button btnVKTTTD = view.findViewById(R.id.btnVKTTTD);
        btnVKLNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKLNT.txt");
                textView.setText(response);
            }
        });
        btnVKLTMTTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKLTMTTM.txt");
                textView.setText(response);
            }
        });
        btnVKTHT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKTHT.txt");
                textView.setText(response);
            }
        });
        btnVKTDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKTDN.txt");
                textView.setText(response);
            }
        });
        btnVKN115.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKN115.txt");
                textView.setText(response);
            }
        });
        btnVKTTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKTTT.txt");
                textView.setText(response);
            }
        });
        btnVKRT7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("btnVKRT7.txt");
                textView.setText(response);
            }
        });
        btnVKTTTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKTTTD.txt");
                textView.setText(response);
            }
        });

        //Văn khấn lễ tang, giỗ tổ tiên
        Button btnVKLCT = view.findViewById(R.id.btnVKLCT);
        Button btnVKLDT = view.findViewById(R.id.btnVKLDT);
        Button btnVKLCTTK = view.findViewById(R.id.btnVKLCTTK);
        Button btnVKNGD = view.findViewById(R.id.btnVKNGD);
        Button btnVKNGH = view.findViewById(R.id.btnVKNGH);
        Button btnVKNGT = view.findViewById(R.id.btnVKNGT);
        Button btnVKNCG = view.findViewById(R.id.btnVKNCG);
        Button btnVKGT = view.findViewById(R.id.btnVKGT);
        btnVKLCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKLCT.txt");
                textView.setText(response);
            }
        });
        btnVKLDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKLDT.txt");
                textView.setText(response);
            }
        });
        btnVKLCTTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKLCTTK.txt");
                textView.setText(response);
            }
        });
        btnVKNGD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKNGD.txt");
                textView.setText(response);
            }
        });
        btnVKNGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKNGH.txt");
                textView.setText(response);
            }
        });
        btnVKNGT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKNGT.txt");
                textView.setText(response);
            }
        });
        btnVKNCG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKNCG.txt");
                textView.setText(response);
            }
        });
        btnVKGT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKGT.txt");
                textView.setText(response);
            }
        });

        //Văn khấn lễ cưới hỏi & sinh con
        Button btnVKCBMTN = view.findViewById(R.id.btnVKCBMTN);
        Button btnVKNLCH = view.findViewById(R.id.btnVKNLCH);
        btnVKCBMTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKCBMTN.txt");
                textView.setText(response);
            }
        });
        btnVKNLCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKNLCH.txt");
                textView.setText(response);
            }
        });

        //Văn khấn làm nhà, chuyển nhà, tân gia
        Button btnVKCNSN = view.findViewById(R.id.btnVKCNSN);
        Button btnVKNT = view.findViewById(R.id.btnVKNT);
        Button btnVKMTG = view.findViewById(R.id.btnVKMTG);
        btnVKCNSN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKCNSN.txt");
                textView.setText(response);
            }
        });
        btnVKNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKNT.txt");
                textView.setText(response);
            }
        });
        btnVKMTG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKMTG.txt");
                textView.setText(response);
            }
        });

        //Các bài văn khấn khác
        Button btnVKLKT = view.findViewById(R.id.btnVKLKT);
        Button btnVKLMT = view.findViewById(R.id.btnVKLMT);
        btnVKLKT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKLKT.txt");
                textView.setText(response);
            }
        });
        btnVKLMT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loadTextFileFromAsset("VKLMT.txt");
                textView.setText(response);
            }
        });

        return view;
    }

    public String loadTextFileFromAsset(String filename) {
        String content = null;
        try {

            InputStream is = getContext().getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            content = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return content;
    }
}