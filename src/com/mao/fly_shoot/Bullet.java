package com.mao.fly_shoot;
/*子弹类;飞行物*/
public class Bullet extends FlyingObject{
	private int speed = 5;      //移动的速度
	
	public Bullet(int x,int y){
		image = ShootGame.bullet;           //初始化图片
		width = image.getWidth();		 //图片宽
		height = image.getHeight();		//图片高
		this.x = x;
		this.y = y;
	}
	public void step(Hero hero){
		y -= speed;
	}
	public boolean outOfBounds(){                //重写出界
		return y<-height;                  //小于负的高时
	}
}
