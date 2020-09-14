package loadBalancer;

public class LoadBalanceMain {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        loadBalance();
    }

    public static void loadBalance() {
 
        doGetServer(new RandomLoadBalance());
    
    }


    public static void doGetServer(LoadBalance loadBalance) {
        doGetServer(loadBalance, 100);
    }

    private static void doGetServer(LoadBalance loadBalance, int queryTimes) {
        for (int i = 0; i < queryTimes; i++) {
            String serverId = loadBalance.getServer(String.valueOf(i));
            System.out.println(String.format("[%s] index:%s,%s", loadBalance.getClass().getSimpleName(), i, serverId));
        }
    }
}