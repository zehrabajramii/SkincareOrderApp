package com.mk.skincareorder.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SkincareModel implements Parcelable {
    // Kjo klasë përfaqëson një model për një dyqan kujdesi për lëkurën dhe implementon ndërfaqen Parcelable,
    // e cila lejon kalimin e objekteve midis aktiviteteve.

    private String name;
    // Emri i dyqanit të kujdesit për lëkurën.

    private String address;
    // Adresa e dyqanit.

    private float delivery_charge;
    // Tarifa e dorëzimit për dyqanin.

    private String imageName;  // Përditësuar për të përputhur fushën e JSON.
    // Emri i skedarit të imazhit që përfaqëson dyqanin.

    private Hours hours;
    // Një objekt i klasës Hours që përmban orët e funksionimit të dyqanit.

    private List<Menus> menus;
    // Një listë e objekteve Menus që përfaqëson menunë e dyqanit.

    protected SkincareModel(Parcel in) {
        // Konstruktori që përdoret për të krijuar një objekt SkincareModel nga një Parcel.
        name = in.readString();
        address = in.readString();
        delivery_charge = in.readFloat();
        imageName = in.readString();  // Përditësuar për të përputhur fushën e JSON.
        menus = in.createTypedArrayList(Menus.CREATOR);
    }

    public static final Creator<SkincareModel> CREATOR = new Creator<SkincareModel>() {
        // CREATOR është një objekt i përdorur për të krijuar objekte SkincareModel nga një Parcel.

        @Override
        public SkincareModel createFromParcel(Parcel in) {
            return new SkincareModel(in);
            // Krijon një instancë të SkincareModel nga një Parcel.
        }

        @Override
        public SkincareModel[] newArray(int size) {
            return new SkincareModel[size];
            // Krijon një array të objekteve SkincareModel me madhësi të dhënë.
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
        parcel.writeString(address);
        parcel.writeFloat(delivery_charge);
        parcel.writeString(imageName);  // Përditësuar për të përputhur fushën e JSON.
        parcel.writeTypedList(menus);
    }

    // Metodat getter dhe setter për secilin fushë të klasës.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(float delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public String getImageName() {  // Përditësuar për të përputhur fushën e JSON.
        return imageName;
    }

    public void setImageName(String imageName) {  // Përditësuar për të përputhur fushën e JSON.
        this.imageName = imageName;
    }

    public Hours getHours() {
        return hours;
    }

    public void setHours(Hours hours) {
        this.hours = hours;
    }

    public List<Menus> getMenus() {
        return menus;
    }

    public void setMenus(List<Menus> menus) {
        this.menus = menus;
    }
}
