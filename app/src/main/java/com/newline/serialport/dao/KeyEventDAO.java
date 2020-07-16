package com.newline.serialport.dao;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/7/16 15:52
 * @describe
 */
public class KeyEventDAO {

    public static Queue<Integer> keyCodeQueue = new ConcurrentLinkedQueue();

} 
