package com.gbase.admin.common.utils.file;

import cn.hutool.core.io.FileUtil;
import com.gbase.admin.common.config.SystemConfig;
import com.gbase.admin.common.constant.Constants;
import com.gbase.admin.common.exception.file.FileNameLengthLimitExceededException;
import com.gbase.admin.common.exception.file.FileSizeLimitExceededException;
import com.gbase.admin.common.exception.file.InvalidExtensionException;
import com.gbase.admin.common.utils.uuid.IdUtils;
import com.gbase.admin.common.utils.DateUtils;
import com.gbase.admin.common.utils.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * file upload tool
 *
 * @author xielibo
 */
public class FileUploadUtils {
    /**
     * default size 50M
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    /**
     * default filename length 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * default baseDir
     */
    private static String defaultBaseDir = SystemConfig.getStoragePath();

    public static void setDefaultBaseDir(String defaultBaseDir) {
        FileUploadUtils.defaultBaseDir = defaultBaseDir;
    }

    public static String getDefaultBaseDir() {
        return defaultBaseDir;
    }

    /**
     * default upload
     *
     * @param file file
     * @return filename
     * @throws Exception
     */
    public static final String upload(MultipartFile file) throws IOException {
        try {
            return upload(getDefaultBaseDir(), file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * upload
     *
     * @param baseDir file root directory
     * @param file    file
     * @return filename
     * @throws IOException
     */
    public static final String upload(String baseDir, MultipartFile file) throws IOException {
        try {
            return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * upload
     *
     * @param baseDir          file root directory
     * @param file             file
     * @param allowedExtension file extension
     * @return filename
     * @throws FileSizeLimitExceededException       file size limit exception
     * @throws FileNameLengthLimitExceededException file name length limit exception
     * @throws IOException                          Io exception
     * @throws InvalidExtensionException            file invalid exception
     */
    public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
            InvalidExtensionException {
        int fileNamelength = file.getOriginalFilename().length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }

        assertAllowed(file, allowedExtension);

        String fileName = extractFilename(file);

        File desc = getAbsoluteFile(baseDir, fileName);
        file.transferTo(desc);
        String pathFileName = getPathFileName(baseDir, fileName);
        return pathFileName;
    }

    /**
     * upload
     * @param baseDir          file root directory
     * @param fileName         fileName
     * @param allowedExtension file extension
     * @return filename
     * @throws FileSizeLimitExceededException       file size limit exception
     * @throws FileNameLengthLimitExceededException file name length limit exception
     * @throws IOException                          Io exception
     * @throws InvalidExtensionException            file invalid exception
     */
    public static final String upload(String baseDir, String fileName, MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
            InvalidExtensionException {
        int fileNamelength = file.getOriginalFilename().length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }
        assertAllowed(file, allowedExtension);
        File desc = getAbsoluteFile(baseDir, fileName);
        file.transferTo(desc);
        String pathFileName = getPathFileName(baseDir, fileName);
        return pathFileName;
    }

    /**
     * generate filename
     */
    public static final String extractFilename(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        fileName = DateUtils.datePath() + "/" + IdUtils.fastUuid() + "." + extension;
        return fileName;
    }

    public static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
        }
        return desc;
    }

    public static final String getPathFileName(String uploadDir, String fileName) {
        int dirLastIndex = SystemConfig.getStoragePath().length() + 1;
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        String pathFileName = Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
        return pathFileName;
    }

    /**
     * file size valid
     *
     * @param file file
     * @param allowedExtension file extension
     * @return
     * @throws FileNameLengthLimitExceededException file name length limit exception
     * @throws InvalidExtensionException            file invalid exception
     */
    public static final void assertAllowed(MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, InvalidExtensionException {
        long size = file.getSize();
        if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE) {
            throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
        }

        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
            if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION) {
                throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension,
                        fileName);
            } else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION) {
                throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension,
                        fileName);
            } else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION) {
                throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension,
                        fileName);
            } else if (allowedExtension == MimeTypeUtils.VIDEO_EXTENSION) {
                throw new InvalidExtensionException.InvalidVideoExtensionException(allowedExtension, extension,
                        fileName);
            } else {
                throw new InvalidExtensionException(allowedExtension, extension, fileName);
            }
        }

    }

    /**
     * Determine whether the MIME type is an allowed MIME type
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the suffix of the file name
     *
     * @param file file
     * @return suffix
     */
    public static final String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(file.getContentType());
        }
        return extension;
    }

    /**
     * delete file
     *
     * @return
     */
    public static final void deleteByPath(String path) {
        String realPath = path.replace(Constants.RESOURCE_PREFIX + "/", "/");
        String absolutePath = SystemConfig.getStoragePath() + "/" + realPath;
        FileUtil.del(absolutePath);
    }

    /**
     * save the svg file
     * @param svgContent
     */
    public static final String writeMenuSvgIcon(String svgContent) {
        String baseDir = SystemConfig.getIconsPath() + File.separator + DateUtils.datePath();
        File dir = new File(baseDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName =  IdUtils.fastUuid() + ".svg";
        File file = new File(baseDir + File.separator + fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileUtil.writeBytes(svgContent.getBytes(StandardCharsets.UTF_8), file.getPath());
            return Constants.RESOURCE_PREFIX + "/icons" + File.separator + DateUtils.datePath() + File.separator + fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
