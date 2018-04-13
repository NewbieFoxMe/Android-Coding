package club.playerfox.material_test;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Fruit implements Parcelable{
    private String name;
    private int resId;

    public Fruit(String name, int resId) {
        this.name = name;
        this.resId = resId;
    }

    protected Fruit(Parcel in) {
        name = in.readString();
        resId = in.readInt();
    }

    public static final Creator<Fruit> CREATOR = new Creator<Fruit>() {
        @Override
        public Fruit createFromParcel(Parcel in) {
            return new Fruit(in);
        }

        @Override
        public Fruit[] newArray(int size) {
            return new Fruit[size];
        }
    };

    public String getName() {
        return name;
    }

    public int getResId() {
        return resId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(resId);
    }
}
