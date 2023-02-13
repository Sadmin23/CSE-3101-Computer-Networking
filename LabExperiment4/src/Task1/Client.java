package Task1;

import java.io.*;
import java.net.*;
import java.nio.*;
public class Client {

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(1234);
        InetAddress address = InetAddress.getByName("localhost");

        //Sending Domain to Auth DNS Server

        byte[] sendData;

        String domain = "www.cse.du.ac.bd";
        System.out.println("Sending: "+domain);
        byte[] messageBytes = domain.getBytes();
        int messageLength = messageBytes.length;

        ByteBuffer buffer = ByteBuffer.allocate(12 + messageLength);
        buffer.putShort((short) 1);
        buffer.put((byte) 2);
        buffer.put((byte) 2);
        buffer.putInt(messageLength);
        buffer.put(messageBytes);

        sendData = buffer.array();

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, 9876);
        socket.send(sendPacket);

        //Receiving IP from the Auth DNS Server

        byte[] receiveData = new byte[1024];

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);

        ByteBuffer receivedBuffer = ByteBuffer.wrap(receiveData);
        int messageLength2 = receivedBuffer.getInt();
        byte[] messageBytes2 = new byte[messageLength2];
        receivedBuffer.get(messageBytes2, 0, Math.min(messageLength2, receivedBuffer.remaining()));
        String IP = new String(messageBytes2);

        System.out.println("Recieved: " + IP);

        socket.close();
    }

}
