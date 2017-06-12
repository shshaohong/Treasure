package com.feicuiedu.hunttreasure.treasure.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.feicuiedu.hunttreasure.custom.TreasureView;
import com.feicuiedu.hunttreasure.treasure.Treasure;
import com.feicuiedu.hunttreasure.treasure.detail.TreasureDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gqq on 2017/1/12.
 */

// RecycleView的适配器
public class TreasureListAdapter extends RecyclerView.Adapter<TreasureListAdapter.MyViewHolder>{

    private ArrayList<Treasure> data = new ArrayList<>();

    public void addItemData(List<Treasure> list){
        data.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TreasureView treasureView = new TreasureView(parent.getContext());
        return new MyViewHolder(treasureView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Treasure treasure = data.get(position);
        holder.mTreasureView.bindTreasure(treasure);
        holder.mTreasureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击宝藏信息卡片的时候，跳转到宝藏的详细页面
                TreasureDetailActivity.open(v.getContext(),treasure);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TreasureView mTreasureView;

        public MyViewHolder(TreasureView itemView) {
            super(itemView);
            this.mTreasureView = itemView;
        }
    }

}
