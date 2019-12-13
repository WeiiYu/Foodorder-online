
package com.foodie.app.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodie.app.R;
import com.foodie.app.model.WorksInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class MyMessageFragment extends Fragment {
    protected static final String TAG = "MessageFragment";

    private SwipeRefreshLayout sr;
    private TextView noDataView;
// private variables
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(
                R.layout.fragment_message_me, container, false);
        sr = (SwipeRefreshLayout) view.findViewById(R.id.sr);
        noDataView = (TextView) view.findViewById(R.id.no_data);
        return view;
    }


    public static class MessageListAdapter
            extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {
        private List<WorksInfo> worksList;
        private Context context;
        private static final int IS_HEADER = 2;
        private static final int IS_NORMAL = 1;
        //private boolean isUserLogin=getUserState();
        private MyItemClickListener myItemClickListener = new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int type) {
                switch (type) {
                    case 1:
                        TextView v = (TextView) view;
                        //v.setText();
                        break;
                }
            }
        };


        public interface MyItemClickListener {
            void onItemClick(View view, int postion, int type);
        }

        public void setMyItemClickListener(MyItemClickListener listener) {
            myItemClickListener = listener;
        }

        public void setWorks(List<WorksInfo> works) {
            this.worksList = works;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public int mViewType;
            public View mView;
            public TextView mPostTimeView;
            public ImageView mWorksImageView;
            public TextView mIntroductionView;
            public MyItemClickListener mOnClickListener;

            public ViewHolder(View view, int viewType, MyItemClickListener listener) {
                super(view);
                mView = view;
                mViewType = viewType;
                if (viewType == IS_HEADER) {
                    return;
                }
                mPostTimeView = (TextView) view.findViewById(R.id.tv_post_time);
                mWorksImageView = (ImageView) view.findViewById(R.id.iv_works_image);
                mIntroductionView = (TextView) view.findViewById(R.id.tv_introduction);
                mOnClickListener = listener;
                //init();
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                }
            }
        }

        public MessageListAdapter(Context context, List<WorksInfo> works) {
            super();
            this.context = context;
            this.worksList = works;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            //RecyclerViewHolder holder;
            if (viewType == IS_HEADER) {
                View view = LayoutInflater.from(context).inflate(R.layout.message_header, parent, false);
                return new ViewHolder(view, IS_HEADER, myItemClickListener);
            } else if (viewType == IS_NORMAL) {
                View view = LayoutInflater.from(context)
                        .inflate(R.layout.list_item_works_me, parent, false);
                return new ViewHolder(view, IS_NORMAL, myItemClickListener);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            DisplayImageOptions worksImageOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.loading_large)
                    .showImageOnFail(R.drawable.recipe)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
            ImageLoader.getInstance().displayImage(worksList.get(position).getThumbnail(), holder.mWorksImageView, worksImageOptions);

            holder.mIntroductionView.setText(worksList.get(position).getIntroduction());
            holder.mPostTimeView.setText(worksList.get(position).getCreateTime());
        }

        @Override
        public int getItemCount() {
            return worksList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return IS_NORMAL;
        }
    }

}
