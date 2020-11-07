package com.example.roomdemo;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<Word>allWords = new ArrayList<>();
    boolean useCardView;
    private WordViewModel wordViewModel;

    public MyAdapter(boolean useCardView, WordViewModel viewModel) {
        this.useCardView = useCardView;
        this.wordViewModel = viewModel;
    }

    public void setAllWords(List<Word> allWords) {
        this.allWords = allWords;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;
        if (useCardView) {
            itemView = inflater.inflate(R.layout.cell_card,parent,false);
        } else {
            itemView = inflater.inflate(R.layout.cell_normal2,parent,false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final Word word = allWords.get(position);
        holder.textViewNumber.setText(String.valueOf(position + 1));
        holder.textViewEnglish.setText(word.getWord());
        holder.aSwitch.setOnCheckedChangeListener(null);//在setChecked时先禁用监听。
        if (word.isChineseInvisible()) {
            holder.textViewChinese.setVisibility(View.GONE);
            holder.aSwitch.setChecked(true);
        } else {
            holder.textViewChinese.setVisibility(View.VISIBLE);
            holder.aSwitch.setChecked(false);
        }
        holder.textViewChinese.setText(word.getChinese());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.baidu.com");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.textViewChinese.setVisibility(View.GONE);
                    word.setChineseInvisible(true);
                    wordViewModel.updateWords(word);
                } else {
                    holder.textViewChinese.setVisibility(View.VISIBLE);
                    word.setChineseInvisible(false);
                    wordViewModel.updateWords(word);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return allWords.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        Switch aSwitch;
        TextView textViewNumber, textViewEnglish, textViewChinese;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
            textViewEnglish = itemView.findViewById(R.id.textViewEnglish);
            textViewChinese = itemView.findViewById(R.id.textViewChinese);
            aSwitch = itemView.findViewById(R.id.switchChineseVisible);
        }
    }

}
