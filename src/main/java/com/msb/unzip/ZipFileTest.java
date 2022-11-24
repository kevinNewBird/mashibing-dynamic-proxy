package com.msb.unzip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * description  ZipFileTest <BR>
 * <p>
 * author: zhao.song
 * date: created in 16:03  2022/10/8
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class ZipFileTest {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        unzip("F:\\mapper_test\\mapper2.zip");
        System.out.printf("spend time:%dms", (System.currentTimeMillis() - start));
    }

    @SuppressWarnings("all")
    private static void unzip0(String path) throws IOException {
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(path);
            Enumeration<? extends ZipEntry> zipList = zipFile.entries();
            String name = zipFile.getName();
            String prefixPath = name.substring(0, name.indexOf("."));
            File pre_f_d = new File(prefixPath);
            if (!pre_f_d.exists()) {
                pre_f_d.mkdirs();
            }
            while (zipList.hasMoreElements()) {
                ZipEntry ze = null;
                try {
                    ze = zipList.nextElement();
                    String subName = ze.getName();

                    if (ze.isDirectory()) {
                        String directory = prefixPath + File.separator + subName.substring(0, subName.indexOf("."));
                        File f_d = new File(directory);
                        if (!f_d.exists()) {
                            f_d.mkdirs();
                        }
                    } else {
                        String filePath = prefixPath + File.separator + subName;
                        copyNewFile(zipFile, ze, filePath);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (Objects.nonNull(zipFile)) {
                zipFile.close();
            }
        }

    }


    @SuppressWarnings("all")
    private static void unzip(String path) throws IOException {
        File srcFile = new File(path);
        if (!srcFile.exists()) {
            return;
        }
        try (ZipFile zipFile = new ZipFile(srcFile)) {
            String srcZipName = zipFile.getName();
            String prefixPath = srcZipName.substring(0, srcZipName.indexOf("."));

            Enumeration<? extends ZipEntry> zipList = zipFile.entries();
            File pre_f_d = new File(prefixPath);
            if (!pre_f_d.exists()) {
                pre_f_d.mkdirs();
            }
            while (zipList.hasMoreElements()) {
                ZipEntry ze = zipList.nextElement();
                String subName = ze.getName();

                if (ze.isDirectory()) {
                    String directory = prefixPath + File.separator + subName;
                    File f_d = new File(directory);
                    if (!f_d.exists()) {
                        f_d.mkdirs();
                    }
                } else if (ze.getName().toLowerCase().endsWith(".xml")) {
                    String xmlFilePath = prefixPath + File.separator + subName;
                    copyNewFile(zipFile, ze, xmlFilePath);
                } else if (ze.getName().toLowerCase().endsWith(".zip")) {
                    String zipFilePath = prefixPath + File.separator + subName;
                    copyNewFile(zipFile, ze, zipFilePath);
                    unzip(zipFilePath);
                }
            }
        }

    }

    @SuppressWarnings("all")
    private static void copyNewFile(ZipFile srcZipFile, ZipEntry srcZe, String path) throws IOException {
        File f_d = new File(path);
        if (!f_d.exists()) {
            f_d.createNewFile();
            try (InputStream fis = srcZipFile.getInputStream(srcZe);
                 FileOutputStream fos = new FileOutputStream(f_d);) {
//                IOUtils.copy(fis, fos);
                byte[] b_p = new byte[1024];
                int length = -1;
                while ((length = fis.read(b_p)) > 0) {
                    fos.write(b_p, 0, length);
                }
                fos.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
