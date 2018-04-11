package com.example.seungleechoi.burgerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    RadioButton White, Wheat, Beef,Grilled_Chicken, Turkey,Salmon,Veggie;
    CheckBox mushroom, lettus, tomato, pickles, mayo, mustard;
    EditText number_of_burgers;
    Button calculate;
    TextView total, price;
    //rate compute;
    rate compute = new rate();
    int maxTopping = 6;
    //int burgers = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        White =(RadioButton) findViewById(R.id.White);
        Wheat =(RadioButton) findViewById(R.id.Wheat);
        Beef =(RadioButton) findViewById(R.id.Beef);
        Grilled_Chicken =(RadioButton) findViewById(R.id.Grilled_Chicken);
        Turkey =(RadioButton) findViewById(R.id.Turkey);
        Salmon =(RadioButton) findViewById(R.id.Salmon);
        Veggie =(RadioButton) findViewById(R.id.Veggie);
        mushroom = (CheckBox) findViewById(R.id.mushrooms);
        lettus = (CheckBox) findViewById(R.id.lettus);
        tomato = (CheckBox) findViewById(R.id.tomato);
        pickles= (CheckBox) findViewById(R.id.pickles);
        mayo = (CheckBox) findViewById(R.id.mayo);
        mustard = (CheckBox) findViewById(R.id.mustard);
        number_of_burgers = (EditText) findViewById(R.id.number_of_burgers);
        calculate = (Button) findViewById(R.id.calculate);
        total = (TextView) findViewById(R.id.total);
        price = (TextView) findViewById(R.id.price);

        calculate.setOnClickListener(this);


    }
    @Override
    public void onClick(View view)
    {



        if (view.getId() == calculate.getId())
        {

            compute.total1 =0;
            compute.total=0;
            compute.total_calorie=0;
            if(White.isChecked())
            {
                //calorie = 140;
                //price =1;
                compute.computePrice(1);
                compute.computerCalorie(140);
                //Toast toast = Toast.makeText(this, "hi", Toast.LENGTH_SHORT);
                //total.setText(String.valueOf(compute.total_calorie));


            }
            if(Wheat.isChecked())
            {

                //calorie =100;
                //price = 1;
                compute.computePrice(1);
                compute.computerCalorie(100);
            }
            if(Beef.isChecked())
            {
                //calorie = calorie +240;
                //price = price+5.5;
                compute.computePrice(5.5);
                compute.computerCalorie(240);
                //total.setText(String.valueOf(compute.total_calorie));

            }
            if (Grilled_Chicken.isChecked())
            {
                //if (Grilled_Chicken.isChecked())
                //{
                //  compute.computerCalorie(180);
                //}
                //calorie = calorie + 180;
                //price = price +5;
                compute.computePrice(5);
                compute.computerCalorie(180);
            }
            if (Turkey.isChecked())
            {
                //calorie = calorie +190;
                //price =price+5;
                compute.computePrice(5);
                compute.computerCalorie(190);
            }
            if (Salmon.isChecked())
            {
                //calorie = calorie +95;
                //price = price+7.5;
                compute.computePrice(7.5);
                compute.computerCalorie(95);
            }
            if (Veggie.isChecked())
            {
                //calorie =calorie +80;
                //price =price +4.5;
                compute.computePrice(4.5);
                compute.computerCalorie(80);
            }
            //if (Veggie.isChecked())
            //{
            //  compute.computePrice(4.5);
            //compute.computerCalorie(80);
            //}
            if (mushroom.isChecked())
            {
                //calorie= calorie + 60;
                //price =price + 1;
                //maxTopping++;
                compute.computePrice(1);
                compute.computerCalorie(60);
                compute.maxTopping();
            }

            if (lettus.isChecked())
            {
                //calorie = calorie+20;
                //price = price +0.3;
                //maxTopping++;
                compute.computePrice(0.3);
                compute.computerCalorie(20);
                compute.maxTopping();
            }

            if (tomato.isChecked())
            {
                //calorie = calorie + 20;
                //price = price +0.3;
                //maxTopping++;
                compute.computePrice(0.3);
                compute.computerCalorie(20);
                compute.maxTopping();
            }

            if (pickles.isChecked())
            {
                //calorie = calorie +30;
                //price =price +0.5;
                //maxTopping++;
                compute.computePrice(0.5);
                compute.computerCalorie(30);
                compute.maxTopping();
            }

            if (mayo.isChecked())
            {
                //calorie = calorie+100;
                //maxTopping++;
                compute.computePrice(0);
                compute.computerCalorie(100);
                //total.setText(String.valueOf(calorie));
                compute.maxTopping();
            }

            if (mustard.isChecked())
            {
                //calorie = calorie +60;
                //maxTopping++;
                compute.computePrice(0);
                compute.computerCalorie(60);
                //total.setText(String.valueOf(calorie));
                compute.maxTopping();
            }



            if (compute.maxTopping() < 5)
            {
                try
                {
                    String a = number_of_burgers.getText().toString();

                    int burgers = Integer.parseInt(a);
                    int calorie = compute.total_calorie * burgers;
                    double price1 = compute.total* burgers;
                    price1 = (price1 *100) /100;
                    //calorie =100;
                    total.setText(String.valueOf(calorie));
                    //price.set

                    price.setText(String.valueOf(price1));
                }
                catch (Exception ex)
                {
                    Toast toast1 = Toast.makeText(this, "INVALID INPUT",Toast.LENGTH_SHORT);
                    toast1.show();
                }

            }
            else
            {
                Toast toast = Toast.makeText(this, "Topping Cant Exeed 3", Toast.LENGTH_SHORT);
                toast.show();
            }

        }


    }
}
