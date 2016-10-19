package com.mao.fly_shoot;

import java.awt.image.BufferedImage;
/*英雄机类*/
public class Hero extends FlyingObject{
	private int life;            //英雄机的命
	private int doubleFire;    //双倍火力
	private BufferedImage[] images = {};           //两张图片
	private int index;     //交换图片时的变量
	
 public int getLife(){        //返回生命数
	 return life;
 }
		/*初始化英雄机*/
	public  Hero(){
		image =ShootGame.hero0;
		width = image.getWidth();
		height = image.getHeight();
		x = 150;          
		y = 400;
		doubleFire = 0;
		life = 3;
		images = new BufferedImage[]{ShootGame.hero0 , ShootGame.hero1};
	}
	
	/*发射子弹方法*/
	public Bullet[] shoot(){
		int xStep = this.width/4;
		int yStep = 20;
		if(doubleFire > 0 ){               // 双倍火力
			Bullet[] bullets = new Bullet[2];
			bullets[0] =new Bullet(this.x+xStep-7,this.y-yStep);
			bullets[1] = new Bullet(this.x+3*xStep,this.y-yStep);
			doubleFire --;
			return bullets;
 		}else{                                  //单倍火力
 			Bullet[] bullets = new Bullet[1];
 			bullets[0] = new Bullet(this.x+2*xStep-3,this.y - yStep);
 			return bullets;
 		}
	}
	public void step(Hero hero){
		int num =index++/10%images.length;      //num s结果只会取到 0或 1，并且是0和1交替
		image = images[num];                //step 方法调用10次换一次图片
		
	}
	/*从鼠标x,y中获取英雄机的 x和y*/
	public void moveTo(int x , int y){
		this.x = x - this.width/2;
		this.y = y - this.height/2;
	}
	public void addDoubleFire(){        //添加双倍火力
		doubleFire += 40;
	}
	public void addLife(){                    //增加生命
		life++;
	}
	public void substractLife(){			//减少生命
		life--;
	}
	/*设置火力*/
	public void setDoubleFire(int foubleFire){
		this.doubleFire = foubleFire;
	}
	/*判断英雄机与敌人是否碰撞*/
	public boolean hit(FlyingObject other){
		int x1 = other.x - width/2 + 10;
		int x2 = other.x +other.width+ width/2 - 10;
		int y1 = other.y - height/2 -10 + 20;
		int y2 = other.y + other.height + height/2 - 30;
		int hx = x+width/2;
		int hy = y + height/2;
		return (hx >x1 && hx <x2 && hy >y1 && y < y2);
	}
	public boolean outOfBounds(){                //重写出界
		return false;
	}
}
