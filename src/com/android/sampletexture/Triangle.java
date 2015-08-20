/**
 * 
 */
package com.android.sampletexture;

import static com.android.sampletexture.ShaderUtil.createProgram;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


import android.opengl.GLES20;
/**
 * @Project SampleTexture	
 * @author houxb
 * @Date 2015-8-19
 */
public class Triangle {
	
	int mProgram;
	int muMVPMatrixHandle;
	int maPositionHandle;
	int maTexCoorHandle;
	String mVertexShader;
	String mFragmentShader;
	FloatBuffer mVertexBuffer;
	FloatBuffer mTexCoorBuffer;
	int vCount = 0;
	float xAngle = 0;
	float yAngle = 0;
	float zAngle = 0;
	
	public Triangle(MySurfaceView mv) {
		initVertexData();
		initShader(mv);
	}
	//����
	public void drawSelf(int texId) {
		 //�ƶ�ʹ��ĳ��shader����
   	 GLES20.glUseProgram(mProgram);        
   	 
   	 MatrixState.setInitStack();
   	 
        //������Z������λ��1
        MatrixState.translate(0, 0, 1);
        
        //������y����ת
        MatrixState.rotate(yAngle, 0, 1, 0);
        //������z����ת
        MatrixState.rotate(zAngle, 0, 0, 1);  
        //������x����ת
        MatrixState.rotate(xAngle, 1, 0, 0);
        //�����ձ任������shader����
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
        //Ϊ����ָ������λ������
        GLES20.glVertexAttribPointer  
        (
        		maPositionHandle,   
        		3, 
        		GLES20.GL_FLOAT, 
        		false,
               3*4,   
               mVertexBuffer
        );       
        //Ϊ����ָ������������������
        GLES20.glVertexAttribPointer  
        (
       		maTexCoorHandle, 
        		2, 
        		GLES20.GL_FLOAT, 
        		false,
               2*4,   
               mTexCoorBuffer
        );   
        //������λ����������
        GLES20.glEnableVertexAttribArray(maPositionHandle);  
        GLES20.glEnableVertexAttribArray(maTexCoorHandle);  
        
        //������
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);
        
        //�����������
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
		
	}
	/**
	 * @param mv
	 */
	private void initShader(MySurfaceView mv) {
		// TODO Auto-generated method stub
		mVertexShader = ShaderUtil.loadFromAssetFile("vertex.sh", mv.getResources());
		mFragmentShader = ShaderUtil.loadFromAssetFile("frag.sh", mv.getResources());
		mProgram = createProgram(mVertexShader, mFragmentShader);
		maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
		maTexCoorHandle = GLES20.glGetAttribLocation(mProgram, "aTexCoor");
		muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
	}
	/**
	 * 
	 */
	private void initVertexData() {
		// TODO Auto-generated method stub
		vCount = 3;
		final float UNIT_SIZE = 0.15f;
		float vertices[] = new float[] {
				0*UNIT_SIZE, 11*UNIT_SIZE, 0,
				-11*UNIT_SIZE, -11*UNIT_SIZE, 0,
				11*UNIT_SIZE, -11*UNIT_SIZE, 0,				
		};
		
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer = vbb.asFloatBuffer();
		mVertexBuffer.put(vertices);
		mVertexBuffer.position(0);
		
		float texCoor[] = new float[] {
				0.5f, 0,
				0, 1,
				1, 1
		};
		ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		mTexCoorBuffer = vbb.asFloatBuffer();
		mTexCoorBuffer.put(texCoor);
		mTexCoorBuffer.position(0);
	}
	
	
}
