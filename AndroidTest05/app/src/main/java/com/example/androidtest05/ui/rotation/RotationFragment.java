package com.example.androidtest05.ui.rotation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidtest05.databinding.FragmentRotationBinding;

public class RotationFragment extends Fragment {

    private FragmentRotationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRotationBinding.inflate(inflater, container, false);
        TextView textView = binding.textView;
        EditText editText = binding.editText;

        RotationViewModel rotationViewModel =
                new ViewModelProvider(this).get(RotationViewModel.class);
        View root = binding.getRoot();

        binding.buttonRotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(editText.getText());
                rotationViewModel.setText(editText.getText().toString());
            }
        });

        rotationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}