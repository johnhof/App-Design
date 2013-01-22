package edu.pitt.cs1635.jmh162.prog1;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button calcButton;
	private RadioGroup packageRadioGroup;
	private EditText weightField;
	private TextView resultText ;
	private AlertDialog errorMessage;
	private double result;
	
    private static final int MIN = 0;
    private static final int MAX = 13;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initializeButtonListener();		
	}
	
	
	private void initializeButtonListener() 
	{
		result = -1;
		
		initializeUIObjects();
		 
		calcButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{		
				//get the current user entries
				String weightString = weightField.getText().toString();

				//if the format of the input is incorrect, return it
				if(weightString.equals("") || Double.parseDouble(weightString) > MAX || weightString.contains(".")) 
				{
					throwError("Weight must be a decimal (#.#) 0-13 oz");
					return;
				}
				
				//retrieve the cost
				result = getCost(packageRadioGroup.getCheckedRadioButtonId(), Double.parseDouble(weightString));
				
//TODO: format string
				//resultText.setText(new java.text.DecimalFormat("$0.00").format(""+result));
				String herp = String.format("%1$,.2f", (float)result);
				resultText.setText("$"+herp);
			}
		});
	}

	private void initializeUIObjects()
	{
		//initialize objects to listen
		calcButton = (Button) findViewById(R.id.calculateButton);
		packageRadioGroup = (RadioGroup) findViewById(R.id.PackageType);
		weightField = (EditText) findViewById(R.id.weightField);
		resultText = (TextView) findViewById(R.id.resultField);
		
		//set the result field back to "N/A" if the input changes
		weightField.setOnKeyListener(new OnKeyListener() 
		{
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				resultText.setText("N/A");
				return false;
			}
		});
	}
	
	//return true if no error occurred
	public double getCost(int radioSelection, double input)
	{
		//SPECIAL CASE: if it is a letter over 3.5 oz, use the large envelope scale
		if(radioSelection == R.id.Letter && input > 3.5) 
		{
			if(input > 3 && input <= 3.5) input = 1.05;
			radioSelection = R.id.LargeEnvelope;
		}
			
		//round up to the next whole number
		input = input-0.6;
		input = (int)input;
		result = -1;

		if(radioSelection == R.id.Letter)
		{
			result = .45+input*.20;
		}
		if(radioSelection == R.id.LargeEnvelope)
		{
			result = .90+input*.20;
		}
		if(radioSelection == R.id.Package)
		{
			if(input<4) return 1.95;
			result = 1.95+(input-1)*.17;
		}
		
		return result;	
	}
	
	@SuppressWarnings("deprecation")
	public void throwError(String message)
	{
		//Set up error message and listener
		errorMessage = new AlertDialog.Builder(this).create();  
		errorMessage.setCancelable(false); 
		errorMessage.setMessage(message);  
		errorMessage.setButton("OK", new DialogInterface.OnClickListener() 
		{  
		    @Override  
		    public void onClick(final DialogInterface dialog, final int which)
		    { 
		    	dialog.dismiss();
		    }                      
		});   
		errorMessage.show(); 
	}

}
