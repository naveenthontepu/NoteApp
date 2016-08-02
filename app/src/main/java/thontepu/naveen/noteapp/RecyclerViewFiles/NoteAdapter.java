package thontepu.naveen.noteapp.RecyclerViewFiles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import thontepu.naveen.noteapp.DBfiles.NotePojo;
import thontepu.naveen.noteapp.R;
import thontepu.naveen.noteapp.Utils.Utilities;

/**
 * Created by mac on 8/2/16.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder>  {
    LayoutInflater inflater;
    Context context;
    Utilities utilities;
    List<NotePojo> data = Collections.emptyList();
    RecyclerView myRidersRecyclerView;
    int viewPosition = 0;
    ItemClickInterface itemClickInterface;

    public NoteAdapter(Context context, List<NotePojo> data,RecyclerView recyclerView) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(this.context);
        utilities = new Utilities();
        myRidersRecyclerView = recyclerView;
    }

    public void setItemClickInterface(ItemClickInterface itemClickInterface){
        this.itemClickInterface = itemClickInterface;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowLayout = inflater.inflate(R.layout.note_item_view,parent,false);
        NoteViewHolder noteViewHolder = new NoteViewHolder(rowLayout,itemClickInterface);
        return noteViewHolder;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.noteTitle.setText(data.get(position).getTitle());
        holder.noteDescription.setText(data.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
