安卓开发学习笔记
========================================================


布局
--------------------------------------------------------
1. LinearLayout

```
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="10dp"
        android:background="#F5F5F5">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/et_number"
            />
        <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp"
            android:layout_marginBottom="10dp"
            android:hint="请输入电话号码！"
            />
        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/bt_callnumber"
            android:text="拨打此号码"
            />
    </LinearLayout>

```

2. RelativeLayout

```
    <?xml version="1.0" encoding="utf-8"?>
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent" android:layout_height="match_parent">


        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/tv_inputnum"
            />
        <EditText
            android:id="@+id/et_number"
            android:layout_below="@id/tv_inputnum"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp"
            android:layout_marginBottom="10dp"
            android:hint="请输入电话号码！"
            />
        <Button
            android:layout_below="@id/et_number"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/bt_callnumber"
            android:text="拨打此号码"
            />

    </RelativeLayout>

```

3. FrameLayout

```
    <?xml version="1.0" encoding="utf-8"?>
    <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent" android:layout_height="match_parent">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="#ffff00"
            >
        </LinearLayout>

        <LinearLayout
            android:layout_marginTop="20dp"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="#ff00ff"
            >
        </LinearLayout>

        <ImageView
            android:layout_gravity="center"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/ic_launcher_background"
            />
    </FrameLayout>

```

4. TableLayout

```
    <?xml version="1.0" encoding="utf-8"?>
    <TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent" android:layout_height="match_parent">

        <TableRow>
            <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="第一行第一列"
            />
            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="第一行第二列"
                />
        </TableRow>
        <TableRow>
            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="第二行第一列"
                />
            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="第二行第二列"
                />
        </TableRow>
    </TableLayout>

```

5. AbsoluteLayout（已弃用）



动态权限申请
-------------------------------------------------------

1. AndroidManifest文件修改

```
    <?xml version="1.0" encoding="utf-8"?>
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
        package="com.example.myapp">

        <application
            android:allowBackup="true"
            android:icon="@mipmap/ic_launcher"
            android:label="@string/app_name"
            android:roundIcon="@mipmap/ic_launcher_round"
            android:supportsRtl="true"
            android:theme="@style/AppTheme">
            <activity android:name=".MainActivity">
                <intent-filter>
                    <action android:name="android.intent.action.MAIN" />

                    <category android:name="android.intent.category.LAUNCHER" />
                </intent-filter>
            </activity>
        </application>
        <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
        <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    </manifest>

```

2. 动态申请权限类

```
    package com.example.myapp;

    import android.Manifest;
    import android.app.Activity;
    import android.content.Context;
    import android.content.pm.PackageManager;
    import android.support.v4.app.ActivityCompat;
    import android.support.v4.content.ContextCompat;

    import java.util.ArrayList;
    import java.util.List;

    /**
    * Created by zhouchaozhong on 2018/1/21.
    */

    public class GrantPermission {
        //要申请的权限清单
        private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        private static final int MY_PERMISSION_REQUEST_CODE = 10000;
        //存储被用户拒绝的权限列表
        List<String> denyList = new ArrayList<String>();


        //检查权限是否授予
        public boolean checkPermission(Context context){
            denyList.clear();
            for(String permission : permissions){
                if(ContextCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED){
                    denyList.add(permission);
                    return false;
                }
            }

            return true;
        }

        public void requestPermissions(Activity activity){
            ActivityCompat.requestPermissions(activity,permissions,MY_PERMISSION_REQUEST_CODE);
        }
    }


```


文件读写
--------------------------------------------------------------

1. activity_main.xml

```
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        tools:context="com.example.myapp.MainActivity">


        <EditText
            android:layout_marginTop="10dp"
            android:layout_marginBottom="10dp"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:id="@+id/et_username"
            android:hint="@string/input_username"
            />
        <EditText
            android:inputType="textPassword"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:id="@+id/et_password"
            android:hint="@string/input_password"
            />
        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
            <CheckBox
                android:layout_alignParentLeft="true"
                android:layout_centerVertical="true"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:id="@+id/cb_rem"
                android:text="@string/rem_password"
                />
            <Button
                android:layout_centerVertical="true"
                android:layout_alignParentRight="true"
                android:paddingLeft="50dp"
                android:paddingRight="50dp"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:id="@+id/bt_login"
                android:text="@string/login"
                />
        </RelativeLayout>
    </LinearLayout>


```

2. MainActivity.java

```
    package com.example.myapp;

    import android.content.Context;
    import android.os.Environment;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.text.TextUtils;
    import android.text.format.Formatter;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.CheckBox;
    import android.widget.EditText;
    import android.widget.Toast;

    import java.io.File;
    import java.util.Map;

    public class MainActivity extends AppCompatActivity implements View.OnClickListener {
        private EditText et_username;
        private EditText et_password;
        private CheckBox cb_rem;
        private Button bt_login;
        private Context mContext;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mContext = this;
            et_username = (EditText) findViewById(R.id.et_username);
            et_password = (EditText) findViewById(R.id.et_password);
            cb_rem = (CheckBox) findViewById(R.id.cb_rem);
            bt_login = (Button) findViewById(R.id.bt_login);

            bt_login.setOnClickListener(this);
            Map<String,String> map = UserInfoUtil.getUserInfo(mContext);
            if(map != null){
                String username = map.get("username");
                String password = map.get("password");
                et_username.setText(username);
                et_password.setText(password);
            }

            //授予sd卡读写权限
            GrantPermission gp = new GrantPermission();
            gp.requestPermissions(this);
        }

        private void login(){
            String username = et_username.getText().toString().trim();
            String password = et_password.getText().toString().trim();
            boolean isrem = cb_rem.isChecked();
            if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                Toast.makeText(mContext,"用户名密码不能为空！",Toast.LENGTH_SHORT).show();
                return;
            }

            if(isrem){
                //判断sd卡状态是否正常
                if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    Toast.makeText(mContext,"SD卡不存在或未挂载！",Toast.LENGTH_SHORT).show();
                    return;
                }

                //判断sd卡存储空间是否满足文件的存储
                File sdcard_filedir = Environment.getExternalStorageDirectory(); //得到sdcard的目录作为一个文件对象
                long usableSpace = sdcard_filedir.getUsableSpace();  //获取文件目录对象的可用空间
                String usableSpace_str = Formatter.formatFileSize(mContext,usableSpace);
                Log.i("info",usableSpace_str);

                if(usableSpace < 1024*1024*200){  //判断剩余空间是否小于200M
                    Toast.makeText(mContext,"SD卡剩余空间不足！剩余空间为：" + usableSpace_str,Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean result = UserInfoUtil.saveUserInfo(mContext,username,password);
                if(result){
                    Toast.makeText(mContext,"用户名密码保存成功！",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mContext,"用户名密码保存失败！",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(mContext,"无需保存！",Toast.LENGTH_SHORT).show();
            }
        }
        public void onClick(View v){
            switch(v.getId()){
                case R.id.bt_login:
                    login();
                    break;
                default:



            }
        }


    }


```

3. UserInfoUtil.java

```
    package com.example.myapp;

    import android.content.Context;
    import android.os.Environment;
    import android.util.Log;

    import java.io.BufferedReader;
    import java.io.File;
    import java.io.FileInputStream;
    import java.io.FileOutputStream;
    import java.io.InputStreamReader;
    import java.util.HashMap;
    import java.util.Map;

    /**
    * Created by zhouchaozhong on 2018/1/21.
    */

    public class UserInfoUtil {

        public static boolean saveUserInfo(Context context, String username, String password){

            try{
                String userinfo = username + "##" + password;
    //            String path = "/data/data/com.example.myapp/";
                //String path = context.getFilesDir().getPath();
                //String path = "/mnt/sdcard/";  //指定保存的路径
    //            String path = Environment.getExternalStorageDirectory().getPath();
    //            String state = Environment.getExternalStorageState();


                //传入私有目录的文件名称，

    //            File file = new File(path,"userinfo.txt");
    //            FileOutputStream fos = new FileOutputStream(file);

                FileOutputStream fos = context.openFileOutput("userinfo.txt",Context.MODE_PRIVATE);
                fos.write(userinfo.getBytes());
                fos.close();
                return true;
            }catch(Exception e){
                e.printStackTrace();
            }

            return false;
        }

        public static Map<String,String> getUserInfo(Context context){
            try{
                //String path = "/data/data/com.example.myapp/";
                //String path = context.getFilesDir().getPath();
                //String path = "/mnt/sdcard/";  //指定保存的路径
    //            String path = Environment.getExternalStorageDirectory().getPath();
    //            File file = new File(path,"userinfo.txt");
    //            FileInputStream fis = new FileInputStream(file);

                FileInputStream fis = context.openFileInput("userinfo.txt");
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));

                String readLine = br.readLine();
                String[] split = readLine.split("##");
                Map<String,String> hashMap = new HashMap<String,String>();
                hashMap.put("username",split[0]);
                hashMap.put("password",split[1]);
                br.close();
                fis.close();
                return hashMap;
            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }



    }


```



外置sdcard文件读写
--------------------------------------------------------

1. Activity_main.xml

```
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        tools:context="com.example.myapp.MainActivity">


        <EditText
            android:layout_marginTop="10dp"
            android:layout_marginBottom="10dp"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:id="@+id/et_username"
            android:hint="@string/input_username"
            />
        <EditText
            android:inputType="textPassword"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:id="@+id/et_password"
            android:hint="@string/input_password"
            />
        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
            <CheckBox
                android:layout_alignParentLeft="true"
                android:layout_centerVertical="true"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:id="@+id/cb_rem"
                android:text="@string/rem_password"
                />
            <Button
                android:layout_centerVertical="true"
                android:layout_alignParentRight="true"
                android:paddingLeft="50dp"
                android:paddingRight="50dp"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:id="@+id/bt_login"
                android:text="@string/login"
                />
        </RelativeLayout>
    </LinearLayout>


```

2. MainActivity.java

```
    package com.example.myapp2;

    import android.content.Context;
    import android.os.Environment;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.text.TextUtils;
    import android.text.format.Formatter;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.CheckBox;
    import android.widget.EditText;
    import android.widget.Toast;

    import java.io.File;
    import java.util.Map;

    public class MainActivity extends AppCompatActivity implements View.OnClickListener {
        private EditText et_username;
        private EditText et_password;
        private CheckBox cb_rem;
        private Button bt_login;
        private Context mContext;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mContext = this;
            et_username = (EditText) findViewById(R.id.et_username);
            et_password = (EditText) findViewById(R.id.et_password);
            cb_rem = (CheckBox) findViewById(R.id.cb_rem);
            bt_login = (Button) findViewById(R.id.bt_login);

            bt_login.setOnClickListener(this);
            Map<String,String> map = UserInfoUtil.getUserInfo(mContext);
            if(map != null){
                String username = map.get("username");
                String password = map.get("password");
                et_username.setText(username);
                et_password.setText(password);
            }

        }

        private void login(){
            String username = et_username.getText().toString().trim();
            String password = et_password.getText().toString().trim();
            boolean isrem = cb_rem.isChecked();
            if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                Toast.makeText(mContext,"用户名密码不能为空！",Toast.LENGTH_SHORT).show();
                return;
            }

            if(isrem){
                //判断sd卡状态是否正常
                if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    Toast.makeText(mContext,"SD卡不存在或未挂载！",Toast.LENGTH_SHORT).show();
                    return;
                }

                //判断sd卡存储空间是否满足文件的存储
                File sdcard_filedir = Environment.getExternalStorageDirectory(); //得到sdcard的目录作为一个文件对象
                long usableSpace = sdcard_filedir.getUsableSpace();  //获取文件目录对象的可用空间
                String usableSpace_str = Formatter.formatFileSize(mContext,usableSpace);
                Log.i("info",usableSpace_str);

                if(usableSpace < 1024*1024*200){  //判断剩余空间是否小于200M
                    Toast.makeText(mContext,"SD卡剩余空间不足！剩余空间为：" + usableSpace_str,Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean result = UserInfoUtil.saveUserInfo(mContext,username,password);
                if(result){
                    Toast.makeText(mContext,"用户名密码保存成功！",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mContext,"用户名密码保存失败！",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(mContext,"无需保存！",Toast.LENGTH_SHORT).show();
            }
        }
        public void onClick(View v){
            switch(v.getId()){
                case R.id.bt_login:
                    login();
                    break;
                default:



            }
        }

    }



```

3. UserInfoUtil.java

```
    package com.example.myapp2;

    import android.content.Context;
    import android.content.SharedPreferences;
    import android.content.SharedPreferences.Editor;
    import android.preference.PreferenceManager;

    import java.util.HashMap;
    import java.util.Map;

    /**
    * Created by zhouchaozhong on 2018/1/21.
    */

    public class UserInfoUtil {

        public static boolean saveUserInfo(Context context, String username, String password){
            //SharedPreferences spf = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
            SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(context);
            Editor editor = spf.edit();
            editor.putString("username",username);
            editor.putString("password",password);
            editor.commit();
            return true;
        }

        public static Map<String,String> getUserInfo(Context context){
            //SharedPreferences spf = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);

            SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(context);
            String username = spf.getString("username","admin");
            String password = spf.getString("password","admin");
            Map<String,String> map = new HashMap<String,String>();
            map.put("username",username);
            map.put("password",password);
            return map;
        }
    }

```