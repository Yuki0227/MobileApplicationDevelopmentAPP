package com.app.bbs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.R;
import com.app.bbs.Activity.ItemShowActivity;
import com.app.bbs.Adapter.RecyclerViewAdapter;
import com.app.bbs.Bean.ItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BBSFragment} factory method to
 * create an instance of this fragment.
 */
public class BBSFragment extends Fragment {

    private String fragmentText;

    private TextView fragmentTextView;
    private Button mBtnFaTie;
    private LinearLayout mLayIine;
    private ItemBean release;

    private EditText release_title;
    private EditText release_content;
    //private View view;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    public BBSFragment(String fragmentText) {
        this.fragmentText = fragmentText;
    }
    List<ItemBean> itemList = new ArrayList<>();
    public void loadExampleData() {
        itemList.add(new ItemBean(
                "愿你知我前来",
                "冬天已去\n" +
                        "阴雨消退\n" +
                        "我骑着骏马\n" +
                        "涉河而行\n" +
                        "愿你知我前来\n" +
                        "我思爱成病\n" +
                        "\n" +
                        "春风扇扬\n" +
                        "花木如锦\n" +
                        "容我见你面貌\n" +
                        "聆你嗓音\n" +
                        "你的嗓音柔和\n" +
                        "你的面貌秀媚\n" +
                        "无花果红熟\n" +
                        "葡萄发着芬芳\n" +
                        "青草为榻　柏树为帐\n" +
                        "莫要惊动\n" +
                        "莫要唤醒我爱的\n" +
                        "等伊自己愿意\n"
        ));
        itemList.add(new ItemBean(
                "愿你知我前来",
                "我良人\n" +
                        "我爱\n" +
                        "我的佳偶\n" +
                        "你美丽　全无瑕疵\n" +
                        "你舌下有蜜有奶\n" +
                        "你的脚趾使我迷醉\n" +
                        "\n" +
                        "将我按在心上\n" +
                        "犹如朱红的记印\n" +
                        "题在你臂上好似刺青\n" +
                        "我每夜来\n" +
                        "像羚羊小鹿\n" +
                        "欢奔在乳香冈上\n" +
                        "\n" +
                        "天起凉风\n" +
                        "日影飞去\n" +
                        "我们快要离别\n" +
                        "我将再来\n" +
                        "左手放在你头上\n" +
                        "右手将你抱起"
        ));
        itemList.add(new ItemBean(
                "红尘绝恋",
                "半生都在渴饮露水\n" +
                        "以养颜值\n" +
                        "期待着红尘中~\n" +
                        "那一双顾盼流连的眼晴\n" +
                        "盛满真诚\n" +
                        "闪烁着星辰\n" +
                        " \n" +
                        "阳光下\n" +
                        "你挣破囚禁你的茧\n" +
                        "轻轻扇动翅翼\n" +
                        "从此\n" +
                        "山山水水的侧影里\n" +
                        "有你的梦在飞翔\n" +
                        "飞越高山大海\n" +
                        "飞向我\n" +
                        "飞向我等待的诗行\n" +
                        "飞向我早已迷离的目光"
        ));
        itemList.add(new ItemBean(
                "红尘绝恋",
                "你恋我美丽的衣裙\n" +
                        "翩翩起舞\n" +
                        "我恋你一身华彩\n" +
                        "倩舞倾城\n" +
                        " \n" +
                        "听溪水流过森林\n" +
                        "鸟儿在林荫里歌唱\n" +
                        "清泉在石上流淌\n" +
                        "而月光衣我以华裳\n" +
                        "岁月可以凋零美丽\n" +
                        "惟灵魂持久弥香\n" +
                        " \n" +
                        "你是蝴蝶\n" +
                        "我是花朵\n" +
                        "情深处\n" +
                        "红尘岁月不寂寞\n" +
                        "你是蝴蝶\n" +
                        "我是花朵\n" +
                        "云烟深处\n" +
                        "碧草青青\n" +
                        "到处都栖息着\n" +
                        "蝶儿恋花\n" +
                        "美丽的传说……"
        ));
        itemList.add(new ItemBean(
                "得到与失去，何必太在意！",
                "有时候失去一些东西的同时，你也会得到一些东西，\n" +
                        "\n" +
                        "　　比如快乐;\n" +
                        "\n" +
                        "　　有时候得到一些东西的同时，你也会失去一些东西，\n" +
                        "\n" +
                        "　　比如自尊。\n" +
                        "\n" +
                        "　　凡事只要达到一定程度，会物极必反，也会否极泰来。\n" +
                        "\n" +
                        "　　得与失之间蕴含着很多人生的哲理，而人的觉悟往往在一刹那豁然开朗。\n" +
                        "\n" +
                        "　　自己的路，需要自己找到出口，而不是在别人的掩护下走到出口。\n" +
                        "\n" +
                        "　　人要学会换位思考问题，这样很多原本想不通放不下的事都会有结果。\n" +
                        "\n" +
                        "　　坦然面对一切，那样自己才会开心。\n" +
                        "\n" +
                        "　　遭遇风浪，饱尝奔波，乃是人生常态，谁都无法拒绝。\n" +
                        "\n" +
                        "　　时间会冲淡一切往事，一切喜与悲。\n" +
                        "\n" +
                        "　　得到与失去，都不必太在意。\n" +
                        "\n" +
                        "　　远走的，都是过眼云烟;\n" +
                        "\n" +
                        "　　留下的，才是值得珍惜的情缘。\n" +
                        "\n" +
                        "　　没有任何东西会永远停驻在身边，人生如梦，去日苦多，珍惜眼前，珍惜当下。\n" +
                        "\n" +
                        "　　生命是一个过程，无论成功还是失败，都是自己的路。\n" +
                        "\n" +
                        "　　路上一串串真实的脚印，汇聚成每个人或长或短的人生。"
        ));
        itemList.add(new ItemBean(
                "得到与失去，何必太在意！",
                "经历得越多，道路将走得越宽，视野才能更开阔。\n" +
                        "\n" +
                        "　　许多美好的事物，值得留恋的人，在时过境迁之后，都会被淡忘。\n" +
                        "\n" +
                        "　　回忆再美好，也经不住遗忘;往事再悲伤，也抵不过时间的冲刷。\n" +
                        "\n" +
                        "　　曾经的誓言，曾经的快乐，都会被现实一点一滴地冲淡、遗忘。\n" +
                        "\n" +
                        "　　经历过的，都停留在了过去。\n" +
                        "\n" +
                        "　　所有精华，都凝聚成当下的经验。\n" +
                        "\n" +
                        "　　曾经深爱过的人，曾经许下的承诺，都会被遗忘，留下依稀的回忆，甚至有时候连回忆都显得那么苍白无力，最终随风而逝。\n" +
                        "\n" +
                        "　　人生中有许多悲伤至极的事，但是也都会随着时间的流逝而淡忘、消逝。\n" +
                        "\n" +
                        "　　再悲伤，也要继续往前走，尽管走的时候还是会带着一丝丝疼痛。\n" +
                        "\n" +
                        "　　可是走到最后，所有疼痛都会忘怀。\n" +
                        "\n" +
                        "　　一路走来，风风雨雨，该经历的都经历了，不堪回首的也都过去了。\n" +
                        "\n" +
                        "　　茫茫然间自己的心仿佛已被生活打磨得坚如磐石!\n" +
                        "\n" +
                        "　　以为自己人生的经历足够于应付余下的漫漫人生路，可是心毕竟是心。\n" +
                        "\n" +
                        "　　越硬的心是越脆弱的，并没有坚如磐石。\n" +
                        "\n" +
                        "　　想得开是天堂。想不开是地狱。\n" +
                        "\n" +
                        "　　冥冥之中，自有定数，因此我们又何必太在意。\n" +
                        "\n" +
                        "　　人的一生中，悲喜交加，聚散离别，经历多了，总会习以为常，你若沉溺，换来的只是更深的伤害。\n" +
                        "\n" +
                        "　　放不下一些人，可也注定无法相守，放不下一些事，可也注定不能尽如人愿。\n" +
                        "\n" +
                        "　　美梦总逃不过现实的摧残，而我们对现实却无能为力。\n" +
                        "\n" +
                        "　　我们无力改变世界，但是起码也要放自己好过。\n" +
                        "\n" +
                        "　　所以要坦然，要放下。\n" +
                        "\n" +
                        "　　你不能决定生命的长度，但你可以决定它的宽度;\n" +
                        "\n" +
                        "　　你不能改变他人，但你可以把握自己;\n" +
                        "\n" +
                        "　　你不能左右天气，但你可以保持好心情;\n" +
                        "\n" +
                        "　　你不能改变容颜，但你可以改变气质;\n" +
                        "\n" +
                        "　　你不能预知明天，但你可以过好今天;\n" +
                        "\n" +
                        "　　你不能事事顺利，但你可以事事尽力。\n" +
                        "\n" +
                        "　　生命的坦然在于学会宽容，生活的充实在于懂得珍惜。\n" +
                        "\n" +
                        "　　该珍惜的，铭记在心;\n" +
                        "\n" +
                        "　　该忘记的，永不再想;\n" +
                        "\n" +
                        "　　该原谅的，一笑而过。\n" +
                        "\n" +
                        "　　得到与失去何必太在意，失而复得，得而复失，塞翁失马，焉知非福。\n" +
                        "\n" +
                        "　　世间没有永恒，要持有“得之我幸，失之我命”的心态去看待。\n" +
                        "\n" +
                        "　　不管是什么，终会随着生命的凋零而消失。\n" +
                        "\n" +
                        "　　得到与失去，何必太在意!"
        ));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bbs, container, false);
        init(view);

        mBtnFaTie=view.findViewById(R.id.btn_item_ly_fatie);
        mBtnFaTie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                release_title=view.findViewById(R.id.edt_relese_title);
                release_content=view.findViewById(R.id.edt_relese_content);
                release=new ItemBean(release_title.getText().toString(), release_content.getText().toString());
                itemList.add(release);
                init(view);
                release_title.setText("");
                release_content.setText("");
                Toast.makeText(getContext(),"发布成功",Toast.LENGTH_LONG).show();
            }
        });




        return view;
    }



    //@Override
    public void onClick(View parent, int position) {
        Intent intent = new Intent(getActivity(), ItemShowActivity.class);
        Bundle bundle = new Bundle();
        ItemBean item = itemList.get(position);
        intent.putExtra("pos",position);
        //System.out.println(position);
        bundle.putSerializable(ItemBean.KEY,item);
        intent.putExtras(bundle);
        startActivityForResult(intent,1);
    }
    public void init(View view) {
        loadExampleData();
        recyclerView = view.findViewById(R.id.recyclerItemList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(itemList, getActivity());
        recyclerViewAdapter.setOnItemClickListener(this::onClick);
        recyclerView.setAdapter(recyclerViewAdapter);


    }





}