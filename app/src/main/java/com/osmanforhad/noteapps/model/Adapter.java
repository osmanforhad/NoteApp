package com.osmanforhad.noteapps.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.osmanforhad.noteapps.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    /* variable declaration **/
    List<String> titles;
    List<String> content;

    /* constructor method **/
    public Adapter(List<String> title, List<String> content){

        /* assign the data **/
        this.titles = title;
        this.content = content;

    }//end of the constructor method

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_view_layout,parent,false);
        return new ViewHolder(view);
    }//end of the onCreateViewHolder method

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        /* for receive data from main activity or list
        * as item position **/
        holder.noteTitle.setText(titles.get(position));
        holder.noteContent.setText(content.get(position));

        /* set background color for note **/
        holder.mCardView.setCardBackgroundColor(holder.view.getResources().getColor(getRandomColor(),null));


        /* for display message when click on the particular item **/
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* message **/
                Toast.makeText(v.getContext(),"The Item is clicked.",Toast.LENGTH_SHORT).show();

            }//end of the onClick method

        });//end of the setOnClickListener

    }//end of the onBindViewHolder method

    private int getRandomColor() {

        /* for hold the color code as integer **/
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.blue);
        colorCode.add(R.color.yellow);
        colorCode.add(R.color.skyblue);
        colorCode.add(R.color.lightPurple);
        colorCode.add(R.color.lightGreen);
        colorCode.add(R.color.gray);
        colorCode.add(R.color.pink);
        colorCode.add(R.color.red);
        colorCode.add(R.color.lightGreen);
        colorCode.add(R.color.notgreen);

        Random randomColor = new Random();

        int number = randomColor.nextInt(colorCode.size());

        return colorCode.get(number);

    }//end of the getRandomColor method

    @Override
    public int getItemCount() {
        return titles.size();
    }//end of the getItemCount method

    public class ViewHolder extends RecyclerView.ViewHolder {

        /* variable Declaration **/
        TextView noteTitle,noteContent;
        View view;
        CardView mCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            /* initial the xml UI as item **/
            noteTitle = itemView.findViewById(R.id.titles);
            noteContent = itemView.findViewById(R.id.content);
            mCardView = itemView.findViewById(R.id.noteCard);

            /* for handle the click on the recycleView Items  **/
            view = itemView;

        }//end of the ViewHolder method

    }//end of the sub class ViewHolder

}//end of the main class Adapter
