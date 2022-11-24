package com.msb.windows;

import io.cloudsoft.winrm4j.client.WinRmClientContext;
import io.cloudsoft.winrm4j.winrm.WinRmTool;
import io.cloudsoft.winrm4j.winrm.WinRmToolResponse;
import org.apache.http.client.config.AuthSchemes;

/**
 * description  WinRMHelper <BR>
 * <p>
 * author: zhao.song
 * date: created in 15:30  2022/10/24
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class WinRMHelper {
    private String ip;

    private String username;

    private String password;

    public static final int DEFAULT_PORT = 5985;

    public WinRMHelper(final String ip, final String username, final String password) {
        this.ip = ip;
        this.username = username;
        this.password = password;
    }

    public String execute(final String command) {
        WinRmClientContext context = WinRmClientContext.newInstance();
        WinRmTool tool = WinRmTool.Builder.builder(ip, username, password)
                .authenticationScheme(AuthSchemes.NTLM)
                .port(DEFAULT_PORT)
                .useHttps(false)
                .context(context)
                .build();
        tool.setOperationTimeout(5000L);
        WinRmToolResponse resp = tool.executeCommand(command);
        context.shutdown();
        return resp.getStdOut();
    }

    public static void main(String[] args) {
        WinRMHelper exec = new WinRMHelper("127.0.0.1", "username", "password");
        String resp = exec.execute("hostname");
        System.out.println(resp);
    }
}
