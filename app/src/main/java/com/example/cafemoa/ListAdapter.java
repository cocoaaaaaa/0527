package com.example.cafemoa;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

//카페리스트 어댑터

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.CustomViewHolder> {

    private ArrayList<ListData> mList = null;
    private Activity context = null;


    public ListAdapter(Activity context, ArrayList<ListData> list1) {
        this.context = context;
        this.mList = list1;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView address;
  /*  protected TextView businessHour;
    protected TextView emptySeats;
    protected TextView instargram;
    protected TextView phone;
    protected TextView inform;
*/

        public CustomViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.cafename);
            this.address = (TextView) view.findViewById(R.id.cafeaddress);
       /* this.businessHour = (TextView) view.findViewById(R.id.cafehour);
        this. emptySeats = (TextView) view.findViewById(R.id.cafeseats);
        this.instargram = (TextView) view.findViewById(R.id.cafeinsta);
        this.phone= (TextView) view.findViewById(R.id.cafephone);
        this.inform = (TextView) view.findViewById(R.id.cafeinform);
*/
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cafe_list2, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {


        viewholder.name.setText(mList.get(position).getCafe_name());
        viewholder.address.setText(mList.get(position).getCafe_address());
    /*    viewholder.businessHour.setText(mList.get(position).getCafe_businessHour());
        viewholder.emptySeats.setText(mList.get(position).getCafe_emptySeats());
        viewholder.instargram.setText(mList.get(position).getCafe_instargram());
        viewholder.phone.setText(mList.get(position).getCafe_phone());
        viewholder.inform.setText(mList.get(position).getCafe_inform());
*/

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
