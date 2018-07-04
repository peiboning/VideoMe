package com.pbn.org.permission;

/**
 * 拥护拒绝过权限后的处理
 */
public interface PermissionRejectHandler {
    boolean showRationaleToUser(String permission);
}
