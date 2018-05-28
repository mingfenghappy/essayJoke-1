package com.fancenxing.fanchen.essayjoke;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fancenxing.fanchen.essayjoke.recycelrView.GridLayoutItemDecoration;
import com.fancenxing.fanchen.essayjoke.recycelrView.wrap.WrapRecyclerAdapter;
import com.fancenxing.fanchen.essayjoke.recycelrView.wrap.WrapRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BasicActivity extends AppCompatActivity {

    WrapRecyclerView recyclerView;

    private WrapRecyclerAdapter mAdapter;
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        recyclerView = (WrapRecyclerView) findViewById(R.id.recyclerView);

        mList = new ArrayList<>();
        for (int i = 'A'; i < 'Y'; i++) {
            mList.add((char) i + "");
        }
        RecyclerAdapter adapter = new RecyclerAdapter();
        mAdapter = new WrapRecyclerAdapter(adapter);
        recyclerView.setAdapter(mAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        GridLayoutItemDecoration decoration = new GridLayoutItemDecoration(this, R.drawable.divider_linear);
        decoration.setSize(1);
        recyclerView.addItemDecoration(decoration);
        View header = LayoutInflater.from(this).inflate(R.layout.header, recyclerView, false);
        recyclerView.addHeaderView(header);
        recyclerView.addFooterView(header);
//        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
//            @Override
//            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//                int swipeFlags = ItemTouchHelper.LEFT;
//                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//                return makeMovementFlags(dragFlags, swipeFlags);
//            }
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                int fromPosition = viewHolder.getAdapterPosition();
//                int targetPosition = target.getAdapterPosition();
//                mAdapter.notifyItemMoved(fromPosition, targetPosition);
//                return false;
//            }
//
//            @Override
//            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
//                super.onSelectedChanged(viewHolder, actionState);
//                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
//                    viewHolder.itemView.setBackgroundColor(Color.RED);
//                }
//            }
//
//            @Override
//            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//                super.clearView(recyclerView, viewHolder);
//                viewHolder.itemView.setBackgroundColor(Color.WHITE);
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                int currentSwipePosition = viewHolder.getAdapterPosition();
//                Log.e("sun", "position--->" + currentSwipePosition);
//                if (currentSwipePosition < mList.size()) {
//                    mAdapter.notifyItemRemoved(currentSwipePosition);
//                    mList.remove(currentSwipePosition);
//                }
//
//            }
//        });
//        touchHelper.attachToRecyclerView(recyclerView);
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(BasicActivity.this)
                    .inflate(R.layout.item_recycler, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
            holder.tvContent.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvContent;

            public ViewHolder(View itemView) {
                super(itemView);
                tvContent = itemView.findViewById(R.id.tv_content);
            }

        }
    }

}
