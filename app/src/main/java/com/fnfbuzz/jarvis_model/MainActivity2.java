package com.fnfbuzz.jarvis_model;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.Calendar;

import static com.fnfbuzz.jarvis_model.Function.string;

public class MainActivity2 extends AppCompatActivity {
    ProgressDialog progressDialog;
    private SpeechRecognizer speechRecognizer;
    private String kipper=null;
    TextView hello;
    private TextToSpeech text;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        b=findViewById(R.id.yes);
        Dexter.withContext(this)
                .withPermission(Manifest.permission.RECORD_AUDIO)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response)
                    {

                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                       System.exit(0);

                    }
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
        initilizeRecognigation();
        initilizeTextTosepech();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,1);
                speechRecognizer.startListening(intent);
            }
        });
    }
    private void initilizeTextTosepech() {
        text=new TextToSpeech(MainActivity2.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (text.getEngines().size()==0) {
                    Toast.makeText(MainActivity2.this, "Not Found Engine", Toast.LENGTH_SHORT).show();
                }
                else {
                    String wish=string();

                    speck("Hi,Welcome to jarvis system.?"+wish);

                }

            }
        });
    }
    private void speck(String msg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            text.speak(msg,TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else {
            text.speak(msg,TextToSpeech.QUEUE_FLUSH,null);
        }
    }
    private void initilizeRecognigation()
    {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            speechRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
            speechRecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle results)
                {
                    ArrayList<String> stringArrayList=results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    if (stringArrayList !=null) {
                        kipper=stringArrayList.get(0);
                        resultRequest(kipper);
                        Toast.makeText(MainActivity2.this, "Result :"+kipper, Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(MainActivity2.this, "Speack Again..", Toast.LENGTH_SHORT).show();
                    }



                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }

    }

    private void resultRequest(String question) {
        String question1=question.toLowerCase();
        if (question1.indexOf("email")!=-1) {
            startActivity(new Intent(getApplicationContext(),Sending_image.class));
        }
        if (question1.indexOf("hi")!=-1) {
            speck("hello sir,how are you?");
        }
        else  if (question1.indexOf("fine")!=-1) {
            speck("It is very good news that you are fine.I am also fine.How Can i help you");
        }
        else if (question1.indexOf("you can speak with me")!=-1) {
            speck("Yeah.i will speak with you.let's start conversation sir ");
        }
        else if (question1.indexOf("what is your name")!=-1) {
            speck("my name is jarvis sir.what is your name");
        }
        else if (question1.indexOf("my name is arif")!=-1) {
            speck("wow.it is a nice name .you are my boss.how about your family member");
        }
        else if (question1.indexOf("good")!=-1) {
            speck("Thanks to Allah that they are fine...what you are doing sir");
        }
        else  if (question1.indexOf("time please")!=-1) {
            Calendar calendar=Calendar.getInstance();
            int time=calendar.get(Calendar.HOUR_OF_DAY);
            if (time>=12) {
                int time2=time-12;
                speck("Now it is"+time2+"PM sir");
            }
            else {

                speck("Now it is" + time + "AM sir");
            }
        }
        else  if (question1.indexOf("date")!=-1) {
            Calendar calendar=Calendar.getInstance();
            int time=calendar.get(Calendar.DAY_OF_MONTH);
            int  name=calendar.get(Calendar.MONTH);
            int year=calendar.get(Calendar.YEAR);


            speck("Now it is" + time + ""+name+""+year+"sir");

        }
        else  if (question1.indexOf("open google")!=-1) {


            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"));
            startActivity(intent);
            speck("opening it sir");

        }
        else  if (question1.indexOf("open newspaper")!=-1) {


            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.prothomalo.com/"));
            startActivity(intent);
            speck("opening newspapers site sir");

        }
        else  if (question1.indexOf("open youtube")!=-1) {


            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"));
            startActivity(intent);
            speck("opening Youtube site sir");

        }
        else if (question1.indexOf("thank you jarvis")!=-1) {
            speck("welcome sir.Your work is over sir..");
        }
        else  if (question1.indexOf("yes")!=-1) {



            speck("Thank you sir for using me ...Have a nice day sir.Now i will finish it");
            finish();

        }

    }








    public void click(View view) {
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,1);
        speechRecognizer.startListening(intent);

    }

    public void float12(View view) {
    }

    public void yes(View view) {
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,1);
        speechRecognizer.startListening(intent);
    }
}