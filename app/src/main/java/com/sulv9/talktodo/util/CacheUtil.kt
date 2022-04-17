package com.sulv9.talktodo.util

import android.os.Parcelable
import android.text.TextUtils
import com.sulv9.talktodo.MainApp
import com.tencent.mmkv.MMKV

class CacheUtil {
    companion object {

        init {
            val rootDir = MMKV.initialize(MainApp.context)
        }

        fun put(key: String, value: Any?): Boolean {
            if (TextUtils.isEmpty(key)) {
                return false
            }
            if (value == null) {
                MMKV.defaultMMKV().removeValueForKey(key)
                return false
            } else {
                when (value) {
                    is Int -> {
                        MMKV.defaultMMKV().encode(key, value)
                        return true
                    }
                    is String -> {
                        MMKV.defaultMMKV().encode(key, value)
                        return true
                    }
                    is Double -> {
                        MMKV.defaultMMKV().encode(key, value)
                        return true
                    }
                    is Float -> {
                        MMKV.defaultMMKV().encode(key, value)
                        return true
                    }
                    is ByteArray -> {
                        MMKV.defaultMMKV().encode(key, value)
                        return true
                    }
                    is Boolean -> {
                        MMKV.defaultMMKV().encode(key, value)
                        return true
                    }
                    is Long -> {
                        MMKV.defaultMMKV().encode(key, value)
                        return true
                    }
                    is Parcelable -> {
                        MMKV.defaultMMKV().encode(key, value)
                        return true
                    }
                    else -> {
                        return false
                    }
                }
            }
        }

        @Suppress("UNCHECKED_CAST")
        fun <T> get(key: String, defaultValue: T): T {
            if (MMKV.defaultMMKV().containsKey(key)) {
                when (defaultValue) {
                    is Int -> {
                        return MMKV.defaultMMKV().decodeInt(key, defaultValue) as T
                    }
                    is String -> {
                        return MMKV.defaultMMKV().decodeString(key, defaultValue) as T
                    }
                    is Double -> {
                        return MMKV.defaultMMKV().decodeDouble(key, defaultValue) as T
                    }
                    is Float -> {
                        return MMKV.defaultMMKV().decodeFloat(key, defaultValue) as T
                    }
                    is ByteArray -> {
                        return MMKV.defaultMMKV().decodeBytes(key, defaultValue) as T
                    }
                    is Boolean -> {
                        return MMKV.defaultMMKV().decodeBool(key, defaultValue) as T
                    }
                    is Long -> {
                        return MMKV.defaultMMKV().decodeLong(key, defaultValue) as T
                    }
                    else -> {
                        return defaultValue
                    }
                }
            } else {
                return defaultValue
            }
        }

        fun <T : Parcelable> get(
            key: String,
            tClass: Class<T>
        ): T? {
            return MMKV.defaultMMKV().decodeParcelable(key, tClass)
        }

        fun <T : Parcelable> get(
            key: String,
            tClass: Class<T>,
            defaultValue: T
        ): T? {
            return MMKV.defaultMMKV().decodeParcelable(key, tClass, defaultValue)
        }
    }
}