package com.sdw.base.utils;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;

/**
* <p>Description: 封装redis 缓存服务器服务接口</p>
*
* @date 2016-12-29下午5:56:21
*/
public class RedisService {
	 //操作redis客户端
    private static Jedis jedis;
    @Autowired
    @Qualifier("jedisConnectionFactory")
    private JedisConnectionFactory jedisConnectionFactory;
    /**
     * 通过key删除（字节）
     */
    public void del(byte [] key){
        this.getJedis().del(key);
    }
    /**
     * 通过key删除
     */
    public void del(String key){
        this.getJedis().del(key);
    }

    /**
     * 添加key value 并且设置存活时间(byte)
     */
    public void set(byte [] key,byte [] value,int liveTime){
        this.set(key, value);
        this.getJedis().expire(key, liveTime);
    }
    /**
     * 添加key value 并且设置存活时间
     */
    public void set(String key,String value,int liveTime){
        this.set(key, value);
        this.getJedis().expire(key, liveTime);
    }
    /**
     * 添加key value
     */
    public void set(String key,String value){
        this.getJedis().set(key, value);
    }
    /**添加key value (字节)(序列化)
     */
    public void set(byte [] key,byte [] value){
        this.getJedis().set(key, value);
    }
    /**
     * 获取redis value (String)
     */
    public String get(String key){
        String value = this.getJedis().get(key);
        return value;
    }
    /**
     * 获取redis value (byte [] )(反序列化)
     */
    public byte[] get(byte [] key){
        return this.getJedis().get(key);
    }

    /**
     * 通过正则匹配keys
     */
    public Set<String> keys(String pattern){
        return this.getJedis().keys(pattern);
    }

    /**
     * 检查key是否已经存在
     */
    public boolean exists(String key){
        return this.getJedis().exists(key);
    }
    /**
     * 清空redis 所有数据
     */
    public String flushDB(){
        return this.getJedis().flushDB();
    }
    /**
     * 查看redis里有多少数据
     */
    public long dbSize(){
        return this.getJedis().dbSize();
    }
    /**
     * 检查是否连接成功
     */
    public String ping(){
        return this.getJedis().ping();
    }
    /**
     * 获取一个jedis 客户端
     */
    private Jedis getJedis(){
        if(jedis == null){
            return jedisConnectionFactory.getShardInfo().createResource();
        }
        return jedis;
    }
    private RedisService (){

    }
}

