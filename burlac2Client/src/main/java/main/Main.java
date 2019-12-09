package main;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int clientPort = 20000;


        try {

            Scanner scanner = new Scanner(System.in);
            DatagramSocket clientSocket = new DatagramSocket();

            byte[] buffer = new byte[1024];
            boolean clientIsActive = true;
            ClientListener clientListener = new ClientListener(clientSocket, buffer);
            clientListener.start();

            while(clientIsActive) {

                System.out.print("> ");
                String data = scanner.nextLine();
                if("!exit".equals(data)){
                    break;
                }

                ClientUtils.sendMessage(clientSocket, data, clientPort);

//                DatagramPacket packet = new DatagramPacket(data.getBytes(),data.getBytes().length, InetAddress.getLocalHost(),clientPort);
//                clientSocket.send(packet);
//                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
//
//                clientSocket.receive(receivePacket);
//                System.out.println("Echo : "+ new String(receivePacket.getData()));

//                Arrays.fill(buffer, (byte)0);

            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }
}
