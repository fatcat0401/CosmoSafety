package ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.example.cosmosafety.Interface.ItemClickListener;
import com.example.cosmosafety.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView categoryName, checkMark;
    public boolean isChecked;
    ItemClickListener itemClickListener;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        isChecked = false;
        categoryName = (TextView)itemView.findViewById(R.id.categoryName_VH);
        checkMark = (TextView)itemView.findViewById(R.id.using_checked);
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