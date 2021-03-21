package com.example.mycalculator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Stack;
class InfixToPostfix1 {

    boolean check_error = false;

    public String chuanhoaso(double num) {
        int a = (int) num;
        if (a == num) return Integer.toString(a);
        else return Double.toString(num);
    }

    public boolean isNum(char c) {
        if (Character.isDigit(c)) return true;
        else return false;
    }

    public String numtostring(double num) {
        return chuanhoaso(num);
    }

    public boolean isOperator(char c) {
        char operator[] = {'+', '-', '*', '/', '(', ')', '~'};
        Arrays.sort(operator);
        if (Arrays.binarySearch(operator, c) > -1)
            return true;
        else return false;
    }


    public int priority(char c){
        if (c == '+' || c == '-') return 1;
        else if (c == '*' || c == '/') return 2;
        else if (c=='~') return 3;
        else return 0;
    }

    public String standardize(String s) {
        String s1 = "";
        s = s.trim();
        s = s.replaceAll("\\s+", "");
        int open = 0, close = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') open++;
            if (c == ')') close++;
        }
        for (int i=0; i< (open - close); i++)
            s += ')';
        for (int i=0; i<s.length(); i++){
            if (i>0 && (s.charAt(i)=='(') && (s.charAt(i-1) == ')' || isNum(s.charAt(i-1)))) s1 = s1 + "*";

            if (i==0&&s.charAt(i)=='-') {
                s1 = s1 + "~";
            }
            else s1 = s1 + s.charAt(i);
        }
        return s1;
    }

    public String[] processString(String sMath) {
        String s1 = "", elementMath[] = null;

        sMath = standardize(sMath);

        InfixToPostfix1 ITP = new InfixToPostfix1();
        for (int i = 0; i < sMath.length(); i++) {
            char c = sMath.charAt(i);

            if (!ITP.isOperator(c))
                s1 = s1 + c;
            else s1 = s1 + " " + c + " ";
        }
        s1 = s1.trim();
        s1 = s1.replaceAll("\\s+", " ");
        elementMath = s1.split(" ");
        return elementMath;
    }

    public String[] postfix(String[] elementMath) {
        InfixToPostfix1 ITP = new InfixToPostfix1();
        String s1 = "", E[];
        Stack<String> S = new Stack<String>();
        for (int i = 0; i < elementMath.length; i++) {
            char c = elementMath[i].charAt(0);
            if (!ITP.isOperator(c))
                s1 = s1 + elementMath[i] + " ";
            else {
                if (c == '(') S.push(elementMath[i]);
                else {
                    if (c == ')') {
                        char c1;
                        do {
                            c1 = S.peek().charAt(0);
                            if (c1 != '(') s1 = s1 + S.peek() + " ";
                            S.pop();
                        } while (c1 != '(');
                    } else {
                        while (!S.isEmpty() && ITP.priority(S.peek().charAt(0)) >= ITP.priority(c))
                            s1 = s1 + S.pop() + " ";
                        S.push(elementMath[i]);
                    }
                }
            }
        }
        while (!S.isEmpty())
            s1 = s1 + S.pop() + " ";
        E = s1.split(" ");
        return E;
    }

    public String valueMath(String[] elementMath) {
        Stack<Double> S = new Stack<Double>();
        InfixToPostfix1 ITP = new InfixToPostfix1();
        double num = 0.0;
        for (int i = 0; i < elementMath.length; i++) {

            char c = elementMath[i].charAt(0);
            if (!ITP.isOperator(c)) S.push(Double.parseDouble(elementMath[i]));
            else {

                double num1 = S.pop();
                if (c == '~') num = -num1;
                if (!S.empty()) {
                    double num2 = S.peek();
                    switch (c) {
                        //-----------------------
                        case '+':
                            num = num2 + num1;
                            S.pop();
                            break;
                        case '-':
                            num = num2 - num1;
                            S.pop();
                            break;
                        case '*':
                            num = num2 * num1;
                            S.pop();
                            break;
                        case '/': {
                            if (num1 != 0) num = num2 / num1;
                            else check_error = true;
                            S.pop();
                            break;
                        }
                    }
                }
                S.push(num);
            }
        }

        return numtostring(S.pop());
    }
}
public class cal_basic extends Fragment {


    String textMath = "", textAns = "0";
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


    public void error(){
        kq.setText("Math Error !");
        textAns = textMath = "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cal_basic, container, false);

        //độc xml và chuyển đổi các thuộc tính của nó thành 1 view trong java code
        //1: file xml muốn chuyển đổi thành view
        //2: là viewgroup nơi mà 1  có thể được nhúng vào, LayoutInflater sẽ chuyển đổi 1 thành View và sử dụng các thuộc tính phù hợp với ViewGroup parrent.
        //3: attachToRoot, attachToRoot=true thì sau khi quá trình chuyển đổi xml file(resource) thành View hoàn thành thì nó sẽ nhúng View đó vào ViewGroup parent (RIGHT NOW) ,
        // khi attachToRoot = false thì nó chỉ chuyển đổi xml file(resource) thành View trong java mà không thêm ngay vào ViewGroup(NOT NOW)

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

        ngoactrai = view.findViewById(R.id.ngoactrai);

        ngoacphai = view.findViewById(R.id.ngoacphai);

        clear = view.findViewById(R.id.clear);

        clearall = view.findViewById(R.id.clearall);


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
        phay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(textMath.isEmpty()){
                    textMath="0.";
                    check_phay = true;
                }
                if(check_phay==false){
                    textMath += ".";
                }
                nhap.setText(textMath);

            }
        });

        cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "+";
                nhap.setText(textMath);
            }
        });
        tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "-";
                nhap.setText(textMath);
            }
        });
        nhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "*";
                nhap.setText(textMath);
            }
        });
        chia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "/";
                nhap.setText(textMath);
            }
        });

        ngoactrai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += "(";
                nhap.setText(textMath);
            }
        });
        ngoacphai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMath += ")";
                nhap.setText(textMath);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nhap.length()>0){
                    try {
                        if(!textMath.isEmpty()){
                            textMath = textMath.substring(0, textMath.length()-1);
                        }
                        nhap.setText(textMath);
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
                textMath="";
                textAns = "0";
                check_phay=false;
                nhap.setText(textMath);
                kq.setText(textAns);
            }
        });


        bang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] b = null;
                InfixToPostfix1 ITP = new InfixToPostfix1();
                if(textMath.length()>0){
                    try {
                        if (!ITP.check_error) b = ITP.processString(textMath);
                        if (!ITP.check_error) b = ITP.postfix(b);
                        if(!ITP.check_error) textAns = ITP.valueMath(b);
                        kq.setText(textAns);
                        textMath = textAns;
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