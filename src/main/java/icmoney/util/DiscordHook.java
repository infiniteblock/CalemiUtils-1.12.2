package icmoney.util;

import javax.net.ssl.HttpsURLConnection;

import icmoney.config.ICMConfig;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DiscordHook {

    public void sendCommand(final String command, final String username) {
    	final String AvatarURL = ICMConfig.economy.AvatarURL;
        final String webhookURL = ICMConfig.economy.webhookURL;
        if (!webhookURL.isEmpty()) {
                try {
                    final HttpsURLConnection connection = (HttpsURLConnection) new URL(webhookURL).openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11");
                    connection.setDoOutput(true);
                    try (final OutputStream outputStream = connection.getOutputStream()) {
                        // Handle backslashes.
                        String preparedCommand = command.replaceAll("\\\\", "");
                        if (preparedCommand.endsWith(" *")) preparedCommand = preparedCommand.substring(0, preparedCommand.length() - 2) + "*";
                        outputStream.write(("{\"content\":\"" + preparedCommand + "\",\"username\":\"" + username + "\",\"avatar_url\":\"" + AvatarURL + username + "\"}").getBytes(StandardCharsets.UTF_8));
                    }
                    connection.getInputStream();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }