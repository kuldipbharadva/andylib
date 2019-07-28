package com.example.libusage.dbRoom;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.libusage.R;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private ArrayList<NoteModel> noteModelArrayList;
    private OnItemClickListener onItemClickListener;

    public NoteAdapter(ArrayList<NoteModel> noteModelArrayList) {
        this.noteModelArrayList = noteModelArrayList;
    }

    public void onClickItem(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_room_note_item, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteHolder holder, int position) {
        NoteModel note = noteModelArrayList.get(position);
        holder.tvNoteTitle.setText(note.getTitle());
        holder.tvNote.setText(note.getContent());

        holder.btnDelete.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClickItem(holder, R.id.btnDelete);
            }
        });

        holder.btnEdit.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClickItem(holder, R.id.btnEdit);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteModelArrayList != null ? noteModelArrayList.size() : 0;
    }

    public class NoteHolder extends RecyclerView.ViewHolder {

        private TextView tvNoteTitle, tvNote;
        private Button btnDelete, btnEdit;

        NoteHolder(@NonNull View itemView) {
            super(itemView);
            tvNoteTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvNote = itemView.findViewById(R.id.tvNote);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }

    public interface OnItemClickListener {
        void onClickItem(NoteHolder holder, int view);
    }
}
