package edu.uci.vgdc.proceduralgame.utils;

import java.util.Random;

public class CustomRandom extends Random {
	private static final long serialVersionUID = 8610343321654645607L;
	
	private long seed;
	
	public CustomRandom() {
		this(System.nanoTime());
	}
	
	public CustomRandom(long seed) {
		this.seed = seed;
	}
	
	@Override
	protected int next(int bits) {
		seed ^= seed << 13;
		seed ^= seed >>> 17;
		seed ^= seed << 5;
		return (int)(seed >>> (64 - bits));
	}
}
