package com.osmanforhad.noteapps.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.osmanforhad.noteapps.R;

import java.util.List;

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

        /* for display message when click on the particular item **/
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* message **/
                Toast.makeText(v.getContext(),"The Item is clicked.",Toast.LENGTH_SHORT).show();

            }//end of the onClick method

        });//end of the setOnClickListener

    }//end of the onBindViewHolder method

    @Override
    public int getItemCount() {
        return titles.size();
    }//end of the getItemCount method

    public class ViewHolder extends RecyclerView.ViewHolder {

        /* variable Declaration **/
        TextView noteTitle,noteContent;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            /* initial the xml UI as item **/
            noteTitle = itemView.findViewById(R.id.titles);
            noteContent = itemView.findViewById(R.id.content);

            /* for handle the click on the recycleView Items  **/
            view = itemView;

        }//end of the ViewHolder method

    }//end of the sub class ViewHolder

}//end of the main class Adapter
