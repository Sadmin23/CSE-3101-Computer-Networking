package DV_Algorithm;

import java.io.*;
import java.net.*;

public class Router2 {

    private static final int MAX = Integer.MAX_VALUE;

    public static void print3DArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                int x = arr[i][j];

                if (x == MAX)
                    System.out.print("∞ ");
                else
                    System.out.print(x + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        while (true) {
            ServerSocket serverSocket = new ServerSocket(6000);
            Socket socket = serverSocket.accept();

            // Get the input stream
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Receive the 2D array and the integer value
            int[][] array = (int[][]) in.readObject();
            int value = in.readInt();

            if (value == 0) {
                // Close the socket and the input stream
                in.close();
                socket.close();
                break;
            }

        }

    }
}