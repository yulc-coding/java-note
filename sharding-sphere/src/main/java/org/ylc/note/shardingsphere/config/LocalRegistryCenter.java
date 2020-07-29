// package org.ylc.note.shardingsphere.config;
//
// import org.apache.shardingsphere.orchestration.reg.api.RegistryCenter;
// import org.apache.shardingsphere.orchestration.reg.api.RegistryCenterConfiguration;
// import org.apache.shardingsphere.orchestration.reg.listener.DataChangedEventListener;
//
// import java.util.List;
// import java.util.Map;
// import java.util.Properties;
// import java.util.concurrent.ConcurrentHashMap;
//
// /**
//  * 代码千万行，注释第一行，
//  * 注释不规范，同事泪两行。
//  * <p>
//  * 本地注册中心（作废）
//  *
//  * @author YuLc
//  * @version 1.0.0
//  * @date 2020/7/26
//  */
// @Deprecated
// public class LocalRegistryCenter implements RegistryCenter {
//
//     public static Map<String, DataChangedEventListener> LISTENERS = new ConcurrentHashMap<>();
//
//     private RegistryCenterConfiguration config;
//
//     private Properties properties;
//     /**
//      * public 是为了在重置节点的时候减少去重新读配置
//      */
//     public static Map<String, String> VALUES = new ConcurrentHashMap<>();
//
//
//     @Override
//     public void init(RegistryCenterConfiguration config) {
//         this.config = config;
//     }
//
//     @Override
//     public String get(String key) {
//         return VALUES.get(key);
//     }
//
//     @Override
//     public String getDirectly(String key) {
//         return VALUES.get(key);
//     }
//
//     @Override
//     public boolean isExisted(String key) {
//         return VALUES.containsKey(key);
//     }
//
//     @Override
//     public List<String> getChildrenKeys(String key) {
//         return null;
//     }
//
//     @Override
//     public void persist(String key, String value) {
//         VALUES.put(key, value);
//     }
//
//     @Override
//     public void update(String key, String value) {
//         VALUES.put(key, value);
//     }
//
//     @Override
//     public void persistEphemeral(String key, String value) {
//         VALUES.put(key, value);
//     }
//
//     @Override
//     public void watch(String key, DataChangedEventListener dataChangedEventListener) {
//         if (dataChangedEventListener != null) {
//             // 将数据改变的事件监听器缓存下来
//             LISTENERS.put(key, dataChangedEventListener);
//         }
//     }
//
//     @Override
//     public void close() {
//         this.config = null;
//     }
//
//     @Override
//     public void initLock(String key) {
//
//     }
//
//     @Override
//     public boolean tryLock() {
//         return false;
//     }
//
//     @Override
//     public void tryRelease() {
//
//     }
//
//     @Override
//     public String getType() {
//         return "shardingLocalRegisterCenter";
//     }
//
//     @Override
//     public Properties getProperties() {
//         return properties;
//     }
//
//     @Override
//     public void setProperties(Properties properties) {
//         this.properties = properties;
//     }
// }
