package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters;

record ObjectThatBeConvertedFromObjectToConvert(String name, Integer code) {
}

record ObjectWithCodeAndDescription(String name, Integer code, String description) {
}

record ObjectWithoutCodeButWithTangamandapil(String name, String tangamandapil) {
}

record CompletlyDifferentObject(String xiforinpola, Integer churrumimos, String tangamandapil) {
}

record RootObjectToConvert(String name, Integer code, TrunkObjectToConvert trunkObject) {
}

record TrunkObjectToConvert(String xiforimpola, Integer code, LeafObjectToConvert leafObject) {
}

record LeafObjectToConvert(String tangamandapil, Long id) {
}

record RootObjectConverted(String name, Integer code, TrunkObjectConverted trunkObject) {
}

record TrunkObjectConverted(String xiforimpola, Integer code, LeafObjectConverted leafObject) {
}

record LeafObjectConverted(String tangamandapil, Long id) {
}