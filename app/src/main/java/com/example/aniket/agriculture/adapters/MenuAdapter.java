package com.example.aniket.agriculture.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aniket.agriculture.model_classes.MenuListItem;
import com.example.aniket.agriculture.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Context context;
    private List<MenuListItem> listItems;
    private int[] itemCount;
    private String[] weights;
    private double[] w;
    private TextView notify;
    static public int ctr = 0;

    public MenuAdapter(List<MenuListItem> listItems, Context context,int[] itemCount,String[] weights,double[] w,TextView notify) {
        this.listItems = listItems;
        this.context = context;
        this.itemCount = itemCount;
        this.weights = weights;
        this.w = w;
        this.notify = notify;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.crop_menu, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuAdapter.ViewHolder holder, int position) {
        final MenuListItem listItem = listItems.get(position);

        holder.name.setText(listItem.getName());
        Glide.with(context).load(listItem.getImage()).into(holder.image);
        holder.price.setText(listItem.getPrice());
        holder.count.setText(listItem.getCount());

        ArrayAdapter<String> weightAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, weights);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(weightAdapter);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                    String inc;
                    inc = listItem.getCount();
                    Integer cnt = Integer.parseInt(inc);
                    cnt++;
                    ctr++;
                    inc = Integer.toString(cnt);
                    listItem.setCount(inc);
                    holder.count.setText(listItem.getCount());
                    if(ctr >= 0){
                        notify.setVisibility(View.VISIBLE);
                        notify.setText(Integer.toString(ctr));
                    }
                    itemCount[holder.getAdapterPosition()] = cnt;//stores the amount ordered at the specific index
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dec;
                dec = listItem.getCount();
                Integer cnt = Integer.parseInt(dec);
                if(cnt!=0){
                    cnt--;
                    ctr--;
                } else
                    Toast.makeText(context, "Count cannot be less than zero", Toast.LENGTH_SHORT).show();

                dec = Integer.toString(cnt);
                listItem.setCount(dec);
                holder.count.setText(listItem.getCount());
                if(ctr >= 0){
                    notify.setVisibility(View.VISIBLE);
                    notify.setText(Integer.toString(ctr));
                }
                itemCount[holder.getAdapterPosition()] = cnt;//stores the amount ordered at the specific index
            }
        });

        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        w[holder.getAdapterPosition()] = 0.25;
                        break;
                    case 1:
                        w[holder.getAdapterPosition()] = 0.5;
                        break;
                    case 2:
                        w[holder.getAdapterPosition()] = 1;
                        break;
                    case 3:
                        w[holder.getAdapterPosition()] = 5;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                w[holder.getAdapterPosition()] = 0;
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView image;
        public CardView menuView;
        public Spinner spinner;
        public ImageButton add;
        public ImageButton remove;
        public TextView price;
        public TextView count;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            spinner = (Spinner) itemView.findViewById(R.id.spinner);
            menuView = (CardView) itemView.findViewById(R.id.menu);
            name = (TextView) itemView.findViewById(R.id.crop_name);
            image = (ImageView) itemView.findViewById(R.id.crop_image);
            add = (ImageButton)itemView.findViewById(R.id.add);
            remove = (ImageButton)itemView.findViewById(R.id.remove);
            price = (TextView)itemView.findViewById(R.id.price);
            count = (TextView)itemView.findViewById(R.id.count);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);

        }
    }
}
