package com.comsince.github.utils;

import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;

/*
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-25 下午5:18
 **/
public class RedisUtils {
    private static RedisUtils redisUtils;

    private RedisUtils(){}

    /**
     * 提供单例模式
     * @return
     */
    public static RedisUtils getInstance(){
        if(redisUtils==null)
            synchronized (RedisUtils.class) {
                if(redisUtils==null) redisUtils=new RedisUtils();
            }
        return redisUtils;
    }


    /**
     * 使用config创建Redisson
     * Redisson是用于连接Redis Server的基础类
     * @param config
     * @return
     */
    public RedissonClient getRedisson(Config config){
        RedissonClient redissonClient=Redisson.create(config);
        System.out.println("成功连接Redis Server");
        return redissonClient;
    }

    /**
     * 使用ip地址和端口创建Redisson
     * @param ip
     * @param port
     * @return
     */
    public RedissonClient getRedisson(String ip,String port){
        Config config=new Config();
        config.useSingleServer().setAddress("redis://"+ip+":"+port);
        RedissonClient redissonClient=Redisson.create(config);
        System.out.println("成功连接Redis Server"+"\t"+"连接"+ip+":"+port+"服务器");
        return redissonClient;
    }

    /**
     * 关闭Redisson客户端连接
     * @param redisson
     */
    public void closeRedisson(Redisson redisson){
        redisson.shutdown();
        System.out.println("成功关闭Redis Client连接");
    }

    /**
     * 获取字符串对象
     * @param redisson
     * @param t
     * @param objectName
     * @return
     */
    public <T> RBucket<T> getRBucket(Redisson redisson,String objectName){
        RBucket<T> bucket=redisson.getBucket(objectName);
        return bucket;
    }

    /**
     * 获取Map对象
     * @param redisson
     * @param objectName
     * @return
     */
    public <K,V> RMap<K, V> getRMap(Redisson redisson,String objectName){
        RMap<K, V> map=redisson.getMap(objectName);
        return map;
    }

    /**
     * 获取有序集合
     * @param redisson
     * @param objectName
     * @return
     */
    public <V> RSortedSet<V> getRSortedSet(Redisson redisson,String objectName){
        RSortedSet<V> sortedSet=redisson.getSortedSet(objectName);
        return sortedSet;
    }

    /**
     * 获取集合
     * @param redisson
     * @param objectName
     * @return
     */
    public <V> RSet<V> getRSet(Redisson redisson,String objectName){
        RSet<V> rSet=redisson.getSet(objectName);
        return rSet;
    }

    /**
     * 获取列表
     * @param redisson
     * @param objectName
     * @return
     */
    public <V> RList<V> getRList(Redisson redisson,String objectName){
        RList<V> rList=redisson.getList(objectName);
        return rList;
    }

    /**
     * 获取队列
     * @param redisson
     * @param objectName
     * @return
     */
    public <V> RQueue<V> getRQueue(Redisson redisson,String objectName){
        RQueue<V> rQueue=redisson.getQueue(objectName);
        return rQueue;
    }

    /**
     * 获取双端队列
     * @param redisson
     * @param objectName
     * @return
     */
    public <V> RDeque<V> getRDeque(Redisson redisson, String objectName){
        RDeque<V> rDeque=redisson.getDeque(objectName);
        return rDeque;
    }

    /**
     * 此方法不可用在Redisson 1.2 中
     * 在1.2.2版本中 可用
     * @param redisson
     * @param objectName
     * @return
     */
    /**
     public <V> RBlockingQueue<V> getRBlockingQueue(Redisson redisson,String objectName){
     RBlockingQueue rb=redisson.getBlockingQueue(objectName);
     return rb;
     }*/

    /**
     * 获取锁
     * @param redisson
     * @param objectName
     * @return
     */
    public RLock getRLock(Redisson redisson, String objectName){
        RLock rLock=redisson.getLock(objectName);
        return rLock;
    }

    /**
     * 获取原子数
     * @param redisson
     * @param objectName
     * @return
     */
    public RAtomicLong getRAtomicLong(Redisson redisson, String objectName){
        RAtomicLong rAtomicLong=redisson.getAtomicLong(objectName);
        return rAtomicLong;
    }

    /**
     * 获取记数锁
     * @param redisson
     * @param objectName
     * @return
     */
    public RCountDownLatch getRCountDownLatch(Redisson redisson,String objectName){
        RCountDownLatch rCountDownLatch=redisson.getCountDownLatch(objectName);
        return rCountDownLatch;
    }

    /**
     * 获取消息的Topic
     * @param redisson
     * @param objectName
     * @return
     */
//    public <T> RTopic<T> getRTopic(Redisson redisson,String objectName){
//        RTopic<T> rTopic=redisson.getTopic(objectName);
//        return rTopic;
//    }
}
