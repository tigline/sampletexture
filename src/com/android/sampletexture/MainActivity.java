package com.android.sampletexture;




import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;


public class MainActivity extends Activity {

	private MySurfaceView mGLSurfaceView;
	//static boolean threadFlag;//���������X����ת������־λ
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//����Ϊ����ģʽ
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//�л���������
		//setContentView(R.layout.activity_main);
		
		
		//��ʼ��GLSurfaceView
        mGLSurfaceView = new MySurfaceView(this);
        setContentView(mGLSurfaceView);	
        mGLSurfaceView.requestFocus();//��ȡ����
        mGLSurfaceView.setFocusableInTouchMode(true);//����Ϊ�ɴ��� 
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
