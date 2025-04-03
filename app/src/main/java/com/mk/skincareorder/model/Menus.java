package com.mk.skincareorder.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Menus implements Parcelable {
    // Kjo klasë përfaqëson një model për një menu të një produkti dhe implementon ndërfaqen Parcelable,
    // e cila lejon kalimin e objekteve midis aktiviteteve.

    private String name;
    // Emri i produktit.

    private float price;
    // Çmimi i produktit.

    private String url;
    // URL-ja që mund të përfaqësojë një lidhje për më shumë informacion rreth produktit.

    private String imageName; // Fusha për emrin e burimit drawable të imazhit.
    // Emri i skedarit të imazhit që përfaqëson produktin.

    private String description; // Fusha e re për përshkrimin e produktit.
    // Përshkrimi i produktit.

    private int TotalinTheCart;
    // Numri total i produkteve të këtij lloji në karrocë.

    // Konstruktori për të krijuar një objekt të klasës Menus me të gjitha fushat e saj.
    public Menus(String name, float price, String url, String imageName, String description) {
        this.name = name;
        this.price = price;
        this.url = url;
        this.imageName = imageName;
        this.description = description;
    }

    // Konstruktori që përdoret për të krijuar një objekt Menus nga një Parcel.
    protected Menus(Parcel in) {
        name = in.readString();
        price = in.readFloat();
        url = in.readString();
        imageName = in.readString(); // Lexon emrin e imazhit nga Parcel.
        description = in.readString(); // Lexon përshkrimin nga Parcel.
        TotalinTheCart = in.readInt();
    }

    public static final Creator<Menus> CREATOR = new Creator<Menus>() {
        // CREATOR është një objekt i përdorur për të krijuar objekte Menus nga një Parcel.

        @Override
        public Menus createFromParcel(Parcel in) {
            return new Menus(in);
            // Krijon një instancë të Menus nga një Parcel.
        }

        @Override
        public Menus[] newArray(int size) {
            return new Menus[size];
            // Krijon një array të objekteve Menus me madhësi të dhënë.
        }
    };

    @Override
    public int describeContents() {
        return 0;
        // Përshkruan përmbajtjen e parcelës, 0 do të thotë se nuk ka objekte të veçanta të përfshira.
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        // Shkruan të dhënat e objektit në një Parcel për t'u kaluar midis aktiviteteve.
        parcel.writeString(name);
        parcel.writeFloat(price);
        parcel.writeString(url);
        parcel.writeString(imageName); // Shkruan emrin e imazhit në Parcel.
        parcel.writeString(description); // Shkruan përshkrimin në Parcel.
        parcel.writeInt(TotalinTheCart);
    }

    // Metodat getter dhe setter për secilën fushë të klasës.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDescription() { // Getter për përshkrimin.
        return description;
    }

    public void setDescription(String description) { // Setter për përshkrimin.
        this.description = description;
    }

    public int getTotalinTheCart() {
        return TotalinTheCart;
    }

    public void setTotalinTheCart(int totalinTheCart) {
        TotalinTheCart = totalinTheCart;
    }
}
