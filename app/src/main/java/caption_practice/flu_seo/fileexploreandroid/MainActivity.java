package caption_practice.flu_seo.fileexploreandroid;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int totalSelected = 0;
    EditText editView;
    ListView listView;
    TextView textView;
    MyListAdapterMy listadapter;
    ArrayList<MyListItem> listAllItems=new ArrayList<MyListItem>();
    ArrayList<MyListItem> listDispItems=new ArrayList<MyListItem>();

    private String root = getFilesDir().getAbsolutePath(); // 최상위 폴더
    private String CurPath = getFilesDir().getAbsolutePath(); // 현재 탐색하는 폴더
    private ArrayList<String> itemFiles = new ArrayList<String>(); // display되는 파일들
    private ArrayList<String> pathFiles = new ArrayList<String>(); // display되는 파일들의 경로 + 이름


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filemain);

    }

    private void getDirInfo(String dirPath){
        if(!dirPath.endsWith("/"))dirPath = dirPath="/";

        File f = new File(dirPath);
        File[] files = f.listFiles();
        if( files == null) return;

        itemFiles.clear();
        pathFiles.clear();

        if(!root.endsWith("/")) root = root+"/";
        if(!root.equals(dirPath)){
            itemFiles.add("../");
            pathFiles.add(f.getParent());
        }

        for(int i=0;i<files.length;i++){
            File file = files[i];
            pathFiles.add(file.getPath());

            if(file.isDirectory())
                itemFiles.add(file.getName()+"/");
            else
                itemFiles.add(file.getName());
        }
    }

    private void itemClick(String name, String path){
        File file = new File(path);
        if(file.isDirectory()){
            if(file.canRead()){
                CurPath = path;
                setupAdapter();
            }else{
                new AlertDialog.Builder(this)
                        .setTitle("["+file.getName()+"] folder can't be read!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
            else{
                new AlertDialog.Builder(this)
                        .setTitle("["+file.getName()+"]")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        }
    }

    private void setupPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        setupAdapter();
    }

    private void setupList(){
        listView = (ListView)findViewById(R.id.list1);
    }

    public class MyListItem{
        int selectedNumber;
        boolean checked;
        String name;
        String path;
    }

    public class MyListAdapterMy extends MyArrayAdapter<MyListItem>{

        public MyListAdapterMy(Context context){
            super(context,R.layout.list_item);
        }
    }

}
