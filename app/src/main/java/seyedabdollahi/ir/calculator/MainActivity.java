package seyedabdollahi.ir.calculator;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.DecimalFormat;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private TextView Display;
    private Button btnClear;
    private Button btnEqual;
    private String txt;
    private Double numberOne;
    private Double numberTwo;
    private String operator;
    private String lastOperator;
    private Double lastNumberTwo;
    private CoordinatorLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        configViews();
        Double x = 132.3659;
        Log.d("TAG" , "Math.floor(x) is:" + Math.floor(x));
        Log.d("TAG" , "##.## is:" + new DecimalFormat("#.##").format(x));
    }

    private void findViews(){
        Display = findViewById(R.id.main_txt);
        btnClear = findViewById(R.id.main_btn_clear);
        btnEqual = findViewById(R.id.main_btn_equal);
        mainLayout = findViewById(R.id.main_layout);
    }

    private void configViews(){
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearClicked();
            }
        });
        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equalClicked();
            }
        });
    }

    private void equalClicked(){
        if (numberOne == null){
            // DO NOTHING
        }else if (operator == null){
            if(lastOperator != null){
                operator = lastOperator;
                numberTwo = lastNumberTwo;
                computing();
            }
        }else if(numberTwo == null){
            operator = null;
            txt = round(numberOne);
            show();
        }else {
            computing();
        }
    }

    private void clearClicked(){
        numberOne = null;
        numberTwo = null;
        operator = null;
        lastOperator = null;
        lastNumberTwo = null;
        txt = "0";
        show();
    }

    private void show(){
        Display.setText(txt);
    }

    private String round(Double x){
        if (Math.floor(x) - x == 0){
            return String.format("%.0f" , x);
        }
        return String.format("%.2f" , x);
    }

    private void computing(){
        switch (operator){
            case "*":
                numberOne = numberOne * numberTwo;
                break;
            case "/":
                if (numberTwo == 0){
                    Snackbar snackbar = Snackbar.make(mainLayout , R.string.error , Snackbar.LENGTH_SHORT );
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(ContextCompat.getColor(MainActivity.this , R.color.red));
                    snackbar.show();
                }else {
                    numberOne = numberOne / numberTwo;
                }
                break;
            case "+":
                numberOne = numberOne + numberTwo;
                break;
            case "-":
                numberOne = numberOne - numberTwo;
                break;
        }
        lastOperator = operator;
        lastNumberTwo = numberTwo;
        numberTwo = null;
        operator = null;
        txt = round(numberOne);
        show();
    }

    public void numberClicked(View view){
        Button btn = (Button) view;
        String number = btn.getText().toString();
        if (numberOne == null){
            numberOne = Double.parseDouble(number);
            txt = number;
            show();
        }else if(numberTwo == null){
            if (operator == null){
                numberOne = Double.parseDouble(Display.getText().toString() + number);
                txt = txt + number;
                Log.d("TAG" , "numberOne is: " + numberOne);
                show();
            } else {
                int i = 0;
                switch (operator){
                    case "*":
                        i = txt.indexOf("*");
                        break;
                    case "/":
                        i = txt.indexOf("/");
                        break;
                    case "+":
                        i = txt.indexOf("+");
                        break;
                    case "-":
                        i = txt.indexOf("-");
                        break;
                }
                txt = Display.getText().toString() + number;
                Log.d("TAG" , "txt.length is: " + txt.length());
                Log.d("TAG" , "i is: " + i);
                try{
                    numberTwo = Double.parseDouble(txt.substring(i + 1 , txt.length()));
                }catch (Exception e){
                    Log.d("TAG" , "error: " + e.toString());
                }
                show();
            }
        }else {
            txt = Display.getText().toString() + number;
            int i = 0;
            switch (operator){
                case "*":
                    i = txt.indexOf("*");
                    break;
                case "/":
                    i = txt.indexOf("/");
                    break;
                case "+":
                    i = txt.indexOf("+");
                    break;
                case "-":
                    i = txt.indexOf("-");
                    break;
            }
            txt = Display.getText().toString() + number;
            Log.d("TAG" , "txt.length is: " + txt.length());
            Log.d("TAG" , "i is: " + i);
            try{
                numberTwo = Double.parseDouble(txt.substring(i + 1 , txt.length()));
            }catch (Exception e){
                Log.d("TAG" , "error: " + e.toString());
            }
            show();
        }
    }

    public void operatorClicked(View view){
        Button btn = (Button) view;
        if (numberOne == null){
            // DO NOTHING
        }else if(operator == null){
            operator = btn.getText().toString();
            txt = txt + operator;
            show();
        }else if(numberTwo == null){
            txt = txt.substring(0 , txt.length()-1);
            operator = btn.getText().toString();
            txt = txt + operator;
            show();
        }else {
            computing();
            operator = btn.getText().toString();
            txt = txt + operator;
            show();
        }
    }

    //set font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
