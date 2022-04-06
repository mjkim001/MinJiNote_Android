package com.example.androidtest04;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        Button button = rootView.findViewById(R.id.buttonMain); //버튼
        final EditText editTextNum1 = rootView.findViewById(R.id.editTextNum1); //입력한 답

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextNum1.getText().toString().equals("3")) //정답을 맞췄을 경우
                    ((MainActivity) getActivity()).plusScore();
                ((MainActivity) getActivity()).changeToSubFragment();
            }
        });
        return rootView;
    }
}