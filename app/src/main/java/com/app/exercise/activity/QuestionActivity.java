package com.app.exercise.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.alibaba.fastjson.JSON;
import com.app.R;
import com.app.exercise.entity.TestPaper;
import com.app.exercise.entity.question.ChoiceQuestion;
import com.app.exercise.entity.question.JudgmentQuestion;
import com.app.exercise.entity.question.ProgrammingQuestion;
import com.app.exercise.entity.question.Question;
import com.app.exercise.hepler.MyTag;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {
    public static List<String> anList;
    private static TestPaper testPaper;
    private int tab;
    private String table, content;
    private TextView tvTitle, tvScore;
    private Chronometer chronometer;
    private Cursor cursor;
    private boolean isCollect = false, isFirst = false;
    private int num;
    private int score = 0, index = 0;
    private String source;
    private String type, que, A, B, C, D, answer, detail;

    private AdapterViewFlipper vf;
    private BaseAdapter adapter;
    private ProgressBar pb;
    private View root;
    private TextView tvQue, tvDetail, tvAns, tvYou;
    private TextView judgement_tvAns, judgment_tvYou;
    private CheckBox cb1, cb2, cb3, cb4;
    private ImageView imgCollect, imgCard;
    private Long qid;
    private ImageView imgPre, imgNext;
    private TextView tv_queprogram, tv_input, tv_output, tv_sample_input, tv_sample_output;
    private String queprogram, input, output, sample_input, sample_output;
    private TextView judgement_tv_que, judgement_tv_you, judgement_tv_answer, judgement_tv_detail;
    private CheckBox judgement_cb_choice1, judgement_cb_choice2;
    private Button exercise_btn_submit;
    private Integer submit= 0 ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_activity_question);
        //ActionBar工具栏设置
        Toolbar toolbar = findViewById(R.id.exercise_toolbar_que);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        tab = intent.getIntExtra("tab", 1);
        Intent intent1 = getIntent();
        final String choiceQuestion = intent1.getStringExtra("choiceQuestion");
        final String programmingQuestion = intent1.getStringExtra("programmingQuestion");
        final String judgementQuestion = intent1.getStringExtra("judgementQuestion");

        try {
            Thread getTestPaperThread = getTestPaper(choiceQuestion, programmingQuestion, judgementQuestion);
            getTestPaperThread.start();
            getTestPaperThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initTable();
        initView();

    }

    public Thread getTestPaper(final String choiceQuestion, final String programmingQuestion, final String judgementQuestion) {
        return new Thread(() -> {
            try {
                FormBody.Builder params = new FormBody.Builder();
                params.add("choiceQuestion", choiceQuestion);
                params.add("programmingQuestion", programmingQuestion);
                params.add("judgementQuestion", judgementQuestion);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://8.131.250.250/paper/getTestPaper")
                        .post(params.build())
                        .build();
                Response response = client.newCall(request).execute();
                String responseData = Objects.requireNonNull(response.body()).string();
                testPaper = JSON.parseObject(responseData, TestPaper.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initTable() {
        switch (tab) {
            case MyTag.QUE://题库
                table = "que";
                content = "题库";
                break;
//           case MyTag.COLLECT://收藏
//                table = "collection ,que where collection.qid=que._id ";
//                content = "收藏";
//                break;
//            case MyTag.WRONG://错题
//                table = "wrong,que where wrong.qid=que._id ";
//                content = "错题";
//                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        //初始化收藏按钮
//        imgCollect = findViewById(R.id.exercise_img_collect);
//        imgCollect.setOnClickListener(this);
        //初始化答题卡按钮
//        imgCard = findViewById(R.id.exercise_img_card);
//        imgCard.setOnClickListener(this);
        //初始化计时器
        chronometer = findViewById(R.id.exercise_mytime);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        chronometer.setOnChronometerTickListener(chronometer -> {
            if (SystemClock.elapsedRealtime() - chronometer.getBase() >= 1.5 * 360 * 1000) {
                Toast.makeText(QuestionActivity.this, "考试时间到", Toast.LENGTH_LONG).show();
                saveExam();
            }
        });
        //获取题目集关键字
        String field = "";// QuestionFragment.field;
        String value = "";//QuestionFragment.value;
        source = value;
        //设置标题
        tvTitle = findViewById(R.id.exercise_tv_title);
        tvTitle.setText("随机试题");
        //获取SQLite数据库中题库数据
//        if(tab==MyTag.QUE)
//            cursor = ToolHelper.loadDB(this,
//                    "select que.* from "+table+" where " + field + "='" + value + "' order by type");
//        else
//            cursor = ToolHelper.loadDB(this,
//                    "select que.* from "+table+" and " + field + "='" + value + "' order by type");
        num = testPaper.getCount();
        //答案List初始化
        anList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            anList.add("");
        }

        //设置进度条
        pb = findViewById(R.id.exercise_pb);
        pb.setMax(num - 1);
        pb.setProgress(0);
        //前后按钮
        imgPre = findViewById(R.id.exercise_img_pre);
        imgNext = findViewById(R.id.exercise_img_next);
        imgPre.setOnClickListener(this);
        imgNext.setOnClickListener(this);
        //设置初始分数
        tvScore = findViewById(R.id.exercise_tv_num);
        tvScore.setText("得分：" + score + "/" + num);
        //设置ViewFlipper
        vf = findViewById(R.id.exercise_vf);
        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return num;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                index = position;
                createView(position);
                return root;
            }
        };
        vf.setAdapter(adapter);
    }

    //答题卡设置
    private void createView(final int pos) {
        int num = testPaper.getCount();
        if (num > pos) {
            Question question = testPaper.getQuestion(pos);
            if (question.getType().equals("单选") || question.getType().equals("多选")) {
                ChoiceQuestion choiceQuestion = (ChoiceQuestion) question;

                root = LayoutInflater.from(QuestionActivity.this).inflate(R.layout.exercise_que_item, null);
                tvQue = root.findViewById(R.id.exercise_tv_que1);
                cb1 = root.findViewById(R.id.exercise_cb_choice1);
                cb2 = root.findViewById(R.id.exercise_cb_choice2);
                cb3 = root.findViewById(R.id.exercise_cb_choice3);
                cb4 = root.findViewById(R.id.exercise_cb_choice4);
                tvAns = root.findViewById(R.id.exercise_tv_answer1);
                tvDetail = root.findViewById(R.id.exercise_tv_detail1);
                tvYou = root.findViewById(R.id.exercise_tv_you);

                //获取数据
                type = choiceQuestion.getType();
                que = choiceQuestion.getQue();
                A = "A." + choiceQuestion.getChoiceA();
                B = "B." + choiceQuestion.getChoiceB();
                C = "C." + choiceQuestion.getChoiceC();
                D = "D." + choiceQuestion.getChoiceD();
                answer = choiceQuestion.getAnswer();
                detail = choiceQuestion.getDetail();
                qid = choiceQuestion.getId();
                //加载内容
                tvQue.setText((pos + 1) + ".(" + type + ")" + que);
                cb1.setText(A);
                cb2.setText(B);
                cb3.setText(C);
                cb4.setText(D);
                cb1.setButtonDrawable(R.drawable.exercise_cb);
                cb2.setButtonDrawable(R.drawable.exercise_cb);
                cb3.setButtonDrawable(R.drawable.exercise_cb);
                cb4.setButtonDrawable(R.drawable.exercise_cb);
                cb1.setEnabled(true);
                cb2.setEnabled(true);
                cb3.setEnabled(true);
                cb4.setEnabled(true);
                cb1.setChecked(false);
                cb2.setChecked(false);
                cb3.setChecked(false);
                cb4.setChecked(false);
                tvAns.setText("【正确答案】" + answer);
                tvDetail.setText("【解析】" + detail);
                if (anList.get(pos).equals("")) {
                    tvAns.setVisibility(View.GONE);
                    tvYou.setVisibility(View.GONE);
                    tvDetail.setVisibility(View.GONE);
                } else {
                    //已答题设置为不可操作
                    disableChecked(pos);
                }

            } else if (question.getType().equals("编程")) {
                ProgrammingQuestion programmingQuestion = (ProgrammingQuestion) question;
                root = LayoutInflater.from(QuestionActivity.this).inflate(R.layout.exercise_que_program, null);

                tv_queprogram = root.findViewById(R.id.exercise_program_tv_que);
                tv_input = root.findViewById(R.id.exercise_program_tv_input);
                tv_output = root.findViewById(R.id.exercise_program_tv_output);
                tv_sample_input = root.findViewById(R.id.exercise_program_tv_sample_input);
                tv_sample_output = root.findViewById(R.id.exercise_program_tv_sample_output);
                exercise_btn_submit = root.findViewById(R.id.exercise_btn_submit);

                //获取数据
                type = programmingQuestion.getType();
                queprogram = programmingQuestion.getQuestion();
                input = programmingQuestion.getInput();
                output = programmingQuestion.getOutput();
                sample_input = programmingQuestion.getSampleInput();
                sample_output = programmingQuestion.getSampleOutput();

                //加载内容
                tv_queprogram.setText((pos + 1) + ".(" + type + ")" + queprogram);
                tv_input.setText(input);
                tv_output.setText(output);
                tv_sample_input.setText(sample_input);
                tv_sample_output.setText(sample_output);
                exercise_btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submit =1;
                        Intent intent =new Intent(QuestionActivity.this,SubmitActivity.class);
                        startActivity(intent);
                    }
                });

            } else if ("判断".equals(question.getType())) {
                JudgmentQuestion judgmentQuestion = (JudgmentQuestion) question;
                root = LayoutInflater.from(QuestionActivity.this).inflate(R.layout.exercise_que_judgment, null);

                judgement_tv_que = root.findViewById(R.id.exercise_judgement_tv_que);
                judgement_cb_choice1 = root.findViewById(R.id.exercise_judgement_cb_choice1);
                judgement_cb_choice2 = root.findViewById(R.id.exercise_judgement_cb_choice2);
                judgement_tv_you = root.findViewById(R.id.exercise_judgement_tv_you);
                judgement_tv_answer = root.findViewById(R.id.exercise_judgement_tv_answer);
                judgement_tv_detail = root.findViewById(R.id.exercise_judgement_tv_detail);


                //获取数据
                type = judgmentQuestion.getType();
                que = judgmentQuestion.getQuestion();
                A = judgmentQuestion.getChoice1();
                B = judgmentQuestion.getChoice2();
                answer = judgmentQuestion.getAnswer();
                detail = judgmentQuestion.getParse();

                //加载内容
                judgement_tv_que.setText((pos + 1) + "(" + type + ")" + que);
                judgement_cb_choice1.setText(A);
                judgement_cb_choice2.setText(B);
                judgement_cb_choice1.setButtonDrawable(R.drawable.exercise_cb);
                judgement_cb_choice2.setButtonDrawable(R.drawable.exercise_cb);
                judgement_cb_choice1.setEnabled(true);
                judgement_cb_choice2.setEnabled(true);
                judgement_cb_choice1.setChecked(false);
                judgement_cb_choice2.setChecked(false);

                judgement_tv_answer.setText("【正确答案】" + answer);
                tvDetail.setText("【解析】" + judgmentQuestion.getParse());
                if (anList.get(pos).equals("")) {
                    judgement_tv_answer.setVisibility(View.GONE);
                    judgement_tv_you.setVisibility(View.GONE);
                    judgement_tv_detail.setVisibility(View.GONE);
                } else {
                    disableChecked(pos);
                }
            }
            //设置当前进度
            pb.setProgress(pos);
            //设置是否被收藏
//            if (queCollect()) {
//                isCollect = true;
//                imgCollect.setImageResource(R.drawable.exercise_star_on);
//            } else {
//                isCollect = false;
//                imgCollect.setImageResource(R.drawable.exercise_star1);
//            }
            //滑动切换
            root.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    float startX = v.getWidth() / 2, endX = v.getWidth() / 2, min = 100;
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            startX = event.getX();
                        case MotionEvent.ACTION_UP:
                            endX = event.getX();
                            break;
                    }
                    if (startX - endX > min) {
                        vf.showNext();
                    } else if (endX - startX > min) {
                        vf.showPrevious();
                    }
                    return true;
                }
            });
        }


    }

    //判断选择答案对错
    private void isAnswerTrue(int pos) {
        Question question = testPaper.getQuestion(pos);
        if (question.getType().equals("单选") || question.getType().equals("多选")) {
            if (cb1.isChecked() || cb2.isChecked() || cb3.isChecked() || cb4.isChecked()) {
                //获取答案
                StringBuffer sb = new StringBuffer();
                if (cb1.isChecked()) sb.append("A");
                if (cb2.isChecked()) sb.append("B");
                if (cb3.isChecked()) sb.append("C");
                if (cb4.isChecked()) sb.append("D");
                String you = sb.toString();
                //保存答案
                anList.set(pos, you);
                //判断对错
                if (you.equals(answer)) {
                    moveCorrect();
                } else {
                    //错误则保存错题，显示答案
                    disableChecked(pos);
                }
            } else {
                Toast.makeText(QuestionActivity.this, "请选择答案", Toast.LENGTH_SHORT).show();
            }
        } else if (question.getType().equals("判断")) {
            if (judgement_cb_choice1.isChecked() || judgement_cb_choice2.isChecked()) {
                StringBuffer sb = new StringBuffer();
                if (judgement_cb_choice1.isChecked()) sb.append("正确");
                if (judgement_cb_choice2.isChecked()) sb.append("错误");
                String you = sb.toString();
                //保存答案
                anList.set(pos, you);
                //判断对错{
                if (you.equals(answer)) {
                    moveCorrect();
                } else {
                    disableChecked(pos);
                }
            } else {
                Toast.makeText(QuestionActivity.this, "请选择答案", Toast.LENGTH_SHORT).show();
            }
        }else if(question.getType().equals("编程")){
            if(submit == 1){
                    moveCorrect();
                }
            }else{
                Toast.makeText(QuestionActivity.this, "请点击提交答案，提交你的答案", Toast.LENGTH_SHORT).show();
            }

        }


    //移除正确题目
    @SuppressLint("SetTextI18n")
    private void moveCorrect() {
        score++;
        tvScore.setText("得分：" + score + "/" + num);
        vf.showNext();
//        int c=ToolHelper.loadDB(this,"select _id from wrong where qid="+qid).getCount();
//        if(c>0)
//        ToolHelper.excuteDB(this, "delete from wrong where qid=" +qid);
    }

    //已做题不可再做
    private void disableChecked(int pos) {
        Question question = testPaper.getQuestion(pos);
        if (question.getType().equals("单选") || question.getType().equals("多选")) {
            tvYou.setText("【你的答案】" + anList.get(pos));
            tvAns.setVisibility(View.VISIBLE);
            tvDetail.setVisibility(View.VISIBLE);
            tvYou.setVisibility(View.VISIBLE);
            if (answer.contains("A")) cb1.setButtonDrawable(R.drawable.exercise_cb_right);
            if (answer.contains("B")) cb2.setButtonDrawable(R.drawable.exercise_cb_right);
            if (answer.contains("C")) cb3.setButtonDrawable(R.drawable.exercise_cb_right);
            if (answer.contains("D")) cb4.setButtonDrawable(R.drawable.exercise_cb_right);
            //设置为不可答题
            cb1.setEnabled(false);
            cb2.setEnabled(false);
            cb3.setEnabled(false);
            cb4.setEnabled(false);
        } else if (question.getType().equals("判断")) {
            judgement_tv_you.setText("【你的答案】" + anList.get(pos));
            tvAns.setVisibility(View.VISIBLE);
            judgement_tv_detail.setVisibility(View.VISIBLE);
            judgement_tv_you.setVisibility(View.VISIBLE);
            if (answer.contains("正确")) cb1.setButtonDrawable(R.drawable.exercise_cb_right);
            if (answer.contains("错误")) cb1.setButtonDrawable(R.drawable.exercise_cb_right);
            //
            judgement_cb_choice1.setEnabled(false);
            judgement_cb_choice2.setEnabled(false);
        }

    }

//    //保存错题
//    private void saveWrong(String ans) {
//        //int c = ToolHelper.loadDB(this,"select _id from wrong where qid="+qid).getCount();
//        if (/*c==0*/false) {
//            Date date = new Date();
//            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            String mydate = ft.format(date);
//            //ToolHelper.excuteDB(this, "insert into wrong (_id,qid,answer,anTime) values (" + Math.random() * 10000 + "," + qid + ",'" + ans + "','" + mydate + "')");
//        }
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exercise_que, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //判断当前题目是否被收藏
    private boolean queCollect() {
        //int c=ToolHelper.loadDB(this,"select _id from collection where qid="+qid).getCount();
        int c = 0;
        return c > 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exercise_que_ok://提交答案
                if (index >= num - 1) {
                    if (!isFirst) {
                        isAnswerTrue(index);
                        isFirst = true;
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("是否结束测试？");
                        builder.setNegativeButton("取消", null);
                        builder.setPositiveButton("确定", (dialog, which) -> saveExam());
                        builder.show();
                    }
                } else {
                    isAnswerTrue(index);
                }
                break;
            case android.R.id.home://返回
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("是否取消测试？");
                builder.setNegativeButton("取消", (dialog, which) -> {
                });
                builder.setPositiveButton("确定", (dialog, which) -> QuestionActivity.this.finish());
                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //保存考试记录
    private void saveExam() {
        chronometer.stop();
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String mytime = chronometer.getText().toString();
        String mydate = ft.format(date);
        String title = source + "\n" + "测试" ;
        //ToolHelper.excuteDB(this, "insert into exam (_id,title,examTime,score,examDate) values (" + Math.random() * 10000 + ",'" + title + "','" + mytime + "'," + score + ",'" + mydate + "')");
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("score", score + "/" + num);
        intent.putExtra("time", mytime);
        intent.putExtra("date", mydate);
        intent.putExtra("title", title);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exercise_img_next:
                vf.showNext();
                break;
            case R.id.exercise_img_pre:
                vf.showPrevious();
                break;
//            case R.id.exercise_img_collect://收藏
//                if (!isCollect) {
//                    imgCollect.setImageResource(R.drawable.exercise_star_on);
//                    //ToolHelper.excuteDB(this, "insert into collection (_id,qid) values (" + Math.random() * 10000 + "," + qid + ")");
//                    Toast.makeText(this, "成功收藏", Toast.LENGTH_SHORT).show();
//                    isCollect = true;
//                } else {
//                    imgCollect.setImageResource(R.drawable.exercise_star1);
//                    //ToolHelper.excuteDB(this,"delete from collection where qid="+qid);
//                    Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show();
//                    isCollect = false;
//                }
//                break;
//            case R.id.img_card:
//                Intent intent=new Intent(this,CardActivity.class);
//                intent.putExtra("num",num);
//                intent.putExtra("from",1);
//                startActivityForResult(intent,MyTag.CARD);
//                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MyTag.CARD && resultCode == MyTag.CARD) {
            int select = data.getIntExtra("card", 0);
            moveToItem(select);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void moveToItem(int t) {
        if (t != index) {
            if (t > index) {
                int d = t - index;
                for (int i = 0; i < d + 1; i++)
                    vf.showNext();
            } else if (t < index) {
                int p = index - t;
                for (int i = 0; i < p + 1; i++)
                    vf.showPrevious();
            }
        }
    }
}

