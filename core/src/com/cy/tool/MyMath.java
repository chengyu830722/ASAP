package com.cy.tool;


public class MyMath
{
    public static int NORMAL=0;
    public static int PINGPONG=1;
    /**
     * cycle 表示周期
     * A,B表示变换的范围
     * input 表示周期中运行的时间点
     */
    public static float linearinterval(int input, int cycle, float A,float B,int style)
    {
	float output=0;
	if (style==NORMAL)
	{
	    input=input%cycle;
	    output= A+input*(B-A)/(cycle-1);
	}
	if (style==PINGPONG)
	{
	    float x=1.0f*input%cycle/cycle;
	    if(x<=0.5f)
	    {
		output= A+x*(B-A)*2;
	    }
	    else {
		x-=0.5f;
		output= B-x*(B-A)*2;
	    }
	}
	return output;
	
    }
}
