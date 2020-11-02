package com.example.roomdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button buttonInsert, buttonDelete;
    WordViewModel wordViewModel;
    RecyclerView recyclerView;
    MyAdapter myAdapter;


    LiveData<List<Word>> allWordsLive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpRecyclerView();

        wordViewModel =ViewModelProviders.of(this).get(WordViewModel.class);

        wordViewModel.getAllWordsLive().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                myAdapter.setAllWords(words);
                myAdapter.notifyDataSetChanged();
            }
        });
        buttonInsert = findViewById(R.id.button1);
        buttonDelete = findViewById(R.id.button2);


        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word1 = new Word("Hello", "你好");
                Word word2 = new Word("World", "世界");
                String[] english = {
                        "hello",
                        "world",
                        "android",
                        "ios",
                        "english",
                        "fitness",
                        "breakfast"
                };
                String[] chinese = {
                        "你好",
                        "世界",
                        "安卓",
                        "苹果",
                        "英语",
                        "健身",
                        "早餐"
                };
                for (int i = 0;i<english.length;i++) {
                    wordViewModel.insertWords(new Word(english[i],chinese[i]));
                }

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


    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        myAdapter = new MyAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
    }

}