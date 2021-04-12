package unfpa.shakawat.anc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import unfpa.shakawat.anc.model.Participant;
import unfpa.shakawat.anc.model.Pts;

public class ListActivity extends AppCompatActivity {
    RecyclerView rec;
    RecAdapter recAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Pts> ptsArrayList;
    ProgressBar progressBar;
    FloatingActionButton fab;
    boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();
    }

    private void init(){
        rec=(RecyclerView) findViewById(R.id.rec);
        ptsArrayList = new ArrayList<Pts>();
        recAdapter = new RecAdapter(ListActivity.this,rec,ListActivity.this,ptsArrayList);
        linearLayoutManager = new LinearLayoutManager(ListActivity.this);
        progressBar = (ProgressBar) findViewById(R.id.pBar);
        rec.setLayoutManager(linearLayoutManager);
        rec.setAdapter(recAdapter);
        getData();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Participant.clear();
                startActivity(new Intent(ListActivity.this,Questionnaire.class));
            }
        });
    }
    private void getData(){
        btn_toggle();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://119.148.17.100:8080/sti/get_anc.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        btn_toggle();
                        Log.d("Respnose",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            if (success==1){
                                if(((TextView) findViewById(R.id.no_data)).getVisibility()==View.VISIBLE){
                                    ((TextView) findViewById(R.id.no_data)).setVisibility(View.GONE);
                                }
                                JSONArray jsonArray = jsonObject.getJSONArray("participants");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jobj = jsonArray.getJSONObject(i);
                                    Pts pts = new Pts();
                                    pts.id= jobj.getInt("id");
                                    pts.hos_num= jobj.getInt("hos_num");
                                    pts.hos_name= jobj.getString("hos_name");
                                    pts.num= jobj.getString("num");
                                    pts.age= jobj.getInt("age");
                                    pts.edu_year= jobj.getInt("edu_year");
                                    pts.edu_oth_txt= jobj.getString("edu_oth_txt");
                                    pts.ocu= jobj.getString("ocu");
                                    pts.hocu= jobj.getString("hocu");
                                    pts.income= jobj.getInt("income");
                                    pts.add= jobj.getString("add");
                                    pts.mob= jobj.getString("mob");
                                    pts.dg= jobj.getInt("dg");
                                    pts.child_num= jobj.getInt("child_num");
                                    pts.preg_comp_oth_txt= jobj.getString("preg_comp_oth_txt");
                                    pts.sm_oth_txt= jobj.getString("sm_oth_txt");
                                    pts.pt_num= jobj.getString("pt_num");
                                    pts.pt_doc= jobj.getString("pt_doc");
                                    pts.sc= jobj.getInt("sc");
                                    pts.is_married= jobj.getInt("is_married");
                                    pts.live_husband= jobj.getInt("live_husband");
                                    pts.edu= jobj.getInt("edu");
                                    pts.have_child= jobj.getInt("have_child");
                                    pts.preg_comp= jobj.getInt("preg_comp");
                                    pts.pt_dis= jobj.getInt("pt_dis");
                                    pts.use_condom= jobj.getInt("use_condom");
                                    pts.transmission= jobj.getInt("transmission");
                                    pts.sm_anc= jobj.getInt("sm_anc");
                                    pts.sm_vd= jobj.getInt("sm_vd");
                                    pts.sm_vi= jobj.getInt("sm_vi");
                                    pts.sm_lap= jobj.getInt("sm_lap");
                                    pts.sm_gu= jobj.getInt("sm_gu");
                                    pts.sm_pu= jobj.getInt("sm_pu");
                                    pts.sm_pi= jobj.getInt("sm_pi");
                                    pts.sm_oth= jobj.getInt("sm_oth");
                                    pts.sm_vd_day= jobj.getString("sm_vd_day");
                                    pts.sm_vi_day= jobj.getString("sm_vi_day");
                                    pts.sm_lap_day= jobj.getString("sm_lap_day");
                                    pts.sm_gu_day= jobj.getString("sm_gu_day");
                                    pts.sm_pu_day= jobj.getString("sm_pu_day");
                                    pts.sm_pi_day= jobj.getString("sm_pi_day");
                                    pts.sm_oth_day= jobj.getString("sm_oth_day");
                                    pts.multi_sex= jobj.getInt("multi_sex");
                                    pts.his_sti= jobj.getString("his_sti");
                                    ptsArrayList.add(pts);
                                }
                                recAdapter.notifyDataSetChanged();
                            }else{
                                if(((TextView) findViewById(R.id.no_data)).getVisibility()!=View.VISIBLE){
                                    ((TextView) findViewById(R.id.no_data)).setVisibility(View.VISIBLE);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        btn_toggle();
                        Toast.makeText(ListActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
        ){

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("tkn","=bfx38pmzy\\4Q^V4");
                    return params;
                }
        };
        queue.add(stringRequest);
    }
    private void btn_toggle(){
        if(progressBar.getVisibility()!= View.VISIBLE){
            loading = true;
            progressBar.setVisibility(View.VISIBLE);
        }else{
            loading = false;
            progressBar.setVisibility(View.GONE);
        }
    }
}