package main;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class ClientListener extends Thread {
    public DatagramSocket clientSocket;
    public byte[] buffer = new byte[1024];
    public static int msgCount = 0;
    public static String entireMsg = new String("");
    public static String[] msgArr = new String[2];
    public static String prevMessage;

    DatagramPacket packetToReceive = new DatagramPacket(buffer, buffer.length);

    public ClientListener(DatagramSocket clientSocket, byte[] buffer) {
        this.clientSocket = clientSocket;
        this.buffer = buffer;
    }

    public void run() {
        while (true) {
            try {
                clientSocket.receive(packetToReceive);
                String receivedMsg = new String(packetToReceive.getData());

                msgArr = receivedMsg.split("!!!");
                msgCount = Integer.valueOf(msgArr[0]);
                entireMsg += (msgArr[1]);

                if (msgCount > 1) {
                    for (int i = 1; i < msgCount; i++) {
                        clientSocket.receive(packetToReceive);
                        entireMsg += (new String(packetToReceive.getData()));
                    }
                }
                String result = entireMsg;
                String decryptedResult = ClientUtils.cipherToText(ClientUtils.magicWithCipher(result.trim()));

                prevMessage = decryptedResult;
                ClientUtils.processMessage(decryptedResult, clientSocket, packetToReceive);
                entireMsg = new String("");


                Arrays.fill(buffer, (byte) 0);


            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }


        }

    }
}
