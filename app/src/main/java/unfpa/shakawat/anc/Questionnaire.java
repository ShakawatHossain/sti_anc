package unfpa.shakawat.anc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import unfpa.shakawat.anc.fragments.CalenderDialog;
import unfpa.shakawat.anc.iview.CalenderInterface;
import unfpa.shakawat.anc.model.Participant;

public class Questionnaire extends AppCompatActivity implements CalenderInterface {
    String hospitalName=Participant.hos_name,patient_number=Participant.num;
    Spinner hos_name;
    EditText num,age,edu_year,edu_oth_txt,ocu,hocu,income,add,mob,dg,child_num,preg_comp_oth_txt,
            sm_oth_txt,sm_vd_day,sm_vi_day,sm_lap_day,sm_gu_day,sm_pu_day,sm_pi_day,sm_oth_day,his_sti;
    TextView pt_num,pt_doc;
    RadioGroup sc,is_married,live_husband,edu,have_child,preg_comp,pt_dis,use_condom,transmission,multi_sex;
    CheckBox sm_anc,sm_vd,sm_vi,sm_lap,sm_gu,sm_pu,sm_pi,sm_oth;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        setContentView(R.layout.activity_questionnaire);
        init();
    }
    private void init(){
        pt_num = (TextView) findViewById(R.id.pt_num);
        pt_num.setText(Participant.pt_num);
        hos_name= (Spinner) findViewById(R.id.hos_name);
        hos_name.setSelection(Participant.hos_num);
        hos_name.setOnItemSelectedListener(this.itemSelectedListener);
//        Edit Text
        num = (EditText) findViewById(R.id.num);
        age = (EditText) findViewById(R.id.age);
        edu_year = (EditText) findViewById(R.id.edu_year);
        edu_oth_txt = (EditText) findViewById(R.id.edu_oth_txt);
        ocu = (EditText) findViewById(R.id.ocu);
        hocu = (EditText) findViewById(R.id.hocu);
        income = (EditText) findViewById(R.id.income);
        add = (EditText) findViewById(R.id.add);
        mob = (EditText) findViewById(R.id.mob);
        dg = (EditText) findViewById(R.id.dg);
        child_num = (EditText) findViewById(R.id.child_num);
        preg_comp_oth_txt = (EditText) findViewById(R.id.preg_comp_oth_txt);
        sm_oth_txt = (EditText) findViewById(R.id.sm_oth_txt);
        sm_vd_day=(EditText) findViewById(R.id.sm_vd_day);
        sm_vi_day=(EditText) findViewById(R.id.sm_vi_day);
        sm_lap_day=(EditText) findViewById(R.id.sm_lap_day);
        sm_gu_day=(EditText) findViewById(R.id.sm_gu_day);
        sm_pu_day=(EditText) findViewById(R.id.sm_pu_day);
        sm_pi_day=(EditText) findViewById(R.id.sm_pi_day);
        sm_oth_day=(EditText) findViewById(R.id.sm_oth_txt_day);
        his_sti=(EditText) findViewById(R.id.his_sti);
        num.setText(Participant.num);
        age.setText(""+Participant.age);
        edu_year.setText(""+Participant.edu_year);
        edu_oth_txt.setText(Participant.edu_oth_txt);
        ocu.setText(Participant.ocu);
        hocu.setText(Participant.hocu);
        income.setText(""+Participant.income);
        add.setText(Participant.add);
        mob.setText(Participant.mob);
        dg.setText(""+Participant.dg);
        child_num.setText(""+Participant.child_num);
        preg_comp_oth_txt.setText(Participant.preg_comp_oth_txt);
        sm_oth_txt.setText(Participant.sm_oth_txt);
        sm_vd_day.setText(Participant.sm_vd_day);
        sm_vi_day.setText(Participant.sm_vi_day);
        sm_lap_day.setText(Participant.sm_lap_day);
        sm_gu_day.setText(Participant.sm_gu_day);
        sm_pu_day.setText(Participant.sm_pu_day);
        sm_pi_day.setText(Participant.sm_pi_day);
        sm_oth_day.setText(Participant.sm_oth_day);
        his_sti.setText(Participant.his_sti);
        num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                patient_number = s.toString();
                pt_num.setText(hospitalName+" ANC "+patient_number);
                Participant.num = patient_number;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pt_doc = (TextView) findViewById(R.id.pt_doc);
        pt_doc.setText(Participant.pt_doc);
        pt_doc.setOnClickListener(this.clickListener);
//        Radio Button
        sc=(RadioGroup) findViewById(R.id.sc);
        is_married=(RadioGroup) findViewById(R.id.is_married);
        live_husband=(RadioGroup) findViewById(R.id.live_husband);
        edu=(RadioGroup) findViewById(R.id.edu);
        have_child=(RadioGroup) findViewById(R.id.have_child);
        preg_comp=(RadioGroup) findViewById(R.id.preg_comp);
        pt_dis=(RadioGroup) findViewById(R.id.pt_dis);
        use_condom=(RadioGroup) findViewById(R.id.use_condom);
        transmission=(RadioGroup) findViewById(R.id.transmission);
        multi_sex=(RadioGroup) findViewById(R.id.multi_sex);
        if(Participant.sc>0){
            if (Participant.sc==1) sc.check(R.id.sc_yes);
            else sc.check(R.id.sc_no);
        }
        if(Participant.is_married>0){
            if (Participant.is_married==1) is_married.check(R.id.is_married_married);
            else if (Participant.is_married==2) is_married.check(R.id.is_married_divorced);
            else if (Participant.is_married==3) is_married.check(R.id.is_married_separate);
        }
        if(Participant.live_husband>0){
            if (Participant.live_husband==1) live_husband.check(R.id.live_husband_yes);
            else live_husband.check(R.id.live_husband_no);
        }
        if(Participant.edu>0){
            if (Participant.edu==1) {
                edu.check(R.id.edu_formal);
                if(((TableRow) findViewById(R.id.edu_year_row)).getVisibility()!=View.VISIBLE )
                    ((TableRow) findViewById(R.id.edu_year_row)).setVisibility(View.VISIBLE);
            }
            else if (Participant.edu==2) edu.check(R.id.edu_illiterate);
            else if (Participant.edu==3) edu.check(R.id.edu_sign);
            else if (Participant.edu==4) edu.check(R.id.edu_inform);
            else if (Participant.edu==5) {
                edu.check(R.id.edu_oth);
                if(((TableRow) findViewById(R.id.edu_oth_txt_row)).getVisibility()!=View.VISIBLE )
                    ((TableRow) findViewById(R.id.edu_oth_txt_row)).setVisibility(View.VISIBLE);
            }
        }
        if(Participant.have_child>0){
            if (Participant.have_child==1) {
                have_child.check(R.id.have_child_yes);
                if(((TableRow) findViewById(R.id.child_num_row)).getVisibility()!=View.VISIBLE )
                    ((TableRow) findViewById(R.id.child_num_row)).setVisibility(View.VISIBLE);
            }
            else if (Participant.have_child==2) have_child.check(R.id.have_child_no);
        }
        if(Participant.preg_comp>0){
            if (Participant.preg_comp==1) preg_comp.check(R.id.misc);
            else if (Participant.preg_comp==2) preg_comp.check(R.id.sb);
            else if (Participant.preg_comp==3) preg_comp.check(R.id.nc);
            else if (Participant.preg_comp==4) {
                preg_comp.check(R.id.preg_comp_oth);
                if(((TableRow) findViewById(R.id.preg_comp_oth_row)).getVisibility()!=View.VISIBLE )
                    ((TableRow) findViewById(R.id.preg_comp_oth_row)).setVisibility(View.VISIBLE);
            }
        }
        if(Participant.pt_dis>0){
            if (Participant.pt_dis==1) pt_dis.check(R.id.pt_dis_yes);
            else if (Participant.pt_dis==2) pt_dis.check(R.id.pt_dis_no);
            else if (Participant.pt_dis==3) pt_dis.check(R.id.pt_dis_dk);
        }
        if(Participant.use_condom>0){
            if (Participant.use_condom==1) use_condom.check(R.id.use_condom_al);
            else if (Participant.use_condom==2) use_condom.check(R.id.use_condom_st);
            else if (Participant.use_condom==3) use_condom.check(R.id.use_condom_nr);
        }
        if(Participant.transmission>0){
            if (Participant.transmission==1) transmission.check(R.id.transmission_yes);
            else if (Participant.transmission==2) transmission.check(R.id.transmission_no);
            else if (Participant.transmission==3) transmission.check(R.id.transmission_dk);
        }
        if(Participant.multi_sex>0){
            if (Participant.multi_sex==1) multi_sex.check(R.id.multi_sex_one);
            else if (Participant.multi_sex==2) multi_sex.check(R.id.multi_sex_mo);
            else if (Participant.multi_sex==3) transmission.check(R.id.multi_sex_na);
        }
        edu.setOnCheckedChangeListener(this.checkedChangeListener);
        have_child.setOnCheckedChangeListener(this.checkedChangeListener);
        preg_comp.setOnCheckedChangeListener(this.checkedChangeListener);
        sm_anc=(CheckBox) findViewById(R.id.sm_anc);
        sm_vd=(CheckBox) findViewById(R.id.sm_vd);
        sm_vi=(CheckBox) findViewById(R.id.sm_vi);
        sm_lap=(CheckBox) findViewById(R.id.sm_lap);
        sm_gu=(CheckBox) findViewById(R.id.sm_gu);
        sm_pu=(CheckBox) findViewById(R.id.sm_pu);
        sm_pi=(CheckBox) findViewById(R.id.sm_pi);
        sm_oth=(CheckBox) findViewById(R.id.sm_oth);
        sm_vd.setOnCheckedChangeListener(compoundCheckedChangeListen);
        sm_vi.setOnCheckedChangeListener(compoundCheckedChangeListen);
        sm_lap.setOnCheckedChangeListener(compoundCheckedChangeListen);
        sm_gu.setOnCheckedChangeListener(compoundCheckedChangeListen);
        sm_pu.setOnCheckedChangeListener(compoundCheckedChangeListen);
        sm_pi.setOnCheckedChangeListener(compoundCheckedChangeListen);
//        sm_oth.setOnCheckedChangeListener(compoundCheckedChangeListen);
        if (Participant.sm_anc==1) sm_anc.setChecked(true);
        if (Participant.sm_vd==1) {
            sm_vd.setChecked(true);
            if(((TableRow) findViewById(R.id.sm_vd_day_row)).getVisibility()!=View.VISIBLE)
                ((TableRow) findViewById(R.id.sm_vd_day_row)).setVisibility(View.VISIBLE);
        }
        if (Participant.sm_vi==1) {
            sm_vi.setChecked(true);
            if(((TableRow) findViewById(R.id.sm_vi_day_row)).getVisibility()!=View.VISIBLE)
                ((TableRow) findViewById(R.id.sm_vi_day_row)).setVisibility(View.VISIBLE);
        }
        if (Participant.sm_lap==1) {
            sm_lap.setChecked(true);
            if(((TableRow) findViewById(R.id.sm_lap_day_row)).getVisibility()!=View.VISIBLE)
                ((TableRow) findViewById(R.id.sm_lap_day_row)).setVisibility(View.VISIBLE);
        }
        if (Participant.sm_gu==1) {
            sm_gu.setChecked(true);
            if(((TableRow) findViewById(R.id.sm_gu_day_row)).getVisibility()!=View.VISIBLE)
                ((TableRow) findViewById(R.id.sm_gu_day_row)).setVisibility(View.VISIBLE);
        }
        if (Participant.sm_pu==1) {
            sm_pu.setChecked(true);
            if(((TableRow) findViewById(R.id.sm_pu_day_row)).getVisibility()!=View.VISIBLE)
                ((TableRow) findViewById(R.id.sm_pu_day_row)).setVisibility(View.VISIBLE);
        }
        if (Participant.sm_pi==1) {
            sm_pi.setChecked(true);
            if(((TableRow) findViewById(R.id.sm_pi_day_row)).getVisibility()!=View.VISIBLE)
                ((TableRow) findViewById(R.id.sm_pi_day_row)).setVisibility(View.VISIBLE);
        }
        if (Participant.sm_oth==1) {
            sm_oth.setChecked(true);
            if(((TableRow) findViewById(R.id.sm_oth_txt_row)).getVisibility()!=View.VISIBLE)
                ((TableRow) findViewById(R.id.sm_oth_txt_row)).setVisibility(View.VISIBLE);
            if(((TableRow) findViewById(R.id.sm_oth_txt_day_row)).getVisibility()!=View.VISIBLE)
                ((TableRow) findViewById(R.id.sm_oth_txt_day_row)).setVisibility(View.VISIBLE);
        }
        sm_oth.setOnCheckedChangeListener(checkboxCheckedChangeListener);
        submit = (Button) findViewById(R.id.btn);
        submit.setOnClickListener(this.clickListener);
    }
    private void setData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://119.148.17.100:8080/sti/insert_anc.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");
                            Toast.makeText(Questionnaire.this,message,Toast.LENGTH_SHORT).show();
                            if (success==200){
                                startActivity(new Intent(Questionnaire.this,ListActivity.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<String, String>();
                params.put("id",String.valueOf(Participant.id));
                params.put("hos_num",String.valueOf(hos_name.getSelectedItemPosition()));
                params.put("hos_name",hospitalName);
                params.put("num",patient_number);
                params.put("age",age.getText().toString());
                params.put("edu_year",edu_year.getText().toString());
                params.put("edu_oth_txt",edu_oth_txt.getText().toString());
                params.put("ocu",ocu.getText().toString());
                params.put("hocu",hocu.getText().toString());
                params.put("income",income.getText().toString());
                params.put("add",add.getText().toString());
                params.put("mob",mob.getText().toString());
                params.put("dg",dg.getText().toString());
                params.put("child_num",child_num.getText().toString());
                params.put("preg_comp_oth_txt",preg_comp_oth_txt.getText().toString());
                params.put("sm_oth_txt",sm_oth_txt.getText().toString());
                params.put("pt_num",pt_num.getText().toString());
                params.put("pt_doc",pt_doc.getText().toString());
                params.put("sc",sc.getCheckedRadioButtonId()==R.id.sc_yes?"1":"2");
                if (is_married.getCheckedRadioButtonId()==R.id.is_married_married)
                    params.put("is_married","1");
                else if (is_married.getCheckedRadioButtonId()==R.id.is_married_divorced)
                    params.put("is_married","2");
                else if (is_married.getCheckedRadioButtonId()==R.id.is_married_separate)
                    params.put("is_married","3");
                params.put("live_husband",live_husband.getCheckedRadioButtonId()==R.id.live_husband_yes?"1":"2");
                if (edu.getCheckedRadioButtonId()==R.id.edu_formal)
                    params.put("edu","1");
                else if (edu.getCheckedRadioButtonId()==R.id.edu_illiterate)
                    params.put("edu","2");
                else if (edu.getCheckedRadioButtonId()==R.id.edu_sign)
                    params.put("edu","3");
                else if (edu.getCheckedRadioButtonId()==R.id.edu_inform)
                    params.put("edu","4");
                else if (edu.getCheckedRadioButtonId()==R.id.edu_oth)
                    params.put("edu","5");
                params.put("have_child",have_child.getCheckedRadioButtonId()==R.id.have_child_yes?"1":"2");
                if(preg_comp.getCheckedRadioButtonId()==R.id.misc)
                    params.put("preg_comp","1");
                else if(preg_comp.getCheckedRadioButtonId()==R.id.sb)
                    params.put("preg_comp","2");
                else if(preg_comp.getCheckedRadioButtonId()==R.id.nc)
                    params.put("preg_comp","3");
                else if(preg_comp.getCheckedRadioButtonId()==R.id.preg_comp_oth)
                    params.put("preg_comp","4");
                if(pt_dis.getCheckedRadioButtonId()==R.id.pt_dis_yes)
                    params.put("pt_dis","1");
                else if(pt_dis.getCheckedRadioButtonId()==R.id.pt_dis_no)
                    params.put("pt_dis","2");
                else if(pt_dis.getCheckedRadioButtonId()==R.id.pt_dis_dk)
                    params.put("pt_dis","3");
                if(use_condom.getCheckedRadioButtonId()==R.id.use_condom_al)
                    params.put("use_condom","1");
                else if(use_condom.getCheckedRadioButtonId()==R.id.use_condom_st)
                    params.put("use_condom","2");
                else if(use_condom.getCheckedRadioButtonId()==R.id.use_condom_nr)
                    params.put("use_condom","3");
                if(transmission.getCheckedRadioButtonId()==R.id.transmission_yes)
                    params.put("transmission","1");
                else if(transmission.getCheckedRadioButtonId()==R.id.transmission_no)
                    params.put("transmission","2");
                else if(transmission.getCheckedRadioButtonId()==R.id.transmission_dk)
                    params.put("transmission","3");
                if(multi_sex.getCheckedRadioButtonId()==R.id.multi_sex_one)
                    params.put("multi_sex","1");
                else if(multi_sex.getCheckedRadioButtonId()==R.id.multi_sex_mo)
                    params.put("multi_sex","2");
                else if(multi_sex.getCheckedRadioButtonId()==R.id.multi_sex_na)
                    params.put("multi_sex","3");
                params.put("sm_anc",sm_anc.isChecked()?"1":"2");
                params.put("sm_vd",sm_vd.isChecked()?"1":"2");
                params.put("sm_vi",sm_vi.isChecked()?"1":"2");
                params.put("sm_lap",sm_lap.isChecked()?"1":"2");
                params.put("sm_gu",sm_gu.isChecked()?"1":"2");
                params.put("sm_pu",sm_pu.isChecked()?"1":"2");
                params.put("sm_pi",sm_pi.isChecked()?"1":"2");
                params.put("sm_oth",sm_oth.isChecked()?"1":"2");
                params.put("sm_vd_day",sm_vd_day.getText().toString());
                params.put("sm_vi_day",sm_vi_day.getText().toString());
                params.put("sm_lap_day",sm_lap_day.getText().toString());
                params.put("sm_gu_day",sm_gu_day.getText().toString());
                params.put("sm_pu_day",sm_pu_day.getText().toString());
                params.put("sm_pi_day",sm_pi_day.getText().toString());
                params.put("sm_oth_day",sm_oth_day.getText().toString());
                params.put("his_sti",his_sti.getText().toString());
                return params;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(parent.getId()==hos_name.getId()){
                if (position>0){
                    hospitalName = hos_name.getSelectedItem().toString();
                    pt_num.setText(hospitalName+" ANC "+patient_number);
                    Participant.hos_name = hospitalName;
                    Participant.hos_num=position;
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId()==pt_doc.getId()){
                new CalenderDialog(Questionnaire.this,Questionnaire.this,pt_doc.getText().toString(),null,pt_doc).show();
            }else if(v.getId()==submit.getId()){
                if(check()){
                    setData();
                }
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (group.getId()==edu.getId()){
                if (checkedId==R.id.edu_formal){
                    if(((TableRow) findViewById(R.id.edu_year_row)).getVisibility()!=View.VISIBLE )
                        ((TableRow) findViewById(R.id.edu_year_row)).setVisibility(View.VISIBLE);
                    if(((TableRow) findViewById(R.id.edu_oth_txt_row)).getVisibility()==View.VISIBLE )
                        ((TableRow) findViewById(R.id.edu_oth_txt_row)).setVisibility(View.GONE);
                }else if (checkedId==R.id.edu_oth){
                    if(((TableRow) findViewById(R.id.edu_year_row)).getVisibility()==View.VISIBLE )
                        ((TableRow) findViewById(R.id.edu_year_row)).setVisibility(View.GONE);
                    if(((TableRow) findViewById(R.id.edu_oth_txt_row)).getVisibility()!=View.VISIBLE )
                        ((TableRow) findViewById(R.id.edu_oth_txt_row)).setVisibility(View.VISIBLE);
                }else {
                    if(((TableRow) findViewById(R.id.edu_year_row)).getVisibility()==View.VISIBLE )
                        ((TableRow) findViewById(R.id.edu_year_row)).setVisibility(View.GONE);
                    if(((TableRow) findViewById(R.id.edu_oth_txt_row)).getVisibility()==View.VISIBLE )
                        ((TableRow) findViewById(R.id.edu_oth_txt_row)).setVisibility(View.GONE);
                }
            }
            if (group.getId()==have_child.getId()){
                if (checkedId==R.id.have_child_yes){
                    if(((TableRow) findViewById(R.id.child_num_row)).getVisibility()!=View.VISIBLE )
                        ((TableRow) findViewById(R.id.child_num_row)).setVisibility(View.VISIBLE);
                }else {
                    if(((TableRow) findViewById(R.id.child_num_row)).getVisibility()==View.VISIBLE )
                        ((TableRow) findViewById(R.id.child_num_row)).setVisibility(View.GONE);
                }
            }
            if (group.getId()==preg_comp.getId()){
                if(checkedId==R.id.preg_comp_oth){
                    if(((TableRow) findViewById(R.id.preg_comp_oth_row)).getVisibility()!=View.VISIBLE )
                        ((TableRow) findViewById(R.id.preg_comp_oth_row)).setVisibility(View.VISIBLE);
                }else{
                    if(((TableRow) findViewById(R.id.preg_comp_oth_row)).getVisibility()==View.VISIBLE )
                        ((TableRow) findViewById(R.id.preg_comp_oth_row)).setVisibility(View.GONE);
                }
            }
        }
    };
    private CheckBox.OnCheckedChangeListener checkboxCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                if(((TableRow) findViewById(R.id.sm_oth_txt_row)).getVisibility()!=View.VISIBLE)
                    ((TableRow) findViewById(R.id.sm_oth_txt_row)).setVisibility(View.VISIBLE);
                if(((TableRow) findViewById(R.id.sm_oth_txt_day_row)).getVisibility()!=View.VISIBLE)
                    ((TableRow) findViewById(R.id.sm_oth_txt_day_row)).setVisibility(View.VISIBLE);
            }else {
                if(((TableRow) findViewById(R.id.sm_oth_txt_row)).getVisibility()==View.VISIBLE)
                    ((TableRow) findViewById(R.id.sm_oth_txt_row)).setVisibility(View.GONE);
                if (((TableRow) findViewById(R.id.sm_oth_txt_day_row)).getVisibility() == View.VISIBLE)
                    ((TableRow) findViewById(R.id.sm_oth_txt_day_row)).setVisibility(View.GONE);
            }
        }
    };

    private boolean check(){
        if(hos_name.getSelectedItemPosition()==0){
            Toast.makeText(Questionnaire.this,"Select hospital",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(num.getText().toString()==null || num.getText().toString().isEmpty() || num.getText().toString().length()!=5){
            Toast.makeText(Questionnaire.this,"Insert participant number",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(pt_doc.getText().toString()==null || pt_doc.getText().toString().isEmpty()){
            Toast.makeText(Questionnaire.this,"Select collection date",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(age.getText().toString()==null || age.getText().toString().isEmpty()){
            Toast.makeText(Questionnaire.this,"Insert age",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(edu.getCheckedRadioButtonId()==R.id.edu_formal){
            if(edu_year.getText().toString()==null || edu_year.getText().toString().isEmpty()){
                Toast.makeText(Questionnaire.this,"Insert year of education",Toast.LENGTH_SHORT).show();
                return false;
            }
        }else if(edu.getCheckedRadioButtonId()==R.id.edu_oth){
            if(edu_oth_txt.getText().toString()==null || edu_oth_txt.getText().toString().isEmpty()){
                Toast.makeText(Questionnaire.this,"Insert Other education",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(ocu.getText().toString()==null || ocu.getText().toString().isEmpty()){
            Toast.makeText(Questionnaire.this,"Insert ocupation",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(income.getText().toString()==null || income.getText().toString().isEmpty()){
            Toast.makeText(Questionnaire.this,"Insert income",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(add.getText().toString()==null || add.getText().toString().isEmpty()){
            Toast.makeText(Questionnaire.this,"Insert address",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(mob.getText().toString()==null || mob.getText().toString().isEmpty() || mob.getText().toString().length()!=11){
            Toast.makeText(Questionnaire.this,"Insert mobile no",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (sm_oth.isChecked()){
            if(sm_oth_txt.getText().toString()==null || sm_oth_txt.getText().toString().isEmpty()){
                Toast.makeText(Questionnaire.this,"Insert other symptom",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(dg.getText().toString()==null || dg.getText().toString().isEmpty()){
            Toast.makeText(Questionnaire.this,"Insert gestation duration",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(have_child.getCheckedRadioButtonId()==R.id.have_child_yes){
            if(child_num.getText().toString()==null || child_num.getText().toString().isEmpty()){
                Toast.makeText(Questionnaire.this,"Insert child number",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(preg_comp.getCheckedRadioButtonId()==R.id.preg_comp_oth){
            if(preg_comp_oth_txt.getText().toString()==null || preg_comp_oth_txt.getText().toString().isEmpty()){
                Toast.makeText(Questionnaire.this,"Prev. pregenancy complications",Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }
    private CompoundButton.OnCheckedChangeListener compoundCheckedChangeListen = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(buttonView.getId()==sm_vd.getId()){
                if(isChecked){
                    if(((TableRow) findViewById(R.id.sm_vd_day_row)).getVisibility()!=View.VISIBLE)
                        ((TableRow) findViewById(R.id.sm_vd_day_row)).setVisibility(View.VISIBLE);
                }
                else {
                    if (((TableRow) findViewById(R.id.sm_vd_day_row)).getVisibility() == View.VISIBLE)
                        ((TableRow) findViewById(R.id.sm_vd_day_row)).setVisibility(View.GONE);
                }
            }
            else if(buttonView.getId()==sm_vi.getId()){
                if(isChecked){
                    if(((TableRow) findViewById(R.id.sm_vi_day_row)).getVisibility()!=View.VISIBLE)
                        ((TableRow) findViewById(R.id.sm_vi_day_row)).setVisibility(View.VISIBLE);
                }
                else {
                    if (((TableRow) findViewById(R.id.sm_vi_day_row)).getVisibility() == View.VISIBLE)
                        ((TableRow) findViewById(R.id.sm_vi_day_row)).setVisibility(View.GONE);
                }
            }
            else if(buttonView.getId()==sm_lap.getId()){
                if(isChecked){
                    if(((TableRow) findViewById(R.id.sm_lap_day_row)).getVisibility()!=View.VISIBLE)
                        ((TableRow) findViewById(R.id.sm_lap_day_row)).setVisibility(View.VISIBLE);
                }
                else {
                    if (((TableRow) findViewById(R.id.sm_lap_day_row)).getVisibility() == View.VISIBLE)
                        ((TableRow) findViewById(R.id.sm_lap_day_row)).setVisibility(View.GONE);
                }
            }
            else if(buttonView.getId()==sm_gu.getId()){
                if(isChecked){
                    if(((TableRow) findViewById(R.id.sm_gu_day_row)).getVisibility()!=View.VISIBLE)
                        ((TableRow) findViewById(R.id.sm_gu_day_row)).setVisibility(View.VISIBLE);
                }
                else {
                    if (((TableRow) findViewById(R.id.sm_gu_day_row)).getVisibility() == View.VISIBLE)
                        ((TableRow) findViewById(R.id.sm_gu_day_row)).setVisibility(View.GONE);
                }
            }
            else if(buttonView.getId()==sm_pu.getId()){
                if(isChecked){
                    if(((TableRow) findViewById(R.id.sm_pu_day_row)).getVisibility()!=View.VISIBLE)
                        ((TableRow) findViewById(R.id.sm_pu_day_row)).setVisibility(View.VISIBLE);
                }
                else {
                    if (((TableRow) findViewById(R.id.sm_pu_day_row)).getVisibility() == View.VISIBLE)
                        ((TableRow) findViewById(R.id.sm_pu_day_row)).setVisibility(View.GONE);
                }
            }
            else if(buttonView.getId()==sm_pi.getId()){
                if(isChecked){
                    if(((TableRow) findViewById(R.id.sm_pi_day_row)).getVisibility()!=View.VISIBLE)
                        ((TableRow) findViewById(R.id.sm_pi_day_row)).setVisibility(View.VISIBLE);
                }
                else {
                    if (((TableRow) findViewById(R.id.sm_pi_day_row)).getVisibility() == View.VISIBLE)
                        ((TableRow) findViewById(R.id.sm_pi_day_row)).setVisibility(View.GONE);
                }
            }
        }
    };

    @Override
    public void getDate(String dat, TextView editText) {
        editText.setText(dat);
    }
}