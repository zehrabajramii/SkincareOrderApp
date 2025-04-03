package com.mk.skincareorder.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mk.skincareorder.R;
import com.mk.skincareorder.ShowDetailsActivity;  // Import the ShowDetailsActivity class
import com.mk.skincareorder.model.Menus;

import java.util.List;
import java.util.Locale;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MyViewHolder> {
    // Kjo klasë zgjeron RecyclerView.Adapter dhe menaxhon shfaqjen e listës së produkteve në RecyclerView.

    private List<Menus> menuList;
    // Lista që përmban të gjitha objektet Menus që do të shfaqen në RecyclerView.

    private final MenuListClickListener clickListener;
    // Një ndërfaqe për të trajtuar ngjarjet e klikimeve në listën e produkteve.

    public MenuListAdapter(List<Menus> menuList, MenuListClickListener clickListener) {
        // Konstruktori i klasës, që inicializon listën e produkteve dhe dëgjuesin e klikimeve.
        this.menuList = menuList;
        this.clickListener = clickListener;
    }

    public void updateData(List<Menus> menuList) {
        // Përditëson listën e produkteve dhe rifreskon pamjen në RecyclerView.
        this.menuList = menuList;
        notifyDataSetChanged();  // Informon adapterin se të dhënat kanë ndryshuar dhe duhet të rifreskohen.
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Krijon një pamje për çdo rresht të RecyclerView duke inflatuar layout-in 'menu_recycler_row'.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_recycler_row, parent, false);
        return new MyViewHolder(view);  // Kthen një instancë të MyViewHolder që mban referencat për pamjet e rreshtit.
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Lidh të dhënat e një objekti Menus me një rresht specifik të RecyclerView.
        Menus menu = menuList.get(position);  // Merr objektin Menus në pozicionin e caktuar.

        holder.menuName.setText(menu.getName());  // Vendos emrin e produktit në TextView përkatës.

        holder.menuPrice.setText(String.format(Locale.getDefault(), "Price: $%.2f", menu.getPrice()));
        // Vendos çmimin e produktit në TextView përkatës duke përdorur formatim për çmimin.

        String imageName = menu.getImageName();  // Merr emrin e imazhit të produktit.
        int imageResId = holder.itemView.getContext().getResources().getIdentifier(
                imageName, "drawable", holder.itemView.getContext().getPackageName());
        // Kthen ID-në e burimit të imazhit bazuar në emrin e tij dhe paketën e aplikacionit.

        Log.d("MenuListAdapter", "Image name: " + imageName);  // Logon emrin e imazhit për qëllime debug.
        Log.d("MenuListAdapter", "Image resource ID: " + imageResId);  // Logon ID-në e burimit të imazhit për qëllime debug.

        if (imageResId != 0) {
            // Kontrollon nëse imazhi ekziston në resurset e aplikacionit.
            Log.d("MenuListAdapter", "Loading image from resource ID: " + imageResId);
            // Nëse ekziston, ngarkon imazhin duke përdorur Glide dhe e shfaq në ImageView.
            Glide.with(holder.itemView.getContext())
                    .load(imageResId)
                    .placeholder(R.drawable.default_image)  // Përdor një imazh të paracaktuar ndërsa imazhi po ngarkohet.
                    .error(R.drawable.default_image)  // Përdor një imazh të paracaktuar nëse ngarkimi dështon.
                    .into(holder.thumbImage);  // Vendos imazhin në ImageView të caktuar.
        } else {
            // Nëse imazhi nuk gjendet, logon këtë dhe përdor imazhin e paracaktuar.
            Log.d("MenuListAdapter", "Image not found: " + imageName);
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.default_image)  // Ngarkon imazhin e paracaktuar në ImageView.
                    .into(holder.thumbImage);
        }

        holder.itemView.setOnClickListener(v -> {
            // Vendos një dëgjues për klikimet mbi rreshtin e RecyclerView për të hapur ShowDetailsActivity.
            Intent intent = new Intent(holder.itemView.getContext(), ShowDetailsActivity.class);
            intent.putExtra("PRODUCT_NAME", menu.getName());  // Shton emrin e produktit në Intent.
            intent.putExtra("PRODUCT_PRICE", String.valueOf(menu.getPrice()));  // Shton çmimin e produktit në Intent.
            intent.putExtra("PRODUCT_IMAGE", imageResId);  // Shton ID-në e imazhit të produktit në Intent.
            intent.putExtra("PRODUCT_DESCRIPTION", menu.getDescription());  // Shton përshkrimin e produktit në Intent.
            holder.itemView.getContext().startActivity(intent);  // Fillon aktivitetin ShowDetailsActivity me të dhënat e dërguara.
        });

        holder.addToCartButton.setOnClickListener(v -> {
            // Vendos një dëgjues për klikimet mbi butonin "Add to Cart".
            menu.setTotalinTheCart(1);  // Vendos sasinë fillestare të produktit në karrocë.
            clickListener.onAddToCartClick(menu);  // Thërret metodën për të shtuar produktin në karrocë.
            holder.addMoreLayout.setVisibility(View.VISIBLE);  // Shfaq layout-in për shtim të mëtejshëm.
            holder.addToCartButton.setVisibility(View.GONE);  // Fsheh butonin "Add to Cart" pasi produkti është shtuar.
            holder.tvCount.setText(String.valueOf(menu.getTotalinTheCart()));  // Përditëson tekstin që tregon numrin e produkteve në karrocë.
        });

        holder.imageMinus.setOnClickListener(v -> {
            // Vendos një dëgjues për klikimet mbi butonin "-" për të ulur numrin e produkteve në karrocë.
            int total = menu.getTotalinTheCart();  // Merr numrin aktual të produkteve në karrocë.
            total--;  // Ul numrin me 1.
            if (total > 0) {
                menu.setTotalinTheCart(total);  // Përditëson numrin në objektin Menus.
                clickListener.onUpdateCartClick(menu);  // Thërret metodën për të përditësuar karrocën.
                holder.tvCount.setText(String.valueOf(total));  // Përditëson tekstin që tregon numrin e produkteve në karrocë.
            } else {
                holder.addMoreLayout.setVisibility(View.GONE);  // Fsheh layout-in për shtim të mëtejshëm nëse numri arrin 0.
                holder.addToCartButton.setVisibility(View.VISIBLE);  // Shfaq përsëri butonin "Add to Cart".
                menu.setTotalinTheCart(0);  // Vendos numrin në 0 në objektin Menus.
                clickListener.onRemoveFromCartClick(menu);  // Thërret metodën për të hequr produktin nga karroca.
            }
        });

        holder.imageAddOne.setOnClickListener(v -> {
            // Vendos një dëgjues për klikimet mbi butonin "+" për të shtuar numrin e produkteve në karrocë.
            int total = menu.getTotalinTheCart();  // Merr numrin aktual të produkteve në karrocë.
            total++;  // Rrit numrin me 1.
            if (total <= 10) {
                // Kontrollon nëse numri i produkteve në karrocë është <= 10. Nëse po, rifreskon numrin.
                menu.setTotalinTheCart(total);  // Përditëson numrin në objektin Menus.
                clickListener.onUpdateCartClick(menu);  // Thërret metodën për të përditësuar karrocën.
                holder.tvCount.setText(String.valueOf(total));  // Përditëson tekstin që tregon numrin e produkteve në karrocë.
            }
        });
    }

    @Override
    public int getItemCount() {
        // Kthen numrin total të artikujve në listën e menues.
        return menuList.size();  // Përcakton sa rreshta do të shfaqen në RecyclerView.
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        // Kjo klasë përfaqëson pamjen e çdo rreshti të RecyclerView dhe mban referenca për elementet e tij.
        TextView menuName;
        TextView menuPrice;
        TextView addToCartButton;
        ImageView thumbImage;
        ImageView imageMinus;
        ImageView imageAddOne;
        TextView tvCount;
        LinearLayout addMoreLayout;

        MyViewHolder(View view) {
            super(view);
            // Inicializon elementet e pamjes (View) për një rresht specifik në RecyclerView.
            menuName = view.findViewById(R.id.menuName);  // Lidh TextView për emrin e produktit.
            menuPrice = view.findViewById(R.id.menuPrice);  // Lidh TextView për çmimin e produktit.
            addToCartButton = view.findViewById(R.id.addToCartButton);  // Lidh butonin "Add to Cart".
            thumbImage = view.findViewById(R.id.thumbImage);  // Lidh ImageView për imazhin e produktit.
            imageMinus = view.findViewById(R.id.imageMinus);  // Lidh butonin "-" për të ulur numrin e produkteve.
            imageAddOne = view.findViewById(R.id.imagePlus);  // Lidh butonin "+" për të shtuar numrin e produkteve.
            tvCount = view.findViewById(R.id.Count);  // Lidh TextView që tregon numrin e produkteve në karrocë.
            addMoreLayout = view.findViewById(R.id.addMoreLayout);  // Lidh layout-in që menaxhon shtimin e produkteve.
        }
    }

    public interface MenuListClickListener {
        // Kjo ndërfaqe përkufizon metodat që duhet të implementohen për menaxhimin e klikimeve në menutë e RecyclerView.
        void onAddToCartClick(Menus menus);  // Përcakton metodën që duhet thirrur kur një produkt shtohet në karrocë.
        void onUpdateCartClick(Menus menus);  // Përcakton metodën që duhet thirrur kur përditësohet numri i produkteve në karrocë.
        void onRemoveFromCartClick(Menus menus);  // Përcakton metodën që duhet thirrur kur një produkt hiqet nga karroca.
    }
}