package com.example.fbflixster;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends Activity {
    TextView textView;
    TextView textView2;
    ImageView imageView;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Testing ViewBinding; doesn't exist!@!!!!! WHY?!?!?!
        //SimpleActivityBinding binding = SimpleActivityBinding.inflate(getLayoutInflater());

    }
}
