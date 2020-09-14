package loadBalancer;

public interface LoadBalance {
    String getServer(String clientIp);
}
