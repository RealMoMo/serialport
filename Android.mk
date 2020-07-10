LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

# commond apk start####################################################
include $(CLEAR_VARS)
LOCAL_MODULE := SerialPort
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)
LOCAL_CERTIFICATE := PRESIGNED
LOCAL_SRC_FILES := $(LOCAL_MODULE)$(COMMON_ANDROID_PACKAGE_SUFFIX)
LOCAL_PROPRIETARY_MODULE := true
include $(BUILD_PREBUILT)
# commond apk end  ####################################################

