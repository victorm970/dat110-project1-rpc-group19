package no.hvl.dat110.messaging;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import no.hvl.dat110.TODO;


public class MessageConnection {

	private DataOutputStream outStream; // for writing bytes to the underlying TCP connection
	private DataInputStream inStream; // for reading bytes from the underlying TCP connection
	private Socket socket; // socket for the underlying TCP connection
	
	public MessageConnection(Socket socket) {

		try {

			this.socket = socket;

			outStream = new DataOutputStream(socket.getOutputStream());

			inStream = new DataInputStream (socket.getInputStream());

		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void send(Message message){

		// TODO - START
		// encapsulate the data contained in the Message and write to the output stream

		try {
			outStream.write(MessageUtils.encapsulate(message));
		} catch (IOException e) {
			e.printStackTrace();
		}


		//if (true)
			//throw new UnsupportedOperationException(TODO.method());
			
		// TODO - END

	}

	public Message receive() {

		Message message = null;
		byte[] segment = null;
		
		// TODO - START
		// read a segment from the input stream and decapsulate data into a Message

		segment = new byte[MessageUtils.SEGMENTSIZE];
		try {
			//data = inStream.readAllBytes();
			for(int i= 0; i < segment.length; i++){
				segment[i] = inStream.readByte();
			}
			// int read = inStream.read(segment,0, MessageUtils.SEGMENTSIZE);

		} catch (IOException e) {
			e.printStackTrace();
		}

		message = MessageUtils.decapsulate(segment);
		
		//if (true)
			//throw new UnsupportedOperationException(TODO.method());
		
		// TODO - END
		
		return message;
		
	}

	// close the connection by closing streams and the underlying socket	
	public void close() {

		try {
			
			outStream.close();
			inStream.close();

			socket.close();
			
		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}