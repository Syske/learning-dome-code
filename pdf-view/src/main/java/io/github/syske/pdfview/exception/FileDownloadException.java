package io.github.syske.pdfview.exception;

/**
 * @program: pdf-view
 * @description: 文件下载异常
 * @author: syske
 * @create: 2020-06-30 14:57
 */
public class FileDownloadException extends RuntimeException {
    public FileDownloadException(String msg) {
        super(msg);
    }
}
