package com.example.fbflixster;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fbflixster.databinding.ActivityTestBinding;

public class TestActivity extends Activity {
    TextView textView;
    TextView textView2;
    ImageView imageView;
    Button button;

    ActivityTestBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This does not work
        //String consumer = BuildConfig.CONSUMER_KEY;
        //ActivityTestBinding
        //Testing ViewBinding; doesn't exist!@!!!!! WHY?!?!?!
        //SimpleActivityBinding binding = SimpleActivityBinding.inflate(getLayoutInflater());

        binding = ActivityTestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();


    }
}
