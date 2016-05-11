package com.insthub.ecmobilemanager.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.insthub.BeeFramework.adapter.BeeBaseAdapter;
import com.insthub.ecmobilemanager.R;
import com.insthub.ecmobilemanager.component.GoodItemLargeCell;
import com.insthub.ecmobilemanager.protocol.GOODS;

import java.util.ArrayList;

public class D0_GoodsListLargeAdapter extends BeeBaseAdapter
{
    public D0_GoodsListLargeAdapter(Context c, ArrayList dataList)
    {
        super(c, dataList);
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount()
    {
        return dataList.size();
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position)
    {
        return null;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position)
    {
        return 0;
    }
    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h)
    {
        GOODS goods = (GOODS)dataList.get(position);
        ((GoodItemLargeCell)cellView).bindData(goods);
        return null;
    }

    @Override
    public View createCellView()
    {
        return mInflater.inflate(R.layout.d0_goods_large_cell, null);
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView)
    {
        return null;
    }
}
