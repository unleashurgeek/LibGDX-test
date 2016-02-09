package edu.uci.vgdc.proceduralgame.utils;

import java.nio.ByteBuffer;

import net.jpountz.xxhash.XXHash64;

public class Hasher {
	
	private final XXHash64 hashFactory;
	private final long seed;
	
	public Hasher(XXHash64 hashFactory, long seed) {
		this.hashFactory = hashFactory;
		this.seed = seed;
	}
	
	public long getHash(int buff) {
		return hashFactory.hash(ByteBuffer.allocate(4).putInt(buff), this.seed);
	}
	
	public long getHash(int x, int y) {
		ByteBuffer buff = ByteBuffer.allocate(8);
		buff.putInt(x);
		buff.putInt(y);
		return hashFactory.hash(buff, seed);
	}
	
	public long getHash(int[] ints) {
		ByteBuffer buff = ByteBuffer.allocate(ints.length * 4);
		for (int i : ints)
			buff.putInt(i);
		return hashFactory.hash(buff, seed);
	}
}
