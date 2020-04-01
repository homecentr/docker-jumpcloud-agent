public class Secrets {
    public static String getConnectKey() {
        System.out.println("CONNECT KEY=" + System.getProperty("connect_key"));

        return System.getProperty("connect_key");
    }

    public static String getApiKey() {
        return System.getProperty("api_key");
    }
}
