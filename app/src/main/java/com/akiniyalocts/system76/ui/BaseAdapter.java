package com.akiniyalocts.system76.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.akiniyalocts.system76.R;
import com.akiniyalocts.system76.model.Item;
import com.github.florent37.picassopalette.PicassoPalette;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by anthony on 10/6/15.
 */
public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.BaseAdapterViewHolder> {
    private List<Item> mItems;
    private LayoutInflater mInflater;
    private Context mContext;
    private OnItemClickedListener listener;

    public BaseAdapter(List mItems, Context mContext) {
        this.mItems = mItems;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setListener(OnItemClickedListener listener){
        this.listener = listener;
    }

    @Override
    public BaseAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item, parent, false);
        return new BaseAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseAdapterViewHolder holder, int position) {
        Item item = mItems.get(position);

        holder.mTitle.setText(item.getTitle());

        holder.mDesc.setText(Html.fromHtml(item.getDescription()));

        Picasso.with(mContext).load(item.getImageLink())
                .fit().centerInside()
                .into(holder.mImage,
                        PicassoPalette.with(item.getImageLink(), holder.mImage)
                                .use(PicassoPalette.Profile.MUTED)
                                .intoBackground(holder.mFrame)

                );
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class BaseAdapterViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.title_frame)
        FrameLayout mFrame;

        @Bind(R.id.item_title)
        TextView mTitle;

        @Bind(R.id.item_image)
        ImageView mImage;

        @Bind(R.id.item_desc)
        TextView mDesc;

        @Bind(R.id.item_parent)
        CardView mParent;

        public BaseAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.item_parent)
        public void onParentClicked(){
            if(listener != null){
                listener.onItemClick(mItems.get(getAdapterPosition()), mParent);
            }
        }
    }



    public interface OnItemClickedListener{
        void onItemClick(Item item, View parent);
    }
}
