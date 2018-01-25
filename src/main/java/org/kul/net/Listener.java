/**

Created on: 12 Sept 2014

Copyright (c) 2013, Philip Deegan

This file is part of org.kul.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this library.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.kul.net;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Listener implements Runnable{

  private NetworkParameters params;

  private ServerSocket serverSock;
  private Socket sock;

  private ObjectInputStream oInStream;

  private ConcurrentLinkedQueue<IExecutable> executables   = new ConcurrentLinkedQueue<IExecutable>();

  private Thread connectionHandler            = new Thread(this);

  public Listener() throws IOException{
    this(new String[0]);
  }  

  public Listener(String[] args) throws IOException{

    params = NetworkParameterHandler.INSTANCE().handle(args);

    try{
      Integer.parseInt(params.getPort());
    }
    catch(NumberFormatException e) {
      e.printStackTrace();
      params.setPort("8080");
    }  

    serverSock   = new ServerSocket(Integer.parseInt(params.getPort()));

    Runtime.getRuntime().addShutdownHook(new Thread(){
      public void run(){
        try {
          if(sock != null){
            sock.close();
          }            
        } 
        catch (IOException e) { e.printStackTrace(); }
      }
    });
    
    System.out.println("Server listening on port: " + params.getPort());
    
    connectionHandler.start();
  }

  @Override
  public void run(){
    try {
      sock     = serverSock.accept();
      System.out.println("Connection inbound");
      
      oInStream   = new ObjectInputStream(sock.getInputStream ());

      IExecutable exe;

      while((exe = (IExecutable) oInStream.readObject()) != null){
        synchronized (executables) {
          executables.add(exe);
        }
      }
    }     
    catch (EOFException  e)     { /* EXPECTED */ }
    catch (SocketException e)    { /* EXPECTED */ }
    catch (IOException e)       { e.printStackTrace(); }
    catch (ClassNotFoundException e){ e.printStackTrace(); }
    
    
    System.out.println("Connection closed");
    
    connectionHandler = new Thread(this);
    connectionHandler.start();
  }

  public boolean add(IExecutable connectable){
    return executables.add(connectable);
  }
  public ArrayList<IExecutable> getExecutables(){    
    ArrayList<IExecutable> exes = new ArrayList<IExecutable>(executables);
    synchronized (executables) {
      executables.clear();
    }
    return exes;
  }
  
  public Socket getCurrentSocket(){ return this.sock; }
}
