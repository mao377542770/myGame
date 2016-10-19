package com.mao.fly_shoot;

import java.util.Random;

/*敌机：又是飞行物，也是敌人*/
public class Airplane extends FlyingObject implements Enemy {
	
	private int speed = 2; // 敌机移动的速度
	
	public Airplane(){
		image = ShootGame.airplane;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;
		Random rand = new Random();
		x = rand.nextInt((ShootGame.WIDTH - width));
	}

	public int getScore() {

		return 50; // 敌机被打到加50分
	}
	public void step(Hero hero){
		y += speed;
	}
	public boolean outOfBounds(){                //重写出界
		return this.y > ShootGame.HEIGHT;
	}
}
