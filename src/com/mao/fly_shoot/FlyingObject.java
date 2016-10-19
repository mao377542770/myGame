package com.mao.fly_shoot;
// 飞行物父类
import java.awt.image.BufferedImage;
//java 建议： 成员变量私有 private
//					方法共有		public 
public abstract class FlyingObject {
	protected int life = 1;
	protected int x;            //x坐标
	protected int y;			//y坐标
	protected int width;		//宽
	protected int height;	//长
	protected BufferedImage image;
	public abstract void step(Hero hero);
	public boolean shootBy(Bullet b){            //子弹打敌人
		int x = b.x;
		int y = b.y;
		if( x>this.x && x<this.x+width && y>this.y && y<this.y+height){
			return true;
		}
		return false;
	}
	public abstract boolean outOfBounds();
}

