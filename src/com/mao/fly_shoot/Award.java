package com.mao.fly_shoot;

public interface Award {
	public static final int DOUBLE_FIRE = 0;       //双倍火力
	int LIFE =1;													//生命
	int getType();										//为0奖励双倍火力，为1奖励声生命
}
