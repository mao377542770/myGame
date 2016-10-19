package com.mao.fly_shoot;
 /*蜜蜂类*      是飞行物，也是奖励*/
public  class Bee extends FlyingObject implements Award{
	private int xSpeed = 2;            //x坐标走步
	private int ySpeed = 3;				//y坐标走步
	private int awardType;             //奖励类型
	
	/*初始化实例变量*/
	public Bee(){
		image = ShootGame.bee;           //初始化图片
		width = image.getWidth();  		 //图片宽
		height = image.getHeight();		//图片高
		y = -height;
		x = (int)(Math.random()*(ShootGame.WIDTH - width));
		awardType =(int)(Math.random()*2);
	}
	
	public int getType(){            //获得奖励
		return awardType;
	}
	public void step(Hero hero){
		x += xSpeed;
		if(x <= 0){
			xSpeed = -2;
		}
		if(x >= ShootGame.WIDTH - width){
			xSpeed = -2;
		}
		y += ySpeed;
	}
	public boolean outOfBounds() {
		return this.y > ShootGame.HEIGHT;
	}
}
