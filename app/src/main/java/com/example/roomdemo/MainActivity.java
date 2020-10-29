package com.example.roomdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

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

    LiveData<List<Word>> allWordsLive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordDatabase = Room.databaseBuilder(this, WordDatabase.class, "word_database").allowMainThreadQueries().build();
        wordDao = wordDatabase.getWordDao();
        textView = findViewById(R.id.textView);
        allWordsLive = wordDao.getAllWords();

        allWordsLive.observe(this, new Observer<List<Word>>() {
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
                wordDao.insertWords(word1, word2);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("aa", "bb");
                word.setId(30);
                wordDao.deleteWords(word);
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("Thanks", "谢谢");
                word.setId(29);
                wordDao.updateWords(word);
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordDao.deleteAllWords();
            }
        });
    }
}