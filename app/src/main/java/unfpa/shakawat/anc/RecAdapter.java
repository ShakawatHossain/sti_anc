package unfpa.shakawat.anc;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import unfpa.shakawat.anc.model.Participant;
import unfpa.shakawat.anc.model.Pts;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.MyViewHolder>{
    Context ctx;
    ListActivity listActivity;
    RecyclerView recyclerView;
    ArrayList<Pts> ptsArrayList;

    public RecAdapter(Context context,RecyclerView recyclerView,ListActivity listActivity,ArrayList<Pts> listDataStores){
        this.ctx = context;
        this.listActivity = listActivity;
        this.ptsArrayList = listDataStores;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list,parent,false);
        viewHolder.setOnClickListener(recListen);
        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pts pts = ptsArrayList.get(position);
        holder.pt_num.setText(pts.pt_num);
        holder.mob.setText(pts.mob);
        holder.hos_name.setText(pts.hos_name);
        holder.age.setText(String.valueOf(pts.age));
        holder.pt_doc.setText(pts.pt_doc);
    }

    @Override
    public int getItemCount() {
        return ptsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mob,hos_name,pt_num,age,pt_doc;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pt_num= (TextView) itemView.findViewById(R.id.pt_num);
            mob= (TextView) itemView.findViewById(R.id.mob);
            hos_name= (TextView) itemView.findViewById(R.id.hos_name);
            age= (TextView) itemView.findViewById(R.id.age);
            pt_doc= (TextView) itemView.findViewById(R.id.pt_doc);
        }
    }

    public View.OnClickListener recListen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = recyclerView.getChildAdapterPosition(v);
            Pts pts = ptsArrayList.get(position);
            Participant.id=pts.id;
            Participant.hos_num=pts.hos_num;
            Participant.hos_name=pts.hos_name;
            Participant.num=pts.num;
            Participant.age=pts.age;
            Participant.edu_year=pts.edu_year;
            Participant.edu_oth_txt=pts.edu_oth_txt;
            Participant.ocu=pts.ocu;
            Participant.hocu=pts.hocu;
            Participant.income=pts.income;
            Participant.add=pts.add;
            Participant.mob=pts.mob;
            Participant.dg=pts.dg;
            Participant.child_num=pts.child_num;
            Participant.preg_comp_oth_txt=pts.preg_comp_oth_txt;
            Participant.sm_oth_txt=pts.sm_oth_txt;
            Participant.pt_num=pts.pt_num;
            Participant.pt_doc=pts.pt_doc;
            Participant.sc=pts.sc;
            Participant.is_married=pts.is_married;
            Participant.live_husband=pts.live_husband;
            Participant.edu=pts.edu;
            Participant.have_child=pts.have_child;
            Participant.preg_comp=pts.preg_comp;
            Participant.pt_dis=pts.pt_dis;
            Participant.use_condom=pts.use_condom;
            Participant.transmission=pts.transmission;
            Participant.sm_anc=pts.sm_anc;
            Participant.sm_vd=pts.sm_vd;
            Participant.sm_vi=pts.sm_vi;
            Participant.sm_lap=pts.sm_lap;
            Participant.sm_gu=pts.sm_gu;
            Participant.sm_pu=pts.sm_pu;
            Participant.sm_pi=pts.sm_pi;
            Participant.sm_oth=pts.sm_oth;
            Participant.sm_vd_day=pts.sm_vd_day;
            Participant.sm_vi_day=pts.sm_vi_day;
            Participant.sm_lap_day=pts.sm_lap_day;
            Participant.sm_gu_day=pts.sm_gu_day;
            Participant.sm_pu_day=pts.sm_pu_day;
            Participant.sm_pi_day=pts.sm_pi_day;
            Participant.sm_oth_day=pts.sm_oth_day;
            Participant.multi_sex=pts.multi_sex;
            Participant.his_sti=pts.his_sti;
            ctx.startActivity(new Intent(ctx,Questionnaire.class));
        }
    };
}
