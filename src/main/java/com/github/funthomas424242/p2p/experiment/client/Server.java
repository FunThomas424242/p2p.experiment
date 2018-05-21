package com.github.funthomas424242.p2p.experiment.client;

/*-
 * #%L
 * p2p.experiment
 * %%
 * Copyright (C) 2018 PIUG
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import org.p2psockets.P2PNetwork;
import org.p2psockets.P2PServerSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    protected String targetHost;

    protected int targetPort;

    public Server(final String targetHost, final int targetPort) {
        this.targetHost = targetHost;
        this.targetPort = targetPort;
    }

    public Server() {
        this("localhost", 8080);
    }

    public static void main(final String[] args) {
        final Server server = new Server();
        server.run();
    }

    @Override
    public void run() {
        try {
            // sign into the peer-to-peer network,
            // using the username "serverpeer", the password "serverpeerpassword",
            // and create/find a scoped peer-to-peer network named "TestNetwork"
            System.out.println("Signing into the P2P network...");
            P2PNetwork.signin("serverpeer", "serverpeerpassword", "TestNetwork");

            // start a server socket for the domain
            // "www.nike.laborpolicy" on port 100
            System.out.println("Creating server socket for " + "www.nike.laborpolicy:100...");
            ServerSocket server = new P2PServerSocket("www.nike.laborpolicy", 100);

            // wait for a client
            System.out.println("Waiting for client...");
            Socket client = server.accept();
            System.out.println("Client Accepted.");

            // now communicate with this client
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF("Hello client world!");
            String results = in.readUTF();
            System.out.println("Message from client: " + results);

            // shut everything down!
            client.close();
            server.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


}



