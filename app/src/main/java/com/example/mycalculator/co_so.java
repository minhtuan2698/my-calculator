package com.example.mycalculator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class co_so extends Fragment {
    String textMath="";
    TextView dec,bin,hex,oct,nhap;
    Button num0,num1,num2,num3,num4,num5,num6,num7,num8,num9,clearall,clear;

    void tinhkq(String s){
        if(textMath.length()>0){
            try {
                int a = Integer.parseInt(s);
                dec.setText("" + a);
                bin.setText("" +Integer.toBinaryString(a));
                hex.setText("" + Integer.toHexString(a));
                oct.setText(""+ Integer.toOctalString(a));
            }
            catch (Exception e){
                error();
                Toast.makeText(getContext(), "Số to quá !", Toast.LENGTH_SHORT).show();
            }
        } else error();

    }
    public void error(){
        dec.setText("Error !");
        bin.setText("Error !");
        hex.setText("Error !");
        oct.setText("Error !");
        textMath = "";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_coso, container, false);

        dec = view.findViewById(R.id.dec);
        bin = view.findViewById(R.id.bin);
        hex = view.findViewById(R.id.hex);
        oct = view.findViewById(R.id.oct);
        nhap = view.findViewById(R.id.nhap);
        num0 = view.findViewById(R.id.num0);
        num1 = view.findViewById(R.id.num1);
        num2 = view.findViewById(R.id.num2);
        num3 = view.findViewById(R.id.num3);
        num4 = view.findViewById(R.id.num4);
        num5 = view.findViewById(R.id.num5);
        num6 = view.findViewById(R.id.num6);
        num7 = view.findViewById(R.id.num7);
        num8 = view.findViewById(R.id.num8);
        num9 = view.findViewById(R.id.num9);
        clearall = view.findViewById(R.id.clearall);
        clear = view.findViewById(R.id.clear);



        num0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "0";
                nhap.setText(textMath);
            }
        });
        num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "1";
                nhap.setText(textMath);
            }
        });
        num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "2";
                nhap.setText(textMath);
            }
        });
        num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "3";
                nhap.setText(textMath);
            }
        });
        num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "4";
                nhap.setText(textMath);
            }
        });
        num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "5";
                nhap.setText(textMath);
            }
        });
        num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "6";
                nhap.setText(textMath);
            }
        });
        num7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "7";
                nhap.setText(textMath);
            }
        });
        num8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "8";
                nhap.setText(textMath);
            }
        });
        num9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "9";
                nhap.setText(textMath);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!textMath.isEmpty()){
                    textMath = textMath.substring(0, textMath.length()-1);
                }
                nhap.setText(textMath);
            }
        });

        clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath="";
                nhap.setText(textMath);
            }
        });


        nhap.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tinhkq(textMath);
            }
        });


        return view;

    }
}