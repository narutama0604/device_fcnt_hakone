#
# Copyright (C) 2023 The Android Open Source Project
#
# SPDX-License-Identifier: Apache-2.0
#

DEVICE_PATH := device/fcnt/M06

# Configure core_64_bit.mk
$(call inherit-product, $(SRC_TARGET_DIR)/product/core_64_bit_only.mk)

# Configure full_base_telephony.mk
$(call inherit-product, $(SRC_TARGET_DIR)/product/full_base_telephony.mk)

# Inherit common 2by2 configurations
$(call inherit-product, vendor/2by2/config/common_full_phone.mk)

# Inherit device configurations
$(call inherit-product, $(DEVICE_PATH)/device.mk)

# Inherit from the proprietary version
$(call inherit-product, vendor/fcnt/M06/M06-vendor.mk)

## Device identifier
PRODUCT_DEVICE := M06
PRODUCT_NAME := M06
PRODUCT_BRAND := FCNT
PRODUCT_MODEL := M06
PRODUCT_MANUFACTURER := FCNT

CUSTOM_PROCESSOR_INFO := Qualcomm Snapdragon 7s Gen 2

# GMS
PRODUCT_GMS_CLIENTID_BASE := android-motorola

PRODUCT_BUILD_PROP_OVERRIDES += \
    BuildDesc="qssi-user 15 V31RM50D 10636 release-keys" \
    BuildFingerprint=FCNT/M06/M06:15/V31RM50D/M06.20250711:user/release-keys \
    DeviceProduct=M06
