/**
 * 
 */
package com.android.sampletexture;


import java.io.IOException;
import java.io.InputStream;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.view.MotionEvent;
import android.opengl.GLES20;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * @Project SampleTexture	
 * @author houxb
 * @Date 2015-8-19
 */
public class MySurfaceView extends GLSurfaceView {

	private final float TOUCH_SCALE_FACTOR = 180.0f/320;
	private float mPreviousX;
	private float mPreviousY;
	private SceneRenderer mRenderer;
	int textureId;
	int currTextureId;//当前纹理id 
	int textureREId;//系统分配的重复纹理id
	/**
	 * @param context
	 * 08-20 10:17:25.924: W/System.err(29990): java.lang.RuntimeException: Unable to start activity ComponentInfo{com.android.sampletexture/com.android.sampletexture.MainActivity}: 

	 */
	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setEGLContextClientVersion(2);
		mRenderer = new SceneRenderer();
		setRenderer(mRenderer);
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY); //主动渲染
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		float x = e.getX();
		float y = e.getY();
		switch (e.getAction()) {
		case MotionEvent.ACTION_MOVE:
			float dy = y - mPreviousY;
			float dx = x - mPreviousX;
			mRenderer.textRect.yAngle += dx * TOUCH_SCALE_FACTOR;
			mRenderer.textRect.zAngle += dy * TOUCH_SCALE_FACTOR;
		}
		mPreviousX = x;
		mPreviousY = y;
		return true;
		
	}
	/**
	 * SCREEN_WIDTH=getWindowManager().getDefaultDisplay().getWidth();
		SCREEN_HEIGHT=getWindowManager().getDefaultDisplay().getHeight();
	 * @Project SampleTexture	
	 * @author houxb
	 * @Date 2015-8-20
	 */
	private class SceneRenderer implements GLSurfaceView.Renderer {

		TextureRect textRect;
		/* (non-Javadoc)
		 * @see android.opengl.GLSurfaceView.Renderer#onDrawFrame(javax.microedition.khronos.opengles.GL10)
		 */
		@Override
		public void onDrawFrame(GL10 arg0) {
			// TODO Auto-generated method stub
			GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
			textRect.drawSelf(textureId);
		}

		/* (non-Javadoc)
		 * @see android.opengl.GLSurfaceView.Renderer#onSurfaceChanged(javax.microedition.khronos.opengles.GL10, int, int)
		 */
		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			// TODO Auto-generated method stub
			GLES20.glViewport(0, 0, width, height);
			float ratio = (float) width/height;
			MatrixState.setProject(-ratio, ratio, -1, 1, 1, 10);
			MatrixState.setCamera(0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
		}

		/* (non-Javadoc)
		 * @see android.opengl.GLSurfaceView.Renderer#onSurfaceCreated(javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.egl.EGLConfig)
		 */
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			// TODO Auto-generated method stub
			GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
			//textRect = new Triangle(MySurfaceView.this);
			textRect=new TextureRect(MySurfaceView.this,1,1); 
			GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            //初始化系统分配的重复纹理id
            textureREId=initTexture(true);
            //初始化当前纹理id
            currTextureId=textureREId;
			GLES20.glDisable(GLES20.GL_CULL_FACE);
		}

		
		
		
	}
	/**
	 * @param isRepeat 
	 * 
	 */
	public int initTexture(boolean isRepeat) {
		// TODO Auto-generated method stub
		int[] textures = new int[1];
		GLES20.glGenTextures
		(
				1,          //产生的纹理id的数量
				textures,   //纹理id的数组
				0           //偏移量
		);    
		textureId=textures[0];    
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);
        
     
        //通过输入流加载图片===============begin===================
        //InputStream is = this.getResources().openRawResource(R.drawable.wall);
		InputStream is = this.getResources().openRawResource(R.drawable.start_bg);;
        Bitmap bitmapTmp;
        try 
        {
        	bitmapTmp = BitmapFactory.decodeStream(is);
        } 
        finally 
        {
            try 
            {
                is.close();
            } 
            catch(IOException e) 
            {
                e.printStackTrace();
            }
        }
        //通过输入流加载图片===============end=====================  
        
        //实际加载纹理
        GLUtils.texImage2D
        (
        		GLES20.GL_TEXTURE_2D,   //纹理类型，在OpenGL ES中必须为GL10.GL_TEXTURE_2D
        		0, 					  //纹理的层次，0表示基本图像层，可以理解为直接贴图
        		bitmapTmp, 			  //纹理图像
        		0					  //纹理边框尺寸
        );
        bitmapTmp.recycle(); 		  //纹理加载成功后释放图片
        return textureId;
	}
}
