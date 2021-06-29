package com.example.mycalendar.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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


        //Văn khấn dịp lễ tết Nguyên Đán
        Button btnVKLOT = view.findViewById(R.id.btnVKLOT);
        Button btnVKLGT = view.findViewById(R.id.btnVKLGT);
        Button btnVKTN = view.findViewById(R.id.btnVKTN);
        Button btnVKLTM = view.findViewById(R.id.btnVKLTM);
        Button btnVKTT = view.findViewById(R.id.btnVKTT);
        Button btnVKTL = view.findViewById(R.id.btnVKTL);
        Button btnVKNM = view.findViewById(R.id.btnVKNM);

        btnVKLOT.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKLOT.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKLGT.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKLGT.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKTN.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKTN.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKLTM.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKLTM.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKTT.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKTT.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKTL.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKTL.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKNM.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKNM.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
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
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKLP.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKQABT.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKQABT.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKTH.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKTH.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKMTN.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKMTN.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKTMLH.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKTMLH.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKLDTT.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKLDTT.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKBCKH.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKBCKH.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKDO.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKDO.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKDTH.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKDTH.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
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
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKLNT.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKLTMTTM.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKLTMTTM.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKTHT.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKTHT.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKTDN.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKTDN.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKN115.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKN115.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKTTT.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKTTT.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKRT7.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKRT7.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKTTTD.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKTTTD.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
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
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKLCT.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKLDT.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKLDT.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKLCTTK.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKLCTTK.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKNGD.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKNGD.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKNGH.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKNGH.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKNGT.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKNGT.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKNCG.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKNCG.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKGT.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKGT.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });

        //Văn khấn lễ cưới hỏi & sinh con
        Button btnVKCBMTN = view.findViewById(R.id.btnVKCBMTN);
        Button btnVKNLCH = view.findViewById(R.id.btnVKNLCH);
        btnVKCBMTN.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKCBMTN.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKNLCH.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKNLCH.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });

        //Văn khấn làm nhà, chuyển nhà, tân gia
        Button btnVKCNSN = view.findViewById(R.id.btnVKCNSN);
        Button btnVKNT = view.findViewById(R.id.btnVKNT);
        Button btnVKMTG = view.findViewById(R.id.btnVKMTG);
        btnVKCNSN.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKCNSN.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKNT.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKNT.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKMTG.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKMTG.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });

        //Các bài văn khấn khác
        Button btnVKLKT = view.findViewById(R.id.btnVKLKT);
        Button btnVKLMT = view.findViewById(R.id.btnVKLMT);
        btnVKLKT.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKLKT.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
            }
        });
        btnVKLMT.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new PrayerContentFragment();
                replaceFragment(fragment);
                response = loadTextFileFromAsset("prayertext/VKLMT.txt");
                Bundle bundle = new Bundle();
                bundle.putString("key", response);
                fragment.setArguments(bundle);
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

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}