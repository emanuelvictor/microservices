package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.brand

@JvmRecord
data class BrandCommandInput(val name: String)

@JvmRecord
data class BrandCommandOutput(val name: String)