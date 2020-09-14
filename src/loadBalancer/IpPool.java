package loadBalancer;



import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IpPool {
    public static Map<String, Integer> ipMap = new ConcurrentHashMap<>();

    static {
        ipMap.put("192.168.1.1", 10);
        ipMap.put("192.168.1.2", 10);
        ipMap.put("192.168.1.3", 10);
        ipMap.put("192.168.1.4", 10);
        ipMap.put("192.168.1.5", 10);
        ipMap.put("192.168.1.6", 10);
        ipMap.put("192.168.1.7", 10);
        ipMap.put("192.168.1.8", 10);
        ipMap.put("192.168.1.9", 10);
        ipMap.put("192.168.1.10", 10);
    }
}


