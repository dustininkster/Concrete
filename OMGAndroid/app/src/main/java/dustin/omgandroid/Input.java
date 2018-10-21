package dustin.omgandroid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;


public class Input extends ActionBarActivity {
    //main method for "Input"

    String buttonpressed;  // just declaring variables and objects here
    Button key1;
    Button key2;
    Button key3;
    Button key4;
    Button key5;
    Button key6;
    Button key7;
    Button key8;
    Button key9;
    Button key0;
    Button keyb;
    Button keye;
    double ro = 0;
    TextView readout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Same thing.  Runs onCreate as soon as it's opened.
        setContentView(R.layout.activity_input);
        //Sets the view to the activity_input layout.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Fixes the orientation to portrait.
        readout = (TextView)findViewById(R.id.readout); // again, just attaching variables to our views
        key1 = (Button) findViewById(R.id.ke1);
        key2 = (Button) findViewById(R.id.ke2);
        key3 = (Button) findViewById(R.id.ke3);
        key4 = (Button) findViewById(R.id.ke4);
        key5 = (Button) findViewById(R.id.ke5);
        key6 = (Button) findViewById(R.id.ke6);
        key7 = (Button) findViewById(R.id.ke7);
        key8 = (Button) findViewById(R.id.ke8);
        key9 = (Button) findViewById(R.id.ke9);
        keye = (Button) findViewById(R.id.kee);
        key0 = (Button) findViewById(R.id.ke0);
        keyb = (Button) findViewById(R.id.keb);
        //readout.setText("Works");
        //^^ This is a line I commented out.  I used it to troubleshoot a crash I was having when you pushed
        //a button.  If I could get it to work in onCreate I could know that it was the button routine
        //that was causing the crash, not the view.
            Bundle extras = getIntent().getExtras();
        //This gets the extras (which button was press) and puts it in a Bundle variable called extras
        //I believe a Bundle is actually a collection of data.
            buttonpressed = extras.getString("bnum");
        //This gets the string that we named bnum (the only thing in the Bundle extras) and stores it
        //in a string called buttonpressed so we can use it.

        switch (buttonpressed){  // This block just sets the title at the top of the input page based
            //                      on the button that was pressed.  See the MainActivity file for more
            //                      on switches.
            case "sg_button":
                setTitle(R.string.sg_label);// Note that I reference the string file so that it will
                break;//                       get the correct language.
            case "pw_button":
                setTitle(R.string.pw_label);
                break;
            case "spw_button":
                setTitle(R.string.spw_label);
                break;
            case "s_button":
                setTitle(R.string.s_label);
                break;
            default://  It will run default and display error if for some reason it didn't get the button
                setTitle("Error");// pressed correctly
                break;
        }

    }




    public void updateDisplay(View view) {
        //This is the method we designated with the onClick attribute of the button in the xml file
        String v = String.valueOf(view);// Put the view in a string just like we did in the main file
        DecimalFormat df = new DecimalFormat("###0.00");// define a format to avoid crazy decimals.
        String press = v.substring(v.lastIndexOf("ke") + 2, v.lastIndexOf("ke") + 3);
        //Here I'm stripping out data from the filename just like in the main file, but instead of parsing
        //just the filename, I'm actually getting the number of the button.  All of the button files are
        //named key then the number of the button.  This is determined by the name we gave it in the xml
        //file.  So I'm telling it to take the character two after the ke.  You'll see why below.
        switch (press) {
            case "b":// Check to see if the key pressed was key "b" or back.

                if (ro < .1){ro = 0.00;}//This gets rid of that last digit on the screen if you hit back
                if (ro/10 > 0.01){//  This checks to see if it's big enough to simply divide by ten
                    ro = Math.floor(ro * 10)/100;// if so, divide by ten.  The floor function basically
                 }//                                just tells it to always round down.
                readout.setText((String.valueOf(df.format(ro))));//display ro converted to a string.

                break;
            case "e"://  Check to see if the key pressed was key "e" or back.
                Intent i = new Intent();  // we are defining a new intent.  We will call this one "i"
               i.putExtra("result",String.valueOf(df.format(ro)));//convert ro to a string and attach
                //                                                  it as an extra called "result"
                setResult(100,i);// send back the result with the code 100.  The routine that catches
                //                  this will evaluate the 100 to make sure it's the right result.
                finish();// Kill the current activity.
                break;
            default://  If it's not back or enter it's a digit.

                if (ro * 10 < 9999.99) {//You can't enter more than four digits
                    ro = ro * 10;//take the current ro value and multiply it by ten
                    ro = ro + Double.valueOf(press) / 100;
                    //convert press (which we know is a number) and convert it to a double.  We want it
                    //to put it in the hundredths slot so we divide by 100
                    readout.setText(String.valueOf(df.format(ro)));//convert to a string and apply format
                    //                                                  then display.
                }
        }
    }
}
