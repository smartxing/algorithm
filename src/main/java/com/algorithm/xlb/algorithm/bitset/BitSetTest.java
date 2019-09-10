package com.algorithm.xlb.algorithm.bitset;

import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;

/**
 *
 * @author xingliangbo
 * @version $Id: v 0.1 2019-09-10 11:13 xingliangbo Exp $
 */
public class BitSetTest {

  public static void main(String[] args) {

    BitSet bitSet = new BitSet(6);
//    bitSet.set(1, false);
    bitSet.set(2, true);
    bitSet.set(3, true);

    System.out.println(bitSet.cardinality());
    BitSet bitSet2 = new BitSet(6);
    bitSet2.set(1, true);
    bitSet2.set(2, true);
//    bitSet.and(bitSet2);
//    System.out.println("xx:" + bitSet);

//    bitSet.or(bitSet2);
//    System.out.println("xx:" + bitSet);
    //同值取0 异值取1
//    bitSet.xor(bitSet2);
//    System.out.println("xx:" + bitSet);
    //相同的取0
    bitSet.andNot(bitSet2);
    System.out.println("xx:" + bitSet);

    System.out.println(Integer.toBinaryString(-1));
    int x = 0x7FFFFFFF;
    System.out.println(Integer.toBinaryString(x));


    System.out.println(-1 & x);
    System.out.println(Integer.MAX_VALUE);
    System.out.println(Integer.MIN_VALUE);
    System.out.println(Integer.toBinaryString(-2147483648));
    System.out.println("1111111111111111111111111111111".length());
    HashMap<Object, Object> objectObjectHashMap = Maps.newHashMap();
    objectObjectHashMap.hashCode();
    //111 => 11
    System.out.println("7 >> 1 = " + (7 >> 1));
    //相当于除以2
    System.out.println("8 >> 1 = " + (8 >> 1));
    //111 => 1110 + 7 +2
    System.out.println("7 << 1 = " + (7 << 1));
    System.out.println("8 << 1 = " + (8 << 1));
    //1000000...1 => 1000000...10
    System.out.println("-1 << 1 = " + (-1 << 1));
    System.out.println("-1 >> 1 = " + (-1 >> 1));
    //1000000...1 => 01000000...0 符号位也会移动
    System.out.println("-1 >> 1 = " + (-1 >>> 1) + " 2^31 " + Integer.MAX_VALUE);

//    System.out.println(bitSet);
//    System.out.println(Arrays.toString(bitSet.toByteArray()));
//    System.out.println(bitSet.get(3));
//
//    System.out.println((-1 & 0x7FFFFFFF));
//
//    System.out.println(Integer.toBinaryString(-1));
//    System.out.println(Integer.toBinaryString(1));
//    int x = 0x7FFFFFFF;
//    System.out.println(x);

  }
}
