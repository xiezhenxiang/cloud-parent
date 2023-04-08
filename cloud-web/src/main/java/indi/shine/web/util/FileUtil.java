package indi.shine.web.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author xiezhenxiang 2019/12/20
 */
public class FileUtil {

    /**
     * 把文件压缩成zip或rar，调用后要关闭流
     * @author xiezhenxiang 2019/12/20
     * @param sourceFile sourceFile
     * @param zos zipOutPutStream
     * @param rootDir 自定义zip内的根目录，可以不传
     **/
    public static void compressToZip(File sourceFile, ZipOutputStream zos, String rootDir) {

        byte[] buf = new byte[1024 * 1024];
        String entryName = sourceFile.getName();
        if (StringUtil.hasText(rootDir)) {
            entryName = rootDir + "/" + entryName;
        }
        try {
            if (sourceFile.isFile()) {
                zos.putNextEntry(new ZipEntry(entryName));
                int len;
                FileInputStream in = new FileInputStream(sourceFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            } else {
                File[] listFiles = sourceFile.listFiles();
                if (listFiles == null || listFiles.length == 0) {
                    zos.putNextEntry(new ZipEntry(entryName + "/"));
                    zos.closeEntry();
                } else {
                    for (File file : listFiles) {
                        compressToZip(file, zos, entryName);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解压文件
     * @author xiezhenxiang 2019/12/21
     **/
    public static void decompressZip(String inputFilePath, Charset charset, String outputDir) {

        try {
            ZipFile zipFile = new ZipFile(inputFilePath, charset);
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if (entry.isDirectory()) {
                    String dirPath = outputDir + "/" + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                } else {
                    File targetFile = new File(outputDir + "/" + entry.getName());
                    if(!targetFile.getParentFile().exists()){
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[1024 * 1024];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.close();
                    is.close();
                }
            }
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取文件编码格式
     */
    public static String getFileCharset(InputStream in) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(in);
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                return charset;
            } else if (first3Bytes[0] == (byte) 0xFF
                    && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0) {
                        break;
                    }
                    if (0x80 <= read && read <= 0xBF) {
                        break;
                    }
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 > read || read > 0xBF) {
                            break;
                        }
                    } else if (0xE0 <= read) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
            bis.close();
        } catch (Exception e) {
            // TODO
        }

        return charset;
    }
}
