import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class JumpCloudApi {
    private static final String systemsEndpointUrl = "https://console.jumpcloud.com/api/systems";

    public static boolean systemExistsWithRetry(String hostName, Integer timeout) throws Exception {
        long startTime = System.currentTimeMillis();

        while (getSystemIdByHostName(hostName) == null) {
            System.out.println("System " + hostName + " not found in JumpCloud...");
            long remaining = System.currentTimeMillis() - startTime - timeout;

            if (remaining > 0) {
                return false;
            }

            Thread.sleep(500);
        }

        System.out.println("System " + hostName + " found in JumpCloud!");
        return true;
    }

    public static void removeSystemIfExists(String hostName) throws Exception {
        String id = getSystemIdByHostName(hostName);

        if(id != null) {
            removeSystem(id);
        }
    }

    private static void removeSystem(String id) throws Exception {
        URL url = new URL(systemsEndpointUrl + "/" + id);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try {
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("x-api-key", Secrets.getApiKey());
            connection.setUseCaches(false);
            connection.connect();

            if(connection.getResponseCode() != 200) {
                throw new Exception("The JumpCloud API returned invalid status code " + connection.getResponseCode());
            }
        }
        finally {
            connection.disconnect();
        }
    }

    private static String getSystemIdByHostName(String hostName) throws Exception {
        URL url = new URL(systemsEndpointUrl);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try {
            connection.setRequestProperty("x-api-key", Secrets.getApiKey());
            connection.setUseCaches(false);
            connection.connect();

            if(connection.getResponseCode() != 200) {
                throw new Exception("The JumpCloud API returned invalid status code " + connection.getResponseCode());
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String json = reader.lines().collect(Collectors.joining());
                JSONObject obj = new JSONObject(json);

                JSONArray systems = obj.getJSONArray("results");

                for (int i = 0; i < systems.length(); i++) {
                    JSONObject system = systems.getJSONObject(i);

                    if(system.has("hostname") && hostName.equals(system.getString("hostname"))) {
                        return system.getString("id");
                    }
                }
            }
        }
        finally {
            connection.disconnect();
        }

        return null;
    }
}