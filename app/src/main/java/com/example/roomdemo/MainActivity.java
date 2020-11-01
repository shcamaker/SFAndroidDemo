package com.example.roomdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    WordDatabase wordDatabase;
    WordDao wordDao;
    TextView textView;
    Button buttonInsert, buttonDelete, buttonUpdate, buttonClear;
    WordViewModel wordViewModel;

    LiveData<List<Word>> allWordsLive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        myViewModel = ViewModelProviders.of(this,factory).get(MyViewModel.class);
        wordViewModel =ViewModelProviders.of(this).get(WordViewModel.class);
        textView = findViewById(R.id.textView);

        wordViewModel.getAllWordsLive().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                StringBuilder text = new StringBuilder();
                for (Word word: words) {
                    text.append(word.getId()).append("、").append(word.getWord()).append(":").append(word.getChinese()).append("\n");
                }
                textView.setText(text);
            }
        });
        buttonInsert = findViewById(R.id.button1);
        buttonDelete = findViewById(R.id.button2);
        buttonUpdate = findViewById(R.id.button3);
        buttonClear = findViewById(R.id.button4);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word1 = new Word("Hello", "你好");
                Word word2 = new Word("World", "世界");
                wordViewModel.insertWords(word1, word2);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("aa", "bb");
                word.setId(30);
                wordViewModel.deleteWords(word);
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("Thanks", "谢谢");
                word.setId(29);
                wordViewModel.updateWords(word);
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordViewModel.deleteAllWords();
            }
        });
    }


}