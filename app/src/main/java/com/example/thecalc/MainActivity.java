package com.example.thecalc;

import androidx.appcompat.app.AppCompatActivity;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import org.mariuszgromada.math.mxparser.*;
import android.widget.TextView;

import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    boolean decider = true;
    int w=0;

    String userexp;
    String result;
    int count=0;
    String previous_buttonclick;
    String data;
    boolean symbol;
    boolean eee=false;
    int e_index;
    String dot; //to assign .
    String buttontext;
    int unwanted_value;
    boolean dot1=false;
    ImageButton imageButton, imageButton1, imageButton2;
    Button buttonclear, buttonadd, buttonminus, buttondivision, buttonmultiplication, buttonmodulas;
    Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, buttondot;
    TextView textviewcalculation, textViewresult;
    HorizontalScrollView horz;
    TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        imageButton = findViewById(R.id.speak);
        imageButton.setOnClickListener(this);
        imageButton1 = findViewById(R.id.backspace);
        imageButton1.setOnClickListener(this);
        imageButton2 = findViewById(R.id.equal);
        imageButton2.setOnClickListener(this);




        textviewcalculation = findViewById(R.id.calculation);
        textViewresult = findViewById(R.id.result);

        buttonclear = findViewById(R.id.clear);
        buttonclear.setOnClickListener(this);

        horz=findViewById(R.id.horizontal);




        assign(button0, R.id.zero);
        assign(button1, R.id.one);
        assign(button2, R.id.two);
        assign(button3, R.id.three);
        assign(button4, R.id.four);
        assign(button5, R.id.five);
        assign(button6, R.id.six);
        assign(button7, R.id.seven);
        assign(button8, R.id.eight);
        assign(button9, R.id.nine);
        assign(buttonadd, R.id.add);
        assign(buttonminus, R.id.minus);
        assign(buttonmultiplication, R.id.multiplication);
        assign(buttondivision, R.id.division);
        assign(buttondivision, R.id.division);
        assign(buttondot, R.id.dot);
        assign(buttonmodulas, R.id.modulus);



        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if(i!=TextToSpeech.ERROR){
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

    }



    public  boolean checker(String passer){

        if(passer.equals("+")||passer.equals("-")||passer.equals("×")||passer.equals("÷")||passer.equals("%")){
            count=0;
            if(passer.equals(".")) count=0;
            return true;
        }
        else{
            return false;
        }
    }

    public void assign(Button btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);

    }

    public String finalresult(){

        userexp= textviewcalculation.getText().toString();
        userexp=userexp.replaceAll("÷","/");
        userexp=userexp.replaceAll("×","*");

        Expression exp=new Expression(userexp);
        result=String.valueOf(exp.calculate());
        int result_length=result.length()-1;

        if(result_length>12) {

             unwanted_value=result_length-12;

            e_index=result.length()-3;
            result= result.substring(0,e_index-unwanted_value)+ result.substring(e_index);
            return result;
        }
        else {
            return result;
        }

    }


    public void output(String passer) {

        data = textviewcalculation.getText().toString();
        data = data + passer;
        textviewcalculation.setText(data);


    }



    @Override
    public void onClick(View v) {


        switch (v.getId()) {


            case R.id.equal:
                if(w==1) {
                    textToSpeech.speak(textViewresult.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
                    textViewresult.setTextSize(50);
                    textviewcalculation.setTextSize(40);
                    break;
                }
                else   break;

            case R.id.speak:

                if (decider) {
                    textToSpeech.shutdown();
                    decider = false;
                    imageButton.setImageResource(R.drawable.ic_baseline_volume_off_24);
                } else {
                    textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int i) {

                            // if No error is found then only it will run
                            if(i!=TextToSpeech.ERROR){
                                // To Choose language of speech
                                textToSpeech.setLanguage(Locale.ENGLISH);
                            }
                        }
                    });
                    decider = true;
                    imageButton.setImageResource(R.drawable.ic_baseline_volume_up_24);
                }
                break;

            case R.id.clear:
                count=0;
                textviewcalculation.setText("");
                data=" ";
                textViewresult.setText("0");

                buttonclear.setText(R.string.clear);
                break;


            case R.id.backspace:
                buttontext=data.substring(data.length()-1,data.length());
                if(checker(buttontext)==false&&buttontext!=".") count=count-1;
                if(data==null||data.length()==0)   break;
                else {

                    dot=data.substring(data.length()-1, data.length());
                    if(dot.equals(".")) {
                        dot1=false; //dot_deleted


                    }


                    data = data.substring(0, data.length() - 1);
                    textviewcalculation.setText(data);
                    if(data.equals("0")) {data=" ";  textviewcalculation.setText(" ");}
                    if(textviewcalculation.length()==0)  textViewresult.setText("0");
                    result=finalresult();
                    if(result.equals("NaN")) break;
                    else textViewresult.setText(finalresult());
                    break;
                }




            default:



                w=1;


                textViewresult.setTextSize(45);
                textviewcalculation.setTextSize(60);
                buttonclear.setText(R.string.cl);
                buttonclear.setTextSize(40);


                Button button = (Button) v;
                String last=button.getText().toString();
                if(last.equals("÷"))    last="/";
                else if(last.equals("-"))   last="minus";
                else if(last.equals("×"))    last="into";

                textToSpeech.speak(last,TextToSpeech.QUEUE_FLUSH,null);
                buttontext = button.getText().toString();

                symbol=checker(buttontext);
                if(symbol==false&&buttontext!="."&&count>=10) break;
                else count=count+1;

                if(data==null||data.length()==0||data==" ") {

                    if (symbol||buttontext.equals(".")) {  data="0"; textviewcalculation.setText(data);}


                }
                else if(buttontext.equals(".")) {
                    previous_buttonclick=data.substring(data.length()-1,data.length());//checking if symbol in multiple times
                    if(previous_buttonclick.equals("."))    break;
                }
                else{
                    if(symbol){
                        previous_buttonclick=data.substring(data.length()-1,data.length());//checking if symbol in multiple times
                        if(checker(previous_buttonclick)){
                            break;
                        }


                    }
                }
                if(!dot1||buttontext!=".") {    //dot was not in data
                    output(buttontext);
                }

                else break; //dot is in data

                if(buttontext.equals(".") ) dot1=true;


                result=finalresult();
                if(result.equals("NaN")) break;

                else  textViewresult.setText(finalresult());

        }



    }
}
