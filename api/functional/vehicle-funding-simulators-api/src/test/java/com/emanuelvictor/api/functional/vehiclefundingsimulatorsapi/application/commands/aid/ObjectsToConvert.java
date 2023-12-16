package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid;

record ObjectThatBeConvertedFromObjectToConvert(String name, Integer code) {
}

record ObjectWithCodeAndDescription(String name, Integer code, String description) {
}

record ObjectWithoutCodeButWithTangamandapil(String name, String tangamandapil) {
}

record CompletlyDifferentObject(String xiforinpola, Integer churrumimos, String tangamandapil) {
}
