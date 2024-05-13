package com.ibenabdallah.motus.data

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type;


class TextConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        if (type === String::class.java) {
            return PlainTextConverter.INSTANCE
        }
        return null
    }

    private class PlainTextConverter : Converter<ResponseBody, String> {
        override fun convert(value: ResponseBody): String {
            val text = value.string()
            return text
        }

        companion object {
            val INSTANCE: PlainTextConverter = PlainTextConverter()
        }
    }
}