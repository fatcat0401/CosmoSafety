package ViewHolder;


import android.view.View;
import android.widget.TextView;

import com.example.cosmosafety.Interface.ItemClickListener;
import com.example.cosmosafety.R;

import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView productName, company, tvCategory, rating;
    ItemClickListener itemClickListener;

    public ProductViewHolder(View itemView) {
        super(itemView);

        productName = (TextView) itemView.findViewById(R.id.productName);
        company = (TextView) itemView.findViewById(R.id.company);
        tvCategory = (TextView) itemView.findViewById(R.id.category);
        rating = (TextView) itemView.findViewById(R.id.rating);
    }

    public void setItemClickListener(ItemClickListener listener)
    {
        this.itemClickListener = listener;
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(itemView,getAdapterPosition(),false);
    }

}

