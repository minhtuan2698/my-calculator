package com.example.mycalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Arrays;
import java.util.Stack;

class InfixToPostfix{
    boolean check_error = false;

    public String chuanhoaso(double num){
        int a = (int)num;
        if (a == num)
            return Integer.toString(a);
        else return Double.toString(num);
    }

    public boolean isCharPi(char c){
        if (c == 'π') return true;
        else return false;
    }

    public boolean isNumPi(double num){
        if (num == Math.PI) return true;
        else return false;
    }

    public boolean isNum(char c){
        if (Character.isDigit(c) || isCharPi(c)) return true;
        else return false;
    }

    public String NumToString(double num){
        if (isNumPi(num)) return "π";
        else return chuanhoaso(num);
    }

    public boolean isOperator(char c){ 	// kiem tra xem co phai toan tu
        char operator[] = { '+', '-', '*', '/', '^', '~', 's', 'c', 't', '@', '!', '%', ')', '(','r'};
        Arrays.sort(operator);
        if (Arrays.binarySearch(operator, c) > -1)
            return true;
        else return false;
    }
    public int priority(char c){
        switch (c) {
            case '+' : case '-' : return 1;
            case '*' : case '/' : return 2;
            case '~' : return 3;
            case '@' : case '!' : case '^' : case 'r' : return 4;
            case 's' : case 'c' : case 't' : return 5;
        }
        return 0;
    }

    public boolean isOneMath(char c){
        char operator[] = { 's', 'c', 't', '@', '('};
        Arrays.sort(operator);
        if (Arrays.binarySearch(operator, c) > -1)
            return true;
        else return false;
    }



    public String standardize(String s){
        String s1 = "";
        s = s.trim();
        s = s.replaceAll("\\s+"," ");
        int open = 0, close = 0;
        for (int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if (c == '(') open++;
            if (c == ')') close++;
        }
        for (int i=0; i< (open - close); i++)
            s += ')';
        for (int i=0; i<s.length(); i++){
            if (i>0 && isOneMath(s.charAt(i)) && (s.charAt(i-1) == ')' || isNum(s.charAt(i-1)))) s1 = s1 + "*";

            if (i==0&&s.charAt(i)=='-') {
                s1 = s1 + "~"; // check so am
            }
            else if (i>0 && (isNum(s.charAt(i-1)) || s.charAt(i-1) == ')') && isCharPi(s.charAt(i))) s1 = s1 + "*" + s.charAt(i);
            else s1 = s1 + s.charAt(i);

        }
        return s1;
    }

    public String[] processString(String sMath){
        String s1 = "", elementMath[] = null;
        sMath = standardize(sMath);
        InfixToPostfix  ITP = new InfixToPostfix();
        for (int i=0; i<sMath.length(); i++){
            char c = sMath.charAt(i);
            if (i<sMath.length()-1 && isCharPi(c) && !ITP.isOperator(sMath.charAt(i+1))){
                check_error = true;
                return null;
            }
            else
            if (!ITP.isOperator(c))
                s1 = s1 + c;
            else s1 = s1 + " " + c + " ";
        }
        s1 = s1.trim();
        s1 = s1.replaceAll("\\s+"," ");
        elementMath = s1.split(" ");
        return elementMath;
    }

    public String[] postfix(String[] elementMath){
        InfixToPostfix  ITP = new InfixToPostfix();
        String s1 = "", E[];
        Stack<String> S = new Stack<String>();
        for (int i=0; i<elementMath.length; i++){
            char c = elementMath[i].charAt(0);

            if (!ITP.isOperator(c))
                s1 = s1 + elementMath[i] + " ";
            else{
                if (c == '(') S.push(elementMath[i]);
                else{
                    if (c == ')'){
                        char c1;
                        do{
                            c1 = S.peek().charAt(0);
                            if (c1 != '(') s1 = s1 + S.peek() + " ";
                            S.pop();
                        }while (c1 != '(');
                    }
                    else{

                        while (!S.isEmpty() && ITP.priority(S.peek().charAt(0)) >= ITP.priority(c))
                            s1 = s1 + S.pop() + " ";
                        S.push(elementMath[i]); //
                    }
                }
            }
        }
        while (!S.isEmpty()) s1 = s1 + S.pop() + " ";
        E = s1.split(" ");
        return E;
    }



    public String valueMath(String[] elementMath){
        Stack <Double> S = new Stack<Double>();
        InfixToPostfix  ITP = new InfixToPostfix();
        double num = 0.0;
        for (int i=0; i<elementMath.length; i++){
            char c = elementMath[i].charAt(0);
            if (isCharPi(c)) S.push(Math.PI);
            else{
                if (!ITP.isOperator(c)) S.push(Double.parseDouble(elementMath[i]));
                else{

                    double num1 = S.pop();
                    switch (c) {
                        case '~' : num = -num1; break;
                        case 's' : num = Math.sin(num1); break;
                        case 'c' : num = Math.cos(num1); break;
                        case 't' : num = Math.tan(num1); break;
                        case '%' : num = num1/100; break;
                        case 'r' : {
                            if(num1!=0) num = 1/num1;
                            else check_error = true;
                        } break;
                        case '@' : {
                            if (num1 >=0){
                                num = Math.sqrt(num1); break;
                            }
                            else check_error = true;
                        }
                        case '!' : {
                            if (num1 >= 0 && (int)num1 == num1){
                                num = 1;
                                for (int j=1; j<=(int)num1; j++)
                                    num = num * j;
                            }
                            else check_error = true;
                        }
                        default : break;
                    }
                    if (!S.empty()){
                        double num2 = S.peek();
                        switch (c) {
                            //-----------------------
                            case '+' : num = num2 + num1; S.pop(); break;
                            case '-' : num = num2 - num1; S.pop(); break;
                            case '*' : num = num2 * num1; S.pop(); break;
                            case '/' : {
                                if (num1 != 0) num = num2 / num1;
                                else check_error = true;
                                S.pop(); break;
                            }
                            case '^' : num = Math.pow(num2, num1); S.pop(); break;
                        }
                    }
                    S.push(num);
                }
            }
        }
        return NumToString(S.pop());
    }
}
public class calculator extends Fragment {


    String textMath = "", textAns = "0", screenTextMath = "";
    boolean check_phay = false;

    private EditText nhap;
    private TextView kq;
    private Button num0;
    private Button num1;
    private Button num2;
    private Button num3;
    private Button num4;
    private Button num5;
    private Button num6;
    private Button num7;
    private Button num8;
    private Button num9;
    private Button cong;
    private Button tru;
    private Button nhan;
    private Button chia;
    private Button bang;
    private Button clear;
    private Button clearall;
    private Button phay;
    private Button ngoactrai;
    private Button ngoacphai;
    private Button sin;
    private Button cos;
    private Button tan;
    private Button giaithua;
    private Button percent;
    private Button can;
    private Button mu;
    private Button reserve;
    private Button pi;




    public void error(){
        kq.setText("Math Error !");
        textAns = textMath = screenTextMath="";
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.calculator, container, false);

        nhap = view.findViewById(R.id.nhap);
        kq = view.findViewById(R.id.kq);
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

        bang = view.findViewById(R.id.bang);

        phay = view.findViewById(R.id.phay);

        cong = view.findViewById(R.id.cong);

        tru = view.findViewById(R.id.tru);

        nhan = view.findViewById(R.id.nhan);

        chia = view.findViewById(R.id.chia);

        sin = view.findViewById(R.id.sin);

        cos = view.findViewById(R.id.cos);

        tan = view.findViewById(R.id.tan);

        giaithua = view.findViewById(R.id.giaithua);

        can = view.findViewById(R.id.can);

        mu = view.findViewById(R.id.mu);

        percent = view.findViewById(R.id.percent);

        reserve = view.findViewById(R.id.reserve);

        pi = view.findViewById(R.id.pi);

        ngoactrai = view.findViewById(R.id.ngoactrai);

        ngoacphai = view.findViewById(R.id.ngoacphai);

        clear = view.findViewById(R.id.clear);

        clearall = view.findViewById(R.id.clearall);



        num0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "0";
                screenTextMath+="0";
                nhap.setText(screenTextMath);
            }
        });
        num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "1";
                screenTextMath+="1";
                nhap.setText(screenTextMath);
            }
        });
        num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "2";
                screenTextMath+="2";
                nhap.setText(screenTextMath);
            }
        });
        num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "3";
                screenTextMath+="3";
                nhap.setText(screenTextMath);
            }
        });
        num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "4";
                screenTextMath+="4";
                nhap.setText(screenTextMath);
            }
        });
        num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "5";
                screenTextMath+="5";
                nhap.setText(screenTextMath);
            }
        });
        num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "6";
                screenTextMath+="6";
                nhap.setText(screenTextMath);
            }
        });
        num7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "7";
                screenTextMath+="7";
                nhap.setText(screenTextMath);
            }
        });
        num8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "8";
                screenTextMath+="8";
                nhap.setText(screenTextMath);
            }
        });
        num9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "9";
                screenTextMath+="9";
                nhap.setText(screenTextMath);
            }
        });
        phay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (screenTextMath.length()<48) {
                    if (textMath.isEmpty()) {
                        screenTextMath=textMath = "0.";
                        check_phay = true;
                    }
                    if(check_phay==false){
                        textMath += ".";
                        screenTextMath += ".";
                    }
                }
                nhap.setText(screenTextMath);

            }
        });

        cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "+";
                screenTextMath += "+";
                nhap.setText(screenTextMath);
            }
        });
        tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "-";
                screenTextMath += "-";
                nhap.setText(screenTextMath);
            }
        });
        nhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "*";
                screenTextMath += "*";
                nhap.setText(screenTextMath);
            }
        });
        chia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "/";
                screenTextMath += "/";
                nhap.setText(screenTextMath);
            }
        });

        ngoactrai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textMath.length()<48) {
                    textMath += "(";
                    screenTextMath +="(";
                }
                nhap.setText(screenTextMath);
            }
        });
        ngoacphai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textMath.length()<48) {
                    textMath += ")";
                    screenTextMath +=")";
                }
                nhap.setText(screenTextMath);
            }
        });

        mu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (screenTextMath.length()<48 && screenTextMath.length() > 0) {
                    textMath += "^(";
                    screenTextMath += "^(";
                }
                nhap.setText(screenTextMath);
            }
        });


        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (screenTextMath.length()<48) {
                    textMath += "s(";
                    screenTextMath +="Sin(";
                }
                nhap.setText(screenTextMath);
            }
        });

        cos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (screenTextMath.length()<48) {
                    textMath += "c(";
                    screenTextMath +="Cos(";
                }
                nhap.setText(screenTextMath);
            }
        });

        tan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (screenTextMath.length()<48) {
                    textMath += "t(";
                    screenTextMath +="Tan(";
                }
                nhap.setText(screenTextMath);
            }
        });

        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (screenTextMath.length()<48) {
                    textMath += "@";
                    screenTextMath +="√";
                }
                nhap.setText(screenTextMath);
            }
        });

        giaithua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (screenTextMath.length()<48) {
                    textMath += "!";
                    screenTextMath +="!";
                }
                nhap.setText(screenTextMath);
            }
        });

        pi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (screenTextMath.length()<48) {
                    textMath += "π";
                    screenTextMath += "π";
                }
                nhap.setText(screenTextMath);
            }
        });

        percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (screenTextMath.length()<48) {
                    textMath += "%";
                    screenTextMath += "%";
                }
                nhap.setText(screenTextMath);
            }
        });

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (screenTextMath.length()<48) {
                    if(screenTextMath.length()==0)screenTextMath="0";
                    textMath += "r";
                    screenTextMath = "1/" + screenTextMath;
                }
                nhap.setText(screenTextMath);
            }
        });



        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nhap.length()>0){
                    try {
                        if(!textMath.isEmpty()&&!screenTextMath.isEmpty()){
                            textMath = textMath.substring(0, textMath.length()-1);
                            screenTextMath = screenTextMath.substring(0,screenTextMath.length()-1);
                        }
                        nhap.setText(screenTextMath);
                    }
                    catch (Exception e){
                        error();
                    }
                }
            }
        });



        clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath = "";
                check_phay=false;
                screenTextMath = "";
                textAns = "0";
                kq.setText(textAns);
                nhap.setText("");
            }
        });



        bang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] b = null;
                InfixToPostfix ITP = new InfixToPostfix();

                if(textMath.length()>0){
                    try {
                        if (!ITP.check_error) b = ITP.processString(textMath);
                        if (!ITP.check_error) b = ITP.postfix(b);
                        if(!ITP.check_error) textAns = ITP.valueMath(b);
                        kq.setText(textAns);
                        textMath = screenTextMath = textAns;
                    }
                    catch (Exception e){
                        error();
                    }
                    if(ITP.check_error) error();
                }
            }
        });


        return view;
    }
}
