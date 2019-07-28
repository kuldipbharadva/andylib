package com.example.libusage.dbRoom;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.libusage.R;

import java.util.ArrayList;

public class RoomActivity extends AppCompatActivity {

    private Context mContext;

    private EditText etTitle, etNote;
    private TextView tvNoData;
    private Button btnAdd, btnAddAll, btnDeleteAll;
    private RecyclerView rvNoteList;

    private NoteDatabase noteDatabase;
    private NoteAdapter noteAdapter;

    private ArrayList<NoteModel> noteModelArrayList;

    private int isEditPosition;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        initBasic();
        initCasting();
        initEventHandler();
        initGeneralTask();
    }

    private void initBasic() {
        mContext = RoomActivity.this;
        noteDatabase = NoteDatabase.getInstance(mContext);
    }

    private void initCasting() {
        rvNoteList = findViewById(R.id.rvNoteList);
        etTitle = findViewById(R.id.etTitle);
        etNote = findViewById(R.id.etNote);
        tvNoData = findViewById(R.id.tvNoData);
        btnAdd = findViewById(R.id.btnAdd);
        btnAddAll = findViewById(R.id.btnAddAll);
        btnDeleteAll = findViewById(R.id.btnDeleteAll);
    }

    private void initEventHandler() {
        btnAdd.setOnClickListener(v -> {
            if (isEdit) {
                editRecord();
            } else {
                addRecordInNote();
            }
        });

        btnAddAll.setOnClickListener(v -> addListInTable());

        btnDeleteAll.setOnClickListener(v -> deleteAllRecord());
    }

    private void initGeneralTask() {
        getAllNotes();
    }

    /* this function validate field before add new note in database */
    private boolean isValidate() {
        if (etTitle.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "please add title", Toast.LENGTH_SHORT).show();
            etTitle.requestFocus();
            return false;
        }
        if (etNote.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "please add your note", Toast.LENGTH_SHORT).show();
            etNote.requestFocus();
            return false;
        }
        return true;
    }

    /* this function reset field and make focus on edit text title box */
    private void clearField() {
        etTitle.requestFocus();
        etTitle.setText("");
        etNote.setText("");
    }

    /* this function insert record in database table */
    private void addRecordInNote() {
        if (isValidate()) {
            if (noteDatabase.getNoteDao().isExist(etTitle.getText().toString().trim())) {
                Toast.makeText(mContext, "Already exist", Toast.LENGTH_SHORT).show();
                return;
            }
            NoteModel noteModel = new NoteModel(etTitle.getText().toString().trim(), etNote.getText().toString().trim());
            noteDatabase.getNoteDao().insert(noteModel);
            clearField();
            getAllNotes();
        }
    }

    /* this function insert list in database table */
    private void addListInTable() {
        ArrayList<NoteModel> myList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            NoteModel noteModel = new NoteModel("Title " + i, "My Note " + i);
            myList.add(noteModel);
        }
        noteDatabase.getNoteDao().insertAll(myList);
        getAllNotes();
    }

    /* this function get all notes/records from database table */
    private void getAllNotes() {
        noteModelArrayList = (ArrayList<NoteModel>) noteDatabase.getNoteDao().getAllNotes();
        noteAdapter = new NoteAdapter(noteModelArrayList);
        rvNoteList.setAdapter(noteAdapter);
        rvNoteList.setLayoutManager(new LinearLayoutManager(mContext));
        rvNoteList.setHasFixedSize(true);
        onAdapterItemClick();
        setNoDataView();
    }

    /* this function used for show/hide no data message */
    private void setNoDataView() {
        if (noteModelArrayList.size() > 0) {
            rvNoteList.setVisibility(View.VISIBLE);
            tvNoData.setVisibility(View.GONE);
        } else {
            rvNoteList.setVisibility(View.GONE);
            tvNoData.setVisibility(View.VISIBLE);
        }
    }

    /* this function handle item click event */
    private void onAdapterItemClick() {
        noteAdapter.onClickItem((holder, view) -> {
            switch (view) {
                case R.id.btnEdit:
                    isEdit = true;
                    isEditPosition = holder.getAdapterPosition();
                    etTitle.setText(noteModelArrayList.get(holder.getAdapterPosition()).getTitle());
                    etNote.setText(noteModelArrayList.get(holder.getAdapterPosition()).getContent());
                    break;
                case R.id.btnDelete:
                    deleteRecord(holder.getAdapterPosition());
                    break;
            }
        });
    }

    /* this function update record in table */
    private void editRecord() {
        if (isValidate()) {
            noteDatabase.getNoteDao().update(new NoteModel(etTitle.getText().toString().trim(), etNote.getText().toString().trim()));

            noteModelArrayList.get(isEditPosition).setTitle(etTitle.getText().toString().trim());
            noteModelArrayList.get(isEditPosition).setContent(etNote.getText().toString().trim());
            noteAdapter.notifyDataSetChanged();

            isEdit = false;
            clearField();
        }
    }

    /* this function delete record from table */
    private void deleteRecord(int position) {
        if (noteModelArrayList != null) {
            noteDatabase.getNoteDao().delete(noteModelArrayList.get(position));
            noteModelArrayList.remove(position);
            noteAdapter.notifyDataSetChanged();
            setNoDataView();
        }
    }

    /* this function delete record from table */
    private void deleteAllRecord() {
        if (noteModelArrayList != null) {
            noteDatabase.getNoteDao().deleteAll(noteModelArrayList);
            noteModelArrayList.clear();
            noteAdapter.notifyDataSetChanged();
            setNoDataView();
        }
    }

    @Override
    protected void onDestroy() {
        noteDatabase.cleanUp();
        super.onDestroy();
    }
}
