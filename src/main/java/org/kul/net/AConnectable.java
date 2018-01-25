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

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


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
      oOutStream   = new ObjectOutputStream(sock.getOutputStream());  

      System.out.println("Connected");

      oOutStream.writeObject(getExecutable());

      oOutStream.flush();

    }
    catch(NumberFormatException e) { 
      System.out.println("Try an actual port"); 
      e.printStackTrace();
    }
    catch (UnknownHostException e)   { e.printStackTrace(); } 
    catch (IOException e)       { e.printStackTrace(); }
    
    System.out.println("Connection closed");
  }

  public abstract IExecutable getExecutable();

  protected final NetworkParameters getParams(){ return params; }
}
