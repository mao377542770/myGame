package com.mao.fly_shoot;
/**
 * 创建Boss 类
 */
public class Boss extends FlyingObject implements Enemy{
	private int speed = 1;        //二小姐下路速度
	//把二小姐重写Boss 行为
	public Boss(){
		image = ShootGame.boss;
		life = 10;
		width = image.getWidth();
		height = image.getHeight();
		y = - height;
		x = (int)(Math.random()*(ShootGame.WIDTH - width));
	}
	public boolean outOfBounds() {
		return this.y > ShootGame.HEIGHT;
	}
	/**
	 * 重写二小姐移动方法
	 * 猫猫版移动方式 -- 有待完善
	 */
	public void step(Hero hero) {
		this.y = this.y + speed;
		int heroX = hero.x;
		if(heroX < x){
			x -= speed;
		}else if(heroX > x){
			x += speed;
		}
	}
	public int getScore() {
		return 1000;
	}
}
