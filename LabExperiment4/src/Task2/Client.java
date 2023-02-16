package Task2;

import java.io.*;
import java.net.*;
import java.nio.*;

public class Client {

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(1234);
        InetAddress address = InetAddress.getByName("localhost");

        short identification;
        short flags;
        short numQuestions;
        short numAnswerRRs;
        short numAuthorityRRs;
        short numAdditionalRRs;

        String Name;
        String Value;
        String Type;
        String TTL;

        String message;

        // Sending Domain to Local DNS Server

        byte[] sendData;

        Name = "www.cse.du.ac.bd";
        Value = "ns1.cse.du.ac.bd.";
        Type = "NS";
        TTL = "86400";

        message = Name + "##" + Value + "##" + Type + "##" + TTL;

        System.out.println("Sending: " + message);
        byte[] messageBytes = message.getBytes();
        int messageLength = messageBytes.length;

        identification = 1;
        flags = 1;
        numQuestions = 1;
        numAnswerRRs = 1;
        numAuthorityRRs = 1;
        numAdditionalRRs = 1;

        ByteBuffer buffer = ByteBuffer.allocate(24 + messageLength);
        buffer.putShort(identification);
        buffer.putShort(flags);
        buffer.putShort(numQuestions);
        buffer.putShort(numAnswerRRs);
        buffer.putShort(numAuthorityRRs);
        buffer.putShort(numAdditionalRRs);
        buffer.putInt(messageLength);
        buffer.put(messageBytes);

        sendData = buffer.array();

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, 5000);
        socket.send(sendPacket);

        // Receiving IP from the Local DNS Server

        byte[] receiveData = new byte[1024];

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);

        ByteBuffer receivedBuffer = ByteBuffer.wrap(receiveData);

        identification = receivedBuffer.getShort();
        flags = receivedBuffer.getShort();
        numQuestions = receivedBuffer.getShort();
        numAnswerRRs = receivedBuffer.getShort();
        numAuthorityRRs = receivedBuffer.getShort();
        numAdditionalRRs = receivedBuffer.getShort();

        messageLength = receivedBuffer.getInt();
        messageBytes = new byte[messageLength];
        receivedBuffer.get(messageBytes, 0, messageLength);
        String IP = new String(messageBytes);
        System.out.println("Received IP: " + IP);

        socket.close();
    }

}
