package com.mk.skincareorder.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mk.skincareorder.R;
import com.mk.skincareorder.model.SkincareModel;

import java.util.List;

public class SkincareListAdapter extends RecyclerView.Adapter<SkincareListAdapter.MyViewHolder> {
    // Kjo klasë zgjeron RecyclerView.Adapter dhe menaxhon shfaqjen e një liste me modele të kujdesit për lëkurën në RecyclerView.

    private List<SkincareModel> skincareModelList;
    // Lista që përmban të gjitha objektet SkincareModel që do të shfaqen në RecyclerView.

    private SkincareListClickListener clickListener;
    // Një ndërfaqe për të menaxhuar klikimet që bëjnë përdoruesit në listë.

    public SkincareListAdapter(List<SkincareModel> skincareModelList, SkincareListClickListener clickListener) {
        // Konstruktori i klasës, që inicializon listën e modeleve të kujdesit për lëkurën dhe dëgjuesin e klikimeve.
        this.skincareModelList = skincareModelList;
        this.clickListener = clickListener;
    }

    public void updateData(List<SkincareModel> skincareModelList) {
        // Përditëson listën e modeleve të kujdesit për lëkurën dhe rifreskon RecyclerView me të dhënat e reja.
        this.skincareModelList = skincareModelList;
        notifyDataSetChanged();  // Informon adapterin se të dhënat kanë ndryshuar dhe duhet të rifreskohen.
    }

    @NonNull
    @Override
    public SkincareListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Krijon një pamje për çdo rresht të RecyclerView duke inflatuar layout-in 'recycler_row'.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(view);  // Kthen një instancë të MyViewHolder që mban referencat për pamjet e rreshtit.
    }

    @Override
    public void onBindViewHolder(@NonNull SkincareListAdapter.MyViewHolder holder, int position) {
        // Lidh të dhënat e një objekti SkincareModel me një rresht specifik të RecyclerView.
        SkincareModel model = skincareModelList.get(position);  // Merr objektin SkincareModel në pozicionin e caktuar.

        holder.skincareName.setText(model.getName());  // Vendos emrin e dyqanit të kujdesit për lëkurën në TextView përkatës.
        holder.skincareAddress.setText("Address: " + model.getAddress());  // Vendos adresën e dyqanit në TextView përkatës.
        holder.skincareHours.setText("Today's hours: " + model.getHours().getTodaysHours());  // Vendos oraret e sotme në TextView përkatës.

        holder.itemView.setOnClickListener(view -> clickListener.onItemClick(model));
        // Vendos një dëgjues për klikimet mbi rreshtin e RecyclerView për të thirrur metodën onItemClick me modelin aktual.

        String imageName = model.getImageName();  // Merr emrin e imazhit nga modeli SkincareModel.
        if (imageName != null && !imageName.isEmpty()) {
            // Kontrollon nëse emri i imazhit nuk është null dhe nuk është bosh.
            int imageResId = holder.itemView.getContext().getResources().getIdentifier(
                    imageName, "drawable", holder.itemView.getContext().getPackageName());
            // Gjen ID-në e burimit të imazhit në resurset drawable të aplikacionit, bazuar në emrin e imazhit.

            if (imageResId != 0) {
                // Nëse ID-ja e burimit të imazhit nuk është 0, kjo do të thotë se imazhi u gjet.
                Log.d("SkincareListAdapter", "Loading image: " + imageName + " with resource ID: " + imageResId);
                // Logon emrin e imazhit dhe ID-në e burimit për qëllime debug.

                // Ngarkon imazhin duke përdorur Glide dhe e shfaq në ImageView përkatës.
                Glide.with(holder.thumbImage.getContext())
                        .load(imageResId)
                        .placeholder(R.drawable.default_image)  // Imazhi që shfaqet përkohësisht ndërsa ngarkohet imazhi i vërtetë.
                        .error(R.drawable.default_image)        // Imazhi që shfaqet nëse ndodh një gabim gjatë ngarkimit.
                        .into(holder.thumbImage);
            } else {
                // Nëse ID-ja e burimit të imazhit është 0, kjo do të thotë se imazhi nuk u gjet.
                Log.e("SkincareListAdapter", "Image resource not found for name: " + imageName);
                // Logon një mesazh gabimi që tregon se burimi i imazhit nuk u gjet për emrin e dhënë.

                // Shfaq imazhin e paracaktuar në ImageView duke përdorur Glide.
                Glide.with(holder.thumbImage.getContext())
                        .load(R.drawable.default_image)
                        .into(holder.thumbImage);
            }
        } else {
            // Nëse emri i imazhit është null ose bosh.
            Log.e("SkincareListAdapter", "Image name is null or empty for position: " + position);
            // Logon një mesazh gabimi që tregon se emri i imazhit është null ose bosh për pozicionin aktual.

            // Shfaq imazhin e paracaktuar në ImageView duke përdorur Glide.
            Glide.with(holder.thumbImage.getContext())
                    .load(R.drawable.default_image)
                    .into(holder.thumbImage);
        }
    }

    @Override
    public int getItemCount() {
        // Kthen numrin total të artikujve në listën e modeleve të kujdesit për lëkurën.
        return skincareModelList.size();  // Përcakton sa rreshta do të shfaqen në RecyclerView.
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Kjo klasë përfaqëson pamjen e çdo rreshti të RecyclerView dhe mban referenca për elementet e tij.
        TextView skincareName;   // TextView për shfaqjen e emrit të dyqanit të kujdesit për lëkurën.
        TextView skincareAddress;// TextView për shfaqjen e adresës së dyqanit të kujdesit për lëkurën.
        TextView skincareHours;  // TextView për shfaqjen e orareve të dyqanit të kujdesit për lëkurën.
        ImageView thumbImage;    // ImageView për shfaqjen e imazhit të dyqanit të kujdesit për lëkurën.

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializon elementet e pamjes (View) për një rresht specifik në RecyclerView.
            skincareName = itemView.findViewById(R.id.skincareName);  // Lidh TextView për emrin e dyqanit.
            skincareAddress = itemView.findViewById(R.id.skincareAddress);  // Lidh TextView për adresën e dyqanit.
            skincareHours = itemView.findViewById(R.id.skincareHours);  // Lidh TextView për oraret e dyqanit.
            thumbImage = itemView.findViewById(R.id.thumbImage);  // Lidh ImageView për imazhin e dyqanit.
        }
    }

    public interface SkincareListClickListener {
        // Kjo ndërfaqe përkufizon metodën që duhet të implementohet për menaxhimin e klikimeve në artikujt e RecyclerView.
        void onItemClick(SkincareModel skincareModel);
    }
}
