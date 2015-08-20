package com.android.sampletexture;




import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;


public class MainActivity extends Activity {

	private MySurfaceView mGLSurfaceView;
	//static boolean threadFlag;//纹理矩形绕X轴旋转工作标志位
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//设置为横屏模式
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//切换到主界面
		//setContentView(R.layout.activity_main);
		
		
		//初始化GLSurfaceView
        mGLSurfaceView = new MySurfaceView(this);
        setContentView(mGLSurfaceView);	
        mGLSurfaceView.requestFocus();//获取焦点
        mGLSurfaceView.setFocusableInTouchMode(true);//设置为可触控 
       // LinearLayout ll = (LinearLayout) findViewById(R.id.back_bg);
		//ll.addView(mGLSurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  threadFlag=true;
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
      //  threadFlag=false;
        mGLSurfaceView.onPause();
    }   
}
