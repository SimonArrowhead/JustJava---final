/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * @param quantity is the number of cups of coffee ordered
     */
    int quantity = 1;
    int pricePerCup = 10;
    int price = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String name;
        CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkboxCream);
            boolean boxCream = checkBox1.isChecked();
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.chocolate_checkbox);
            boolean boxChocolate = checkBox2.isChecked();

        EditText getName = (EditText) findViewById(R.id.nameField);
        name = getName.getText().toString();

        calculatePrice();

        String creamYesNo;
        if(boxCream) creamYesNo = getString(R.string.Yes);
            else creamYesNo =getString(R.string.No);

        String chocolateYesNo;
        if (boxChocolate) chocolateYesNo = getString(R.string.Yes);
            else chocolateYesNo = getString(R.string.No);

        //message send through email
        String priceMessage = getString(R.string.nameJ, name);
        priceMessage += "\n" + getString(R.string.wCreamJ, creamYesNo);
        priceMessage += "\n" + getString(R.string.wChocolateJ, chocolateYesNo);
        priceMessage += "\n" + getString(R.string.quantityJ, quantity);
        priceMessage += "\n" + getString(R.string.totalJ, price);
        priceMessage += "\n" + getString(R.string.thankYouJ);

        //sending email method
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("*/*");
        intent.setData(Uri.parse("mailto:szymon.groth@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subjectJ));
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Increases quantity of the cups of coffees ordered when the button is clicked
     */
    public void submitIncrement(View view) {
        if (quantity <= 99) {
            quantity = quantity + 1;
        } else {
            Toast over100 = Toast.makeText(this, "You can order max 100 coffees", Toast.LENGTH_SHORT);
            over100.show();
            return;
        }

        calculatePrice();
        displayTotalPrice();
        displayQuantity(quantity);
    }

    /**
     * Decreases quantity of the cups of coffees ordered when the button is clicked
     */
    public void submitDecrement(View view) {
        if (quantity >= 2) {
            quantity = quantity - 1;
        } else {
            Toast over100 = Toast.makeText(this, "You can order min 1 coffee", Toast.LENGTH_SHORT);
            over100.show();
            return;
        }

        calculatePrice();
        displayTotalPrice();
        displayQuantity(quantity);
    }

   public void ifChecked(View view){
       calculatePrice();
       displayTotalPrice();
   }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int cups) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + cups);
    }

    /**
     * Calculates the price of the order.
     *
     * @param boxCream     checks if checkBox for Cream is checked and if condition is made
     *                     add 1 for price of toppings
     * @param boxChocolate checks if checkBox for Chocolate is checked and if condition is made
     *                     add 2 for price of toppings
     */
    private void calculatePrice() {
        int toppings = 0;
        CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkboxCream);
        boolean boxCream = checkBox1.isChecked();
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean boxChocolate = checkBox2.isChecked();
        if (boxCream) toppings += 1;

        if (boxChocolate) toppings += 2;


        price = quantity * (pricePerCup + toppings);

    }

    /**
     * @return returns Order Summary
     */
    public void displayTotalPrice() {
        String totalMessage = getString(R.string.totalJ, price);
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText("" + totalMessage);
    }


}