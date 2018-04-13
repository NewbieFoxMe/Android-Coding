package club.playerfox.material_test;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
    private Context context;
    private List<Fruit> fruits;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        ViewHolder holder = new ViewHolder(view);

        holder.cardView.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            Fruit fruit = fruits.get(position);
            Intent intent = new Intent(context,FruitActivity.class);
            intent.putExtra("Fruit",fruit);

            context.startActivity(intent);
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit = fruits.get(position);
        holder.fruitName.setText(fruit.getName());
        Glide.with(context).load(fruit.getResId()).into(holder.fruitImage);
    }

    @Override
    public int getItemCount() {
        return fruits.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            fruitImage = itemView.findViewById(R.id.fruit_image);
            fruitName = itemView.findViewById(R.id.fruit_name);
        }
    }

    public FruitAdapter(List<Fruit> fruits) {
        this.fruits = fruits;
    }
}
