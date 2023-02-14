package cn.zeroeden.interfaceBrushProtection.interceptor;

import cn.zeroeden.interfaceBrushProtection.annotation.AccessLimit;
import cn.zeroeden.interfaceBrushProtection.constant.ResultCode;
import cn.zeroeden.interfaceBrushProtection.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandle;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: Zero
 * @time: 2023/2/14
 * @description: 接口防刷拦截处理
 */
@Slf4j
public class AccessLimintInterceptor  implements HandlerInterceptor {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

//    /**
//     * 多长时间内
//     */
//    @Value("${interfaceAccess.second}")
//    private Long second = 10L;
//
//    /**
//     * 访问次数
//     */
//    @Value("${interfaceAccess.time}")
//    private Long time = 3L;
//
//    /**
//     * 禁用时长--单位/秒
//     */
//    @Value("${interfaceAccess.lockTime}")
//    private Long lockTime = 60L;

    /**
     * 锁住时的key前缀
     */
    public static final String LOCK_PREFIX = "LOCK";

    /**
     * 统计次数时的key前缀
     */
    public static final String COUNT_PREFIX = "COUNT";


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        简单实现
//        String uri = request.getRequestURI();
//        String ip = request.getRemoteAddr(); // 这里忽略代理软件方式访问，默认直接访问，获取得到的就是访问者真实ip地址
//        String lockKey = LOCK_PREFIX + ip + uri;
//        Object isLock = redisTemplate.opsForValue().get(lockKey);
//        if(Objects.isNull(isLock)){
//            // 还未被禁用
//            String countKey = COUNT_PREFIX + ip + uri;
//            Object count = redisTemplate.opsForValue().get(countKey);
//            if(Objects.isNull(count)){
//                // 首次访问
//                log.info("首次访问");
//                redisTemplate.opsForValue().set(countKey,1,second, TimeUnit.SECONDS);
//            }else{
//                // 此用户前一点时间就访问过该接口
//                if((Integer)count < time){
//                    redisTemplate.opsForValue().increment(countKey);
//                }else{
//                    log.info("{}禁用访问{}",ip, uri);
//                    // 禁用
//                    redisTemplate.opsForValue().set(lockKey, 1,lockTime, TimeUnit.SECONDS);
//                    // 删除统计
//                    redisTemplate.delete(countKey);
//                    throw new CommonException(ResultCode.ACCESS_FREQUENT);
//                }
//            }
//        }else{
//            // 此用户访问此接口已被禁用
//            throw new CommonException(ResultCode.ACCESS_FREQUENT);
//        }
//        return true;


//        自定义注解 + 反射 实现
        // 判断访问的是否是接口方法
        if(handler instanceof HandlerMethod){
            // 访问的是接口方法，转化为待访问的目标方法对象
            HandlerMethod targetMethod = (HandlerMethod) handler;
            // 取出目标方法中的 AccessLimit 注解
            AccessLimit accessLimit = targetMethod.getMethodAnnotation(AccessLimit.class);
            // 判断此方法接口是否要进行防刷处理（方法上没有对应注解就代表不需要，不需要的话进行放行）
            if(!Objects.isNull(accessLimit)){
                // 需要进行防刷处理，接下来是处理逻辑
                String ip = request.getRemoteAddr();
                String uri = request.getRequestURI();
                String lockKey = LOCK_PREFIX + ip + uri;
                Object isLock = redisTemplate.opsForValue().get(lockKey);
                // 判断此ip用户访问此接口是否已经被禁用
                if (Objects.isNull(isLock)) {
                    // 还未被禁用
                    String countKey = COUNT_PREFIX + ip + uri;
                    Object count = redisTemplate.opsForValue().get(countKey);
                    long second = accessLimit.second();
                    long maxTime = accessLimit.maxTime();

                    if (Objects.isNull(count)) {
                        // 首次访问
                        log.info("首次访问");
                        redisTemplate.opsForValue().set(countKey, 1, second, TimeUnit.SECONDS);
                    } else {
                        // 此用户前一点时间就访问过该接口，且频率没超过设置
                        if ((Integer) count < maxTime) {
                            redisTemplate.opsForValue().increment(countKey);
                        } else {

                            log.info("{}禁用访问{}", ip, uri);
                            long forbiddenTime = accessLimit.forbiddenTime();
                            // 禁用
                            redisTemplate.opsForValue().set(lockKey, 1, forbiddenTime, TimeUnit.SECONDS);
                            // 删除统计--已经禁用了就没必要存在了
                            redisTemplate.delete(countKey);
                            throw new CommonException(ResultCode.ACCESS_FREQUENT);
                        }
                    }
                } else {
                    // 此用户访问此接口已被禁用
                    throw new CommonException(ResultCode.ACCESS_FREQUENT);
                }
            }
        }
        return  true;
    }
}
