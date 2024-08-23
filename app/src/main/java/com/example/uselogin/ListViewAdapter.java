package com.example.uselogin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.prolayouting> {
    private Context context;
    private ArrayList<uploadfilemodel> models;


    ListViewAdapter(Context context, ArrayList<uploadfilemodel> model){
         this.context = context;
         this.models = model;

    }

    @NonNull
    @Override
    public prolayouting onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prolayout,null);
        return new prolayouting(view);
    }
    @Override
    public void onBindViewHolder(@NonNull prolayouting holder, int position) {
     holder.ViewTitle.setText(models.get(position).getTitle());
     holder.ViewAbstract.setText(models.get(position).getAbstract());
     holder.ViewProject.setText(models.get(position).getProjectType());
     holder.downloadbtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent = new Intent(context,PDFActivity.class);//read tp pdf
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          context.startActivity(intent);
         }
     });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class prolayouting extends RecyclerView.ViewHolder{
        TextView ViewTitle, ViewAbstract, ViewProject;
        CardView cardView;
        Button downloadbtn;
        public prolayouting(@NonNull View itemView) {
            super(itemView);
            this.ViewTitle = itemView.findViewById(R.id.title);
            this.ViewAbstract = itemView.findViewById(R.id.abstract_id);
            this.ViewProject = itemView.findViewById(R.id.project_id);
            this.downloadbtn = itemView.findViewById(R.id.download_btn);
            this.cardView = itemView.findViewById(R.id.parent_layout);
        }


    }
}
