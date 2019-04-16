package com.example.eventkeeper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchRecyclerView extends RecyclerView.Adapter<SearchRecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<GroupItem> mGroupList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public SearchRecyclerView(Context context, ArrayList<GroupItem> GroupList) {
        mContext = context;
        mGroupList = GroupList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        GroupItem currentItem = mGroupList.get(i);

        String group = currentItem.getGroup();
        String location = currentItem.getLocation();

        viewHolder.mTextViewGroup.setText(group);
        viewHolder.mTextViewLocation.setText(location);
    }

    @Override
    public int getItemCount() {
        return mGroupList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextViewGroup;
        public TextView mTextViewLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewGroup = itemView.findViewById(R.id.GroupName);
            mTextViewLocation = itemView.findViewById(R.id.Location);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
