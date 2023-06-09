package com.gbase.admin.common.exception.file;

import com.gbase.admin.common.exception.base.BaseException;

/**
 * File Exception
 *
 * @author xielibo
 */
public class FileException extends BaseException {
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args) {
        super("file", code, args, null);
    }

}
