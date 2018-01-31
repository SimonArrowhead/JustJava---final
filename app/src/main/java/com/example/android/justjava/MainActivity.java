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
    //   if (checkBox1.isChecked()) boxCream = "Yes";
    //    else boxCream = "No";
            boolean boxCream = checkBox1.isChecked();
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.chocolate_checkbox);
    //    if (checkBox2.isChecked()) boxChocolate = "Yes";
    //     else boxChocolate = "No";
            boolean boxChocolate = checkBox2.isChecked();

        EditText getName = (EditText) findViewById(R.id.nameField);
        name = getName.getText().toString();

        int price = calculatePrice(boxCream, boxChocolate);

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
        intent.setData(Uri.parse("mailto:szymon@sdasd.pl"));
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
        displayQuantity(quantity);
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
    private int calculatePrice(boolean boxCream, boolean boxChocolate) {
        int toppings = 0;
        if (boxCream) toppings += 1;

        if (boxChocolate) toppings += 2;


        return quantity * (pricePerCup + toppings);
    }

    /**
     * @param price      calculated price for the order
     * @param trueFalse1 holds true or false if check box 1 is checked or not
     * @param trueFalse2 holds true or false if check box 2 is checked or not
     * @param name       String imported from the nameField
     * @return returns Order Summary
     */
    private String createOrderSummary(String name, int price, String trueFalse1, String trueFalse2) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nWith whipped cream: " + trueFalse1;
        priceMessage += "\nWitch chocolate topping: " + trueFalse2;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $ " + price;
        priceMessage += "\nThank you! Have a nice day :)";
        return priceMessage;
    }


}