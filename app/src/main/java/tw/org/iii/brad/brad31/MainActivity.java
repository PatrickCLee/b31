package tw.org.iii.brad.brad31;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private ExpandableListView expList;
    private LinkedList<HashMap<String,String>> groups = new LinkedList<>();
    private LinkedList<HashMap<String,String>> items1= new LinkedList<>();
    private LinkedList<HashMap<String,String>> items2= new LinkedList<>();
    private MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expList = findViewById(R.id.expList);
        init();
    }

    private void init(){
        HashMap<String,String> gitem1 = new HashMap<>();
        gitem1.put("group", "報價");
        groups.add(gitem1);
        HashMap<String,String> gitem2 = new HashMap<>();
        gitem2.put("group", "訂單");
        groups.add(gitem2);

        for(int i=0;i<10;i++){
            HashMap<String,String> row = new HashMap<>();
            row.put("title","Item1: " + i);
            items1.add(row);
        }

        for(int i=0;i<40;i++){
            HashMap<String,String> row = new HashMap<>();
            row.put("toto","Item2: " + i);
            items2.add(row);
        }

        myAdapter = new MyAdapter();
        expList.setAdapter(myAdapter);

        expList.expandGroup(0); //此處把畫面預設展開
        expList.expandGroup(1);
    }

    private class MyAdapter extends BaseExpandableListAdapter{
        private LayoutInflater inflater;

        MyAdapter(){
            inflater = getLayoutInflater();     //此處因MyAdapter寫成內部類別故不用在建構時指定Context(Activity)
        }

        @Override
        public int getGroupCount() {
            return groups.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            if(groupPosition==0){
                return items1.size();
            }else if(groupPosition==1){
                return items2.size();
            }
            return 0;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groups.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            if(groupPosition == 0){
                return items1.get(childPosition);
            }else if(groupPosition ==1){
                return items2.get(childPosition);
            }
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return groupPosition*1000 + childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            View view = inflater.inflate(R.layout.title, parent, false);
            TextView title = view.findViewById(R.id.title_txt);
            title.setText(groups.get(groupPosition).get("group"));
            return view;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View view = null;
            TextView content = null;
            if(groupPosition == 0){
                view =  inflater.inflate(R.layout.child, parent, false);
                content = view.findViewById(R.id.child_txt);
                content.setText(items1.get(childPosition).get("title"));
            }else if(groupPosition ==1){
                view =  inflater.inflate(R.layout.child2, parent, false);
                content = view.findViewById(R.id.child2_txt);
                content.setText(items2.get(childPosition).get("toto"));
            }
            return view;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }
}
