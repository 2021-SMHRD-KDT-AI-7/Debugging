package com.example.project_02;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class GuideFragment_6 extends Fragment {
    Button btn_start;

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            ViewGroup rootView = (ViewGroup) inflater.inflate(
                    R.layout.fragment_guide_6, container, false);

                btn_start = rootView.findViewById(R.id.btn_start);

                btn_start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(),frontActivity.class);
                        startActivity(intent);
                    }
                });
            return rootView;
    }
}