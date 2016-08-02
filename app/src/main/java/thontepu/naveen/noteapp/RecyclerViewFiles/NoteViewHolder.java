package thontepu.naveen.noteapp.RecyclerViewFiles;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import thontepu.naveen.noteapp.R;
import thontepu.naveen.noteapp.RecyclerViewFiles.ItemClickInterface;

/**
 * Created by mac on 8/2/16.
 */
public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView noteTitle;
    TextView noteDescription;
    CardView itemCardView;
    ItemClickInterface itemClickInterface;
    RelativeLayout itemRL;

    public NoteViewHolder(View itemView,ItemClickInterface itemClickInterface) {
        super(itemView);
        noteTitle = (TextView)itemView.findViewById(R.id.noteTitle);
        itemRL = (RelativeLayout)itemView.findViewById(R.id.itemRL);
        noteDescription = (TextView)itemView.findViewById(R.id.noteDescription);
        itemCardView = (CardView) itemView.findViewById(R.id.itemCardView);
        itemCardView.setOnClickListener(this);
        this.itemClickInterface = itemClickInterface;
    }

    @Override
    public void onClick(View view) {
        if (itemClickInterface!=null){
            itemClickInterface.onItemClickListener(view,getAdapterPosition());
        }
    }
}
