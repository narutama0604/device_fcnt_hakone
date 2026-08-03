#
# Copyright (C) 2023 The Android Open Source Project
#
# SPDX-License-Identifier: Apache-2.0
#

DEVICE_PATH := device/fcnt/hakone

# Configure core_64_bit.mk
$(call inherit-product, $(SRC_TARGET_DIR)/product/core_64_bit_only.mk)

# Configure full_base_telephony.mk
$(call inherit-product, $(SRC_TARGET_DIR)/product/full_base_telephony.mk)

# Inherit common Lineage configurations
$(call inherit-product, vendor/lineage/config/common_full_phone.mk)

# Inherit device configurations
$(call inherit-product, $(DEVICE_PATH)/device.mk)

# Inherit from the proprietary version
$(call inherit-product, vendor/fcnt/hakone/hakone-vendor.mk)

# 2by2 stuff
PROCESSOR_INFO := Qualcomm Snapdragon 7s Gen 2
WITAQUA_MAINTAINER := kailua

## Device identifier
PRODUCT_DEVICE := hakone
PRODUCT_NAME := lineage_hakone
PRODUCT_BRAND := FCNT
PRODUCT_MODEL := M06
PRODUCT_MANUFACTURER := FCNT

# GMS
PRODUCT_GMS_CLIENTID_BASE := android-motorola

PRODUCT_BUILD_PROP_OVERRIDES += \
    BuildDesc="M06-user 16 V51RM45A M06.20260417 release-keys" \
    BuildFingerprint=FCNT/M06/M06:16/V51RM45A/M06.20260417:user/release-keys \
    DeviceProduct=M06
