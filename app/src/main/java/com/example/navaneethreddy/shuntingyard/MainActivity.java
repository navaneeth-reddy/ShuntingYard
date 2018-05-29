package com.example.navaneethreddy.shuntingyard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    EditText ed;
    Button submit;
    TextView result;
    LinkedList<String> inputQueue = new LinkedList<String>();
    LinkedList<String> outputQueue = new LinkedList<String>();
    Stack<String> operatorStack = new Stack<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed = (EditText)findViewById(R.id.editText);
        submit = (Button)findViewById(R.id.button);
        result = (TextView)findViewById(R.id.textView);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String expressionInput= ed.getText().toString().trim();
                ed.setText("");

                StringTokenizer tokens = new StringTokenizer(expressionInput, "+-*/", true);
                while (tokens.hasMoreTokens()) {
                    inputQueue.addLast(tokens.nextToken().trim());
                }

                conversion();
                result.setText(conversion());
            }

                public String conversion()
                {
                    String symbol;
                    while(inputQueue.size() > 0)
                    {
                        symbol = inputQueue.removeFirst();
                        if(this.checkOperator(symbol))
                        {
                            while(!operatorStack.empty()  && checkPriority(operatorStack.peek()) >= checkPriority(symbol))
                            {
                                outputQueue.addLast(operatorStack.pop());
                            }
                            operatorStack.push(symbol);

                        }
                        else
                        {
                            outputQueue.addLast(symbol);
                        }
                    }

                    while(!operatorStack.empty())
                    {
                        outputQueue.addLast(operatorStack.pop());
                    }

                    return this.calculation();

                }


                public String calculation()
                {
                    double num1;
                    double num2;
                    Stack<String> operatorStack = new Stack<String>();
                    for(String str : outputQueue)
                    {
                        if(this.checkOperator(str))
                        {
                            num2 = Double.parseDouble(operatorStack.pop());
                            num1 = Double.parseDouble(operatorStack.pop());
                            operatorStack.push("" + this.operation(num1, num2, str));
                        }
                        else
                        {
                            operatorStack.push(str);
                        }
                    }
                    return operatorStack.pop();
                }



            public int checkPriority(String operator)
            {
                if(operator.equals("+") || operator.equals("-"))
                {
                    return 2;
                }
                else
                {
                    //All other operators /*
                    return 3;
                }

            }

            public boolean checkOperator(String symbol)
            {
                return "+-*/".indexOf(symbol) >= 0;
            }

            public double operation(double number1, double number2, String operator)
            {
                if(operator.equals("+"))
                {
                    return number1 + number2;
                }
                else if(operator.equals("-"))
                {
                    return number1 - number2;
                }
                else if(operator.equals("*"))
                {
                    return number1 * number2;
                }
                else
                {
                    return number1 / number2;
                }

            }
            });

    }
}
