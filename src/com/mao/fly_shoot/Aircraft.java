package com.mao.fly_shoot;

import java.util.Random;

public class Aircraft extends FlyingObject implements Enemy{
	private int speed = 3;    //*中敌机的移动速度
	
	public Aircraft(){
		life = 3;                                      //*重新初始化中敌机生命值 
		image = ShootGame.aircraft;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;
		Random rand = new Random();
		x = rand.nextInt((ShootGame.WIDTH - width));
	}
/**
 * 重写出界，如果尾部出最下界面则判断出界
 */
	public boolean outOfBounds() {    
		return this.y > ShootGame.HEIGHT;
	}

	public void step(Hero hero) {
		this.y += speed;
	}
/**
 * 返回得分200
 */
 	public int getScore() {  
		return 200;
	}
}
