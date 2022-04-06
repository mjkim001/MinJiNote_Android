package com.example.androidtest04;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SubFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_sub, container, false);
        Button button = rootView.findViewById(R.id.buttonSub);
        TextView editTextNum2 = rootView.findViewById(R.id.editTextNum2); //sub애서 입력받은 계산값

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextNum2.getText().toString().equals("13"))//정답을 맞췄을 경우
                    ((MainActivity) getActivity()).plusScore();
                ((MainActivity) getActivity()).changeToResFragment();
            }
        });
        return rootView;
    }

}