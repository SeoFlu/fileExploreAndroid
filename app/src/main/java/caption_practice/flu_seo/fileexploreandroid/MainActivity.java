package caption_practice.flu_seo.fileexploreandroid;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String root = getFilesDir().getAbsolutePath(); // 최상위 폴더
    private String CurPath = getFilesDir().getAbsolutePath(); // 현재 탐색하는 폴더
    private ArrayList<String> itemFiles = new ArrayList<String>(); // display되는 파일들
    private ArrayList<String> pathFiles = new ArrayList<String>(); // display되는 파일들의 경로 + 이름



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filemain);

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


}
