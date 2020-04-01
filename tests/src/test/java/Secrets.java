public class Secrets {
    public static String getConnectKey() {
        return System.getProperty("connect_key");
    }

    public static String getApiKey() {
        return System.getProperty("api_key");
    }
}
