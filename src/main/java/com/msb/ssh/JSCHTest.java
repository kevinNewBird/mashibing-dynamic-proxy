package com.msb.ssh;

import com.jcraft.jsch.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * description  JSCHTest <BR>
 * <p>
 * author: zhao.song
 * date: created in 10:28  2022/10/8
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class JSCHTest {

    private static List<String> filePathList = new ArrayList<>();


    /**
     * TIP: 引入依赖jsch.jar
     *
     * @param args
     */
    public static void main(String[] args) {
        String username = "root";
        String password = "trsadmin@123";
        String host = "101.132.151.4";
        int port = 22;

        // 创建JSch对象
        JSch jSch = new JSch();
        Session jSchSession = null;
        Channel channel = null;

        boolean reulst = false;

        try {

            // 根据主机账号、ip、端口获取一个Session对象
            jSchSession = jSch.getSession(username, host, port);

            // 存放主机密码
            jSchSession.setPassword(password);

            Properties config = new Properties();

            // 去掉首次连接确认
            config.put("StrictHostKeyChecking", "no");

            jSchSession.setConfig(config);

            // 超时连接时间为3秒
            jSchSession.setTimeout(3000);

            // 进行连接
            jSchSession.connect();

            // 获取连接结果
            reulst = jSchSession.isConnected();

            channel = jSchSession.openChannel("sftp");
            channel.connect(3000);
            ChannelSftp sftp = (ChannelSftp) channel;

            sftp.setFilenameEncoding("UTF-8");

//            Vector fileList = sftp.ls("/software/var");
//            Vector<ChannelSftp.LsEntry> fileList = new Vector<>();
            listFiles(sftp, "/software/var",new AllFileLsEntrySelector());
            System.out.println(filePathList);

        } catch (JSchException | SftpException e) {
            System.out.println(e.getMessage());
        } finally {
            // 关闭jschSesson流
            if (jSchSession != null && jSchSession.isConnected()) {
                jSchSession.disconnect();
                channel.disconnect();
            }
        }

        if (reulst) {
            System.err.println("【SSH连接】连接成功");
        } else {
            System.err.println("【SSH连接】连接失败");
        }
    }

    private static void listFiles(ChannelSftp sftp, String path, AllFileLsEntrySelector selector) throws SftpException {
        sftp.ls(path, selector);
        selector.fileList.forEach(lsEntry
                -> filePathList.add(path + "/" + lsEntry.getFilename()));
        if (selector.dirList.isEmpty()) {
            return;
        }else {
            for (ChannelSftp.LsEntry lsEntry : selector.dirList) {
                String subPath = path + "/" + lsEntry.getFilename();
                listFiles(sftp, subPath, new AllFileLsEntrySelector());
            }
        }

    }


    public static class AllFileLsEntrySelector implements ChannelSftp.LsEntrySelector {

        private Vector<ChannelSftp.LsEntry> fileList = new Vector<>();

        private Vector<ChannelSftp.LsEntry> dirList = new Vector<>();


        public Vector<ChannelSftp.LsEntry> getFileList() {
            return fileList;
        }

        public Vector<ChannelSftp.LsEntry> getDirList() {
            return dirList;
        }

        @Override
        public int select(ChannelSftp.LsEntry entry) {
            SftpATTRS attrs = entry.getAttrs();
            if (!attrs.isDir() && entry.getFilename().endsWith(".out")) {
                fileList.addElement(entry);
            }
            if (attrs.isDir() && !".".equals(entry.getFilename())
                    && !"..".equals(entry.getFilename())) {
                dirList.add(entry);
            }
            return CONTINUE;
        }
    }

}
