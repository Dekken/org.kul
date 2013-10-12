package org.kul.net.conn;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.kul.net.exec.IExecutable;
import org.kul.net.param.NetworkParameterHandler;
import org.kul.net.param.NetworkParameters;


public abstract class AConnectable{

	private NetworkParameters params;

	protected ObjectOutputStream oOutStream;

	protected Socket sock;

	public AConnectable() throws IOException{
		this(new String[0]);		
	}	

	public AConnectable(String[] args) throws IOException{

		params = NetworkParameterHandler.INSTANCE().handle(args);

		params.setHost(params.getHost() == "" ? "localhost" : params.getHost());		
	}

	public void connect(){
		System.out.println("Attempting connection to " + params.getHost() + "\nOn port " + params.getPort());

		try{			

			sock = new Socket(params.getHost(), Integer.parseInt(params.getPort()));
			oOutStream 	= new ObjectOutputStream(sock.getOutputStream());	

			System.out.println("Connected");

			oOutStream.writeObject(getExecutable());

			oOutStream.flush();

		}
		catch(NumberFormatException e) { 
			System.out.println("Try an actual port"); 
			e.printStackTrace();
		}
		catch (UnknownHostException e) 	{ e.printStackTrace(); } 
		catch (IOException e) 			{ e.printStackTrace(); }
		
		System.out.println("Connection closed");
	}

	public abstract IExecutable getExecutable();

	protected final NetworkParameters getParams(){ return params; }
}
