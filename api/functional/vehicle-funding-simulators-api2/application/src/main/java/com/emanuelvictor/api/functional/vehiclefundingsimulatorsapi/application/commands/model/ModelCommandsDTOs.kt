package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.model

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.brand.BrandCommandInput
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.brand.BrandCommandOutput

@JvmRecord
data class ModelCommandInput(val name: String, val brand: BrandCommandInput)

@JvmRecord
data class ModelCommandOutput(val name: String, val brand: BrandCommandOutput)
