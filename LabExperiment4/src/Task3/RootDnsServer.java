package Task3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class RootDnsServer {

    public static void main(String[] args) throws IOException {

        DatagramSocket socket = new DatagramSocket(7000);
        InetAddress address = InetAddress.getByName("localhost");

        //receive message from Local DNS Server

        byte[] receiveData = new byte[1024];

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);

        ByteBuffer receivedBuffer = ByteBuffer.wrap(receiveData);

        int messageLength = receivedBuffer.getInt();
        byte[] messageBytes = new byte[messageLength];
        receivedBuffer.get(messageBytes, 0, Math.min(messageLength, receivedBuffer.remaining()));
        String domain = new String(messageBytes);

        System.out.println("Received from Local DNS: " + domain);

        //send message to Root TLD DNS Server

        String IP="0.0.1.0";

        byte[] sendData;

        System.out.println("Sending to TLD DNS: " + IP);

        byte[] messageBytes2 = IP.getBytes();
        int messageLength2 = messageBytes2.length;

        ByteBuffer buffer = ByteBuffer.allocate(12 + messageLength2);
        buffer.putShort((short) 1);
        buffer.put((byte) 2);
        buffer.put((byte) 2);
        buffer.putInt(messageLength2);
        buffer.put(messageBytes2);

        sendData = buffer.array();

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, 9800);
        socket.send(sendPacket);

        //receive message from TLD DNS Server

        byte[] receiveData2 = new byte[1024];

        DatagramPacket receivePacket2 = new DatagramPacket(receiveData2, receiveData2.length);
        socket.receive(receivePacket2);

        ByteBuffer receivedBuffer2 = ByteBuffer.wrap(receiveData2);

        int messageLength3 = receivedBuffer2.getInt();
        byte[] messageBytes3 = new byte[messageLength3];
        receivedBuffer2.get(messageBytes3, 0, Math.min(messageLength3, receivedBuffer2.remaining()));
        String domain2 = new String(messageBytes3);

        System.out.println("Received from TLD DNS: " + domain2);


        //send message to Local DNS Server

        String IP2="1.1.1.1";

        byte[] sendData2;

        System.out.println("Sending to Root DNS: " + IP2);

        byte[] messageBytes5 = IP2.getBytes();
        int messageLength5 = messageBytes5.length;

        ByteBuffer buffer2 = ByteBuffer.allocate(12 + messageLength5);
        buffer2.putShort((short) 1);
        buffer2.put((byte) 2);
        buffer2.put((byte) 2);
        buffer2.putInt(messageLength5);
        buffer2.put(messageBytes5);

        sendData2 = buffer2.array();

        DatagramPacket sendPacket2 = new DatagramPacket(sendData2, sendData2.length, address, 5000);
        socket.send(sendPacket2);

    }
}