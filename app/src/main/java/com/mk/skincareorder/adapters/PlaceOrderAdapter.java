package com.mk.skincareorder.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mk.skincareorder.R;
import com.mk.skincareorder.model.Menus;

import java.util.List;

public class PlaceOrderAdapter extends RecyclerView.Adapter<PlaceOrderAdapter.MyViewHolder> {
    // Kjo klasë zgjeron RecyclerView.Adapter dhe menaxhon shfaqjen e listës së produkteve që janë në porosi (në karrocë).

    private List<Menus> menuList;
    // Lista që përmban të gjitha objektet Menus që janë në porosi dhe do të shfaqen në RecyclerView.

    public PlaceOrderAdapter(List<Menus> menuList) {
        // Konstruktori i klasës, që inicializon listën e produkteve që janë në porosi.
        this.menuList = menuList;
    }

    public void updateData(List<Menus> menuList) {
        // Përditëson listën e produkteve në porosi dhe rifreskon RecyclerView me të dhënat e reja.
        this.menuList = menuList;
        notifyDataSetChanged();  // Informon adapterin se të dhënat kanë ndryshuar dhe duhet të rifreskohen.
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Krijon një pamje për çdo rresht të RecyclerView duke inflatuar layout-in 'placeorder_recycler_row'.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.placeorder_recycler_row, parent, false);
        return new MyViewHolder(view);  // Kthen një instancë të MyViewHolder që mban referencat për pamjet e rreshtit.
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Lidh të dhënat e një objekti Menus me një rresht specifik të RecyclerView.
        Menus menu = menuList.get(position);  // Merr objektin Menus në pozicionin e caktuar.

        holder.menuName.setText(menu.getName());  // Vendos emrin e produktit në TextView përkatës.

        holder.menuPrice.setText(String.format("Price: $%.2f", menu.getPrice() * menu.getTotalinTheCart()));
        // Vendos çmimin e produktit në TextView përkatës, duke shumëzuar çmimin e një produkti me sasinë në karrocë.

        holder.menuQty.setText("Qty: " + menu.getTotalinTheCart());
        // Vendos sasinë e produktit në karrocë në TextView përkatës.

        int imageResId = holder.itemView.getContext().getResources().getIdentifier(
                menu.getImageName(), "drawable", holder.itemView.getContext().getPackageName());
        // Përpiqet të gjejë ID-në e burimit të imazhit në resurset drawable të aplikacionit, bazuar në emrin e imazhit.

        Glide.with(holder.thumbImage.getContext())
                .load(imageResId != 0 ? imageResId : R.drawable.default_image)
                // Nëse ID-ja e imazhit është 0 (nuk u gjet), përdoret një imazh i paracaktuar.
                .placeholder(R.drawable.default_image)  // Imazhi që shfaqet përkohësisht ndërsa ngarkohet imazhi i vërtetë.
                .error(R.drawable.default_image)        // Imazhi që shfaqet nëse ndodh një gabim gjatë ngarkimit.
                .into(holder.thumbImage);               // Vendos imazhin në ImageView të caktuar.
    }

    @Override
    public int getItemCount() {
        // Kthen numrin total të artikujve në listën e porosive.
        return menuList.size();  // Përcakton sa rreshta do të shfaqen në RecyclerView.
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        // Kjo klasë përfaqëson pamjen e çdo rreshti të RecyclerView dhe mban referenca për elementet e tij.
        TextView menuName;   // TextView për shfaqjen e emrit të produktit.
        TextView menuPrice;  // TextView për shfaqjen e çmimit të produktit.
        TextView menuQty;    // TextView për shfaqjen e sasisë së produktit në karrocë.
        ImageView thumbImage;// ImageView për shfaqjen e imazhit të produktit.

        public MyViewHolder(View view) {
            super(view);
            // Inicializon elementet e pamjes (View) për një rresht specifik në RecyclerView.
            menuName = view.findViewById(R.id.menuName);  // Lidh TextView për emrin e produktit.
            menuPrice = view.findViewById(R.id.menuPrice);  // Lidh TextView për çmimin e produktit.
            menuQty = view.findViewById(R.id.menuQuantity);  // Lidh TextView për sasinë e produktit në karrocë.
            thumbImage = view.findViewById(R.id.thumbImage);  // Lidh ImageView për imazhin e produktit.
        }
    }
}