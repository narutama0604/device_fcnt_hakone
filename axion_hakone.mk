#
# Copyright (C) 2023 The Android Open Source Project
#
# SPDX-License-Identifier: Apache-2.0
#

DEVICE_PATH := device/fcnt/hakone

# AudioFx
TARGET_INCLUDE_AXFX := true

# Axion stuff
TARGET_DISABLE_EPPE := true

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

# Axion stuff
AXION_PROCESSOR := Qualcomm_Snapdragon_7s_Gen_2
AXION_MAINTAINER := シマエナガ好き
AXION_CAMERA_REAR_INFO := 50,8
AXION_CAMERA_FRONT_INFO := 16

## Device identifier
PRODUCT_DEVICE := hakone
PRODUCT_NAME := axion_hakone
PRODUCT_BRAND := FCNT
PRODUCT_MODEL := M06
PRODUCT_MANUFACTURER := FCNT

# GMS
PRODUCT_GMS_CLIENTID_BASE := android-motorola

PRODUCT_BUILD_PROP_OVERRIDES += \
    BuildDesc="M06-user 16 V51RM45A M06.20260417 release-keys" \
    BuildFingerprint=FCNT/M06/M06:16/V51RM45A/M06.20260417:user/release-keys \
    DeviceProduct=M06
