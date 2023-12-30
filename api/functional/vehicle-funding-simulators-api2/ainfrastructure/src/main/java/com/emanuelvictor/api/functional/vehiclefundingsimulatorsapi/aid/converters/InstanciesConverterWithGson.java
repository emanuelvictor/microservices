package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.aid.converters;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class InstanciesConverterWithGson {

    public static final Logger LOGGER = LoggerFactory.getLogger(InstanciesConverterWithGson.class);

    public static <Out, In> Out tryConvertInstances(Class<Out> classOfOutputObject, In objectToConvert) {
        final String json = inputToJson(objectToConvert);
        return jsonToOutput(classOfOutputObject, json);
    }

    public static <In> String inputToJson(In input) {
        final var gson = new Gson();
        final var json = gson.toJson(input);
        LOGGER.info("Converting " + input.getClass() + " to json: " + json);
        return json;
    }

    public static <Out> Out jsonToOutput(Class<Out> classOfOutputObject, String json) {
        final var gson = new Gson();
        final var output = gson.fromJson(json, classOfOutputObject);
        LOGGER.info("Converting " + json + " to output instance " + output.getClass());
        return output;
    }
}
