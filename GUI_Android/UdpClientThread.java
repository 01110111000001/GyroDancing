package com.example.pierr.testudp;

import android.os.Message;
import android.os.SystemClock;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import android.widget.Toast;


public class UdpClientThread extends Thread{

    String dstAddress;
    byte donn[]=new byte[3];
    String acc;
    int dstPort;
    private boolean running;
    MainActivity.UdpClientHandler handler;
    DatagramSocket socket;
    InetAddress address;

    public UdpClientThread(String addr, int port, MainActivity.UdpClientHandler handler, int puissance, int direction) {
        super();
        dstAddress = addr;
        dstPort = port;
        this.handler = handler;
        acc = String.valueOf(puissance) + " " + String.valueOf(direction);
        // ici je mets les données dans un byte, le premier, tu laisses 118.
        donn[0]=118;
        donn[1]=(byte) (puissance+100); // de base ma puissance est entre -100 et 100, mais on la veut entre 0 et 200. <100, recul, >100 avance
        donn[2]=(byte) (direction+100); // <100 droite, >100 gauche
    }

    public void setRunning(boolean running){
        this.running = running;
    }

    private void sendState(String state){
        handler.sendMessage(
                Message.obtain(handler,
                        MainActivity.UdpClientHandler.UPDATE_STATE, state));
    }

    @Override
    public void run() {
        //sendState("connecting...");
        running = true;

        try {
            SystemClock.sleep(10);
            // ici j'envoie toutes les données
            socket = new DatagramSocket();
            address = InetAddress.getByName(dstAddress);
            socket.connect(address, dstPort);
            if (socket.isConnected()) {
                sendState(acc);
            }
            // send request
            byte[] buf;
            buf=donn;
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, dstPort);
            socket.send(packet);
            /*buf=new byte[256];
            // get response
            packet = new DatagramPacket(buf, buf.length);
            sendState(String.valueOf(i));
            do {
                socket.receive(packet);
            } while(packet.getLength() == 0);
            String line = new String(packet.getData(), 0, packet.getLength());
            handler.sendMessage(
                Message.obtain(handler, MainActivity.UdpClientHandler.UPDATE_MSG, line));
*/
        } catch (SocketException e) {
            sendState("err 1");
            e.printStackTrace();
        } catch (UnknownHostException e) {
            sendState("err 2");
            e.printStackTrace();
        } catch (IOException e) {
            sendState("err 3");
            e.printStackTrace();
        } finally {
            if(socket != null){
                socket.close();
                handler.sendEmptyMessage(MainActivity.UdpClientHandler.UPDATE_END);
            }
        }
    }
}