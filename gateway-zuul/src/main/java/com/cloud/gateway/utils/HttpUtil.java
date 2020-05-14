package com.cloud.gateway.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;


@Slf4j
@Component
public class HttpUtil
{


   /* private String sRemoteAddr;
    private int iRemotePort = 137;
    private byte[] buffer = new byte[1024];
    private DatagramSocket ds = null;
*/

    protected static  DatagramPacket send(final byte[] bytes, String ip, DatagramSocket datagramSocket) throws IOException {
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(ip), 137);
        datagramSocket.send(dp);
        return dp;
    }

    protected static  DatagramPacket receive(DatagramSocket datagramSocket) throws Exception {
        DatagramPacket dp = new DatagramPacket(new byte[1024], new byte[1024].length);
        datagramSocket.receive(dp);
        return dp;
    }

    // 询问包结构:
    // Transaction ID 两字节（16位） 0x00 0x00
    // Flags 两字节（16位） 0x00 0x10
    // Questions 两字节（16位） 0x00 0x01
    // AnswerRRs 两字节（16位） 0x00 0x00
    // AuthorityRRs 两字节（16位） 0x00 0x00
    // AdditionalRRs 两字节（16位） 0x00 0x00
    // Name:array [1..34] 0x20 0x43 0x4B 0x41(30个) 0x00 ;
    // Type:NBSTAT 两字节 0x00 0x21
    // Class:INET 两字节（16位）0x00 0x01
    protected static byte[] getQueryCmd() throws Exception {
        byte[] t_ns = new byte[50];
        t_ns[0] = 0x00;
        t_ns[1] = 0x00;
        t_ns[2] = 0x00;
        t_ns[3] = 0x10;
        t_ns[4] = 0x00;
        t_ns[5] = 0x01;
        t_ns[6] = 0x00;
        t_ns[7] = 0x00;
        t_ns[8] = 0x00;
        t_ns[9] = 0x00;
        t_ns[10] = 0x00;
        t_ns[11] = 0x00;
        t_ns[12] = 0x20;
        t_ns[13] = 0x43;
        t_ns[14] = 0x4B;
        for (int i = 15; i < 45; i++) {
            t_ns[i] = 0x41;
        }
        t_ns[45] = 0x00;
        t_ns[46] = 0x00;
        t_ns[47] = 0x21;
        t_ns[48] = 0x00;
        t_ns[49] = 0x01;
        return t_ns;
    }

    // 表1 “UDP－NetBIOS－NS”应答包的结构及主要字段一览表
    // 序号 字段名 长度
    // 1 Transaction ID 两字节（16位）
    // 2 Flags 两字节（16位）
    // 3 Questions 两字节（16位）
    // 4 AnswerRRs 两字节（16位）
    // 5 AuthorityRRs 两字节（16位）
    // 6 AdditionalRRs 两字节（16位）
    // 7 Name<Workstation/Redirector> 34字节（272位）
    // 8 Type:NBSTAT 两字节（16位）
    // 9 Class:INET 两字节（16位）
    // 10 Time To Live 四字节（32位）
    // 11 Length 两字节（16位）
    // 12 Number of name 一个字节（8位）
    // NetBIOS Name Info 18×Number Of Name字节
    // Unit ID 6字节（48位
    protected static String getMacAddr(byte[] brevdata) throws Exception {
        // 获取计算机名
        int i = brevdata[56] * 18 + 56;
        String sAddr = "";
        StringBuffer sb = new StringBuffer(17);
        // 先从第56字节位置，读出Number Of Names（NetBIOS名字的个数，其中每个NetBIOS Names
        // Info部分占18个字节）
        // 然后可计算出“Unit ID”字段的位置＝56＋Number Of
        // Names×18，最后从该位置起连续读取6个字节，就是目的主机的MAC地址。
        for (int j = 1; j < 7; j++) {
            sAddr = Integer.toHexString(0xFF & brevdata[i + j]);
            if (sAddr.length() < 2) {
                sb.append(0);
            }
            sb.append(sAddr.toUpperCase());
            if (j < 6) {
                sb.append('-');
            }
        }
        return sb.toString();
    }


    public static  String getRemoteMacAddr(String ip) {
        /**
         * Java使用DatagramSocket代表UDP协议的Socket，
         * DatagramSocket本身只是码头，不维护状态，不能产生IO流，
         * 它的唯一作用就是接收和发送数据报，
         * Java使用DatagramPacket来代表数据报，
         * DatagramSocket接收和发送的数据都是通过DatagramPacket对象完成的
         */
        DatagramSocket datagramSocket = null;
        DatagramPacket datagramPacket = null;
        String smac = null;
        try {
            datagramSocket = new DatagramSocket();

            byte[] bqcmd = getQueryCmd();
            send(bqcmd, ip, datagramSocket);
            try {
                datagramPacket = receive(datagramSocket);
            } catch (Exception e) {
                e.printStackTrace();
            }
            smac = getMacAddr(datagramPacket.getData());

        } catch (SocketException e) {

            e.printStackTrace();
            log.error(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            return smac;

        }


    }

    public static String getLocalMacAddr() {
        String macAddress = null;
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            byte[] mac = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || netInterface.isPointToPoint() || !netInterface.isUp()) {
                    continue;
                } else {
                    mac = netInterface.getHardwareAddress();
                    if (mac != null) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < mac.length; i++) {
                            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                        }
                        if (sb.length() > 0) {
                            return sb.toString();
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("MAC地址获取失败", e);
        }
        return "";
    }

    public static String getMacAddr(String ip){
        String mac=null;
        if  ("127.0.0.1".equals(ip)||"0:0:0:0:0:0:0:1".equals(ip)){

            mac = getLocalMacAddr();
        }else {
            mac=getRemoteMacAddr(ip);
        }
        return mac;

    }

    public static void main(String[] args) throws Exception {
        String mac = getRemoteMacAddr("192.168.13.");
        System.out.println(mac);

    }

    private static final String UNKNOWN = "unknown"; //
    /**
     * 获取请求的真实ip
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}